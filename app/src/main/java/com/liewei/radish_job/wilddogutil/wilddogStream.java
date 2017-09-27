package com.liewei.radish_job.wilddogutil;

import com.wilddog.video.LocalStream;
import com.wilddog.video.LocalStreamOptions;
import com.wilddog.video.WilddogVideo;
import com.wilddog.video.WilddogVideoView;

/**
 * Created by murphy on 2017/9/10.
 */

public  class wilddogStream {
    private  WilddogVideo video;//通过用户信息初始化的video
    private  WilddogVideoView localView;//用于播放视频的控件
    private  LocalStream localStream;

    public wilddogStream(WilddogVideo video){
        this.video = video;
    }

    public wilddogStream(WilddogVideo video,WilddogVideoView localView){
        this.video = video;
        this.localView = localView;
    }

    /**
     * TODO 初始化视频流
     */
    public LocalStream init(){
        LocalStreamOptions.Builder builder = new LocalStreamOptions.Builder();
        builder.maxFps(30);
        LocalStreamOptions options = builder.captureAudio(true).captureVideo(true).dimension(LocalStreamOptions.Dimension.DIMENSION_720P).build();//maxFps(30).build();
        localStream = video.createLocalStream(options);
        return localStream;
    }

    /**
     * TODO 开始播放
     */
    public  void play(){
        localStream.attach(localView);
    }

    /**
     * TODO 停止播放
     */
    public void stop(){
        localStream.detach();
    }
}
