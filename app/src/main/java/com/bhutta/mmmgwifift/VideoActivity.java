package com.bhutta.mmmgwifift;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bhutta.mmmgwifift.webserver.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    MediaController mediaController;
    VideoView videoView;
    int video_counter = 0;
    ArrayList<String> videoList;
    private int counter_video_loop;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    boolean result;

    String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Digitals Screens";
    ImageView iv_image;

    String TAG="Video";
    @Override
    public void onBackPressed() {
       showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Please enter password to exit");

        final EditText input = new EditText(VideoActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();

                if(value.equals("1234")){
                  finishAffinity();
                    startActivity(new Intent(VideoActivity.this, MainActivity.class));

                }else{
                    String message = "The message you have entered is incorrect."+ "\n \n"+ "Please try again!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage(message);
                    builder.setPositiveButton("Cancel", null);
                    builder.create().show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.video_activity);

        iv_image= findViewById(R.id.iv_image);
        videoView = findViewById(R.id.vidvw);
        if(checkPermission()) {
            loadFiles();
            startPlaying();
        }

    }


    void startPlaying(){
        try {
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            mediaController.setVisibility(View.GONE);



            if(videoList.size()!=0) {

               if(fileType(videoList.get(video_counter))==3){
                   videoView.setVisibility(View.VISIBLE);
                   iv_image.setVisibility(View.GONE);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(PATH + "/" + videoList.get(video_counter)));


                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        videoView.start();
                    }
                });

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {


                        if (video_counter < videoList.size() - 1) {
                            video_counter++;
                           try{
                               Log.d(TAG, "onCompletion: Completetion"+video_counter);

                               startPlaying();
                           }catch (Exception e){
                               Log.d(TAG, "onCompletion: vided Completion Exception"+video_counter);

                               video_counter=0;
                               loadFiles();
                               startPlaying();
                           }
                        } else {
                            Log.d(TAG, "onCompletion: vided Counter is greater"+video_counter);
                              video_counter = 0;
                              loadFiles();
                              startPlaying();
                           }
                    }
                });
               }else if (fileType(videoList.get(video_counter))==2){
                   Log.d("Vide", "startPlaying:image "+video_counter);
                   videoView.setVisibility(View.GONE);
                   iv_image.setVisibility(View.VISIBLE);
                   try {
                       iv_image.setImageURI(Uri.fromFile(new File(PATH + "/" + videoList.get(video_counter))));
                       //Thread.sleep(10000);

                       new CountDownTimer(10000,1000) {

                           @Override
                           public void onTick(long millisUntilFinished) {}

                           @Override
                           public void onFinish() {
                               video_counter++;
                               if (video_counter > videoList.size() - 1) {
                                   video_counter = 0;
                                   loadFiles();
                                   startPlaying();
                               } else {
                                   startPlaying();

                               }

                           }
                       }.start();

                     //                               Glide.with(getApplicationContext()).load(Uri.fromFile())
//                                      .listener(new RequestListener<Drawable>() {
//                                          @Override
//                                          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                              Log.d(TAG, "onLoadFailed: "+e);
//                                              return false;
//                                          }
//
//                                          @Override
//                                          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                              return false;
//                                          }
//                                      }). into(iv_image);

                   }catch (Exception e){
                       Log.d(TAG, "startPlaying: "+e);
                   }

               }
//            videoView.start();
            }else{
                Toast.makeText(getApplicationContext(),"No video found in directory , kindly upload",Toast.LENGTH_LONG).show();
                startActivity(new Intent(VideoActivity.this, MainActivity.class));
            }

        }catch (Exception e){
            Log.d("Exception", "onCreate: "+e);
            e.printStackTrace();
        }

    }


    private int fileType(String filename){
        String[] temp = filename.split("\\.");
        switch (temp[temp.length - 1].toLowerCase()){
            case "txt":
            case "xlsx":
            case "xls":
            case "gslides":
            case "ppt":
            case "pptx":
            case "pdf":
            case "doc":
            case "docx":
            case "docm":
                return 1;
            case "gif":
            case "tif":
            case "ico":
            case "jpg":
            case "jpeg":
            case "png":
            case "tiff":
                return 2;
            case "3gp":
            case "avi":
            case "flv":
            case "m4v":
            case "mkv":
            case "mov":
            case "mng":
            case "mpeg":
            case "mpg":
            case "mpe":
            case "mp4":
            case "wmv":
            case "webm":
                return 3;
            case "mp1":
            case "mp2":
            case "mp3":
            case "aac":
            case "wma":
            case "amr":
            case "wav":
                return 4;
            case "zip":
            case "7z":
            case "cab":
            case "gzip":
            case "bin":
            case "rar":
            case "tar":
            case "iso":
                return 5;
            default:
                return 0;
        }
    }

    void loadFiles(){
        videoList = new ArrayList<>();
        File directory = new File(PATH);
        if (! directory.exists()){
            directory.mkdir();
            Toast.makeText(getBaseContext(),"Directory Created",Toast.LENGTH_LONG).show();
        }
        File f =  new File(PATH);
        File file[] = f.listFiles();
        if(file.length==0){
                Toast.makeText(getApplicationContext(),"No video found in directory , kindly upload",Toast.LENGTH_LONG).show();
                startActivity(new Intent(VideoActivity.this, MainActivity.class));
                finishAffinity();

        }
        for(int i = 0; i< file.length; i++){
            videoList.add(file[i].getName());
            Log.d("Video", "loadFiles: "+fileType(file[i].getName()));
            videoList.get(i);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(VideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(VideoActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write Storage permission is necessary to Download Images and Videos!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public void checkAgain() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(VideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(VideoActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission necessary");
            alertBuilder.setMessage("Write Storage permission is necessary to Download Images and Videos!!!");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
            });
            android.app.AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
    }


    //Here you can check App Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       startPlaying();
                } else {
                    //code for deny
                    checkAgain();
                }
                break;
        }
    }

}