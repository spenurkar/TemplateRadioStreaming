package com.radiostreaming.app;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button btnStart, btnStop;
	private MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		player = new MediaPlayer();
		try {
			player.setDataSource("http://listen.radionomy.com/one-love-hip-hop-radio");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				
				Log.d("TemplateRadioStreaming", "MainActivity.initMediaPlayer().new OnBufferingUpdateListener() {...}: "+percent);
				
			}
		});
	}

	private void init() {
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
	}

	 @Override
	    protected void onPause() {
	        super.onPause();
	        if (player.isPlaying()) {
	            player.stop();
	        }
	    }
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.btnStart:
			startPlaying();
			break;
		case R.id.btnStop:
			stopPlaying();
			break;
		default:
			break;
		}
	}
	
	private void startPlaying() {
        btnStop.setEnabled(true);
        btnStart.setEnabled(false);

        //playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();

        player.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });

    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initMediaPlayer();
        }

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        //playSeekBar.setVisibility(View.INVISIBLE);
    }

}
