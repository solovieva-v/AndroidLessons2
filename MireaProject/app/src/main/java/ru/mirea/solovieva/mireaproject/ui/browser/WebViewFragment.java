package ru.mirea.solovieva.mireaproject.ui.browser;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.mirea.solovieva.mireaproject.R;
public class WebViewFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        webView = view.findViewById(R.id.webViewFragment);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com");

        return view;
    }
}
