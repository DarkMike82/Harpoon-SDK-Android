package com.amgrade.harpoonsdk;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * Created by ilya on 6/12/15.
 */
public class Popup extends DialogFragment {

    private static final String KEY_URL = "url";

    private String mUrl;

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private ImageButton mCloseButton;
    private View mProgressView;

    public Popup() {

    }

    public static void show(String url, FragmentManager manager) {
        Popup popup = new Popup();
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        popup.setArguments(args);
        popup.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopUpStyle);
        popup.show(manager, Popup.class.getSimpleName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(KEY_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);

        mWebView = (WebView) view.findViewById(R.id.popupWebView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.popupProgressIndicator);
        mProgressView = view.findViewById(R.id.popupProgressLayout);
        mCloseButton = (ImageButton) view.findViewById(R.id.popupCloseButton);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
                mProgressView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);
            }
        });
        mWebView.loadUrl(mUrl);

//        String content = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><head><body>";
//        String mydata="Hello world!";
//        content += mydata + "</body></html>";
//        mWebView.loadData(content, "text/html", "UTF-8");

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.stopLoading();
                dismiss();
            }
        });

        return view;
    }
}
