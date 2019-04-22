package cz.levinzonr.studypad.presentation.screens.challenges

import cz.levinzonr.studypad.domain.models.ChallengeEntry
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.repository.ChallengesRepository
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState

class ChallengesOverviewViewModel(private val repository: ChallengesRepository) : BaseViewModel()  {

    val recentChallenges = repository.getRecentlyCompleted()


    fun onConfigureChallengeClicked() {
        navigateTo(ChallengesFragmentDirections.actionChallengesFragmentToSetupChallengeFragment())
    }

    fun onRecentChallengeClicked(challengeEntry: ChallengeEntry) {
        val setup = SetupChallengeViewState(challengeEntry.type, challengeEntry.titleFirst, challengeEntry.shuffled, challengeEntry.notebook)
        navigateTo(ChallengesFragmentDirections.actionChallengesFragmentToChallengeActivity(setup))
    }

    fun onLuanchQuickChallenge(type: ChallengeType, notebook: Notebook) {
        val setup = SetupChallengeViewState(type, notebook = notebook)
        navigateTo(ChallengesFragmentDirections.actionChallengesFragmentToChallengeActivity(setup))
    }
}