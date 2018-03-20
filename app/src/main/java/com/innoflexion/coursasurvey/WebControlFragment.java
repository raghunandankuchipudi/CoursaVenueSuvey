package com.innoflexion.coursasurvey;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebControlFragment extends Fragment implements View.OnTouchListener, Handler.Callback {

    private static final int CLICK_ON_WEB_VIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private static final String TAG = "WebControl";

    private final Handler handler = new Handler(this);

    private View view;
    private WebView webView;
    private WebViewClient client;
    private ProgressDialog pd;
    private Context mContext;

    public WebControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        mContext = getActivity();
        pd = new ProgressDialog(mContext);
        pd.setMessage(getString(R.string.progress_bar_message));
        pd.show();

        webView = (WebView) view.findViewById(R.id.web);

        webView.setOnTouchListener(this);

        client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!pd.isShowing()) {
                    pd.show();
                }
                handler.sendEmptyMessage(CLICK_ON_URL);
                Log.v(TAG, "shouldOverrideUrlLoading -> " + url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.v(TAG, "onPageStarted -> " + url);
            }

            @SuppressLint("JavascriptInterface")
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Log.v(TAG, "onPageFinished -> " + url);

                /*We can able to input the values to web page*/
               /* final String name = "Raghu", email = "kuchipudiraghu@gmail.com", message = "Testing web page", verification = null;
                final String js = "javascript:(function(){" +
                        "document.getElementById('nf-field-12').value = '" + name + "';" +
                        "document.getElementById('nf-field-13').value = '" + email + "';" +
                        "document.getElementById('nf-field-14').value = '" + message + "';" +
                        "document.getElementById('nf-field-15').value = '" + verification + "';" +
                        "document.getElementById('nf-field-16').value = 'submit button';" +
                        "c=document.getElementById('nf-field-16');" +
                        "c.dispatchEvent(e);" +
                        "})()";
                //click()

                if (Build.VERSION.SDK_INT >= 21) {

                    view.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            Log.v(TAG, "evaluateJavascript -> " + s);
                        }


                    });
                } else {
                    view.loadUrl(js);
                }*/
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.v(TAG, "onLoadResource -> " + url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                Log.v(TAG, "onPageCommitVisible -> " + url);
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                super.onUnhandledKeyEvent(view, event);
//                Log.v(TAG, "onUnhandledKeyEvent -> " + event.toString());
            }
        };

        webView.setWebViewClient(client);
        webView.setVerticalScrollBarEnabled(false);
        webView.setAddStatesFromChildren(true);
        webView.canGoBack();
        webView.canGoForward();
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.loadUrl(getString(R.string.url_web_view));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.web && event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.sendEmptyMessageDelayed(CLICK_ON_WEB_VIEW, 500);
        }
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL) {
            handler.removeMessages(CLICK_ON_WEB_VIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEB_VIEW) {
            //Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
