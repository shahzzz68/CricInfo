package com.example.shahz.cricinfo.Services;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.example.shahz.cricinfo.Upload_Video_activity;
import com.example.shahz.cricinfo.model_class.Video_MetaData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Uploading_Service extends BaseUploading_Service {

    private static final String TAG = "MyUploadService";
    /** Intent Actions **/
    public static final String ACTION_UPLOAD = "action_upload";
    public static final String UPLOAD_COMPLETED = "upload_completed";
    public static final String UPLOAD_ERROR = "upload_error";

    /** Intent Extras **/
    public static final String VIDEO_NAME = "video_name";
    public static final String VIDEO_THUMBNAIL = "thumbnail";
    public static final String EXTRA_FILE_URI = "extra_file_uri";
    public static final String EXTRA_DOWNLOAD_URL = "extra_download_url";

    // [START declare_ref]
    private StorageReference mStorageRef;
    private StorageReference mImageRef;
    private FirebaseFirestore mDatabaseRef;

    public static UploadTask uploadTask;
    public static boolean isCancelled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mStorageRef = FirebaseStorage.getInstance().getReference("videos");
        mImageRef = FirebaseStorage.getInstance().getReference("videosThumnails");
        mDatabaseRef = FirebaseFirestore.getInstance();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_UPLOAD.equals(intent.getAction())) {
            Uri fileUri = intent.getParcelableExtra(EXTRA_FILE_URI);
            String vName = intent.getStringExtra(VIDEO_NAME);
            String thumbnaildownloadlink = intent.getStringExtra(VIDEO_THUMBNAIL);


           // Toast.makeText(this, "Starting Upload...", Toast.LENGTH_LONG).show();
          //  Toast.makeText(this, thumbnail.toString(), Toast.LENGTH_SHORT).show();

            // Make sure we have permission to read the data
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getContentResolver().takePersistableUriPermission(
//                        fileUri,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }

              //  ImgThumbnail(thumbnail,vName);
                uploadFromUri(fileUri,vName,thumbnaildownloadlink);


        }

        return START_REDELIVER_INTENT;
    }

    private void uploadFromUri(final Uri fileUri, final String videoname , final String link) {

        // [START_EXCLUDE]
       // taskStarted();
       // showProgressNotification("Uploading", 0, 0);
        // [END_EXCLUDE]

        final StorageReference photoRef = mStorageRef.child(videoname
                        + "." + getFileExtension(fileUri));

         uploadTask=photoRef.putFile(fileUri);

          uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        if (isCancelled)
                            return;
                        showProgressNotification("Uploading",
                                taskSnapshot.getBytesTransferred(),
                                taskSnapshot.getTotalByteCount());
                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        // Forward any exceptions
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        Log.d(TAG, "uploadFromUri: upload success");

                        // Request the public download URL
                        return photoRef.getDownloadUrl();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull final Uri downloadUri) {
                        // Upload succeeded
                        Log.d(TAG, "uploadFromUri: getDownloadUri success");

                        // [START_EXCLUDE]
                        broadcastUploadFinished(downloadUri, fileUri);
                        showUploadFinishedNotification(downloadUri, fileUri);
                        taskCompleted();
                        // [END_EXCLUDE]

                        //[META_DATA TO DATABASE]


                        StorageReference reference =FirebaseStorage.getInstance().getReference("videosThumnails").child(videoname+".jpg");
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String,Object> videoMetaData= new HashMap<>();
                                videoMetaData.put("videoName",videoname);
                                 videoMetaData.put("thumbnail",uri.toString());
                                videoMetaData.put("videoUrl",downloadUri.toString());
                                videoMetaData.put("time", FieldValue.serverTimestamp());

                                mDatabaseRef.collection("VideoMetadata").document(videoname)
                                        .set(videoMetaData);
//
                            }
                        });



                        Toast.makeText(getApplicationContext(), "Upload Complete", Toast.LENGTH_SHORT).show();

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
              @Override
              public void onCanceled() {

                  dismissProgressNotification();
                  stopSelf();
              }
          });

    }

    private void broadcastUploadFinished(Uri downloadUrl, Uri fileUri) {

        boolean success = downloadUrl != null;

        String action = success ? UPLOAD_COMPLETED : UPLOAD_ERROR;

        Intent broadcast = new Intent(action)
                .putExtra(EXTRA_DOWNLOAD_URL, downloadUrl)
                .putExtra(EXTRA_FILE_URI, fileUri);
         LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(broadcast);
    }

    private void showUploadFinishedNotification(@Nullable Uri downloadUrl, @Nullable Uri fileUri) {
        // Hide the progress notification
        dismissProgressNotification();

        // Make Intent to UploadActivity
        Intent intent = new Intent(this, Upload_Video_activity.class)
                .putExtra(EXTRA_DOWNLOAD_URL, downloadUrl)
                .putExtra(EXTRA_FILE_URI, fileUri)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        boolean success = downloadUrl == null;
        String caption="" ;
        if (success==true)
        {
            caption="Upload fail";
        }
        else if (success==false)
        {
            caption="Upload Complete";
        }

        showFinishedNotification(caption, intent);
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_COMPLETED);
        filter.addAction(UPLOAD_ERROR);

        return filter;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void cancelUpload()
    {

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }


}
