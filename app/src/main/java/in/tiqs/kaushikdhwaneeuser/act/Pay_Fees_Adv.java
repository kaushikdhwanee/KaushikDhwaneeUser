package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atom.mobilepaymentsdk.PayActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.Pending_fees_adv_adapter;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.models.Advance_amount_model;
import in.tiqs.kaushikdhwaneeuser.receivers.MyFirebaseInstanceIDService;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

public class Pay_Fees_Adv extends AppCompatActivity implements Pending_fees_adv_adapter.advance_fees_listener
{
    ArrayList<Advance_amount_model> advance_amount_models;

    ListView pending_payments;
    Pending_fees_adv_adapter pending_fees_adv_adapter;
    CheckBox select;
    RelativeLayout submitt;
    LinearLayout header2;
    LinearLayout heading;
    //TextView header3;
    RelativeLayout advance;
    SharedPreferences sp ;
    LinearLayout header3;
    Button pay_now;
    Button pay_adv;
    TextView pfb_tamt;

    ArrayList<String>invoice_ids,amounts,enroll_student_id,start_dates,end_dates,total_session,classes;

  //  tv1.setText( months[Integer.parseInt(jsonObject1.getString("invoice_month"))-1]+""+jsonObject1.getString("invoice_year"));
  String [] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("abdfge","work");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_adv);

        sp =getSharedPreferences(MyFirebaseInstanceIDService.pref, Context.MODE_PRIVATE);
        invoice_ids=new ArrayList<>();
        amounts=new ArrayList<>();
        enroll_student_id=new ArrayList<>();


        advance_amount_models=new ArrayList<>();
        pending_payments=(ListView)findViewById(R.id.pending_payments);
        select=(CheckBox) findViewById(R.id.select);
        pay_now=(Button) findViewById(R.id.submit);
        pfb_tamt=(TextView) findViewById(R.id.pfb_tamt);
        //pay_adv=(Button)findViewById(R.id.pay_adv);
     //   header1=(RelativeLayout) findViewById(R.id.header1);
        //advance=(RelativeLayout) findViewById(R.id.submit_adv);
        header2=(LinearLayout) findViewById(R.id.header2);
       // heading=(LinearLayout) findViewById(R.id.heading);
        header2.setBackgroundColor(Color.parseColor("#00aeef"));
        submitt=(RelativeLayout)findViewById(R.id.submitt);
        select.setVisibility(View.GONE);
        //pay_adv.setVisibility(View.GONE);
        //header3=(LinearLayout)findViewById(R.id.header3);
        //header3.setVisibility(View.GONE);


        for (int i=0;i<header2.getChildCount();i++)
        {
            if (header2.getChildAt(i) instanceof TextView)
            {

                TextView textView=(TextView) header2.getChildAt(i);
                textView.setTextColor(Color.WHITE);
                textView.setText("Advance Payments");
            }else if (header2.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout textView=(LinearLayout) header2.getChildAt(i);
                for (int j=0;j<header2.getChildCount();j++)
                {
                    if (textView.getChildAt(j) instanceof TextView)
                    {

                        TextView textVieww=(TextView) textView.getChildAt(j);
                        textVieww.setTextColor(Color.WHITE);
                        textVieww.setText("Pending Payments");
                    }
                }
             }
              }

        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("user_id",new DataBase_Helper(this).getUserId("1"));
                Log.e("data",jsonObject.toString());
                get_advance_fees(jsonObject,getResources().getString(R.string.server)+ Constants.get_price_advance_service);
                Log.e("data",jsonObject.toString());
            }catch (Exception e)
            {

            }
        }
        else
        {
            Toast.makeText(Pay_Fees_Adv.this, "Please Check your internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }
    public void get_advance_fees (JSONObject jo, String url)
    {

        CustomAsync ca = new CustomAsync(Pay_Fees_Adv.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(Pay_Fees_Adv.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Log.e("check","check");
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);
                         Log.e("checkin","checkin");
                        int  Status = jo.getInt("success");
                        ArrayList<String> asession=new ArrayList<>();
                        //asession.add("0");
                        if (Status==1)
                        {

                            JSONArray jsonArray1=jo.getJSONArray("sessioncount");


                            Log.e("",jsonArray1.length()+"");

                            for (int j=0;j<jsonArray1.length();j++){

                                JSONObject aj = jsonArray1.getJSONObject(j);

                                    asession.add(aj.getString("attendence"));
                                    Log.e("", "asession");



                                //advance_amount_models.add(advance_amt_model);
                            }
                            JSONArray jsonArray=jo.getJSONArray("data");

                            Log.e("jsonArraylength",jsonArray.length()+"  ");
                            advance_amount_models.clear();
                            Log.e("jsonArray1","jsonArray1");
                            String total_sessions;


                            for (int i=0;i<jsonArray.length();i++)
                            {

                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                Log.e("jsonArray3","jsonArray3");
                                //JSONArray  jsonArray1=jo.getJSONArray(jsonObject.getString("enroll_id"));
                                Log.e("jsonArray3","jsonArray3");
                                //for (int j=0;j<jsonArray1.length();j++) {
                                    //JSONObject jsonObject1=jsonArray1.getJSONObject(i);
                                    Log.e("jsonArray2","jsonArray2");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    //Date start = new Date();
                                    Date start = new Date();

                                    String start_date = "";
                                    String end = "";
                                    Log.e("abnkcde", "pending_amount_model");
                                    String sessions = jsonObject.getString("total_sessions");
                                    String plan = jsonObject.getString("plan");
                                    //String due_date = jsonObject.getString("end_date");
                                Date due = dateFormat.parse(jsonObject.getString("end_date"));

                                    Calendar c = Calendar.getInstance();
                                    Log.e("abcdef", "pending_amount_model");
                                    c.setTime(due);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(start);
                                    //cal.add(Calendar.DATE, 1);
                                    start_date = dateFormat.format(cal.getTime());
                                    Log.e("abb", plan);

                                    if (plan.equals("1") || plan.equals("2")) {
                                        Log.e("abc", "pending_amount_model");
                                        c.add(Calendar.MONTH, 3);
                                        Log.e("abc", "pending_amount_model");
                                        end = dateFormat.format(c.getTime());
                                        Log.e("ab", "pending_amount_model");
                                        // Log.e("xyz",start);

                                    }
                                    if (plan.equals("3") || plan.equals("4")) {
                                        c.add(Calendar.MONTH, 6);
                                        end = dateFormat.format(c.getTime());
                                        // Log.e("xyz", start);
                                    }

                                    // Log.e("plan",plan);

                                    Advance_amount_model advance_amount_model = new Advance_amount_model();
                                    Log.e("abcde", "pending_amount_model");
                                    advance_amount_model.setStudent_name(jsonObject.getString("name"));
                                    advance_amount_model.setClass_name_amount(jsonObject.getString("class_name"));
                                    advance_amount_model.setStart_date(start_date);
                                    advance_amount_model.setEnd_date(end);
                                    Log.e("ab#cde", "pending_amount_models");
                                    // pending_amount_model.setTotal_amount(jsonObject.getString("name"));
                                    advance_amount_model.setTotal_amount(jsonObject.getString("course_fee"));
                                    advance_amount_model.setPenidn_amount(jsonObject.getString("course_fee"));
                                    advance_amount_model.setPlan(jsonObject.getString("plan"));
                                    //advance_amount_model.setInvoice_id(jsonObject.getString("invoice_id"));
                                    advance_amount_model.setTotal_sessions(jsonObject.getString("total_sessions"));
                                    advance_amount_model.setSessions_week(jsonObject.getString("sessions_per_week"));
                                    //advance_amount_model.setInvoice_month(months[Integer.parseInt(jsonObject.getString("invoice_month")) - 1]);
                                    //advance_amount_model.setInvoice_year(jsonObject.getString("invoice_year"));
                                    advance_amount_model.setEnroll_student_id(jsonObject.getString("enroll_student_id"));
                                    Log.e("abcde", "pending_amount_model");
                                    advance_amount_model.setSelection_status(false);

                                    if (!jsonObject.getString("course_fee").equals("0")) {
                                        advance_amount_models.add(advance_amount_model);
                                    }
                                //}
                            }


                            pending_fees_adv_adapter=new Pending_fees_adv_adapter(Pay_Fees_Adv.this,advance_amount_models,asession);
                            Log.e("","goto");
                            pending_fees_adv_adapter.setCustomButtonListner(Pay_Fees_Adv.this);
                            pending_payments.setAdapter(pending_fees_adv_adapter);

                            pay_now.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try
                                    {
                                        if (!pfb_tamt.getText().toString().equals("0.0"))
                                        {



                                            Date dNow = new Date();
                                            SimpleDateFormat ft = new SimpleDateFormat("yy/MM/dd hh:mm:ss");
                                            String datetime = ft.format(dNow);
/*

                                            Intent newPayIntent = new Intent(Pay_Fees_2.this, PayActivity.class);
                                            newPayIntent.putExtra("merchantId", "41735");
                                            newPayIntent.putExtra("txnscamt", "0");
                                            newPayIntent.putExtra("loginid", "41735");
                                            newPayIntent.putExtra("password", "KAUSHIK@123");
                                            newPayIntent.putExtra("prodid", "KAUSHIK");
                                            newPayIntent.putExtra("txncurr", "INR");
                                            newPayIntent.putExtra("clientcode", "001");
                                            newPayIntent.putExtra("custacc", "100000036600");
                                            newPayIntent.putExtra("amt", "51");
                                            newPayIntent.putExtra("txnid", "013");
                                            newPayIntent.putExtra("date", datetime);
                                            newPayIntent.putExtra("customerEmailID",sp.getString("email",""));
                                            newPayIntent.putExtra("discriminator", "NBFundTransfer");
                                            newPayIntent.putExtra("ru","https://payment.atomtech.in/mobilesdk/param");
                                            newPayIntent.putExtra("customerMobileNo", new DataBase_Helper(Pay_Fees_2.this).getUserMobileNumber("1"));

                                            Log.e("PaytmData", newPayIntent.getExtras().toString());
                                            startActivityForResult(newPayIntent, 1);

*/

                                            //  newPayIntent.putExtra("ru", "https://paynetzuat.atomtech.in/mobilesdk/param");
                                            // newPayIntent.putExtra("customerName", "JKL PQR");
                                            // newPayIntent.putExtra("customerEmailID", "jkl.pqr@atomtech.in");

                                            // newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 1");
                                            // newPayIntent.putExtra("mprod", mprod);


                    /*                        Intent newPayIntent = new Intent(Pay_Fees_2.this, PayActivity.class);
                                            newPayIntent.putExtra("merchantId", "459");
                                            newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
                                            newPayIntent.putExtra("loginid", "459");
                                            newPayIntent.putExtra("password", "Test@123");
                                            newPayIntent.putExtra("prodid", "NSE");
                                            newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
                                            newPayIntent.putExtra("clientcode", "007");
                                            newPayIntent.putExtra("custacc", "100000036600");
                                            newPayIntent.putExtra("channelid", "INT");
                                            newPayIntent.putExtra("amt",  pfb_tamt.getText().toString().trim());//Should be 3 decimal number i.e 100.000
                                            newPayIntent.putExtra("txnid", "2365F315");
                                            newPayIntent.putExtra("date", datetime);//Should be in same format
                                            newPayIntent.putExtra("cardtype", "DC");// CC or DC ONLY (value should be same as commented)
                                            newPayIntent.putExtra("cardAssociate",  "VISA");// for VISA and MASTER.  MAESTRO ONLY (value should be same as commented)
                                            newPayIntent.putExtra("surcharge", "NO");// Should be passed YES if surcharge is applicable else pass NO
                                            newPayIntent.putExtra("ru", "https://paynetzuat.atomtech.in/mobilesdk/param");

                                            newPayIntent.putExtra("customerName", "khusaki");//Only for Name
                                            // newPayIntent.putExtra("customerEmailID", "pqr.lmn@atomtech.in");//Only for Email ID
                                            newPayIntent.putExtra("customerMobileNo", "9550479500");//Only for Mobile Number
                                            newPayIntent.putExtra("billingAddress", "Hyderabad");//Only for Address
                                            newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");// Can pass any data
                                            //  newPayIntent.putExtra("mprod", "mprod");  // Pass data in XML format, only for Multi product
                                            Log.e("PaytmData", newPayIntent.getExtras().toString());
                                            startActivityForResult(newPayIntent, 1);
*/

                                            Intent newPayIntent = new Intent(Pay_Fees_Adv.this, PayActivity.class);
                                            newPayIntent.putExtra("merchantId","41735");
                                            newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
                                            newPayIntent.putExtra("loginid", "41735");
                                            newPayIntent.putExtra("password","Kaushik$2510");//"f5c42702");
                                            newPayIntent.putExtra("portno", "443");
                                            newPayIntent.putExtra("prodid", "KAUSHIK");
                                            newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
                                            newPayIntent.putExtra("clientcode", "007");
                                            newPayIntent.putExtra("custacc", "32200984908");
                                            // newPayIntent.putExtra("amt", "50");//Should be 3 decimal number i.e 100.000
                                            newPayIntent.putExtra("amt",pfb_tamt.getText().toString().trim());//Should be 3 decimal number i.e 100.000
                                            newPayIntent.putExtra("txnid", "2365F315");
                                            newPayIntent.putExtra("date", datetime);//Should be in same format
                                            //newPayIntent.putExtra("ttype", "NBFundTransfer");
                                            newPayIntent.putExtra("customerEmailID",sp.getString("email",""));  //newPayIntent.putExtra("cardtype", "DC");// CC or DC ONLY (value should be same as commented)
                                            // newPayIntent.putExtra("cardAssociate", "VISA");// for VISA and MASTER.  MAESTRO ONLY (value should be same as commented)
                                            //  newPayIntent.putExtra("surcharge", "NO");// Should be passed YES if surcharge is applicable else pass NO
                                            newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param");
                                            newPayIntent.putExtra("customerMobileNo",new DataBase_Helper(Pay_Fees_Adv.this).getUserMobileNumber("1"));
                                            newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");// Can pass any data
                                            newPayIntent.putExtra("discriminator", "All"); //NB or IMPS or All ONLY (value should be same as commented)
                                            newPayIntent.putExtra("signature_request", "30b9415cb7ee313262");
                                            newPayIntent.putExtra("signature_response", "00e7291c96efb88dc1");
                                            Log.e("PaytmData", newPayIntent.getExtras().toString());
                                            startActivityForResult(newPayIntent, 1);


               /* Card Number:5555 5555 5555 4444
                Expiry 12/16
                Cvv:123*/

                                        }
                                        else
                                        {
                                            Toast.makeText(Pay_Fees_Adv.this,"Select fees to be paid", Toast.LENGTH_SHORT).show();
                                          //  finish();
                                        }
                                    }catch (Exception e)
                                    {
                                        Log.e("Exception",e.toString());
                                    }

                                }
                            });



                            Toast.makeText(Pay_Fees_Adv.this, "", Toast.LENGTH_SHORT).show();

                        }

                        else if (Status==5){

                            Log.e("ElseCondition", "WhatHappen"+ Status);
                           //advance.setVisibility(View.VISIBLE);

                           Toast.makeText(Pay_Fees_Adv.this, "Please Try Again ", Toast.LENGTH_SHORT).show();
                            }
                    }catch (Exception e) {

                        e.printStackTrace();
                    }


                }

            }

        });ca.execute();
    }
    @Override
    public   void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (data != null)
            {

                String stats="";
                String message = data.getStringExtra("status");
                Log.e("PaytmData", message);
                String[] resKey = data.getStringArrayExtra("responseKeyArray");
                String[] resValue = data.getStringArrayExtra("responseValueArray");
                if(resKey!=null && resValue!=null)
                {
                    JSONObject jsonObject=new JSONObject();
                    for(int i=0; i<resKey.length; i++)
                    {
                        if (resKey[i].equals("f_code"))
                        {
                            stats=resValue[i];
                        }
                        try {
                            jsonObject.put(resKey[i],resValue[i]);
                            Log.e("dtaaar",resKey[i]+" resValue : "+resValue[i]+"kkkkk   "+stats);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                    try
                    {
                        if (stats.equals("success_00")||stats.equals("Ok"))

                        {

                            SharedPreferences member_datails=getSharedPreferences("member_datails", Context.MODE_PRIVATE);
                        jsonObject.put("user_id", new DataBase_Helper(Pay_Fees_Adv.this).getUserId("1"));
                        jsonObject.put("total_amount",pfb_tamt.getText().toString().trim());
                        jsonObject.put("final_amount", pfb_tamt.getText().toString().trim());
                        jsonObject.put("member_id", member_datails.getString("member_id","0"));
                        jsonObject.put("invoice_id",invoice_ids.toString().replace("]","").replace("[","").replace(" ",""));
                        jsonObject.put("amount", amounts.toString().replace("]","").replace("[","").replace(" ",""));
                        jsonObject.put("enroll_student_id", enroll_student_id.toString().replace("]","").replace("[","").replace(" ",""));
                        jsonObject.put("payment_through", "2");
                        jsonObject.put("start_dates", start_dates.toString().replace("]","").replace("[",""));
                        jsonObject.put("end_dates", end_dates.toString().replace("]","").replace("[",""));
                        jsonObject.put("total_session", total_session.toString().replace("]","").replace("[",""));
                        jsonObject.put("classes", classes.toString().replace("]","").replace("[",""));

                            historyData(jsonObject, getResources().getString(R.string.server)+Constants.payment_success_service);

                        }else if (stats.equals("F"))
                        {
                            Toast.makeText(Pay_Fees_Adv.this, "Payment declined please retry..", Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("dtaJson",jsonObject.toString());


                }

           /* 5597 2500 4001 6999
                12/20
                        943*/


            }
        }



    }
    public void historyData (JSONObject jo, String url) {

        CustomAsync ca = new CustomAsync(Pay_Fees_Adv.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(Pay_Fees_Adv.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);

                        String Status = jo.getString("success");
                        if (Status.equals("1"))
                        {


                            Intent intent = new Intent(Pay_Fees_Adv.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                            Toast.makeText(Pay_Fees_Adv.this, "Your Payment is completed successfully", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            //Log.e("ElseCondition", "WhatHappen"+ Status);

                           /* Toast.makeText(Cart_Page.this, ""+Status, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Cart_Page.this, Cart_Page.class);
                            startActivity(intent);
                       */ }
                    }catch (Exception e) {

                        e.printStackTrace();
                    }


                }

            }

        });ca.execute();
    }



    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Pay_Fees_Adv.this,SubCat.class));
        finish();
    }


    @Override
    public void setCustomButtonListner(ArrayList<String> amounts, ArrayList<String> invoice_ids, ArrayList<String> enrollment_ids,ArrayList<String> start_dates,ArrayList<String> end_dates,ArrayList<String> total_session,ArrayList<String> classes,double tot) {
        this. invoice_ids =invoice_ids ;
        this. amounts=amounts;
        this.enroll_student_id=enrollment_ids;
        this.start_dates=start_dates;
        this.end_dates=end_dates;
        this.total_session=total_session;
        this.classes = classes;
        Log.e("dtaJson", this. invoice_ids.toString()+ "\n iddddd "+
                        " amounttt \n"+ this. amounts);


pfb_tamt.setText(""+tot);
    }
}
