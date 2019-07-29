package in.tiqs.kaushikdhwaneeuser.act;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.ItypeAdapter;
import in.tiqs.kaushikdhwaneeuser.adap.SmAdapter;
import in.tiqs.kaushikdhwaneeuser.animations.Animation;
import in.tiqs.kaushikdhwaneeuser.animations.SlideInUnderneathAnimation;
import in.tiqs.kaushikdhwaneeuser.animations.SlideOutUnderneathAnimation;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;
import in.tiqs.kaushikdhwaneeuser.utils.RowItem;

/**
 * Created by TechIq on 2/28/2017.
 */

public class FeedBack extends AppCompatActivity {

    Button b1,b2,submit;
    EditText description;
    ImageView iv1,iv2;
    ListView lv1;
    boolean aa=true;
    private List<RowItem> rowItems;
    LinearLayout l1lay;
    int issue_type = 0;
    static {
        // AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb);
        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText("Feedback");
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        description=(EditText)findViewById(R.id.description);
        submit=(Button)findViewById(R.id.submt);
        b1=(Button)findViewById(R.id.itype);
        b2=(Button)findViewById(R.id.sclass_fb);
       // iv1=(ImageView)findViewById(R.id.spin1_lv);
        iv2=(ImageView)findViewById(R.id.spin2_lv);
        lv1=(ListView)findViewById(R.id.spin1_lv);
        l1lay=(LinearLayout)findViewById(R.id.spin1_lay);
        rowItems = new ArrayList<RowItem>();
        String[] menutitles = getResources().getStringArray(R.array.itype_data);
        int[] menuIcons={R.drawable.ic_fiber_manual_record_white_24px,R.drawable.ic_fiber_manual_record_white_24px,R.drawable.ic_fiber_manual_record_white_24px,R.drawable.ic_fiber_manual_record_white_24px};
        // places = savedInstanceState.getString("PLACES");
        for (int i = 0; i < menutitles.length; i++)
        {
            RowItem items = new RowItem(menutitles[i],menuIcons[i]);
            rowItems.add(items);
        }
        ItypeAdapter adapter = new ItypeAdapter(getApplicationContext(), rowItems);

        lv1.setAdapter(adapter);


        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(l1lay.getVisibility()==View.GONE)
                {
                    l1lay.setVisibility(View.VISIBLE);


//                    new SlideInUnderneathAnimation(l1lay).setDirection(
//                            Animation.DIRECTION_UP).setDuration(1000).animate();
                }

            }
        });



        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                issue_type=(i+1);
                l1lay.setVisibility(View.GONE);
                TextView c = (TextView) view.findViewById(R.id.fbi_title);
                b1.setText(c.getText().toString());


            }
        });



//        iv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                iv1.setVisibility(View.GONE);
////                new SlideOutUnderneathAnimation(iv1).setDirection(
////                        Animation.DIRECTION_UP).setDuration(1000).animate();
//
//            }
//        });
       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                if(iv2.getVisibility()==View.GONE)
                {
                    iv2.setVisibility(View.VISIBLE);
                }
                else
                    iv2.setVisibility(View.GONE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (b1.getText().toString().trim().equals("Issue Type")||
                        description.getText().toString().trim().length()==0)
                {
                    Toast.makeText(FeedBack.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if (new DataBase_Helper(FeedBack.this).getUserCount()!=0)
                 {
                if (new ConnectionDetector(FeedBack.this).isConnectingToInternet())
                {
                    try
                    {


                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("user_id",new DataBase_Helper(FeedBack.this).getUserId("1"));
                        jsonObject.put("support_type",""+issue_type);
                        jsonObject.put("description",description.getText().toString().trim());
                        Log.e("jsonObject",""+jsonObject.toString());
                        send_feedback(jsonObject,getResources().getString(R.string.server)+ Constants.feedback_service);

                    }catch (Exception  e)
                    {
                        Log.e("Exception",""+e.toString());
                    }
                }else
                {
                    Toast.makeText(FeedBack.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                }
                }
                 else
                 {
                     Toast.makeText(FeedBack.this, "Please login", Toast.LENGTH_SHORT).show();

                 }
                }



        });

    }
    private  void send_feedback(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(FeedBack.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(FeedBack.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {

                            Toast.makeText(FeedBack.this, "Thanks for your valuable feedback", Toast.LENGTH_SHORT).show();
finish();
                        }
                        else if(status==5)
                        {

                            Toast.makeText(FeedBack.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(FeedBack.this, ""+status, Toast.LENGTH_SHORT).show();

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
