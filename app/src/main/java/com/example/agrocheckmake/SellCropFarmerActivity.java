package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class SellCropFarmerActivity extends AppCompatActivity
{
    private String[] dataArray = new String[8];

    private EditText editTextName;
    private EditText editTextQuantity;
    private EditText editTextRate;
    private EditText editTextDescription;
    private ImageView imageViewImage;
    private Button buttonUploadImage;
    private Button buttonSellCrop;
    private Spinner spinnerCropQuantity;
    private Spinner spinnerCropRate;

    private Uri filepath;
    private Bitmap bitmap;


    private int PICK_IMAGE_REQUEST = 1;

//    public static final String UPLOAD_URL = "http://192.168.0.59/IU/upload_agro.php";

    public static final String UPLOAD_URL_SELL_CROP =
            "http://agrocheckmake.000webhostapp.com/IU/upload_agro.php";

//    http://agrocheckmake.000webhostapp.com/ImagesUpload/


    private ProgressDialog progressDialog;


    private static final String TAG = "SellCropFarmerActivity";

    public String stringCropQuanitySpinner;
    public String stringCropRateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop_farmer);

        editTextName = findViewById(R.id.idEditTextCropNameSellCropFarmerActivity);
        editTextQuantity = findViewById(R.id.idEditTextQuantitySellCropFarmerActivity);
        editTextRate = findViewById(R.id.idEditTextRateSellCropFarmerActivity);
        editTextDescription = findViewById(R.id.idEditTextCropDescriptionFarmerActivity);
        imageViewImage = findViewById(R.id.idImageViewUploadImageSellCropFarmerActivity);
        buttonUploadImage = findViewById(R.id.idButtonUploadImagesSellCropFarmerActivity);

        buttonSellCrop = findViewById(R.id.idButtonSellCropSellCropFarmerActivity);

        spinnerCropQuantity = findViewById(R.id.idSpinnerCropQuantitySellCropFarmerActivity);
        spinnerCropRate = findViewById(R.id.idSpinnerCropRateSellCropFarmerActivity);

        setUpCropQuantitySpinner();
        setUpCropRateSpinner();

        progressDialog = new ProgressDialog(this);

        buttonSellCrop.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                verifyData();

                if(
                        dataArray[0] != null &&
                        dataArray[2] !=null &&
                        dataArray[3] != null &&
                        dataArray[4] !=null &&
                        dataArray[5] != null
                )
                {
                    SellCropAsyncTask sellCropAsyncTask = new SellCropAsyncTask();
                    sellCropAsyncTask.execute(dataArray);
                }
            }
        });
    }

    private class SellCropAsyncTask extends AsyncTask<String, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings)
        {

            try
            {

                String uploadId = UUID.randomUUID().toString();

                new MultipartUploadRequest(
                        getApplicationContext(),
                        uploadId,
                        UPLOAD_URL_SELL_CROP)
                        .addFileToUpload(strings[0], "image")
                        .addParameter("f_id", strings[1])
                        .addParameter("name", strings[2])
                        .addParameter("quantity", strings[3])
                        .addParameter("rate", strings[4])
                        .addParameter("description", strings[5])
                        .addParameter("farmername", strings[6])
                        .addParameter("farmernumber", strings[7])
//                    .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(9)
                        .startUpload();


            }
            catch (Exception ex)
            {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

//            if(SellCropFarmerActivity.this.isFinishing())
//            {
//                Toast.makeText(getApplicationContext(),"Post Upload Successfully",Toast.LENGTH_LONG).show();
//            }
            Toast.makeText(getApplicationContext(),"Post Upload Successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),HomePageFarmerActivity.class));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void verifyData()
    {
        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
        final String f_id = String.valueOf(i);

        final String name = editTextName.getText().toString().trim();

        String tempQuantity = editTextQuantity.getText().toString().trim();
        final String quantity = tempQuantity +" "+stringCropQuanitySpinner;

        String tempRate = editTextRate.getText().toString().trim();
        final String rate = tempRate +" "+ stringCropRateSpinner;

        final String description = editTextDescription.getText().toString().trim();

        if (name.length() == 0)
        {
            editTextName.setError("Enter Crop Name");
            return;
        }
        if (tempQuantity.length() == 0)
        {
            editTextQuantity.setError("Enter Quantity");
            return;
        }
        if (tempRate.length() == 0)
        {
            editTextRate.setError("Enter Rate");
            return;
        }
        if (description.length() == 0) {
            editTextDescription.setError("Enter Description");
            return;
        }


        String path = FileUtils.getLocalPath(this, filepath);
//        Log.d(TAG, "onActivityResultpath: "+path);

//        String path = FilePath.getPath(this, filepath);

//        final String path = getPath(filepath);

//        final String path = getPathInputStream(filepath);

        Log.d(TAG, "1 uploadImage: " + path);

        final String farmername = SharedPrefManagerFarmer.getInstance(this).getUserName();
        final String farmernumber = SharedPrefManagerFarmer.getInstance(this).getUserMobile();



        if (path == null || path.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_LONG).show();
            return;
        }

            dataArray[0] = path;
            dataArray[1] = f_id;
            dataArray[2] = name;
            dataArray[3] = quantity;
            dataArray[4] = rate;
            dataArray[5] = description;
        dataArray[6] = farmername;
        dataArray[7] = farmernumber;

    }



    private void ShowFileChooser()
    {

//        Intent chooseFile;
//        Intent intent;
//        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
//        chooseFile.setType("*/ *");
//        intent = Intent.createChooser(chooseFile, "Choose a file");
//        startActivityForResult(intent, 1);
//
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);

//        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

//        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
//        gallery.setType("*/*");
//        startActivityForResult(gallery,PICK_IMAGE_REQUEST);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null)
        {

            filepath = data.getData();
            Log.d(TAG, "onActivityResult: "+data);
            Log.d(TAG, "onActivityResult: "+filepath);


            Log.d(TAG, "uploadImage: "+filepath);
            try
            {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400,400);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageViewImage.setImageBitmap(bitmap);
                imageViewImage.setVisibility(View.VISIBLE);
                imageViewImage.setLayoutParams(layoutParams);
            }
            catch (Exception ex)
            {

            }
        }
    }

    // this method declared in xml file
    public void selectImage(View view)
    {
        ShowFileChooser();
    }


    private String getPath(Uri uri)
    {
        String path = "";
        if(uri != null)
        {
            try
            {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                Log.d(TAG, "onActivityResultcu1: "+cursor);
                cursor.moveToFirst();
                Log.d(TAG, "onActivityResultcu2: "+cursor);

                String document_id = cursor.getString(0);
                Log.d(TAG, "onActivityResultcu3: "+document_id);

                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
                Log.d(TAG, "onActivityResultcu4: "+document_id);

//                cursor = getContentResolver().query(
//                        MediaStore.Files.getContentUri(uri)
//                )
                cursor.close();
//                cursor = getContentResolver().query(
//                        MediaStore.Files.getContentUri("external"), null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
//                );

                cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
                );

                Log.d(TAG, "onActivityResultcu5: "+cursor);

                if(cursor != null && cursor.moveToFirst())
                {
                    Log.d(TAG, "onActivityResultcu7: "+cursor);
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();
                }
                else
                {
                    Log.d(TAG, "onActivityResultcu6: "+cursor);

                }

            }
            catch (CursorIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            Log.d(TAG, "onActivityResult1:  "+path);
            return path;


        }
        else
        {
            Log.d(TAG, "onActivityResultnull:  "+path);
            return "";
        }
    }

//    private void uploadImage()
//    {
//
//        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
//        final String f_id = String.valueOf(i);
//
//        final String name = editTextName.getText().toString().trim();
//
//        String tempQuantity = editTextQuantity.getText().toString().trim();
//        final String quantity = tempQuantity +" "+stringCropQuanitySpinner;
//
//        String tempRate = editTextRate.getText().toString().trim();
//        final String rate = tempRate +" "+ stringCropRateSpinner;
//
//        final String description = editTextDescription.getText().toString().trim();
//
//
//        final String path = getPath(filepath);
//
//        Log.d(TAG, "1 uploadImage: " + path);
//
//        final String farmername = SharedPrefManagerFarmer.getInstance(this).getUserName();
//        final String farmernumber = SharedPrefManagerFarmer.getInstance(this).getUserMobile();
//
//
//        if (name.length() == 0)
//        {
//            editTextName.setError("Enter Crop Name");
//            return;
//        }
//        if (tempQuantity.length() == 0)
//        {
//            editTextQuantity.setError("Enter Quantity");
//            return;
//        }
//        if (tempRate.length() == 0)
//        {
//            editTextRate.setError("Enter Rate");
//            return;
//        }
//        if (description.length() == 0) {
//            editTextDescription.setError("Enter Description");
//            return;
//        }
//
//        if (path.length() == 0)
//        {
//            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//
//        try
//        {
//
//            String uploadId = UUID.randomUUID().toString();
//
//            new MultipartUploadRequest(
//                    this,
//                    uploadId,
//                    UPLOAD_URL_SELL_CROP)
//                    .addFileToUpload(path, "image")
//                    .addParameter("f_id", f_id)
//                    .addParameter("name", name)
//                    .addParameter("quantity", quantity)
//                    .addParameter("rate", rate)
//                    .addParameter("description", description)
//                    .addParameter("farmername", farmername)
//                    .addParameter("farmernumber", farmernumber)
////                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(9)
//                    .startUpload();
//
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(),"Post Upload Successfully",Toast.LENGTH_LONG).show();
//                startActivity(new Intent(this,HomePageFarmerActivity.class));
//        }
//        catch (Exception ex)
//        {
//
//
//        }
//    }

    private void setUpCropQuantitySpinner()
    {
        ArrayList<String> units = new ArrayList<>();

//        units.add(0,"Unit");
        units.add("Kg");
        units.add("Quintle");
        units.add("Tonne");

        //style the spinner

        ArrayAdapter<String> arrayAdapterCropQuantity = new ArrayAdapter(this,R.layout.spinner_item,units);


        //Dropdown layouto style
        arrayAdapterCropQuantity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //attaching data adapter to spinner
        spinnerCropQuantity.setAdapter(arrayAdapterCropQuantity);

        spinnerCropQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stringCropQuanitySpinner = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }

    private void setUpCropRateSpinner()
    {
        ArrayList<String> units = new ArrayList<>();

//        units.add(0,"Unit");
        units.add("Rs/Kg");
        units.add("Rs/Quintle");
        units.add("Rs/Tonne");
        units.add("Rs/Dozen");


        //style the spinner

        ArrayAdapter<String> arrayAdapterCropQuantity = new ArrayAdapter(this,R.layout.spinner_item,units);


        //Dropdown layouto style
        arrayAdapterCropQuantity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //attaching data adapter to spinner
        spinnerCropRate.setAdapter(arrayAdapterCropQuantity);

        spinnerCropRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stringCropRateSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_collections,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.item1:
                startActivity(new Intent(this,SellingCropListFarmerActivity.class));
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
