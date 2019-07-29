package in.tiqs.kaushikdhwaneeuser.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.data_base.User;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.data_base.User;
import in.tiqs.kaushikdhwaneeuser.receivers.MyFirebaseInstanceIDService;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

public class Login_page extends AppCompatActivity

{
    String  user_registration_status,uid, mobile, email,Trip_id, Trip_id1, Datee, Type;
  EditText login_mobile;
         Button submit;
    TextView register;
    SharedPreferences sp,sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login_mobile=(EditText)findViewById(R.id.login_mobile);
        submit=(Button)findViewById(R.id.submit);
        register=(TextView) findViewById(R.id.register);

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (login_mobile.getText().toString().length()==10)
                {


                if (new ConnectionDetector(Login_page.this).isConnectingToInternet())
                {
                   try
                   {
                       final SharedPreferences sp = getSharedPreferences(MyFirebaseInstanceIDService.pref, Context.MODE_PRIVATE);
                       final String Notification = sp.getString("Notification", "");
                       JSONObject jsonObject=new JSONObject();
                       jsonObject.put("mobile",login_mobile.getText().toString().trim());
                       jsonObject.put("gcm_id",Notification);
                       jsonObject.put("device_id","");
                       Login(jsonObject,getResources().getString(R.string.server)+ Constants.login);
                   }catch (Exception e)
                   {
                       Log.e("Exception",e.toString());

                   }


                }
                else
                {
                    Toast.makeText(Login_page.this, "Please check your internet connecivity", Toast.LENGTH_SHORT).show();
                }
                } else
                {
                    Toast.makeText(Login_page.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login_page.this,Reg.class));
                finish();


            }
        });


    }
    private  void Login(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Login_page.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Login_page.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                            uid= j.getString("id");
                            mobile=j.getString("mobile");
                            email=j.getString("email");


                        if(status==1)

                        {


                            DataBase_Helper dh = new DataBase_Helper(Login_page.this);
                            int c = dh.getUserCount();
                            if (c == 0)
                            {
                                dh.insertUserId(new User("1", uid, mobile));
                            } else
                            {
                                dh.updatetUserId(new User("1", uid, mobile));
                            }
                            List<User> li = dh.getUserData();
                            for (User u : li)
                            {
                                String log = "username" + " " + u.getName() + " mobile " + u.getMobile() + " user id " + u.getUid();
                                //     Log.e("db data",log);
                            }

                            SharedPreferences.Editor editor=sp2.edit();
                            editor.putString("email",email);
                            editor.commit();

                            Intent intent = new Intent(Login_page.this, MainActivity.class);
                            startActivity(intent);

                            //unregisterReceiver(broadcastReceiver);
                            finish();














                            /*startActivity(new Intent(Login_page.this,Otp_page.class)
                           .putExtra("mobile",j.getString("mobile"))
                           .putExtra("otp",""+j.getInt("otp"))
                           .putExtra("uid",""+j.getInt("id"))
                           .putExtra("email",""+j.getString("email"))
                           );
                            finish();*/
                        }
                        else if(status==3)
                        {

                            Toast.makeText(Login_page.this, "Not an existing user please register", Toast.LENGTH_SHORT).show();
                        } else if(status==4)
                        {

                            Toast.makeText(Login_page.this, "Not an existing user please register", Toast.LENGTH_SHORT).show();
                        }
                        else if(status==5)
                        {

                            Toast.makeText(Login_page.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(Login_page.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("asdfghh",""+e.toString());
                    }
                }
            }

        });
        ca.execute();



}

}
