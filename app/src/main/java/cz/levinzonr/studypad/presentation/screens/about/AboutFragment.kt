package cz.levinzonr.studypad.presentation.screens.about


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import cz.levinzonr.studypad.BuildConfig

import cz.levinzonr.studypad.R
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appVersionTv.text = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        aboutLicenceBtn.setOnClickListener {
            startActivity( Intent(context, OssLicensesMenuActivity::class.java))
        }

        aboutFeedbackBtn.setOnClickListener {
            findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToSendFeedbackFragment())
        }

    }


}