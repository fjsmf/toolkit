package ss.com.toolkit.record;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
~ Nilesh Deokar @nieldeokar on 09/18/18 6:25 PM
*/

import java.io.File;
import java.io.FileInputStream;

import ss.com.toolkit.R;

public class AudioRecordActivity extends AppCompatActivity implements View.OnClickListener {

    AudioRecording mAudioRecording;

    MediaPlayer player;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_record_actitity);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        Button btnPlay = (Button) findViewById(R.id.btnplay);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        mAudioRecording = new AudioRecording();

    }

    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void startRecording() {
        if (!checkPermission(Manifest.permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            return;
        }
        AudioRecording.OnAudioRecordListener onRecordListener = new AudioRecording.OnAudioRecordListener() {
            @Override
            public void onRecordFinished() {
                Log.d("nadiee","onFinish ");
            }

            @Override
            public void onError(int e) {
                Log.d("nadiee","onError "+e);
            }

            @Override
            public void onRecordingStarted() {
                Log.d("nadiee","onStart ");

            }
        };
        filePath = new File(Environment.getExternalStorageDirectory(),"Recorder")  + "/" + System.currentTimeMillis() + ".aac";
//        filePath = new File(getFilesDir(), "/record/" +  System.currentTimeMillis() + ".aac").getPath();
        mAudioRecording.setOnAudioRecordListener(onRecordListener);
        mAudioRecording.setFile(filePath);
        mAudioRecording.startRecording();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            startRecording();
        }
    }

    private void stopRecording() {
        if( mAudioRecording != null){
            mAudioRecording.stopRecording(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                startRecording();
                break;
            case R.id.btnStop:
                stopRecording();
                break;
            case R.id.btnplay:
                play();
                break;
        }
    }

    private void play() {
        try {
            player = new MediaPlayer();
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.i("nadiee", "onPrepared" );
                    mediaPlayer.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.i("nadiee", "onCompletion then release player");
                    mediaPlayer.release();
                }
            });
            Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            File tempFile = new File(filePath);
            Log.i("nadiee", "audio file exist:"+tempFile.exists() + ", path:"+tempFile.getPath());
            FileInputStream fis = new FileInputStream(tempFile);
            player.setDataSource(fis.getFD());
//            player.setDataSource(this, uri);
            player.prepareAsync();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
