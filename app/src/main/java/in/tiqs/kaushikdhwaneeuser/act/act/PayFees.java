package in.tiqs.kaushikdhwaneeuser.act.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.act.ConnectionDetector;
import in.tiqs.kaushikdhwaneeuser.act.MainActivity;
import in.tiqs.kaushikdhwaneeuser.act.SubCat;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 3/3/2017.
 */

public class PayFees extends AppCompatActivity  {
    LinearLayout llay;
    String []s_names={"Tabla Classes","Hindustani classes"};
    String []amt={"Rs.1100/-","Rs.1000/-"};
    Button b;
    String class_name,enroll_student_id;
    TextView header,pfb_tamt,pfb_payamt;
    LayoutInflater inflater;
    HashMap<String, String> paramMap = new HashMap<String, String>();
    String [] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String UserId = "", dt = "", url = "", WUrl = "", OrderId="", Amount="", Response="";
ArrayList<String>amounts=new ArrayList<>();
ArrayList<String>invoice_ids=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payfee);

        header=(TextView)findViewById(R.id.header);
        pfb_tamt=(TextView)findViewById(R.id.pfb_tamt);
        pfb_payamt=(TextView)findViewById(R.id.pfb_payamt);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               startActivity(new Intent(PayFees.this,SubCat.class));
                finish();
            }
        });

        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText("Pay Fees");

        llay=(LinearLayout)findViewById(R.id.pf_slist_lay);
         inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        b=(Button)findViewById(R.id.pn_btn);

         class_name=getIntent().getStringExtra("class_name");
         enroll_student_id=getIntent().getStringExtra("enroll_student_id");
        header.setText(class_name+" balance amount");

        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("enroll_student_id",enroll_student_id);
                Log.e("data",jsonObject.toString());
                get_balance_amount(jsonObject,getResources().getString(R.string.server)+ Constants.getpayments_due_service);
            }catch (Exception e)
            {

            }
        }
        else
        {
            Toast.makeText(PayFees.this, "Please Check your internet connectivity", Toast.LENGTH_SHORT).show();
        }


/*
        for(int i=0;i<s_names.length;i++)
        {

            View v = inflater.inflate(R.layout.slist_row, null);
            if (v != null) {
                TextView tv1 = (TextView) v.findViewById(R.id.wdfs_name);
                tv1.setText(s_names[i]);

                TextView tv2 = (TextView) v.findViewById(R.id.wdfs_samt);
                tv2.setText(amt[i]);


                llay.addView(v);
            }
        }
*/


    }
    private  void get_balance_amount(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(PayFees.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    // Snackbar.make(findViewById(R.id.root), "Please Retry", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(PayFees.this, "Please Retry", Toast.LENGTH_LONG).show();
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

                                View v = inflater.inflate(R.layout.slist_row, null);
                                if (v != null)
                                {
                                    TextView tv1 = (TextView) v.findViewById(R.id.wdfs_name);
                                    tv1.setText( months[Integer.parseInt(jsonObject1.getString("invoice_month"))-1]+""+jsonObject1.getString("invoice_year"));

                                    TextView tv2 = (TextView) v.findViewById(R.id.wdfs_samt);
                                    tv2.setText(jsonObject1.getString("balance_amount"));
                                    s=jsonObject1.getString("balance_amount");

                                    llay.addView(v);
                                 }

                                 }
                             int balance_amount=0;
                            for (int i=0;i<amounts.size();i++)
                            {
                                 balance_amount=balance_amount+Integer.parseInt(amounts.get(i));
                            }
                            pfb_tamt.setText(""+balance_amount);
                            pfb_payamt.setText(""+balance_amount);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)

                                {
                                    try
                                    {
                                        if (!pfb_tamt.getText().toString().equals("0"))
                                    {


                                        Date dNow = new Date();
                                        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
                                        String datetime = ft.format(dNow);


                                        JSONObject jsonObject_checksum = new JSONObject();

                                        jsonObject_checksum.put("ORDER_ID", datetime);
                                        jsonObject_checksum.put("MID", "GowebN54473481353909");
                                        jsonObject_checksum.put("CUST_ID", new DataBase_Helper(PayFees.this).getUserId("1"));
                                        jsonObject_checksum.put("CHANNEL_ID", "WAP");
                                        jsonObject_checksum.put("INDUSTRY_TYPE_ID", "Retail");
                                        jsonObject_checksum.put("TXN_AMOUNT", pfb_payamt.getText().toString().trim());
                                        jsonObject_checksum.put("WEBSITE", "APP_STAGING");
                                        jsonObject_checksum.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
                                        jsonObject_checksum.put("EMAIL", "absabc@gmail.com");
                                        jsonObject_checksum.put("MOBILE_NO", new DataBase_Helper(PayFees.this).getUserMobileNumber("1"));
                                        Log.e("PaytmData", jsonObject_checksum.toString());
                                        generate_checksum(jsonObject_checksum, getResources().getString(R.string.server) + "generatechecksum_service", datetime, "0");

                                    }
                                    else
                                    {
                                        Toast.makeText(PayFees.this,"You don't have any  dues", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    }catch (Exception e)
                                    {
                                        Log.e("Exception",e.toString());
                                    }

                                }
                            });



                        }
                        else if (jsonObject.getInt("success") == 5)
                        {
                            Toast.makeText(PayFees.this,"You don't have any  dues", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        else
                        {
                            Toast.makeText(PayFees.this, jsonObject.getInt("success"), Toast.LENGTH_SHORT).show();
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
    public void generate_checksum (JSONObject jo, String url,final String order_id,final String tot) {

        CustomAsync ca = new CustomAsync(PayFees.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(PayFees.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);

                        String Status = jo.getString("payt_STATUS");
                        if (Status.equals("1"))
                        {

                            // paramMap.put("REQUEST_TYPE", "DEFAULT");






                            paramMap.put("ORDER_ID", order_id);
                            paramMap.put("MID", "GowebN54473481353909");
                            paramMap.put("CUST_ID",new DataBase_Helper(PayFees.this).getUserId("1"));
                            paramMap.put("CHANNEL_ID", "WAP");
                            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                            paramMap.put("WEBSITE", "APP_STAGING");
                            paramMap.put("TXN_AMOUNT",pfb_payamt.getText().toString().trim());
                            //paramMap.put("THEME", "merchant");
                            // paramMap.put("EMAIL", "parvezmd48@gmail.com");
                            //paramMap.put("MOBILE_NO", "9908819653");
                            paramMap.put("CHECKSUMHASH",jo.getString("CHECKSUMHASH"));
                            paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
                            paramMap.put("MOBILE_NO",new DataBase_Helper(PayFees.this).getUserMobileNumber("1"));
                            paramMap.put("EMAIL","absabc@gmail.com");

                            Log.e("PaytmData", paramMap.toString());

                            PaytmPGService Service = PaytmPGService.getStagingService();

                            PaytmOrder Order = new PaytmOrder(paramMap);

                            PaytmMerchant Merchant = new PaytmMerchant("http://fudyz.com/users/generatechecksum_service", "http://fudyz.com/users/verifychecksum_service");

                            Service.initialize(Order, null);

                            Service.startPaymentTransaction(PayFees.this, true, true, new PaytmPaymentTransactionCallback() {
                                @Override
                                public void onTransactionResponse(Bundle bundle)
                                {

                                    Log.e("bundlebundle", bundle.toString());
                                    OrderId = bundle.getString("ORDERID");
                                    Amount = bundle.getString("TXNAMOUNT");
                                    Response = bundle.getString("RESPMSG");
                                    String  RESPCODE = bundle.getString("RESPCODE");


                                    if (RESPCODE.equals("01"))
                                    {
                                        try
                                        {
                                            SharedPreferences member_datails=getSharedPreferences("member_datails", Context.MODE_PRIVATE);


                                            JSONObject jo = new JSONObject();
                                            jo.put("GATEWAYNAME", bundle.getString("GATEWAYNAME"));
                                            jo.put("TXNDATE", bundle.getString("TXNDATE"));
                                            jo.put("PAYMENTMODE", bundle.getString("PAYMENTMODE"));
                                            jo.put("MID", bundle.getString("MID"));
                                            jo.put("ORDERID", bundle.getString("ORDERID"));
                                            jo.put("CURRENCY", bundle.getString("CURRENCY"));
                                            jo.put("TXNID", bundle.getString("TXNID"));
                                            jo.put("TXNAMOUNT", bundle.getString("TXNAMOUNT"));
                                            jo.put("IS_CHECKSUM_VALID", bundle.getString("IS_CHECKSUM_VALID"));
                                            jo.put("BANKTXNID", bundle.getString("BANKTXNID"));
                                            jo.put("BANKNAME", bundle.getString("BANKNAME"));
                                            jo.put("RESPMSG", bundle.getString("RESPMSG"));
                                            jo.put("RESPCODE", bundle.getString("RESPCODE"));
                                            jo.put("STATUS", bundle.getString("STATUS"));
                                            jo.put("CHECKSUMHASH", bundle.getString("CHECKSUMHASH"));
                                            jo.put("user_id", "23");
                                            jo.put("total_amount",pfb_tamt.getText().toString().trim());
                                            jo.put("final_amount", pfb_payamt.getText().toString().trim());
                                            jo.put("member_id", member_datails.getString("member_id","0"));
                                            jo.put("invoice_id",invoice_ids.toString());
                                            jo.put("amount", amounts.toString());
                                            jo.put("enroll_student_id", enroll_student_id);
                                            Log.e("PaytmData", jo.toString());

                                            historyData(jo, getResources().getString(R.string.server)+Constants.payment_success_service);


                                        }catch (Exception e) {

                                            e.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(PayFees.this, "Your payment transaction failed please retry", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void networkNotAvailable() {

                                }

                                @Override
                                public void clientAuthenticationFailed(String s) {

                                }

                                @Override
                                public void someUIErrorOccurred(String s) {

                                }

                                @Override
                                public void onErrorLoadingWebPage(int i, String s, String s1) {

                                }

                                @Override
                                public void onBackPressedCancelTransaction() {

                                }

                                @Override
                                public void onTransactionCancel(String s, Bundle bundle) {

                                }
                            });


                        }

                        else
                        {

                            //Log.e("ElseCondition", "WhatHappen"+ Status);
                        }
                    }catch (Exception e) {

                        e.printStackTrace();
                    }


                }

            }

        });ca.execute();
    }


    public void historyData (JSONObject jo, String url) {

        CustomAsync ca = new CustomAsync(PayFees.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(PayFees.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);

                        String Status = jo.getString("success");
                        if (Status.equals("1"))
                        {


                            Intent intent = new Intent(PayFees.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                            Toast.makeText(PayFees.this, "Your Payment is completed successfully", Toast.LENGTH_SHORT).show();

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
        startActivity(new Intent(PayFees.this,SubCat.class));
        finish();
    }
}
