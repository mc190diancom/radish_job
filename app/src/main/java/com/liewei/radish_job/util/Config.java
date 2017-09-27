package com.liewei.radish_job.util;

import android.os.Environment;

import com.liewei.radish_job.App;

/**
 * Created by murphy on 2017/9/8.
 */

public class Config {
    public final static String DIR_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Android/data/"+ App.self.getPackageName() + "/files/radish";
    public final static String DIR_PATH2 = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Android/data/"+ App.self.getPackageName() + "/files";
    public final static String SERVER_URL = "http://114.215.125.52/index.php/Home/";
    public final static String APP_ID = "wd1603956872hteziy";//野狗的appIdwd6379696906gnbwmc wd4276013757tdwzof
    public final static String VIDEO_ID = "wd8926362719hlnvhg";//野狗的appIdwd6379696906gnbwmc wd4276013757tdwzof

}
