package in.tiqs.kaushikdhwaneeuser.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.PayAdapter;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 3/3/2017.
 */

public class MyCal extends AppCompatActivity {
    MaterialCalendarView widget;
    TextView my_class;
    boolean isNet;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycal);

        TextView title = (TextView) findViewById(R.id.ab2_title);
        title.setText("My Calendar");
        widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        my_class = (TextView) findViewById(R.id.my_class);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DATE);
        ConnectionDetector cd = new ConnectionDetector(MyCal.this);
        isNet = cd.isConnectingToInternet();
        widget.setMinimumDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
        //setContentView(widget);
        /*widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                Log.e("selected_date",date+""+widget.getSelectedDate().toString().substring(12,20));



            }
        });*/

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        //my_class.setText(dayOfTheWeek);

        Log.e("selected_date",widget.getFirstDayOfWeek()+""+widget.getSelectedDate().toString());
        if (isNet) {

            try {

                JSONObject jo = new JSONObject();
                jo.put("user_id", "23");
                Log.e("id",jo.toString());
                getMy_cal(jo, getResources().getString(R.string.server) + Constants.getmyclasses_service);


            }catch (Exception e)
            {
                Log.e("id",e.toString());

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
    public void getMy_cal (JSONObject jo, String url) {

        CustomAsync ca = new CustomAsync(MyCal.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {


                    Snackbar snackBar = Snackbar.make(MyCal.this.findViewById(R.id.main_content), "Please try Again!", Snackbar.LENGTH_INDEFINITE)
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
                        ArrayList<String> acids=new ArrayList<>();

                        if (status.equals("1"))
                        {
                            String  image_url = jo.getString("image_url");

                            JSONArray cjar=jo.getJSONArray("classes");
                            HashMap<String,String> cnames=new HashMap<>();
                            HashMap<String,String> clogos=new HashMap<>();

                            for(int c=0;c<cjar.length();c++)
                            {
                                JSONObject cj=cjar.getJSONObject(c);
                                cnames.put(cj.getString("id"),cj.getString("class_name"));
                                clogos.put(cj.getString("id"),image_url+"/"+cj.getString("logo"));

                            }
                            JSONArray ujar=jo.getJSONArray("members");

                            JSONObject ujab = ujar.getJSONObject(0);
                           // u_name = ujab.getString("name");
                            //u_name.put(ujab.getString("id"),ujab.getString("name"));


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
                                    ArrayList<String> snames_list=new ArrayList<>();
                                    ArrayList<String> slogos_list=new ArrayList<>();

                                    while(skeys.hasNext())
                                    {
                                        String key=skeys.next();
                                        skeys_list.add(key);
                                        snames_list.add(cnames.get(key));
                                        slogos_list.add(clogos.get(key));

                                    }
                                    ArrayList<ArrayList<String >> adays=new ArrayList<>();
                                    ArrayList<ArrayList<String >> atimes=new ArrayList<>();


                                    ArrayList<String> days=new ArrayList<>();
                                    ArrayList<String> times=new ArrayList<>();

                                    for(int s=0;s<skeys_list.size();s++)
                                    {
                                        JSONArray jar=dobj.getJSONArray(skeys_list.get(s));


                                        /*if(jar.equals(s)){

                                        }*/

                                        for(int m=0;m<jar.length();m++)
                                        {
                                            JSONObject job=jar.getJSONObject(m);
                                            if (job.getString("type").equals("1"))
                                            {
                                                days.add("Mon");

                                            }
                                            if (job.getString("type").equals("2"))
                                            {
                                                days.add("Tus");

                                            }
                                            if (job.getString("type").equals("3"))
                                            {
                                                days.add("Wed");

                                            }
                                            if (job.getString("type").equals("4"))
                                            {
                                                days.add("Thus");

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





                                            //times.add(job.getString("start_time"));
                                            //times = dateFormat.format(job.get("start_time"));
                                            //times.add(job.get("dateFormat").toString());
                                            //+" - "+job.getString("end_time"));

											/*List<String> days = new ArrayList<>();
											List<String> times = new ArrayList<>();*/



                                        }


                                        //adays.add(days);
                                        atimes.add(times);


                                        ArrayList<String> mergedList = new ArrayList<>();
                                        mergedList.clear();
                                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                                        Date d = new Date();
                                        String dayOfTheWeek = sdf.format(d);
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

                                        //my_class.setText(adays.toString());

                                    }



                                    Log.e("ff","sdf"+adays);

                                }

                            }

                        } else {

                            Toast.makeText(MyCal.this, "" + status, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            }
        });
        ca.execute();
    }


}
