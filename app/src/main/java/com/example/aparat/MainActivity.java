package com.example.aparat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Button btnTakePhoto;
    ImageView imageView;
    public static final int RequestPermissionCode = 1;
    private int seekR, seekG, seekB, seekA;

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBar1:
                    seekR = progress;
                    break;
                case R.id.seekBar2:
                    seekG = progress;
                    break;
                case R.id.seekBar3:
                    seekB = progress;
                    break;
                case R.id.seekBar4:
                    seekA = progress;
            }

            rgb();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTakePhoto = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        EnableRuntimePermission();
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            }
        });

        SeekBar sbR = (SeekBar) findViewById(R.id.seekBar1);
        SeekBar sbG = (SeekBar) findViewById(R.id.seekBar2);
        SeekBar sbB = (SeekBar) findViewById(R.id.seekBar3);
        SeekBar sbA = (SeekBar) findViewById(R.id.seekBar4);


        sbR.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbG.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbB.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbA.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

        }
    }
    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(MainActivity.this,"Dostęp do kamery jest potrzebny aby aplikacja funkcjonowała poprawnie",     Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        super.onRequestPermissionsResult(requestCode, permissions, result);
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void rgb() {
        imageView = findViewById(R.id.imageView);
        imageView.setColorFilter(Color.argb(seekA, seekR, seekG, seekB));

    }

    public void rotate(View view) {
        imageView = findViewById(R.id.imageView);
        imageView.setRotation(imageView.getRotation() + 90);

    }
}