package com.example.exoplayeryt;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long POLL_INTERVAL_MS = 1;
    PlayerView playerView;
    SimpleExoPlayer player;
    ConcatenatingMediaSource concatenatingMediaSource;

    String videoTitle;
    int position;
    TextView title;
    ImageView btn_next,btn_prev;
    Button quality;

    private boolean isShowingTrackSelectionDialog;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @SuppressLint({"MissingInflatedId", "StaticFieldLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_exo_player);
        getSupportActionBar().hide();


        btn_next=findViewById(R.id.exo_next);
        btn_prev=findViewById(R.id.exo_prev);
        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);
        playerView = findViewById(R.id.exoplayer);
        videoTitle = getIntent().getStringExtra("title");
        title = findViewById(R.id.videotitle);
        title.setText(videoTitle);

        quality = findViewById(R.id.btnQuality);


        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isShowingTrackSelectionDialog
                        && TrackSelectionDialog.willHaveContent(player)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog =
                            TrackSelectionDialog.createForPlayer(player, dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), null);
                }


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExoPlayerActivity.this);
                alertDialog.setTitle("AlertDialog");
                String[] items = {"144p","240p","360p","420p","720p","1080p","Auto Select"};
                int checkedItem = getIntent().getIntExtra("item",4);
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent.putExtra("link", getIntent().getStringExtra("link"));
//                                intent.putExtra("quality", "160");
                                intent.putExtra("quality", "worst");
                                intent.putExtra("item", 0);
                                intent.putExtra("position", player.getCurrentPosition());
                                startActivity(intent);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 144p", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Intent intent240 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent240.putExtra("link", getIntent().getStringExtra("link"));
                                intent240.putExtra("quality", "133/140");
                                intent240.putExtra("item", 1);
                                intent240.putExtra("position", player.getCurrentPosition());
                                startActivity(intent240);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 240p", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Intent intent1 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent1.putExtra("link", getIntent().getStringExtra("link"));
                                intent1.putExtra("quality", "134/140");
                                intent1.putExtra("item", 2);
                                intent1.putExtra("position", player.getCurrentPosition());
                                startActivity(intent1);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 360p", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                Intent intent420 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent420.putExtra("link", getIntent().getStringExtra("link"));
                                intent420.putExtra("quality", "135/140");
                                intent420.putExtra("item", 3);
                                intent420.putExtra("position", player.getCurrentPosition());
                                startActivity(intent420);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 420p", Toast.LENGTH_LONG).show();
                                break;
                            case 4:
                                Intent intent2 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent2.putExtra("link", getIntent().getStringExtra("link"));
                                intent2.putExtra("quality", "136/140");
                                intent2.putExtra("item", 4);
                                intent2.putExtra("position", player.getCurrentPosition());
                                startActivity(intent2);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 720p", Toast.LENGTH_LONG).show();
                                break;
                            case 5:
                                Intent intent3 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent3.putExtra("link", getIntent().getStringExtra("link"));
                                intent3.putExtra("quality", "137/140");
                                intent3.putExtra("item", 5);
                                intent3.putExtra("position", player.getCurrentPosition());
                                startActivity(intent3);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Video Quality Selected to 1080p", Toast.LENGTH_LONG).show();
                                break;
                            case 6:
                                Intent intent4 = new Intent(getApplicationContext(), ExoPlayerActivity.class);
                                intent4.putExtra("link", getIntent().getStringExtra("link"));
                                intent4.putExtra("quality", "best");
//                                intent4.putExtra("quality", "bestvideo");
                                intent4.putExtra("item", 6);
                                intent4.putExtra("position", player.getCurrentPosition());
                                startActivity(intent4);
                                finish();
                                Toast.makeText(ExoPlayerActivity.this, "Auto Video Quality Selected", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();


            }
        });


        try {
            YoutubeDL.getInstance().init(this);
        } catch (YoutubeDLException e) {
            Log.e(TAG, "failed to initialize youtubedl-android", e);
        }


        String qualityy = getIntent().getStringExtra("quality");

        startStream(qualityy);

    }


    private void startStream(String videoQualityyyy) {

        Disposable disposable = Observable.fromCallable(() -> {
                    YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
//                    request.addOption("-f", "134+140/m4a/bestaudio");
                    request.addOption("-f", "264");
                    return YoutubeDL.getInstance().getInfo(request);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(streamInfo -> {
                    String videoUrl = streamInfo.getUrl();

                    System.out.println(">>> Check " + videoUrl);

//                    playVideo(videoUrl, videoUrl);

                    playVideo1(videoUrl);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player.seekTo(10);
                        }
                    }, 1000);

                    /*
                    if (TextUtils.isEmpty(videoUrl)) {
                        Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                    } else {

                        if (Objects.equals(videoQualityyyy, "bestvideo")) {

                            Disposable disposable1 = Observable.fromCallable(() -> {
                                        YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                                        request.addOption("-f", "bestaudio");
                                        return YoutubeDL.getInstance().getInfo(request);
                                    })
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(streamInfo1 -> {
                                        String audioUrl = streamInfo1.getUrl();
                                        if (TextUtils.isEmpty(audioUrl)) {
                                            Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                                        } else {
                                            System.out.println(">>>>>>>>>>>>>>>>>>> Best Video Link : " + videoUrl);
                                            playVideo(videoUrl, audioUrl);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    player.seekTo(10);
                                                }
                                            }, 1000);
                                        }
                                    }, e -> {
                                        Toast.makeText(ExoPlayerActivity.this, "streaming failed. failed to get stream info", Toast.LENGTH_LONG).show();
                                    });

                            compositeDisposable.add(disposable1);

                        } else if (Objects.equals(videoQualityyyy, "133")) {


                            Disposable disposable2 = Observable.fromCallable(() -> {
                                        YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                                        request.addOption("-f", "bestaudio");
                                        return YoutubeDL.getInstance().getInfo(request);
                                    })
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(streamInfo2 -> {
                                        String audioUrl = streamInfo2.getUrl();
                                        if (TextUtils.isEmpty(audioUrl)) {
                                            Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                                        } else {
                                            System.out.println(">>>>>>>>>>>>>>>>>>> 240p Video Link : " + videoUrl);
                                            playVideo(videoUrl, audioUrl);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    player.seekTo(10);
                                                }
                                            }, 1000);
                                        }
                                    }, e -> {
                                        Toast.makeText(ExoPlayerActivity.this, "streaming failed. failed to get stream info", Toast.LENGTH_LONG).show();
                                    });

                            compositeDisposable.add(disposable2);

                        }  else if (Objects.equals(videoQualityyyy, "135")) {


                            Disposable disposable3 = Observable.fromCallable(() -> {
                                        YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                                        request.addOption("-f", "bestaudio");
                                        return YoutubeDL.getInstance().getInfo(request);
                                    })
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(streamInfo3 -> {
                                        String audioUrl = streamInfo3.getUrl();
                                        if (TextUtils.isEmpty(audioUrl)) {
                                            Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                                        } else {
                                            System.out.println(">>>>>>>>>>>>>>>>>>> 420p Video Link : " + videoUrl);
                                            playVideo(videoUrl, audioUrl);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    player.seekTo( 10);
                                                }
                                            }, 1000);
                                        }
                                    }, e -> {
                                        Toast.makeText(ExoPlayerActivity.this, "streaming failed. failed to get stream info", Toast.LENGTH_LONG).show();
                                    });

                            compositeDisposable.add(disposable3);

                        }   else if (Objects.equals(videoQualityyyy, "160")) {


                            Disposable disposable4 = Observable.fromCallable(() -> {
                                        YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                                        request.addOption("-f", "bestaudio");
                                        return YoutubeDL.getInstance().getInfo(request);
                                    })
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(streamInfo4 -> {
                                        String audioUrl = streamInfo4.getUrl();
                                        if (TextUtils.isEmpty(audioUrl)) {
                                            Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                                        } else {
                                            System.out.println(">>>>>>>>>>>>>>>>>>> 144p Video Link : " + videoUrl);
                                            playVideo(videoUrl, audioUrl);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    player.seekTo( 10);
                                                }
                                            }, 4000);
                                        }
                                    }, e -> {
                                        Toast.makeText(ExoPlayerActivity.this, "streaming failed. failed to get stream info", Toast.LENGTH_LONG).show();
                                    });

                            compositeDisposable.add(disposable4);

                        } else {
                            playVideo(videoUrl, videoUrl);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    player.seekTo(getIntent().getLongExtra("position", 100));
                                }
                            }, 1000);

                        }


                    }
                    */


                }, e -> {

                    /*
                    Disposable disposablenew = Observable.fromCallable(() -> {
                                YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                                request.addOption("-f", "best");
                                return YoutubeDL.getInstance().getInfo(request);
                            })
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(streamInfo1 -> {
                                String audioUrl = streamInfo1.getUrl();
                                if (TextUtils.isEmpty(audioUrl)) {
                                    Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                                } else {
                                    System.out.println(">>>>>>>>>>>>>>>>>>> Best Video Link : " + audioUrl);
                                    playVideo(audioUrl, audioUrl);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            player.seekTo(10);
                                        }
                                    }, 1000);
                                }
                            }, ee -> {
                                Toast.makeText(ExoPlayerActivity.this, "This Video Quality is not available for this video", Toast.LENGTH_LONG).show();
                            });

                    compositeDisposable.add(disposablenew);

                    Toast.makeText(ExoPlayerActivity.this, "This Video Quality is not available for this video", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ExoPlayerActivity.this, "Switched to Auto Quality", Toast.LENGTH_LONG).show();
                    */

                });




        compositeDisposable.add(disposable);
    }


    private void playVideo(String urlVideo, String urlAudio) {

        player = new SimpleExoPlayer.Builder(this).build();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();


        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(urlVideo));
        MediaSource audioSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(urlAudio));

        player.setMediaSource(new MergingMediaSource(
                        true,
                        videoSource,
                        audioSource),
                true
        );

        player.prepare();

        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        playerError();
    }


    private void playVideo1(String urlVideo) {

        player = null;

        player = new SimpleExoPlayer.Builder(this).build();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "app"));
        concatenatingMediaSource = new ConcatenatingMediaSource();

        MediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(urlVideo));
        concatenatingMediaSource.addMediaSource(mediaSource);


        player.prepare();

        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        playerError();
    }


    private void playerError() {
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);

                Disposable disposablenew = Observable.fromCallable(() -> {
                            YoutubeDLRequest request = new YoutubeDLRequest(getIntent().getStringExtra("link"));
                            request.addOption("-f", "best");
                            return YoutubeDL.getInstance().getInfo(request);
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(streamInfo1 -> {
                            String audioUrl = streamInfo1.getUrl();
                            if (TextUtils.isEmpty(audioUrl)) {
                                Toast.makeText(ExoPlayerActivity.this, "failed to get stream url", Toast.LENGTH_LONG).show();
                            } else {
                                System.out.println(">>>>>>>>>>>>>>>>>>> Best Video Link : " + audioUrl);
                                playVideo(audioUrl, audioUrl);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        player.seekTo(10);
                                    }
                                }, 1000);
                            }
                        }, ee -> {
                            Toast.makeText(ExoPlayerActivity.this, "This Video Quality is not available for this video", Toast.LENGTH_LONG).show();
                        });

                compositeDisposable.add(disposablenew);

                Toast.makeText(ExoPlayerActivity.this, "This Video Quality is not available for this video", Toast.LENGTH_SHORT).show();
                Toast.makeText(ExoPlayerActivity.this, "Switched to Auto Quality", Toast.LENGTH_LONG).show();

//                Toast.makeText(ExoPlayerActivity.this, "Video Playing Error "+error, Toast.LENGTH_SHORT).show();
            }
        });
        player.setPlayWhenReady(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player.isPlaying()) {
            player.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        player.setPlayWhenReady(true);
//        player.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exo_next:
                try {
                    player.stop();
                    position++;
//                    playVideo(video, audio);
                } catch (Exception e) {
                    Toast.makeText(this, "NO next Video", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exo_unlock:
                try {
                    player.stop();
                    position--;
//                    playVideo(video, audio);
                } catch (Exception e) {
                    Toast.makeText(this, "NO previous Video", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
