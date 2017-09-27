package com.liewei.radish_job;

import android.app.Application;

import com.liewei.radish_job.util.Config;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;


public class App extends Application {
	public static App self;

	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
		WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://"+ Config.APP_ID+".wilddogio.com").build();
		WilddogApp.initializeApp(App.self, options);
	}
}
