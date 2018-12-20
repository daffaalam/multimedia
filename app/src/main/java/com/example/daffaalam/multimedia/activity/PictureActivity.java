package com.example.daffaalam.multimedia.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.daffaalam.multimedia.R;
import com.example.daffaalam.multimedia.tool.Functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PictureActivity extends Functions implements View.OnClickListener {

    boolean permissionCamera, permissionReadStorage, permissionWriteStorage;

    ImageView ivPicture;
    Button bGetPicture, bShowPicture;
    File fileFolder;
    String nameFile, timeNow;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        permissionCamera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        permissionReadStorage = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        permissionWriteStorage = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionCamera || permissionReadStorage || permissionWriteStorage) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
        }

        ivPicture = findViewById(R.id.ivPicture);
        bGetPicture = findViewById(R.id.bGetPicture);
        bShowPicture = findViewById(R.id.bShowPicture);
        bGetPicture.setOnClickListener(this);
        bShowPicture.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults.length != 3 || grantResults[0] != 0 || grantResults[1] != 0 || grantResults[2] != 0) {
                myToast("give permission first");
                onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGetPicture:
                fileFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/Multimedia");
                if (!fileFolder.exists()) {
                    fileFolder.mkdirs();
                }
                timeNow = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
                nameFile = fileFolder.getPath() + "/IMG_" + timeNow + ".jpg";
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(nameFile)));
                startActivityForResult(intent, 401);
                break;
            case R.id.bShowPicture:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 402);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 401:
                switch (resultCode) {
                    case RESULT_OK:
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(new File(nameFile))));
                        myToast("image saved on " + nameFile);
                        break;
                    case RESULT_CANCELED:
                        myToast(getResources().getString(android.R.string.cancel));
                        break;
                }
                break;
            case 402:
                switch (resultCode) {
                    case RESULT_OK:
                        try {
                            ivPicture.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData())));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case RESULT_CANCELED:
                        myToast(getResources().getString(android.R.string.cancel));
                        break;
                }
                break;
        }
    }
}
