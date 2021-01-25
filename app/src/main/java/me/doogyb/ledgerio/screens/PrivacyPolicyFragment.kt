package me.doogyb.ledgerio.screens

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.doogyb.ledgerio.R

private const val TAG = "PrivacyPolicy"

class PrivacyPolicyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.privacy_policy_fragment, container, false)
        val textView = view.findViewById(R.id.privacy_textview) as TextView

        val privacyPolicyHtmlString = getString(R.string.privacy_policy)
        textView.text = Html.fromHtml(privacyPolicyHtmlString, Html.FROM_HTML_MODE_COMPACT)
        textView.movementMethod = LinkMovementMethod.getInstance()

        return view
    }
}