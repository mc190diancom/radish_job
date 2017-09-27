package com.liewei.radish_job.async;

import android.content.Context;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.EOFException;
import java.net.SocketTimeoutException;
import java.util.Locale;

/**
 * @author shiner
 */
@SuppressWarnings("deprecation")
public class ExceptionHandler {
	public static void handleException(Context ctx, Result<?> result) {
		if (result.getError() == Result.OK) {
			result.setError(-1);
		}
		Throwable t = result.getThrowable();
		if (t == null) {
			t = new DException("未知错误");
			result.setThrowable(t);
		}
		String clsName = t.getClass().getName();
		if (t instanceof DException) {
			result.setErrorMsg(t.getMessage());
		} else if (t instanceof SocketTimeoutException) {
			result.setErrorMsg("服务器响应超时，请稍后再试");
		} else if (t instanceof ConnectTimeoutException) {
			result.setErrorMsg("请求服务器超时，请稍后再试");
		}  else if (t.getClass().getName().toLowerCase(Locale.CHINA).contains("timeout")) {
			if (t.getMessage() != null && t.getMessage().contains("read")) {
				result.setErrorMsg("服务器响应超时，请稍后再试");
			} else {
				result.setErrorMsg("请求服务器超时，请检查网络");
			}
		} else if (t instanceof com.lidroid.xutils.exception.HttpException) {
			com.lidroid.xutils.exception.HttpException ex = (com.lidroid.xutils.exception.HttpException) t;
			result.setErrorMsg(getErrorMsg(ex.getExceptionCode()));
		} else if (clsName.startsWith("java.net") || clsName.startsWith("org.apache.http")
				|| t instanceof EOFException) {
			result.setErrorMsg("网络不稳定，请稍后再试");
		} else if (result.getErrorMsg() == null) {
			result.setErrorMsg("亲，出错了");
			//result.setErrorMsg("未知错误" + "\r\n" + t.getMessage());
		}
	}

	private static String getErrorMsg(int exceptionCode) {
		return "网络问题:" + exceptionCode;
	}
}
