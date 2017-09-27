package com.liewei.radish_job.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class XPreference {
	private static final String DEFAULT_SP_NAME = "prefs_def";

	private String mSpName;
	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public XPreference(String mSpName, Context mContext) {
		super();
		this.mContext = mContext;
		if (TextUtils.isEmpty(mSpName)) {
			this.mSpName = DEFAULT_SP_NAME;
		} else {
			this.mSpName = mSpName;
		}
	}

	public boolean hasKey(Context context, final String key) {
		return getPreferences().contains(key);
	}

	public String getString(String key, String defaultValue) {
		return getPreferences().getString(key, defaultValue);
	}

	public boolean setString(String key, String value) {
		return getPreferences().edit().putString(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return getPreferences().getBoolean(key, defaultValue);
	}

	public boolean setBoolean(String key, boolean value) {
		return getPreferences().edit().putBoolean(key, value).commit();
	}

	public int getInt(String key, int defaultValue) {
		return getPreferences().getInt(key, defaultValue);
	}

	public boolean setInt(String key, int value) {
		return getPreferences().edit().putInt(key, value).commit();
	}

	public float getFloat(String key, float defaultValue) {
		return getPreferences().getFloat(key, defaultValue);
	}

	public boolean setFloat(String key, float value) {
		return getPreferences().edit().putFloat(key, value).commit();
	}

	public void setDouble(String key, double value) {
		getPreferences().edit().putLong(key, Double.doubleToRawLongBits(value))
				.commit();
	}

	public double getDouble(String key, double defaultValue) {
		return Double.longBitsToDouble(getPreferences().getLong(key,
				Double.doubleToLongBits(defaultValue)));
	}

	public long getLong(String key, long defaultValue) {
		return getPreferences().getLong(key, defaultValue);
	}

	public boolean setLong(String key, long value) {
		return getPreferences().edit().putLong(key, value).commit();
	}

	public boolean clearPreference() {
		return getPreferences().edit().clear().commit();
	}

	/**
	 * 存储是不是第一次进入
	 * @param key
	 * @return
	 */
	public void setIsFirstIn(String key, boolean isFirstIn) {
		getPreferences().edit().putBoolean(key, isFirstIn).commit();
	}
	
	public boolean getIsFirstIn(String key, boolean defValue) {
		return getPreferences().getBoolean(key, defValue);
	}

	public void setDisableNu(String key, String disableNu) {
		getPreferences().edit().putString(key, disableNu).commit();
	}

	public String getDisableNu(String key, String disableNu) {
		return getPreferences().getString(key, disableNu);
	}

	private SharedPreferences getPreferences() {
		if (mSharedPreferences == null) {
			mSharedPreferences = mContext.getSharedPreferences(mSpName,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences;
	}
}
