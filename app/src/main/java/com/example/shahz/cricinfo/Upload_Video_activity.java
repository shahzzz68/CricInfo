package com.example.shahz.cricinfo;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.shahz.cricinfo.Services.BaseUploading_Service;
import com.example.shahz.cricinfo.Services.Uploading_Service;
import com.example.shahz.cricinfo.model_class.Video_MetaData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Upload_Video_activity extends AppCompatActivity implements View.OnClickListener {

    int REQUEST_VIDEO=1;
    int READ_EXTERNAL_STORAGE_PERMMISSION_RESULT=2;
    int VIDEO_RECORD_REQUEST=3;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";

    Uri videoUri;
    Uri mDownloadUrl = null;
    Uri mFileUri = null;
    byte[] videoThumbnailUri;

    private StorageReference mImageRef;
    private DatabaseReference mDatabaseRef;

    StorageReference videoRef;
    BroadcastReceiver mBroadcastReceiver;
    ProgressDialog mProgressDialog;

    Intent service;


    ImageView videoView;
    EditText editText;
    Button btnRecord, btnCancel, btnLibrary, btnUpload;

    String videoName;
    String thumbnailDownloadurl;

    boolean isNetConnected=false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload__video_activity);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            isNetConnected = true;
        }
        else
        {
            isNetConnected=false;
        }


        checkReadExternalStoragePermission();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        videoRef=storageReference.child("videos").child("video");

        mImageRef = FirebaseStorage.getInstance().getReference("videosThumnails");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("videos data");

        videoView=findViewById(R.id.videoView);

        btnRecord=findViewById(R.id.recordBtn);
        btnCancel=findViewById(R.id.cancelBtn);
        btnLibrary=findViewById(R.id.libraryBtn);
        btnUpload=findViewById(R.id.uploadBtn);
        editText=findViewById(R.id.videoNameEdittxt);
        videoName=editText.getText().toString();


        btnLibrary.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnCancel.setOnClickListener(this);



        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction())
                {
                    case Uploading_Service.UPLOAD_COMPLETED:

                    case Uploading_Service.UPLOAD_ERROR:

                        onUploadResultIntent(intent);
                        break;
                }
            }
        };


    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.libraryBtn:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent,"Select Video"),REQUEST_VIDEO);
                break;

            case R.id.recordBtn:
                Intent recIntent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(recIntent,VIDEO_RECORD_REQUEST);
                break;

            case R.id.cancelBtn:

                Uploading_Service.isCancelled = true;
                Uploading_Service.uploadTask.cancel();
                stopService(service);
                hideprogress();

                    break;

            case R.id.uploadBtn:
                if (isNetConnected) {
                    if (!editText.getText().toString().equals("") && videoUri != null) {
                        //Toast.makeText(this,videoThumbnailUri.toString(), Toast.LENGTH_SHORT).show();
                        uploadFromUri(videoUri, editText.getText().toString(), thumbnailDownloadurl);
                        ImgThumbnail(videoThumbnailUri, editText.getText().toString());

                    } else {
                        Toast.makeText(this, "Video path or name is empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
                }
                break;




        }
    }

    @ Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            try {
                if (data.getData()!=null) {
                    videoUri = data.getData();
                }



                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Video.Thumbnails.MINI_KIND);
                videoView.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                videoThumbnailUri = baos.toByteArray();
            }
            catch (Exception e)
            {
                Toast.makeText(this, "No file path", Toast.LENGTH_SHORT).show();
            }


            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_VIDEO) {
                   // Toast.makeText(this, picturePath, Toast.LENGTH_LONG).show();

                } else if (requestCode == VIDEO_RECORD_REQUEST) {
                   // Toast.makeText(this, bitmap.toString(), Toast.LENGTH_LONG).show();
                }
            }

        else
        {
            Toast.makeText(this, "No file", Toast.LENGTH_SHORT).show();
        }

    }



    private void checkReadExternalStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_PERMMISSION_RESULT);

            } else {
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();

            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 2:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void uploadFromUri(Uri fileUri,String videoname, String thumnail) {
        // Save the File URI
        mFileUri = fileUri;

        // Clear the last download, if any
        mDownloadUrl = null;

        // Start MyUploadService to upload the file, so that the file is uploaded
        // even if this Activity is killed or put in the background
        Uploading_Service.isCancelled = false;
        service = new Intent(getApplicationContext(),Uploading_Service.class);
        service.putExtra(Uploading_Service.EXTRA_FILE_URI, fileUri)
               .putExtra(Uploading_Service.VIDEO_NAME,videoname)
               .putExtra(Uploading_Service.VIDEO_THUMBNAIL,thumnail)
               .setAction(Uploading_Service.ACTION_UPLOAD);
        startService(service);


        // Show loading spinner

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Check if this Activity was launched by clicking on an upload notification
        if (intent.hasExtra(Uploading_Service.EXTRA_DOWNLOAD_URL)) {
            onUploadResultIntent(intent);
        }

    }

    private void onUploadResultIntent(Intent intent) {
        // Got a new intent from MyUploadService with a success or failure
        mDownloadUrl = intent.getParcelableExtra(Uploading_Service.EXTRA_DOWNLOAD_URL);
        mFileUri = intent.getParcelableExtra(Uploading_Service.EXTRA_FILE_URI);

    }

    private void showMessageDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create();
        ad.show();
    }

    private void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // Register receiver for uploads
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(mBroadcastReceiver, Uploading_Service.getIntentFilter());
    }

    @Override
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    private void ImgThumbnail(byte[] path , final String name)
    {
       // byte[] b = path.getBytes();
        final UploadTask task= mImageRef.child(name+".jpg").putBytes(path);
      task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


          }
      });
    }



    private void showFinishedNotification(String caption) {
        Intent finishIntent = new Intent(this, Upload_Video_activity.class);
        // Make PendingIntent for notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* requestCode */, finishIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //int icon = success ? R.drawable.ic_check_white_24 : R.drawable.ic_error_white_24dp;
        int icon = R.drawable.video_live_icon;

        createDefaultChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, BaseUploading_Service.CHANNEL_ID_DEFAULT)
                .setSmallIcon(icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(caption)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());
    }

    private void createDefaultChannel() {
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(BaseUploading_Service.CHANNEL_ID_DEFAULT,
                    "Default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }
    }

    private void stoppingService()
    {
        if (service!=null)
        {
            stopService(new Intent(this,Uploading_Service.class));
        }

        // Uploading_Service.uploadTask.cancel();

    }

    private void hideprogress()
    {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(0);

    }

}
