package com.group2.swinghelper;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements View.OnClickListener,SurfaceHolder.Callback {

    private Button btnStartRec;
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    private int randomNum;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view001 = inflater.inflate(R.layout.fragment_video,container,false);
        recorder = new MediaRecorder();
        initRecorder();
        btnStartRec = (Button) view001.findViewById(R.id.btnCaptureVideo);
        btnStartRec.setOnClickListener(this);
        SurfaceView cameraView = (SurfaceView)view001.findViewById(R.id.surfaceCamera);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);

        return view001;
    }



    @SuppressLint({ "SdCardPath", "NewApi" })
    private void initRecorder() {

        Random rn = new Random();
        int maximum = 10000000;
        int minimum = 00000001;
        int range = maximum  - minimum  + 1;
        randomNum =  rn.nextInt(range) + minimum + 1 - 10;

        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            recorder.setOrientationHint(90);//plays the video correctly
        }else{
            recorder.setOrientationHint(180);
        }


        recorder.setOutputFile("/sdcard/MediaAppVideos/"+randomNum+".mp4");

    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            //finish();
        } catch (IOException e) {
            e.printStackTrace();
            //finish();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCaptureVideo:
                try{
                    if (recording) {
                        recorder.stop();
                        recording = false;
                        // Let's initRecorder so we can record again
                        //initRecorder();
                        //prepareRecorder();
                    } else {
                        recording = true;
                        recorder.start();
                    }

                }catch(Exception e){

                }

            default:
                break;

        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            if (recording) {
                recorder.stop();
                recording = false;
            }
            recorder.release();
            // finish();
        } catch (Exception e) {

        }

    }
}
