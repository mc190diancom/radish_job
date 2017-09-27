package com.liewei.radish_job.wilddogutil;

import com.wilddog.video.LocalStream;

/**
 * Created by murphy on 2017/9/11.
 */

public class startCall {
    private static String uid;//通话接收方的uid，uid是wilddog为认证用户分配的唯一身份标识
    private static LocalStream localStream;//通话发起的本地媒体流
    private static String data;//用户

    public startCall(String uid, LocalStream localStream, String data){
        this.uid = uid;
        this.localStream = localStream;
        this.data = data;
    }

}
