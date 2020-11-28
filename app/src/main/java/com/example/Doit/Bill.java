package com.example.Doit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.security.AccessController;

public class Bill extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        imageView = findViewById(R.id.my_avatar);
        imageView1 = findViewById(R.id.my_avatar1);
        imageView2= findViewById(R.id.my_avatar2);
        imageView3= findViewById(R.id.my_avatar3);
    }

    public void getimage(View view) {
        selectImage(imageView.getContext());
    }

    public void getimage1(View view) {

        selectImage(imageView1.getContext());
    }
    public void getimage2(View view) {
        selectImage(imageView2.getContext());
    }

    public void getimage3(View view) {

        selectImage(imageView3.getContext());
    }

private void selectImage(Context context) {

    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Choose your profile picture");

    builder.setItems(options, (dialog, item) -> {

        if (options[item].equals("Take Photo")) {
            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);

        } else if (options[item].equals("Choose from Gallery")) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);

        } else if (options[item].equals("Cancel")) {
            dialog.dismiss();
        }
    });
    builder.show();
} @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}