package in.tiqs.kaushikdhwaneeuser.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.NotifAdapter;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 2/27/2017.
 */

public class Notif extends AppCompatActivity {
    ListView lv;
    String []names={"Raj","Rahul","Chandu"};
    ArrayList<String> messages,dates;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif);
        ImageView back=(ImageView)findViewById(R.id.back);
        messages=new ArrayList<>();
        dates=new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("user_id",new DataBase_Helper(this).getUserId("1"));

                get_notifications(jsonObject,getResources().getString(R.string.server)+ Constants.getnotifications_service);
            }
            catch (Exception e)
            {
                Log.e("Exception",e.toString());

            }

        }else {
            Toast.makeText(Notif.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }

        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText(getString(R.string.notif));
        lv=(ListView)findViewById(R.id.notif_lv);
        lv.setHorizontalScrollBarEnabled(false);
        lv.setVerticalScrollBarEnabled(false);

    }
    private  void get_notifications(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Notif.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Notif.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {
                         for (int i=0;i<j.length()-1;i++)
                         {
                             JSONObject jsonObject=j.getJSONObject(""+i);

                             messages.add(jsonObject.getString("message"));
                             dates.add(jsonObject.getString("created_on"));
                         }
                            Collections.reverse(messages);
                            Collections.reverse(dates);

                            lv.setAdapter(new NotifAdapter(Notif.this,messages,dates));


                        }
                        else if(status==5)
                        {

                            Toast.makeText(Notif.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(Notif.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        Log.e("Exception",e.toString());

                    }
                }
            }

        });
        ca.execute();
    }

}
