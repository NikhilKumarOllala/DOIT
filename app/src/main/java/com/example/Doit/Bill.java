package com.example.Doit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;


public class Bill extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private Button msharebtn;
    public EditText meditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        imageView = findViewById(R.id.my_avatar);
        imageView1 = findViewById(R.id.my_avatar1);
        imageView2= findViewById(R.id.my_avatar2);
        imageView3= findViewById(R.id.my_avatar3);
        msharebtn = findViewById(R.id.sharebtn);
        meditText = findViewById(R.id.editText);

        msharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image();
            }
        });

    }
    private void image(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File f = new File(getExternalCacheDir() + "/"+getResources().getString(R.string.app_name)+".png");
        Intent shareint;

        try{
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            shareint = new Intent(Intent.ACTION_SEND);
            shareint.setType("image/*");
            shareint.putExtra(Intent.EXTRA_TEXT,meditText.getText().toString().trim());
            shareint.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"share image"));

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

    final CharSequence[] options = {"Take Picture", "Choose from Gallery", "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Choose an action");

    builder.setItems(options, (dialog, item) -> {

        if (options[item].equals("Take Picture")) {
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