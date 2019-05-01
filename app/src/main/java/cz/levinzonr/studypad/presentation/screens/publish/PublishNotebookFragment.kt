package cz.levinzonr.studypad.presentation.screens.publish


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.publish.steps.*
import cz.levinzonr.studypad.presentation.screens.selectors.language.LanguageSelectorDialog
import cz.levinzonr.studypad.presentation.screens.selectors.tag.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.selectors.topic.TopicSearchDialog
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversitySelectorViewModel
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PublishNotebookFragment : BaseFragment(), StepperFormListener, BaseStep.StepViewClickListener, BackButtonHandler {

    private val args: PublishNotebookFragmentArgs by navArgs()
    override val viewModel: PublishNotebookViewModel by viewModel { parametersOf(args.notebook, args.publishedId) }
    private val universitySelectorViewModel: UniversitySelectorViewModel by sharedViewModel()

    private val stepOne: BasicStep by inject { parametersOf(this) }
    private val stepTwo: AdditionalInfoStep by inject { parametersOf(this) }
    private val stepThree: DescriptionStep by inject { parametersOf(this) }
    private val stepFour: ConfirmationStep by inject { parametersOf(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepperForm
            .setup(this, listOf(stepOne, stepTwo, stepThree, stepFour))
            .displayStepButtons(false)
            .displayBottomNavigation(false)
            .includeConfirmationStep(false)
            .init()

    }


    override fun subscribe() {

        universitySelectorViewModel.universitySelectedEvent.observeNonNull(viewLifecycleOwner) {
            it?.handle { stepOne.setUniversity(it, true) }
        }

        viewModel.defaultStateObservable.observeNonNull(viewLifecycleOwner) { state ->
            state.stepOneDefaults.let(stepOne::setDefaultData)
            state.stepTwoDefaults?.let(stepTwo::setDefaultData)
            state.stepThreeDefaults?.let(stepThree::setDefaultData)
        }
    }


    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.setText(R.string.progress_publishing)
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.notebookTopicEt -> {
                showTopicSelector()
            }
            R.id.notebookLanguageEt -> {
                showLanguageSelector()
            }
            R.id.notebookSchoolEt -> {
                showUniversitySelector()
            }
            else -> {
                showTagSelector()
            }

        }
    }

    override fun showError(viewError: ViewError) {
        super.showError(viewError)
        stepperForm.cancelFormCompletionOrCancellationAttempt()
    }

    override fun showNetworkUnavailableError() {
        showSimpleDialog(getString(R.string.error_network_title),getString(R.string.error_network_message))
        stepperForm.cancelFormCompletionOrCancellationAttempt()
    }

    override fun onCompletedForm() {
        viewModel.publish(stepOne.stepData, stepTwo.stepData, stepThree.stepData)
    }

    override fun onCancelledForm() {

    }

    private fun showTagSelector() {
        TagSearchDialog.show(childFragmentManager, stepTwo.stepData.tags.toSet()) { value, enable ->
            stepTwo.toggleTag(value, enable)
        }
    }

    private fun showUniversitySelector() {
        viewModel.onSelectUniversityClicked()
    }

    private fun showTopicSelector() {
        TopicSearchDialog.show(childFragmentManager) {
            stepTwo.setTopic(it)
        }
    }

    private fun showLanguageSelector() {
        LanguageSelectorDialog.show(childFragmentManager) {
            stepOne.setLanguage(it)
        }
    }

    override fun handleBackButton() {
        if (stepperForm.openStepPosition == 0) {
            baseActivity?.navigateBack()
        } else
            stepperForm.goToPreviousStep(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stepperForm.cancelForm()
    }
}
