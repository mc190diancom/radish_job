package com.liewei.radish_job.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.liewei.radish_job.R;


/**
 * @author shiner
 */
public class WebViewController extends LinearLayout {

	private View backBtn;
	private View forwardBtn;
	private View stopBtn;
	private View refreshBtn;
	private View outBtn;
	private ProgressBar progressBar;
	private WebView webView;
	private String firstUrl;

	public WebViewController(Context context) {
		super(context);
		init();
	}

	public WebViewController(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setFocusable(false);

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.web_controller, this, true);
		backBtn = findViewById(R.id.web_back);
		forwardBtn = findViewById(R.id.web_forward);
		outBtn = findViewById(R.id.web_out);
		outBtn.setVisibility(View.INVISIBLE);
		progressBar = (ProgressBar) findViewById(R.id.web_progress);
		refreshBtn = findViewById(R.id.web_refresh);
		stopBtn = findViewById(R.id.web_stop);
	}

	public View getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(View backBtn) {
		this.backBtn = backBtn;
	}

	public View getForwardBtn() {
		return forwardBtn;
	}

	public void setForwardBtn(View forwardBtn) {
		this.forwardBtn = forwardBtn;
	}

	public View getStopBtn() {
		return stopBtn;
	}

	public void setStopBtn(View stopBtn) {
		this.stopBtn = stopBtn;
	}

	public View getRefreshBtn() {
		return refreshBtn;
	}

	public void setRefreshBtn(View refreshBtn) {
		this.refreshBtn = refreshBtn;
	}

	public View getOutBtn() {
		return outBtn;
	}

	public void setOutBtn(View outBtn) {
		this.outBtn = outBtn;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView, String firstUrl) {
		this.webView = webView;
		this.firstUrl = firstUrl;
		setUpBtn();
	}

	private void setUpBtn() {
		if (backBtn != null) {
			backBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (webView.canGoBack()) {
						webView.goBack();
					}
				}
			});
		}
		if (forwardBtn != null) {
			forwardBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (webView.canGoForward()) {
						webView.goForward();
					}
				}
			});
		}
		if (stopBtn != null) {
			stopBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					webView.stopLoading();
				}
			});
		}
		if (refreshBtn != null) {
			refreshBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(webView.getOriginalUrl())) {
						webView.loadUrl(firstUrl);
					} else {
						webView.reload();
					}
				}
			});
		}
		if (outBtn != null) {
			outBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = webView.getUrl();
					if (TextUtils.isEmpty(url) || url.contains("data:text/html")) {
						url = firstUrl;
					}
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					webView.getContext().startActivity(intent);
				}
			});
		}
	}

	public void onlyProgress() {
		findViewById(R.id.web_controller_layout).setVisibility(View.GONE);
	}
}
