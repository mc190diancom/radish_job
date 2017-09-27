package com.liewei.radish_job.util;

/**
 * Created by murphy on 2017/9/18.
 */

public interface OnUpdateListner {
    void onUpdate(boolean isUpdate, boolean isqueryOk, String LOG, int length, String VSCODE);
}
