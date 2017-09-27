package com.liewei.radish_job.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateUtils {
	// 文件大小
	private int mLength = 7257912;

	String mVersion = "20170915";
    private int mProgress = 0;
	private Context mContext;
	private ProgressBar mProgressBar;
	private AlertDialog mDialog;

	public UpdateUtils(Context mContext) {
		this.mContext = mContext;
	}

	public void queryVersion(OnUpdateListner onupdate) {
		int version = 0;
		int SVersion = 20170916;//服务器的版本号
		try {
			JSONObject jsonObject = new JSONObject(reader());
			mVersion = jsonObject.optString("version");
			version = Integer.valueOf(mVersion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (version<Integer.valueOf(SVersion)) {
			onupdate.onUpdate(true, true, "正在加载资源包...", mLength, mVersion);
			//Onupdate.onUpdate(false, true, null, 0, null);
		} else {
			onupdate.onUpdate(false, true, null, 0, null);
		}
	}

	/*public void setOnUpdateCliclenser(OnUpdateClicklsener Onupdate) {
		this.Onupdate = Onupdate;
	}*/

	private String reader(){
		String pathRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		final File file = new File(pathRoot + "/Android/data/"
				+ mContext.getPackageName() + "/files/radish/config.txt");
		if(!file.exists()){
			return null;
		}
		BufferedReader bufferedReader=null;
		try {
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file),"utf-8");//考虑到编码格式
			bufferedReader = new BufferedReader(read);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String s = null,sum="";
		try {
			while((s = bufferedReader.readLine())!=null){//使用readLine方法，一次读一行
				sum += s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
		return sum;
	}

	private Handler handler = new Handler(Looper.getMainLooper()){
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch(what) {
				case 2:
					Toast.makeText(mContext,"下载完成,开始解压资源",Toast.LENGTH_SHORT).show();
					mDialog.dismiss();
					File file = new File(Config.DIR_PATH );
					if(file.exists()){
						file.delete();
					}

					break;
				case 1:
					int percent = (int)msg.arg1;
					mProgressBar.setProgress(percent);
					break;
			}
		};
	};

	/**
	 * 开启post请求下载apk
	 */
	public void DownLoadApk(final String url, ProgressBar progressBar,AlertDialog dialog) {
		mProgressBar = progressBar;
		mDialog = dialog;
		new Thread(new Runnable() {
			@Override
			public void run() {
				File file = new File(Config.DIR_PATH+".zip" );
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				InputStream is = null;
				FileOutputStream fos = null;
				try {
					URL realUrl = new URL(url);
					// 打开和URL之间的连接
					HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
					connection.setRequestMethod("POST");
					// 发送POST请求必须设置如下两行
					connection.setDoOutput(true);
					connection.setDoInput(true);
					// 设置通用的请求属性
					connection.setRequestProperty("accept", "*/*");
					connection.setRequestProperty("connection", "Keep-Alive");
					connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					connection.setConnectTimeout(12 * 1000);
					connection.setReadTimeout(12 * 1000);
					// 建立实际的连接
					// connection.connect();
					System.err.println("返回码为：" + mLength + "");
					// 获取文件大小
					// (这里到时候直接从接口中拉取文件大小)
					int length = mLength;
					// 创建输入流
					is = connection.getInputStream();
					fos = new FileOutputStream(file);
					int count = 0;
					int code = connection.getResponseCode();
					System.err.println("返回码为：" + code);
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						int progress = (int) (((float) count / length) * 100);
						if (mProgress != progress) {
							mProgress = progress;
							Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.arg1 = progress;
							handler.sendMessage(msg);
						}
						if (numread <= 0) {
							Message msg = handler.obtainMessage();
							msg.what = 2;
							handler.sendMessage(msg);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (true);// 点击取消就停止下载.
			/*if (!MsgConfig.isUpdate) {
				mNotifyManager.cancel(NOTIFICATION_ID);
				if (mProgress < 99) {
					fileIsdelete(versioin);
				}
				stopSelf();
			}*/
				} catch (Exception e) {
					System.out.println("发送GET请求出现异常！" + e);
					e.printStackTrace();
					if (mProgress < 99) {
						fileIsdelete();
					}
				}
				// 使用finally块来关闭输入流
				finally {
					try {
						if (is != null) {
							is.close();
						}
						if (fos != null) {
							fos.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		}).start();
	}

	public boolean fileIsdelete() {

		try {
			File f = new File(Config.DIR_PATH + ".zip");
			if (!f.exists()) {
				return false;
			}
			f.delete();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
