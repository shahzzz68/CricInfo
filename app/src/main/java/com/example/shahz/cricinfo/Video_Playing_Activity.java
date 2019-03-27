package com.example.shahz.cricinfo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.shahz.cricinfo.Adapters.Video_Gallery_adapter;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class Video_Playing_Activity extends AppCompatActivity {

    MediaController controller;
    PlayerView videoView;
    SimpleExoPlayer player;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video__playing_activity);

        progressBar=findViewById(R.id.videoProgressBar);


        videoView=findViewById(R.id.playVideo);




//        if (controller== null)
//        {
//            controller= new MediaController(this);
//            controller.setAnchorView(videoView);
//        }
//        videoView.setVideoURI(Uri.parse(videoUri));
//        videoView.setMediaController(controller);
//        videoView.requestFocus();
//
//
//
//        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//            @Override
//            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//
//                if (i== MediaPlayer.MEDIA_INFO_BUFFERING_START)
//                {
//                    progressBar.setVisibility(View.VISIBLE);
//
//                }
//                else if (i== MediaPlayer.MEDIA_INFO_BUFFERING_END)
//                {
//                    progressBar.setVisibility(View.GONE);
//                    videoView.start();
//                }
//                return true;
//            }
//        });
//
//
//        videoView.start();
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                Toast.makeText(Video_Playing_Activity.this, "Video Completed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
   }

    @Override
    protected void onStart() {
        super.onStart();

        String videoUri=getIntent().getStringExtra(Video_Gallery_adapter.VIDEO_URI);

        player= ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        videoView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "CricInfo"));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUri));
// Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);


        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState== Player.STATE_BUFFERING)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        videoView.setPlayer(null);
        player.release();
        player=null;
    }
}
