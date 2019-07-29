package in.tiqs.kaushikdhwaneeuser.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import in.tiqs.kaushikdhwaneeuser.Branches_model;
import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 3/8/2017.
 */

public class Bademo extends AppCompatActivity
{
    String class_id ="";
    EditText pr_name1,ul_num1, ul_p1,comments;
    Button submit;
    ArrayList<Branches_model> branches_models;

    ArrayList<String>b_names=new ArrayList<>();

    TextView branch, reg_fee,total_fee;

    String branch_id="",newss="",friends="",just_dials="",others="" ,internets="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bademo);
        pr_name1=(EditText)findViewById(R.id.pr_name1);
        ul_num1=(EditText)findViewById(R.id.ul_num1);
        ul_p1=(EditText)findViewById(R.id.ul_p1);
        comments=(EditText)findViewById(R.id.comments);
        submit=(Button) findViewById(R.id.submit);
        branch=(TextView) findViewById(R.id.reg_sbranch1);
        branches_models=new ArrayList<>();
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        class_id=getIntent().getStringExtra("id");
        Log.e("jsonObject",""+class_id);
        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();

                jsonObject.put("mobile","132");
                getBranches(jsonObject,getResources().getString(R.string.server)+Constants.getbranches_service);
            }catch ( Exception e)
            {
                Log.e("exception",""+e.toString());

            }
        }
        else
        {
            Toast.makeText(this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }




        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (pr_name1.getText().toString().trim().length()==0||
                        ul_num1.getText().toString().trim().length()==0
                        ||ul_p1.getText().toString().trim().length()==0)
                {
                    Toast.makeText(Bademo.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();

                }
                else if (new ConnectionDetector(Bademo.this).isConnectingToInternet())
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("name",pr_name1.getText().toString().trim());
                        jsonObject.put("mobile",ul_num1.getText().toString().trim());
                        jsonObject.put("email",ul_p1.getText().toString().trim());
                        jsonObject.put("class_id",class_id);
                        jsonObject.put("branch_id",branch_id);
                        jsonObject.put("comments",comments.getText().toString().trim());
                        Log.e("jsonObject",""+jsonObject.toString());

                        bookDemo(jsonObject,getResources().getString(R.string.server)+Constants.add_demo_class_service);
                    }
                    catch ( Exception e)
                    {
                        Log.e("exception",""+e.toString());

                    }
                }
                else
                {
                    Toast.makeText(Bademo.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                }

            }
        });

        }



    private  void bookDemo(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Bademo.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Bademo.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        String status=j.getString("success");
                        if(status.equals("1"))
                        {
                            startActivity(new Intent(Bademo.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            Toast.makeText(Bademo.this, "Your Demo Is booked Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else{

                            Toast.makeText(Bademo.this, ""+status, Toast.LENGTH_SHORT).show();
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
    private  void getBranches(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(Bademo.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(Bademo.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                        if(status==1)
                        {
                            for (int i=0;i<j.length()-1;i++)
                            {
                                JSONObject jsonObject=j.getJSONObject(""+i);
                                Branches_model branches_model=new Branches_model();
                                branches_model.setBranch_id(jsonObject.getString("id"));
                                branches_model.setBranch_name(jsonObject.getString("branch_name"));
                                if (!jsonObject.getString("registration_amount").equals(null))
                                {
                                    branches_model.setReg_fee(jsonObject.getString("registration_amount"));
                                }
                                else
                                {
                                    branches_model.setReg_fee("0");

                                }
                                b_names.add(jsonObject.getString("branch_name"));
                                branches_models.add(branches_model);

                            }
                            branch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {

                                    final Dialog dialog = new Dialog(Bademo.this);
                                    dialog.requestWindowFeature(1);
                                    dialog.setContentView(R.layout.branch_list_popup);
                                    ListView listView=(ListView)dialog.findViewById(R.id.list_branches);
                                    listView.setAdapter(new ArrayAdapter<String>(Bademo.this,android.R.layout.simple_dropdown_item_1line,b_names));
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                        {

                                            branch.setText(b_names.get(position));
                                            branch_id=""+branches_models.get(position).getBranch_id();
                                            dialog.dismiss();
                                        }
                                    });
                                    Window window = dialog.getWindow();

                                    window.setGravity(Gravity.CENTER);
                                    dialog.show();
                                }
                            });


                       //     Toast.makeText(Bademo.this, ""+status, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            Toast.makeText(Bademo.this, ""+status, Toast.LENGTH_SHORT).show();
                        }



                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("breamch_exception",""+e.toString());
                    }
                }
            }

        });
        ca.execute();
    }

}
