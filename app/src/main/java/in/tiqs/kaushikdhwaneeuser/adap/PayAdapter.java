package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.ExpandableHeightGridView;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.act.ConnectionDetector;
import in.tiqs.kaushikdhwaneeuser.act.MainActivity;
import in.tiqs.kaushikdhwaneeuser.act.MyClass_data_adapter;
import in.tiqs.kaushikdhwaneeuser.act.PayFees;
import in.tiqs.kaushikdhwaneeuser.act.Reschedule;
import in.tiqs.kaushikdhwaneeuser.act.SubCat;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.models.Members_Model;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by TechIq on 2/14/2017.
 */

public class PayAdapter extends BaseAdapter {
    ArrayList<String> titles;
    ArrayList<String> images;
    ArrayList<String> session;
    ArrayList<String> attendence;
    ArrayList<Members_Model> name_user;



    Context context;
    boolean isNet;

    ArrayList<ArrayList<String>> days;
    ArrayList<ArrayList<String>> times;
    String count;
    ArrayList<String>amounts=new ArrayList<>();
    ArrayList<String>invoice_ids=new ArrayList<>();
    ArrayList<String>enroll_student_ids;
    int balance_amount=0;
   public PayAdapter(Context con, ArrayList<String> titles, ArrayList<String> images, ArrayList<String> session,ArrayList<String> attendence,
                     ArrayList<ArrayList<String>> days, ArrayList<ArrayList<String>> times, ArrayList<Members_Model> uname, ArrayList<String>enroll_student_ids)
    {
        this.context=con;
        //this.myclass_data_modell = myclass_data_models;
        this.titles=titles;
        this.images=images;
        this.days=days;
        this.name_user = uname;
        this.times=times;
        this.session = session;
        this.attendence=attendence;
        this.enroll_student_ids=enroll_student_ids;

        //this.count_class = class_count;
        //this.count_class =0;
    }




    @Override
    public int getCount()
    {
        //return myclass_data_modell.size();
        return titles.size();

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
//        Typeface tf= Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");

        View v = view;
        int i;


        if (v == null) {
            vh=new ViewHolder();
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.sub_cat_row, null);

//            vh.rl=(LinearLayout)v.findViewById(R.id.ofr_lay);
            vh.name = (TextView) v.findViewById(R.id.sc_title);
            vh.fl=(FrameLayout)v.findViewById(R.id.scimgView) ;
            vh.ivi=(ImageView)v.findViewById(R.id.scimgItem) ;
            vh.pay=(Button)v.findViewById(R.id.scat_pay);
            vh.rs=(Button)v.findViewById(R.id.scat_rschdul);
            vh.leave=(Button)v.findViewById(R.id.scat_aleave);
           /* vh.days=(TextView)v.findViewById(R.id.cday);
            vh.times=(TextView)v.findViewById(R.id.ctime);*/
            vh.c_count=(TextView)v.findViewById(R.id.class_count);
            vh.class_count_text=(TextView)v.findViewById(R.id.class_count_text);
            vh.u_name=(TextView)v.findViewById(R.id.user_name);
           vh.s_count=(TextView)v.findViewById(R.id.session_count);
            vh.a_count=(TextView)v.findViewById(R.id.attendence_count);
            vh.listView=(ExpandableHeightGridView)v.findViewById(R.id.class_slots);




//            vh.desc1=(TextView)v.findViewById(R.id.ofr_desc) ;
//            vh.desc2=(TextView)v.findViewById(R.id.ofr_sub);

            v.setTag(vh);


        }
        else{
            vh =   (ViewHolder) v.getTag();
        }

        try
        {
            vh.c_count.setText(""+days.get(position).size());
            if (days.get(position).size()==1)
            {
                vh.class_count_text.setText("Class/week");

            }else
            {
                vh.class_count_text.setText("Classes/week");
            }
        }catch (Exception e)
        {

        }

        //vh.listView.setAdapter(new ArrayAdapter<String>(context,R.layout.my_class_slot_item,days.get(position)));


        //vh.name.setText(myclass_data_modell.get(position).class_title);
        //vh.name.setText(myclass_data_modell.get(position).class_title);
        //vh.ivi.setText(myclass_data_modell.get(position).class_image);
//
        vh.name.setText(titles.get(position));

        try
        {

           /* vh.s_count.setText(session.get(position));*/
            vh.u_name.setText(name_user.get(position).getName());
            vh.listView.setAdapter(new ArrayAdapter<String>(context,R.layout.my_class_slot_item,days.get(position)));
             vh.s_count.setText("Total Sessions:"+" "+session.get(position));
            vh.a_count.setText("Sessions Attended:" +" "+ attendence.get(position));
        }catch (Exception e)
        {

        }

        //vh.c_count.setText(days.size());

        vh.fl.setBackgroundResource(R.drawable.ic_circle_flower);
        Glide.with(context).load(images.get(position))
                .placeholder(R.drawable.calss_default)
                .bitmapTransform(new CropCircleTransformation(context))


                // .error(R.drawable.profile60)
                //  .bitmapTransform(new RoundedCornersTransformation(HomeActivity.this,30,30))
                .into(vh.ivi);




        vh.leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof SubCat)
                {

                    final Dialog dialog = new Dialog(context);
                    // Include dialog.xml file
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.leave_popup);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    Button fifteen_days=(Button)dialog.findViewById(R.id.fifteen_days);
                    Button thierty_days=(Button)dialog.findViewById(R.id.thierty_days);

                    fifteen_days.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ((SubCat)context).calCal(1,enroll_student_ids.get(position));
                            dialog.dismiss();

                        } });

                            thierty_days.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    ((SubCat)context).calCal(2,enroll_student_ids.get(position));
                                    dialog.dismiss();

                                }
                            });
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                    window.setBackgroundDrawable(new ColorDrawable(
                            Color.WHITE));
                    //   window.setWindowAnimations(R.style.ActivityAnimations);
                    dialog.show();

                }
            }
        });

        vh.rs.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Reschedule.class));
            }
        });

        vh.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                balance_amount=0;

                if (new ConnectionDetector(context).isConnectingToInternet())
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("enroll_student_id",enroll_student_ids.get(position));
                        Log.e("data",enroll_student_ids+"kkjkjkjk"+"      "+jsonObject.toString());
                        get_balance_amount(jsonObject,context.getResources().getString(R.string.server)+ Constants.getpayments_due_service,position);

                    }catch (Exception e)
                    {

                    }
                }
                else
                {
                    Toast.makeText(context, "Please Check your internet connectivity", Toast.LENGTH_SHORT).show();
                }



            }
        });

        return v;

    }



    static class ViewHolder
    {
        TextView name,days,times,c_count,u_name,class_count_text,s_count,a_count;
        LinearLayout rl;
        ImageView ivi;
        FrameLayout fl;
        Button pay,rs,leave;
        ExpandableHeightGridView listView;

    }
    private  void get_balance_amount(JSONObject jo, String url,final int position)
    {
        CustomAsync ca=new CustomAsync(context, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    // Snackbar.make(findViewById(R.id.root), "Please Retry", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(context, "Please Retry", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getInt("success") == 1)
                        {
                            String s="";
                            for (int i=0;i<jsonObject.length()-2;i++)
                            {
                                JSONObject jsonObject1=jsonObject.getJSONObject(""+i);
                                amounts.add(jsonObject1.getString("balance_amount"));
                                invoice_ids.add(jsonObject1.getString("ionvoice_id"));
                                Log.e("abc",jsonObject1.getString("ionvoice_id"));

                            }

                            balance_amount=0;
                            Log.e("amoutnt",""+balance_amount);
                            for (int i=0;i<amounts.size();i++)
                            {
                                balance_amount=balance_amount+Integer.parseInt(amounts.get(i));
                            }

                            if (balance_amount>0)
                            {
                                context.startActivity(new Intent(context, PayFees.class)
                                        .putExtra("class_name",titles.get(position))
                                        .putExtra("enroll_student_id",enroll_student_ids.get(position))

                                );
                                ((Activity)context).finish();

                            }else
                            {
                                Toast.makeText(context,"You don't have any  dues", Toast.LENGTH_SHORT).show();

                            }
                            balance_amount=0;
                            //balance_amount=0;

                        }
                        else if (jsonObject.getInt("success") == 5)
                        {
                            Toast.makeText(context,"You don't have any  dues", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(context, jsonObject.getInt("success"), Toast.LENGTH_SHORT).show();
                        }
                        // getting XML



                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("Exception","del"+e);

                    }
                }
            }

        });
        ca.execute();
    }


}
