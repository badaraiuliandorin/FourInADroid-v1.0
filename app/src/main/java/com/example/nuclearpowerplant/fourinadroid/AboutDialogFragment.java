package com.example.nuclearpowerplant.fourinadroid;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by NUCLEARPOWERPLANT on 06-May-17.
 */

public class AboutDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstance){
        View view = inflater.inflate(R.layout.dialog_fragment_about, container, false);

        WebView webView = (WebView)view.findViewById(R.id.about_text);
        webView.setVerticalScrollBarEnabled(true);

        String data = getString(R.string.html_for_about);
        getDialog().setTitle("About");
        webView.loadDataWithBaseURL(null, data, "text/html; charset=utf-8", "UTF-8", null);

        return view;
    }
}
