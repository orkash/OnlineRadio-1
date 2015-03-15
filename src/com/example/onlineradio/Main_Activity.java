package com.example.onlineradio;

import android.os.Bundle;
import android.os.Environment;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.onlineradio.R;

public class Main_Activity extends Activity{

	 private ProgressBar playSeekBar;

	    private Button buttonPlay;

	    private Button buttonStopPlay;

	    private MediaPlayer player;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);

	        initializeUIElements();

	        initializeMediaPlayer();
	    }

	    private void initializeUIElements() {

	        playSeekBar = (ProgressBar) findViewById(R.id.progressBar1);
	        playSeekBar.setMax(100);
	        playSeekBar.setVisibility(View.INVISIBLE);

	        buttonPlay = (Button) findViewById(R.id.buttonPlay);
	        buttonPlay.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					 startPlaying();
				}
			});

	        buttonStopPlay = (Button) findViewById(R.id.buttonStopPlay);
	        buttonStopPlay.setEnabled(false);
	        buttonStopPlay.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					 stopPlaying();
				}
			});

	    }

	    private void startPlaying() {
	        buttonStopPlay.setEnabled(true);
	        buttonPlay.setEnabled(false);

	        playSeekBar.setVisibility(View.VISIBLE);

	        player.prepareAsync();

	    }

	    private void stopPlaying() {
	        if (player.isPlaying()) {
	            player.stop();
	            player.reset();
	            initializeMediaPlayer();
	        }

	        buttonPlay.setEnabled(true);
	        buttonStopPlay.setEnabled(false);
	        playSeekBar.setVisibility(View.INVISIBLE);
	    }

	    private void initializeMediaPlayer() {
	    	
	    	player = new MediaPlayer();
	        try {
	        	//player.setDataSource("http://radyoretro.com:5836/");
	        	String filePath = "/Users/selinozturk/Music/olga/06 - Undertow.mp3";
	        	player.setDataSource(filePath);
	        	
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (IllegalStateException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        player.setOnPreparedListener(new OnPreparedListener(){
	            public void onPrepared(MediaPlayer mp) {
	                     player.start();
	            } 
	        });

	        player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

	            public void onBufferingUpdate(MediaPlayer mp, int percent) {
	                playSeekBar.setSecondaryProgress(percent);
	                Log.i("Buffering", "" + percent);
	            }
	        });
	    }

	    @Override
	    protected void onPause() {
	        super.onPause();
	        if (player.isPlaying()) {
	            player.stop();
	        }
	    }
}
