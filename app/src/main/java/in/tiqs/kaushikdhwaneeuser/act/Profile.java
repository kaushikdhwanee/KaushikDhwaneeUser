package in.tiqs.kaushikdhwaneeuser.act;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.Manifest;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 2/23/2017.
 */

public class Profile extends AppCompatActivity {
    ImageView profile_image;
    TextView user_name,edit_profile;
    EditText name,mobile_num,email;
    Button submit;
    SharedPreferences member_datails;
    private int FILE_SELECT_CODE = 1;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView my_profile;
    private Uri filePath;
    ImageView back,edit;
    String picturePath,type;
    ConnectionDetector cd;
    static boolean isInternentAvailable;
    String Uid,mobile;
    TextView namee,mobilee,address,address_count,orders_count;
    Bitmap  mBitmap,resized;
    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prfl);

        TextView title;
        title=(TextView)findViewById(R.id.ab2_title);
        title.setText("Profile");


        profile_image = (ImageView) findViewById(R.id.pimg1);
        user_name = (TextView) findViewById(R.id.user_name);
        edit_profile = (TextView) findViewById(R.id.edit_profile);
        name = (EditText) findViewById(R.id.pr_name1);
        mobile_num = (EditText) findViewById(R.id.ul_num1);
        email = (EditText) findViewById(R.id.ul_email1);
        submit = (Button) findViewById(R.id.p_submit);
        name.setEnabled(false);
        mobile_num.setEnabled(false);
        email.setEnabled(false);
        member_datails=getSharedPreferences("member_datails", Context.MODE_PRIVATE);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isStoragePermissionGranted())
                {
                    showFileChooser();

                } else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                    builder.setMessage("You can't use this app without granting this permission")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                                }
                            });
                    builder.create();
                }


            }
        });

        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("user_id",new DataBase_Helper(this).getUserId("1"));
                get_profile(jsonObject, getResources().getString(R.string.server)+Constants.get_userprofile_service);

            }
            catch (Exception e)
            {
                Log.e("exception",e.toString());
            }

        }else
        {
            Toast.makeText(Profile.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (new ConnectionDetector(Profile.this).isConnectingToInternet())
                {
                    try
                    {

                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("user_id",new DataBase_Helper(Profile.this).getUserId("1"));
                        jsonObject.put("name",name.getText().toString().trim());
                        jsonObject.put("email",email.getText().toString().trim());
                        jsonObject.put("member_id",member_datails.getString("member_id","0"));
                        Log.e("data",jsonObject.toString());
                        Update_profile(jsonObject, getResources().getString(R.string.server)+Constants.update_profile_service);

                    }
                    catch (Exception e)
                    {
                        Log.e("exception",e.toString());
                    }

                }else
                {
                    Toast.makeText(Profile.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                }

            }
        });
        submit.setVisibility(View.GONE);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                name.setEnabled(true);
                email.setEnabled(true);
                submit.setVisibility(View.VISIBLE);

            }
        });

    }
    private void showFileChooser() {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try
        {
            startActivityForResult(i, FILE_SELECT_CODE);
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }


        //return false;



    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }

        //return false;


    }
    public  boolean isStoragePermissionGranted1() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else {
            return true;
        }

        //return false;


    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1:
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                    showFileChooser();

                }
            case 2:
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                   // writeToFile(getStringImage(resized),this);
                    Log.e("Exceptionw", "File write failed: " );
                   // writeToFile(getEncoded64ImageStringFromBitmap(mBitmap));

                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE) {


            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    Uri selectedImage = data.getData();


                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        Log.e("resized",selectedImage+""+mBitmap.getHeight());

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }





                     Bitmap resized = Bitmap.createScaledBitmap(mBitmap,(int)(mBitmap.getWidth()*0.3), (int)(mBitmap.getHeight()*0.3), true);
                    Log.e("resized",resized.getWidth()+""+resized.getHeight());
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);

                    if (new ConnectionDetector(Profile.this).isConnectingToInternet())
                    {
                        try
                        {



                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("user_id",new DataBase_Helper(Profile.this).getUserId("1"));
                            jsonObject.put("member_id",member_datails.getString("member_id","0"));
                            jsonObject.put("profile_pic",getEncoded64ImageStringFromBitmap(resized));
                            Log.e("jsaon)data",jsonObject.toString());
                            Upload_profile_pic(jsonObject,getResources().getString(R.string.server)+Constants.update_profile_picture_service);
                        }catch (Exception e)
                        {
                            Log.e("Exception ",e.toString());
                        }

                    }
                    else
                    {
                        Toast.makeText(Profile.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                    }




                    type = getContentResolver().getType(selectedImage);

                    if (type.equals(null)) type = "images/jpeg";

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    try{
                        File file = new File(picturePath);
                        long length = file.length();
                        length = length/1024;
                        Log.e("File_Path : " , file.getPath() + ", File size : " + length +" KB");
                    }catch(Exception e)
                    {
                        System.out.println("File not found : " + e.getMessage() + e);
                    }


                     if (picturePath.isEmpty() || picturePath == null) {
                        Toast.makeText(this, "Unable to capture the image..Please try again", Toast.LENGTH_LONG).show();

                    } else
                     {

                    }
                } else {
                    Toast.makeText(this,
                            "Image Loading Failed", Toast.LENGTH_LONG)
                            .show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(this,
                        "User cancelled file upload", Toast.LENGTH_LONG)
                        .show();
            } else {
                // failed to capture image

                Toast.makeText(this,
                        "Sorry! Failed to load file", Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

    public void writeToFile(String data)
    {
        Log.e("Exception", "File write failed: " );

        // Get the directory for the user's public pictures directory.
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                Environment.DIRECTORY_DCIM + "/parvez/"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        final File file = new File(path, "config.txt");

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private  void get_profile(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Profile.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Profile.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {

                            user_name.setText(j.getString("name"));
                            name.setText(j.getString("name"));
                                    mobile_num.setText(j.getString("mobile"));
                            email.setText(j.getString("email"));
                            Glide.with(Profile.this)
                                    .load(j.get("profile_pic"))
                                    .placeholder(R.drawable.user_default)
                                    .into(profile_image);

                            SharedPreferences.Editor editor=member_datails.edit();
                            editor.putString("member_id",j.getString("member_id"));
                            editor.commit();

                        }
                        else if(status==5)
                        {

                            Toast.makeText(Profile.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                         else
                        {

                                Toast.makeText(Profile.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        ca.execute();
    }
    private  void Upload_profile_pic(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Profile.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Profile.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {

                            profile_image.setImageBitmap(mBitmap);

                        }
                        else if(status==5)
                        {

                            Toast.makeText(Profile.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                         else
                        {

                                Toast.makeText(Profile.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        ca.execute();
    }
    private  void Update_profile(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Profile.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Profile.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {
                            if (new ConnectionDetector(Profile.this).isConnectingToInternet())
                            {
                                try
                                {
                                    name.setEnabled(false);
                                    mobile_num.setEnabled(false);
                                    email.setEnabled(false);
                                    if (submit.getVisibility()==View.VISIBLE)
                                    {
                                        submit.setVisibility(View.GONE);
                                    }

                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put("user_id",new DataBase_Helper(Profile.this).getUserId("1"));
                                    get_profile(jsonObject, getResources().getString(R.string.server)+Constants.get_userprofile_service);

                                }
                                catch (Exception e)
                                {
                                    Log.e("exception",e.toString());
                                }

                            }else
                            {
                                Toast.makeText(Profile.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                            }



                        }
                        else if(status==5)
                        {

                            Toast.makeText(Profile.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                         else
                        {

                                Toast.makeText(Profile.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        ca.execute();
    }

}
