package com.irrigai.mobile.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.DatabaseInitializer;

public class LandingActivity extends AppCompatActivity {

    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseInitializer.seedIfEmpty(this);
        setContentView(R.layout.activity_landing);

        setupVideoBackground();

        Button signUpButton = findViewById(R.id.btnSignUp);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, AuthActivity.class);
            startActivity(intent);
        });
    }

    private void setupVideoBackground() {
        PlayerView playerView = findViewById(R.id.playerView);
        Uri videoUri = null;
        try {
            getAssets().open("video/farm.mp4").close();
            videoUri = Uri.parse("file:///android_asset/video/farm.mp4");
        } catch (Exception ignored) {}
        if (videoUri == null) {
            int rawId = getResources().getIdentifier("farm", "raw", getPackageName());
            if (rawId != 0) videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
        }
        if (videoUri != null) {
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);
            player.setVolume(0f);
            playerView.setVisibility(View.VISIBLE);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            player.setMediaItem(mediaItem);
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
            player.prepare();
            player.setPlayWhenReady(true);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }
}
