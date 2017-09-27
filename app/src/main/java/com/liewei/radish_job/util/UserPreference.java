package com.liewei.radish_job.util;

import android.content.Context;

public class UserPreference extends XPreference {
	private final static String SP_NAME = "user";

	public UserPreference(Context mContext) {
		super(SP_NAME, mContext);
	}

	public void setId(String id) {
		setString("id", id);
	}

	public String getId() {
		return getString("id", "");
	}

	public String getUser() {
		return getString("login_user", "");
	}

	public String getMobile() {
		return getString("login_mobile", "");
	}

	public String getPwd() {
		return getString("login_pwd", "");
	}

	public void updateUser(String id, String login_user,String login_mobile,String login_pwd) {
		setString("id", id);
		setString("login_user", login_user);
		setString("login_mobile", login_mobile);
		setString("login_pwd", login_pwd);
	}
}
