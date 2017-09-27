package com.liewei.radish_job.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


import com.liewei.radish_job.R;
import com.liewei.radish_job.views.WebViewController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUtil {
	protected static final String TAG = "WebUtil";
	private static Activity mContext;

	public static void wrapWebView(final WebView webView, WebViewController ctrl) {
		setUpWebView(webView, ctrl, null,null);
		setBtnOnState(webView, ctrl);
	}

	public static void wrapWebView(final WebView webView, WebViewController ctrl, List<HandleCallback> handlers) {
		setUpWebView(webView, ctrl, handlers,null);
		setBtnOnState(webView, ctrl);
	}

	public static void wrapWebView(final WebView webView, WebViewController ctrl, Activity context) {
		setUpWebView(webView, ctrl, null,context);
		setBtnOnState(webView, ctrl);
	}

	private static void setBtnOnState(WebView webView, WebViewController ctrl) {
		ctrl.getBackBtn().setEnabled(webView.canGoBack());
		ctrl.getForwardBtn().setEnabled(webView.canGoForward());
		int progress = webView.getProgress();
		boolean finish = progress == 100;
		ctrl.getStopBtn().setVisibility(finish ? View.GONE : View.VISIBLE);
		ctrl.getRefreshBtn().setVisibility(finish ? View.VISIBLE : View.GONE);

		if (progress != 100) {
			if (ctrl.getProgressBar() != null) {
				ctrl.getProgressBar().setVisibility(View.VISIBLE);
				ctrl.getProgressBar().setMax(100);
				ctrl.getProgressBar().setProgress(progress);
			}
		} else {
			if (ctrl.getProgressBar() != null) {
				ctrl.getProgressBar().setVisibility(View.GONE);
			}
		}
	}

	@SuppressLint({"NewApi", "JavascriptInterface"})
	private static void setUpWebView(final WebView webView, final WebViewController ctrl,
                                     final List<HandleCallback> handlers,Activity mContext) {

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		if (hasHoneycomb()) {
			settings.setDisplayZoomControls(false);
		}
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setAllowFileAccess(true);
		settings.setPluginState(PluginState.ON);
		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		if(mContext != null){
			webView.addJavascriptInterface(new JavaScriptinterface(mContext), "gostart");
		}
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
				System.out.println("nnnn:" + view.getUrl());
				return false;
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				Context ctx = view.getContext();
				if (ctx instanceof Activity) {
					TextView titleTv = (TextView) ((Activity) ctx).findViewById(R.id.title);
					if (titleTv != null) {
						// titleTv.setText(title);
					}
				}
				super.onReceivedTitle(view, title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				setBtnOnState(webView, ctrl);
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.contains("http://www.luobosp.com/index.php")){
					view.loadUrl(url);
					return true;
				}else{
					return false;
				}

		}

		});

		/*webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
				Log.i("tag", "url=" + url);
				Log.i("tag", "userAgent=" + userAgent);
				Log.i("tag", "contentDisposition=" + contentDisposition);
				Log.i("tag", "mimetype=" + mimetype);
				Log.i("tag", "contentLength=" + contentLength);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				webView.getContext().startActivity(intent);
			}
		});*/

	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public interface HandleCallback {
		public boolean handle(String url);

		public boolean accept(String url);
	}

	/*public static String appendParams(String url) {
		if (TextUtils.isEmpty(url)) {
			return url;
		}
		UserPreference prefs = new UserPreference(App.self);
		String firstSplit = null;
		if (url.contains("?")) {
			firstSplit = "&";
		} else {
			firstSplit = "?";
		}
		url += firstSplit + "pop_code=" + prefs.getString("pop_code", "") + "&user_id=" + prefs.getId();
		return url;
	}*/

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 *
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> getParams(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 *
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String truncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

}
