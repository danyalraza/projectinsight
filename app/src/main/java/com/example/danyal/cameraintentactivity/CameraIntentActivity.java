package com.example.danyal.cameraintentactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CameraIntentActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 5;
    private ImageView mPhotoCapturedImageView;
    private String mImageFileLocation = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);
        mPhotoCapturedImageView = (ImageView) findViewById(R.id.capturePhotoImageView);
    }

    public void takePicture(View view) {
     //   Toast.makeText(this, "camera button pressed", Toast.LENGTH_SHORT).show();
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        }   catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            //Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
//            Bundle extras = data.getExtras();
//            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
//            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
//            Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
//            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
            setReducedImageSize();
        }
    }

    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE-" + timeStamp;
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();
//        int x = 1;
//        while(x < 100)
//        {
//            System.out.println(mImageFileLocation);
//            x++;
//        }

        return image;
    }

    void setReducedImageSize() {
        int x = 0;
        while(x < 100)
        {
            System.out.println(mImageFileLocation);
            x++;
        }
//        int targetImageViewWidth = mPhotoCapturedImageView.getWidth();
//        int targetImageViewHeight = mPhotoCapturedImageView.getHeight();
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
//        int cameraImageWidth = bmOptions.outWidth;
//        int cameraImageHeight = bmOptions.outHeight;
//        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inJustDecodeBounds = false;
//        Bitmap photoReducedSizeBitmap = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
//        mPhotoCapturedImageView.setImageBitmap(photoReducedSizeBitmap);
    }


}
