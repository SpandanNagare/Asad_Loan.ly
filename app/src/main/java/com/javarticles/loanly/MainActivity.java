package com.javarticles.loanly;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;
    Button selectphoto1;
    Button selectphoto2;
    Button selectphoto3;
    Button selectphoto4;
    TextView image1data;
    TextView image2data;
    TextView image3data;
    TextView image4data;
    Button submit;
    Bitmap[] sampleimages;
    String[] sampleimagesinfo;

    int imagenumber=-1;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextEmailAddress);
        phone=findViewById(R.id.editTextPhone);
        selectphoto1=findViewById(R.id.buttonimage1);
        selectphoto2=findViewById(R.id.buttonimage2);
        selectphoto3=findViewById(R.id.buttonimage3);
        selectphoto4=findViewById(R.id.buttonimage4);
        image1data=findViewById(R.id.textViewimage1);
        image2data=findViewById(R.id.textViewimage2);
        image3data=findViewById(R.id.textViewimage3);
        image4data=findViewById(R.id.textViewimage4);
        submit=findViewById(R.id.submit);

        sampleimages= new Bitmap[]{null, null, null, null};
        sampleimagesinfo=new String[]{null,null,null,null};

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_function();
            }
        });

        selectphoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber=0;
                capture_function();
            }
        });

        selectphoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber=1;
                capture_function();
            }
        });

        selectphoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber=2;
                capture_function();
            }
        });

        selectphoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber=3;
                capture_function();
            }
        });
    }

    void submit_function(){

    }
    void capture_function(){
        final int REQUEST_IMAGE_CAPTURE = 1;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                        "com.javarticles.loanly.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }


    //creating a temporary file for image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        //storing filename for display
        if(imagenumber!=-1){
            sampleimagesinfo[imagenumber]=imageFileName+".jpg ";
        }

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
