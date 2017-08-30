package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/26/17.
 */

public class WebViewActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.web_view_activity);
            WebView webView = (WebView) findViewById(R.id.web_view_container);

            final ProgressBar pbar;
            pbar = (ProgressBar) findViewById(R.id.pB1);

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if(progress < 100 && pbar.getVisibility() == ProgressBar.GONE){
                        pbar.setVisibility(ProgressBar.VISIBLE);
                    }

                    pbar.setProgress(progress);
                    if(progress == 100) {
                        pbar.setVisibility(ProgressBar.GONE);
                    }
                }
            });

            String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
            webView.getSettings().setUserAgentString(newUA);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadUrl(getIntent().getExtras().getString("url"));


        }
}
