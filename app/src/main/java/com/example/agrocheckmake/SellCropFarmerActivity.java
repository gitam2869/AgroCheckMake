package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class SellCropFarmerActivity extends AppCompatActivity implements View.OnClickListener

{

    private static final Object FOREGROUND_SERVICE_ID = 111;
    private EditText editTextName;
    private EditText editTextQuantity;
    private EditText editTextRate;
    private EditText editTextDescription;
    private ImageView imageViewImage;
    private Button buttonUploadImage;
    private Button buttonSellCrop;

    private Uri filepath;
    private Bitmap bitmap;


    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;

//    public static final String UPLOAD_URL = "http://192.168.0.59/IU/upload_agro.php";

    public static final String UPLOAD_URL = "http://agrocheckmake.000webhostapp.com/IU/upload_agro.php";

//    http://agrocheckmake.000webhostapp.com/ImagesUpload/


    private ProgressDialog progressDialog;


    private static final String TAG = "SellCropFarmerActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop_farmer);

        requestStoragePermission();

        editTextName = findViewById(R.id.idEditTextCropNameSellCropFarmerActivity);
        editTextQuantity = findViewById(R.id.idEditTextQuantitySellCropFarmerActivity);
        editTextRate = findViewById(R.id.idEditTextRateSellCropFarmerActivity);
        editTextDescription = findViewById(R.id.idEditTextCropDescriptionFarmerActivity);
        imageViewImage = findViewById(R.id.idImageViewUploadImageSellCropFarmerActivity);
        buttonUploadImage = findViewById(R.id.idButtonUploadImagesSellCropFarmerActivity);
        buttonSellCrop = findViewById(R.id.idButtonSellCropSellCropFarmerActivity);

        progressDialog = new ProgressDialog(this);

        buttonSellCrop.setOnClickListener(this);
    }



    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ShowFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null)
        {

            filepath = data.getData();
            try
            {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageViewImage.setImageBitmap(bitmap);
                imageViewImage.setVisibility(View.VISIBLE);
            }
            catch (Exception ex)
            {

            }
        }
    }

    public void selectImage(View view)
    {
        ShowFileChooser();
    }


    private String getPath(Uri uri) {
        String path = "";
        if(uri != null)
        {
            try
            {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                String document_id = cursor.getString(0);
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
                cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
                );
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
            catch (CursorIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            return path;


        }
        else
        {
            return "";
        }
    }

    private void uploadImage()
    {

        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
        String f_id = String.valueOf(i) ;

        String name = editTextName.getText().toString().trim();
        String quantity = editTextQuantity.getText().toString().trim();
        String rate = editTextRate.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();


        String path = getPath(filepath);

        String farmername = SharedPrefManagerFarmer.getInstance(this).getUserName();
        String farmernumber = SharedPrefManagerFarmer.getInstance(this).getUserMobile();


        if(name.length() == 0)
        {
            editTextName.setError("Enter Crop Name");
            return;
        }
        if(quantity.length() == 0)
        {
            editTextQuantity.setError("Enter Quantity");
            return;
        }
        if(rate.length() == 0)
        {
            editTextRate.setError("Enter Rate");
            return;
        }
        if(description.length() == 0)
        {
            editTextDescription.setError("Enter Description");
            return;
        }

        Log.d(TAG, "uploadImage: Gadkddddddddddddddd"+path);
        if(path.length()==0)
        {
            Toast.makeText(getApplicationContext(),"Select Image",Toast.LENGTH_LONG).show();

//            buttonUploadImage.setText("Select image");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        try
        {
            progressDialog.dismiss();

            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(
                    this,
                    uploadId,
                    UPLOAD_URL)
                    .addFileToUpload(path, "image")
                    .addParameter("f_id", f_id)
                    .addParameter("name", name)
                    .addParameter("quantity", quantity)
                    .addParameter("rate", rate)
                    .addParameter("description", description)
                    .addParameter("farmername", farmername)
                    .addParameter("farmernumber", farmernumber)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(9)
                    .startUpload();


            Toast.makeText(getApplicationContext(),"Post Uploaded Successfully",Toast.LENGTH_LONG).show();
            finish();

        }
        catch (Exception ex)
        {


        }




    }

//    public void saveData(View view)
//    {
//        uploadImage();
//    }


    @Override
    public void onClick(View v) {
        if(v == buttonSellCrop)
        {
            uploadImage();
        }
    }



}
