package com.example.android.deltatask2;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<theImage> thatImage ;
    private imgAdapter adapter;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri CapturedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thatImage = new ArrayList<theImage>();
        adapter = new imgAdapter(this, thatImage);
        ListView lisView = (ListView) findViewById(R.id.lisView);
        lisView.setAdapter(adapter);
        Button fromGallery = (Button) findViewById(R.id.fromGallery);
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryBtnClk();
            }
        });
        Button fromCamera = (Button) findViewById(R.id.fromCamera);
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto();
            }
        });

       



    }
    private void TakePhoto() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            CapturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, CapturedImageUri);
            startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void GalleryBtnClk() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    theImage Img = new theImage();
                    Img.setPath(picturePath);
                    thatImage.add(Img);


                }
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(CapturedImageUri, projection, null, null, null);
                    int columnData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(columnData);
                    theImage Img = new theImage();
                    Img.setPath(picturePath);
                    thatImage.add(Img);
                }
        }
    }

}
