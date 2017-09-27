package com.liewei.radish_job.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by murphy on 2017/9/18.
 */

public class UnZip {
    /**
     * 解压zip文件
     * @param zipFilePath  压缩文件路径名  E:/b/a.zip
     * @param base  解压文件存放路径  E:/b/c
     * @param deleteFile 是否删除压缩包
     * @return boolean 是否解压成功
     * */
    public static boolean unZip(String zipFilePath, String base, boolean deleteFile) {
        long t = System.currentTimeMillis();
        Log.e("time","time:"+t);
        try {
            File file = new File(zipFilePath);
            if (!file.exists()) {
                throw new RuntimeException("解压文件不存在!");
            }
            ZipFile zipFile = new ZipFile(file);
            Enumeration e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(base+ "/" + name);
                    f.mkdirs();
                } else {
                    File f = new File(base + zipEntry.getName());
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    InputStream is = zipFile.getInputStream(zipEntry);
                    FileOutputStream fos = new FileOutputStream(f);
                    int length = 0;
                    byte[] b = new byte[1024];
                    while ((length = is.read(b, 0, 1024)) != -1) {
                        fos.write(b, 0, length);
                    }
                    is.close();
                    fos.close();
                }
            }
            Log.e("time","time:"+(System.currentTimeMillis()-t));
            if (zipFile != null) {
                zipFile.close();
            }
            if (deleteFile) {
                file.deleteOnExit();
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}
