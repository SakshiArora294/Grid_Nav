package com.example.sheliza.grid_nav;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {

    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_web, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.loadUrl("http://www.gndec.ac.in/");
        return v;

    }
}
