package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.PayAdapter;
import in.tiqs.kaushikdhwaneeuser.adap.SectionedRecyclerViewAdapter;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.models.Members_Model;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 2/24/2017.
 */

public class SubCat extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ListView lv;
    boolean isNet;
    View view;
    String u_name = "",UserId="";

	int position = 0;
    ArrayList<MyClass_data_adapter> myclass_data_modell;
    private PayAdapter payAdapter;
	public static final String TIME_FORMAT = "hh:mm aa";

    String[] titles={"Tabla Classes","Hindustani Flute"};
    ArrayList<String>enroll_student_ids;
    int leave_type;
    String en_idd;
    Button submit;

    ArrayList<Members_Model >members_models=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schd_pay);
        lv=(ListView)findViewById(R.id.sc_pay_lv);
       // lv.setAdapter(new PayAdapter(SubCat.this,titles));

        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubCat.this,Pay_Fees_2.class));
                finish();
            }
        });
        TextView title;
        title=(TextView)findViewById(R.id.ab2_title);
        title.setText("My Classes");

        ConnectionDetector cd = new ConnectionDetector(SubCat.this);
        isNet = cd.isConnectingToInternet();

        enroll_student_ids=new ArrayList<>();
        DataBase_Helper db = new DataBase_Helper(this);
        UserId = db.getUserId("1");
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        }
        );
        if (isNet)
        {
            try {

                JSONObject jo = new JSONObject();
                jo.put("user_id", UserId);
                Log.e("id",""+jo.toString());
                get_myclass_page(jo, getResources().getString(R.string.server) + Constants.getmyclasses_service);


               }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else {

            Snackbar snackBar = Snackbar.make(view, "No Internent Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finish();
                            startActivity(getIntent());
                        }
                    });
            snackBar.setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackBar.show();

        }

    }


    public void get_myclass_page (JSONObject jo, String url) {

        CustomAsync ca = new CustomAsync(SubCat.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {


                    Snackbar snackBar = Snackbar.make(SubCat.this.findViewById(R.id.main_content), "Please try Again!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View sbView = snackBar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackBar.show();

                } else {
                    try {

                        JSONObject jo = new JSONObject(result);
                        String status = jo.getString("success");
                        ArrayList<String> anames=new ArrayList<>();
                        ArrayList<String> alogos=new ArrayList<>();
                        ArrayList<String> asession=new ArrayList<>();
                        ArrayList<String> attendence=new ArrayList<>();
                        ArrayList<String> acids=new ArrayList<>();

                        if (status.equals("1"))
                        {

                            String  image_url = jo.getString("image_url");

                            HashMap<String,String> cnames=new HashMap<>();
                            HashMap<String,String> clogos=new HashMap<>();
                            //HashMap<String,String> csession=new HashMap<>();
                            ArrayList<String> snames_list=new ArrayList<>();
                            ArrayList<String> slogos_list=new ArrayList<>();
                            ArrayList<String> ssession_list=new ArrayList<>();

                            ArrayList<ArrayList<String >> adays=new ArrayList<>();
                            ArrayList<ArrayList<String >> atimes=new ArrayList<>();

                            ArrayList<String> days=new ArrayList<>();
                            ArrayList<String> times=new ArrayList<>();

                            JSONArray cjar=jo.getJSONArray("classes");
                            Log.e("eee","eee0");

                            for(int c=0;c<cjar.length();c++)
                            {
                                Log.e("eee","eee1");
                                JSONObject cj=cjar.getJSONObject(c);
                                Log.e("eee","eee2");

                                cnames.put(cj.getString("id"),cj.getString("class_name"));
                                clogos.put(cj.getString("id"),image_url+"/"+cj.getString("logo"));

                            }
                            JSONArray sjar=jo.getJSONArray("sessions");
                            Log.e("eee","eee0");

                            for(int c=0;c<sjar.length();c++) {
                                Log.e("eee", "eee1");
                                JSONObject sj = sjar.getJSONObject(c);
                                Log.e("eee", "eee2");

                                asession.add(sj.getString("total_sessions"));
                                Log.e("bee", "eeeb");
                            }
                            JSONArray ajar=jo.getJSONArray("attendence");
                            for(int c=0;c<ajar.length();c++) {
                                JSONObject aj = ajar.getJSONObject(c);
                                attendence.add(aj.getString("attendence"));
                                Log.e("bee", "eeeb");
                            }
                                //attendence.add(sj.getString("attendence"));
                                //Log.e("eee",sj.getString("attendence"));
                                //ssession_list.add(asession.toString().replace("[","").replace("]",""));
                                //String csession =asession.toString();


                               Log.e("attendence","attendence");

                            JSONArray ujar=jo.getJSONArray("members");
                            members_models.clear();
                            for(int members=0;members<ujar.length();members++)
                            {

                                JSONObject ujab = ujar.getJSONObject(members);
                                if (jo.has(ujab.getString("id")))
                                {
                                    JSONObject jsonObject= jo.getJSONObject(ujab.getString("id"));
                                    //Log.e("id",ujab.getString("id"));



                                    for (int m=0;m<jsonObject.length();m++)
                                    {
                                        Members_Model members_model=new Members_Model();
                                        members_model.setId(ujab.getString("id"));
                                        members_model.setName(ujab.getString("name"));
                                        members_models.add(members_model);
                                        u_name = ujab.getString("name");
                                        //u_name.put(ujab.getString("id"),ujab.getString("name"));
                                    }


                                    Log.e("arrays_keys",jsonObject.length()+"");

                                    Iterator<String> mkeys=jsonObject.keys();

                                    while(mkeys.hasNext())
                                    {
                                        String key=mkeys.next();
                                        JSONArray jsonArray=jsonObject.getJSONArray(key);
                                        JSONArray jsonArray1=jo.getJSONArray("classes");
                                        Log.e("arraysadd_keys",clogos.get(key)+"  "+cnames.get(key));
                                        snames_list.add(cnames.get(key));
                                        slogos_list.add(clogos.get(key));


                                    }


                                }
                            }

                            payAdapter=new PayAdapter(SubCat.this,snames_list,slogos_list, asession,attendence,adays,atimes,members_models,enroll_student_ids);
                            lv.setAdapter(payAdapter);
                            if (payAdapter.getCount()>0)
                            {
                                submit.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                submit .setVisibility(View.GONE);

                            }







                            Iterator<String> mkeys=jo.keys();
                            ArrayList<String> mkeys_list=new ArrayList<>();

                            while(mkeys.hasNext())
                            {

                                String key=mkeys.next();
                                mkeys_list.add(key);

                            }

                            for(int k=0;k<mkeys_list.size();k++)
                            {
                                if(!mkeys_list.get(k).equals("success")||!mkeys_list.get(k).equals("classes")
                                        ||!mkeys_list.get(k).equals("members")||!mkeys_list.get(k).equals("image_url")){

                                    JSONObject dobj=jo.getJSONObject(mkeys_list.get(k));
                                    Iterator<String> skeys=dobj.keys();
                                    ArrayList<String> skeys_list=new ArrayList<>();


                                    while(skeys.hasNext())
                                    {
                                        String key=skeys.next();
                                        skeys_list.add(key);
                                       /* snames_list.add(cnames.get(key));
                                        slogos_list.add(clogos.get(key));
*/
                                    }
                                  /*  ArrayList<ArrayList<String >> adays=new ArrayList<>();
                                    ArrayList<ArrayList<String >> atimes=new ArrayList<>();

									ArrayList<String> days=new ArrayList<>();
									ArrayList<String> times=new ArrayList<>();
*/
                                    Log.e("skeys_list","hh"+skeys_list);

                                    for(int s=0;s<skeys_list.size();s++)
                                    {
                                        JSONArray jar=dobj.getJSONArray(skeys_list.get(s));

                                        /*if(jar.equals(s)){

                                        }*/

                                        days.clear();
                                        times.clear();

                                        for(int m=0;m<jar.length();m++)
                                        {
                                            JSONObject job=jar.getJSONObject(m);
                                            if (job.getString("type").equals("1"))
                                            {
                                                days.add("Mon");

                                            }
                                            if (job.getString("type").equals("2"))
                                            {
                                                days.add("Tue");

                                            }
                                            if (job.getString("type").equals("3"))
                                            {
                                                days.add("Wed");

                                            }
                                            if (job.getString("type").equals("4"))
                                            {
                                                days.add("Thu");

                                            }
                                            if (job.getString("type").equals("5"))
                                            {
                                                days.add("Fri");

                                            }
                                            if (job.getString("type").equals("6"))
                                            {
                                                days.add("Sat");

                                            }
                                            if (job.getString("type").equals("7"))
                                            {
                                                days.add("Sun");

                                            }

                                            //days.addAll(times);
                                            //days.add(job.getString("type"));

                                            if (!enroll_student_ids.contains(job.getString("enroll_student_id")))enroll_student_ids.add(job.getString("enroll_student_id"));


                                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                            Date convertedDate = new Date();
                                            try {
                                                convertedDate = dateFormat.parse(job.getString("start_time"));
                                            } catch (ParseException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
                                            String finalDate = timeFormat.format(convertedDate);
                                            Log.e("date","hh"+finalDate);

                                            System.out.println(finalDate);

                                            ArrayList<String> final_time = new ArrayList<>();
                                            final_time.add(finalDate);
                                            //Log.e("time","ss"+final_time);
                                            times.add(finalDate);
                                        }


                                        //adays.add(days);
                                        atimes.add(times);


                                        ArrayList<String> mergedList = new ArrayList<>();
                                        mergedList.clear();
                                        if(days.size() == times.size())
                                        {
                                            int n = days.size();
                                            for(int index=0; index<n; index++)
                                            {

                                                mergedList.add(days.get(index) + " - " + times.get(index));

                                                Log.e("cs", "ad" + mergedList);

                                            }

                                            adays.add(mergedList);


                                        } else {
                                            System.out.println("Cannot combine");
                                            //Throw exception or take any action you need.
                                        }


                                    }



                                    Log.e("ff","sdf"+adays);

                                   /* payAdapter=new PayAdapter(SubCat.this,snames_list,slogos_list,adays,atimes,u_name,enroll_student_ids);
                                    lv.setAdapter(payAdapter);
*/
                                }

                            }


                        }
                        else
                        {

                            Toast.makeText(SubCat.this, "" + status, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e)
                    {

                        e.printStackTrace();
                        Log.e("Exception",e.toString()+"   "+e.getStackTrace()[0].getLineNumber());
                    }
                }

            }
        });
        ca.execute();
    }




    public void calCal(int pos,String en_id)
    {
        leave_type=pos;
        en_idd=en_id;
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                SubCat.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(false);
        dpd.setMinDate(now);
        dpd.setYearRange(2017,2017);

        dpd.show(getFragmentManager(), "Datepickerdialog");




    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "You picked the following date: From- "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
        //  dateTextView.setText(date);
        Log.e("sdate",""+date);

        if (new ConnectionDetector(SubCat.this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();

                jsonObject.put("user_id",UserId);
                jsonObject.put("start_date",year+"-"+monthOfYear+"-"+dayOfMonth);
                jsonObject.put("type",""+leave_type);
                jsonObject.put("enroll_student_id",en_idd);
                Log.e("jsonObject",""+jsonObject.toString());

                ApplyforLeave(jsonObject,getResources().getString(R.string.server)+Constants.leaves_service);
            }
            catch ( Exception e)
            {
                Log.e("exception",""+e.toString());

            }
        }
        else
        {
            Toast.makeText(SubCat.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }

    }
    private  void ApplyforLeave(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(SubCat.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(SubCat.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {
                            //startActivity(new Intent(SubCat.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                            Toast.makeText(SubCat.this, "Your leave request is submitted for approval. You will be notified shortly", Toast.LENGTH_SHORT).show();

                        }
                        else if (status==3)
                        {
                            Toast.makeText(SubCat.this, "Your have already  applied for leave", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                            Toast.makeText(SubCat.this, ""+status, Toast.LENGTH_SHORT).show();
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
