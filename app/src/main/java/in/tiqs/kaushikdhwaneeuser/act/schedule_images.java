package in.tiqs.kaushikdhwaneeuser.act;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.tiqs.kaushikdhwaneeuser.R;

public class schedule_images extends AppCompatActivity {
    ViewPager view_pager;
    SQLiteDatabase db;
    private static String DB_PATH = "";
    private static String DB_NAME ="SchoolParent";
    String dbpath,muty="",lstatus="",stu_ids,clstype="",gid,stid,sid;
    DownloadManager.Request request;
    DownloadManager mgr;
    private long enqueue;
    String [] images;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);


        // Drawable d=new ColorDrawable(Color.parseColor("#27547e"));

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PhotoView imageView = (PhotoView)findViewById(R.id.imageView);
       if (getIntent().getStringExtra("status").equals("0"))
       {
           imageView.setImageResource(R.drawable.kondapur);
           /*Glide
                   .with(schedule_images.this)
                   .load(R.drawable.kondapur)
                   .into(imageView);*/
       }else
       {

           imageView.setImageResource(R.drawable.nallagandla);
          /* Glide
               .with(schedule_images.this)
               .load(R.drawable.nallagandla)
               .into(imageView);
*/
       }





    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so l
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            // return true;
			/*Intent i=new Intent(Parent_Notifications.this,Parent
      	startActivity(i);*/
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        else  return false;


    }







}
