package in.tiqs.kaushikdhwaneeuser.frag;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atom.mobilepaymentsdk.PayActivity;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.tiqs.kaushikdhwaneeuser.Branches_model;
import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.act.ConnectionDetector;
import in.tiqs.kaushikdhwaneeuser.act.MainActivity;
import in.tiqs.kaushikdhwaneeuser.act.act.Terms_Condicctions;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.data_base.User;
import in.tiqs.kaushikdhwaneeuser.receivers.MyFirebaseInstanceIDService;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * Created by TechIq on 3/8/2017.
 */

public class RegFrag extends Fragment {

    View v;
    ArrayList<Branches_model> branches_models;
    ArrayList<String>b_names=new ArrayList<>();
    SharedPreferences member_datails;
    HashMap<String, String> paramMap = new HashMap<String, String>();
    String Promotional_id="",Promotional_amount ="",stats="",strCardType="",OrderId="", Amount="",Response="",strPaymentMode="";
    SharedPreferences sp ;
    static {
        // AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public RegFrag() {
        // Required empty public constructor
    }
    boolean user_status = true;

    TabLayout payment_mode,type;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    EditText student_name,mobile_no, whtapp_nom, email,age,organization_name,apply_coupon,other_input;
    TextView branch, reg_fee,total_fee,apply,terms_con;
    String branch_id="",newss="",friends="",just_dials="",others="" ,internets="";
    CheckBox news,friend,just_dial,other,internet,tandC;
    JSONObject reg_data;
    Button submit;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.reg, container, false);
        submit = (Button) v.findViewById(R.id.reg_submit);
        sp = getActivity().getSharedPreferences(MyFirebaseInstanceIDService.pref, Context.MODE_PRIVATE);

        terms_con=(TextView)v.findViewById(R.id.terms_con);
        tandC=(CheckBox) v.findViewById(R.id.tandC);
        submit.setVisibility(View.GONE);
        tandC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    submit.setVisibility(View.VISIBLE);

                }
                else
                {
                    submit.setVisibility(View.GONE);

                }

            }
        });
        terms_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Terms_Condicctions.class));
            }
        });
        news=(CheckBox)v.findViewById(R.id.news);
        friend=(CheckBox)v.findViewById(R.id.friend);
        just_dial=(CheckBox)v.findViewById(R.id.just_dial);
        other=(CheckBox)v.findViewById(R.id.other);
        internet=(CheckBox)v.findViewById(R.id.internet);
        member_datails=getActivity().getSharedPreferences("member_datails", Context.MODE_PRIVATE);

        other_input = (EditText) v.findViewById(R.id.other_input);
        apply_coupon = (EditText) v.findViewById(R.id.apply_coupon);
        student_name = (EditText) v.findViewById(R.id.reg_name1);
        mobile_no = (EditText) v.findViewById(R.id.reg_num1);
        whtapp_nom = (EditText) v.findViewById(R.id.reg_wnum1);
        email = (EditText) v.findViewById(R.id.reg_email1);
        age = (EditText) v.findViewById(R.id.reg_age1);
        organization_name = (EditText) v.findViewById(R.id.reg_oname1);
        branch = (TextView) v.findViewById(R.id.reg_sbranch1);
        reg_fee = (TextView) v.findViewById(R.id.fee);
        total_fee = (TextView) v.findViewById(R.id.total);
        apply = (TextView) v.findViewById(R.id.apply);
        branches_models=new ArrayList<>();

        payment_mode=(TabLayout)v. findViewById(R.id.payment_mode);
        type=(TabLayout)v. findViewById(R.id.type);

        other_input.setVisibility(View.GONE);
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    other_input.setVisibility(View.VISIBLE);
                    other_input.setHint("How you know about Us?");
                } else
                {
                    other_input.setVisibility(View.GONE);

                }
            }
        });
        friend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    other_input.setVisibility(View.VISIBLE);
                    other_input.setHint("Enter your Friend name");
                } else
                {
                    other_input.setVisibility(View.GONE);

                }
            }
        });

/*
        payment_mode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tab.getPosition()==2)
                {
                    type.setVisibility(View.GONE);
                }else
                {
                    type.setVisibility(View.VISIBLE);

                }



            }





            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                if (tab.getPosition()==2)
                {
                    type.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/






        if (new ConnectionDetector(getActivity()).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();

                jsonObject.put("mobile",mobile_no.getText().toString().trim());
                getBranches(jsonObject,getResources().getString(R.string.server)+Constants.getbranches_service);
            }catch ( Exception e)
            {
                Log.e("exception",""+e.toString());

            }
        }
        else
        {
            Toast.makeText(getActivity(), "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }

        mobile_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (mobile_no.getText().toString().trim().length()==10)
                {
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet())
                    {
                        try
                        {
                            JSONObject jsonObject=new JSONObject();

                            jsonObject.put("mobile",mobile_no.getText().toString().trim());
                            checkUserMobileNumber(jsonObject,getResources().getString(R.string.server)+Constants.checkusermobile_service);
                        }catch ( Exception e)
                        {
                            Log.e("exception",""+e.toString());

                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!reg_fee.getText().toString().trim().equals("0.0"))
                {

                    if (new ConnectionDetector(getActivity()).isConnectingToInternet())
                    {
                        try
                        {
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("total_amount",reg_fee.getText().toString().trim());
                            jsonObject.put("promocode",apply_coupon.getText().toString().trim());
                            validate_coupon(jsonObject,getResources().getString(R.string.server)+
                                    Constants.apply_coupon);

                        }
                        catch (Exception e)
                        {
                            Log.e("Exception",e.toString());

                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(), "Please select branch first", Toast.LENGTH_SHORT).show();

                }
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.e("wrd",friend.getText().toString().trim()+"erd"+news.getText().toString().trim());
                String s= email.getText().toString().toString();
                if (student_name.getText().toString().trim().equals("") ||
                        mobile_no.getText().toString().trim().equals("")||
                        !whtapp_nom.getText().toString().trim().equals("")&&
                                whtapp_nom.getText().toString().trim().length()<10||
                        email.getText().toString().trim().equals("")||
                        age.getText().toString().trim().equals("")||
                        reg_fee.getText().toString().trim().equals("0.0")||
                        total_fee.getText().toString().trim().equals("0.0"))
                {

                    Snackbar.make(v,"Fields are empty", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view)

                                {

                                }
                            })
                            .show();
                }
                else  if (!email.getText().toString().toString().matches(emailPattern))
                {
                    Snackbar.make(v,"Invalid email id ", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view)

                                {

                                }
                            })
                            .show();
                }else  if (user_status==false)
                {
                    Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   /* name',"email","mobile","address","age","branch_id","whatsapp_number",
                    "organization_name","known_from","registration_amount",gcm_id, device_id,
                            promotional_id,promotional_amount,final_amount;*/
                    prepare_reg_data();
                    generate_checksumm();
                }

            }
        });
        return v;
    }
    public void prepare_reg_data()
    {
        String Notification = sp.getString("Notification", "");
        StringBuilder stringBuilder=new StringBuilder();
        if (news.isChecked())stringBuilder.append(news.getText().toString().trim()).append(",");
        if (friend.isChecked())stringBuilder.append(friend.getText().toString().trim()).append(",");
        if (just_dial.isChecked())stringBuilder.append(just_dial.getText().toString()).append(",");
        if (other.isChecked())stringBuilder.append(other.getText().toString().trim()).append(",");
        if (internet.isChecked())stringBuilder.append(internet.getText().toString().trim()).append(",");
        reg_data = new JSONObject();
        try
        {
            reg_data.put("name", student_name.getText().toString().trim());
            reg_data.put("mobile", mobile_no.getText().toString().trim());
            reg_data.put("whatsapp_number", whtapp_nom.getText().toString().trim());
            reg_data.put("email", email.getText().toString().trim());
            reg_data.put("age", age.getText().toString().trim());
            reg_data.put("organization_name", organization_name.getText().toString().trim());
            reg_data.put("branch_id",branch_id);
            reg_data.put("known_from",stringBuilder.toString());
            reg_data.put("registration_amount", reg_fee.getText().toString().trim());
            reg_data.put("gcm_id",Notification );
            reg_data.put("device_id", "");
            reg_data.put("final_amount",total_fee.getText().toString().trim());
            reg_data.put("promotional_id",Promotional_id);
            reg_data.put("promotional_amount",Promotional_amount);
            reg_data.put("payment_through","2");
            reg_data.put("other_info",other_input.getText().toString().trim());
            Log.e("reg_data",""+reg_data.toString());


            //reg_send_data(jsonObject,getResources().getString(R.string.server) + Constants.user_registration_service);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
    public void generate_checksumm()
    {
        try
        {
        if (!total_fee.getText().toString().trim().equals("0"))
        {

            prepare_reg_data();

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yy/MM/dd- hh:mm:ss");
            String datetime = ft.format(dNow);

              /*  Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
                newPayIntent.putExtra("merchantId", "459");
                newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
                newPayIntent.putExtra("loginid", "459");
                newPayIntent.putExtra("password", "Test@123");
                newPayIntent.putExtra("prodid", "NSE");
                newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
                newPayIntent.putExtra("clientcode", "007");
                newPayIntent.putExtra("custacc", "100000036600");
                newPayIntent.putExtra("channelid", "INT");
                newPayIntent.putExtra("amt",  total_fee.getText().toString().trim());//Should be 3 decimal number i.e 100.000
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
                startActivityForResult(newPayIntent, 1);*/


            Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
            newPayIntent.putExtra("merchantId","41735");
            newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
            newPayIntent.putExtra("loginid", "41735");
            newPayIntent.putExtra("password","Kaushik$2510");//"f5c42702");
            newPayIntent.putExtra("prodid", "KAUSHIK");
            newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
            newPayIntent.putExtra("clientcode", "007");
            newPayIntent.putExtra("portno", "443");
            newPayIntent.putExtra("custacc", "32200984908");
            // newPayIntent.putExtra("amt", "50");//Should be 3 decimal number i.e 100.000
            newPayIntent.putExtra("amt",  total_fee.getText().toString().trim());//Should be 3 decimal number i.e 100.000
            newPayIntent.putExtra("txnid", "2365F315");
            newPayIntent.putExtra("date", datetime);//Should be in same format
            newPayIntent.putExtra("ttype", "NBFundTransfer");
            newPayIntent.putExtra("customerEmailID",email.getText().toString().trim());  //newPayIntent.putExtra("cardtype", "DC");// CC or DC ONLY (value should be same as commented)
            // newPayIntent.putExtra("cardAssociate", "VISA");// for VISA and MASTER.  MAESTRO ONLY (value should be same as commented)
            //  newPayIntent.putExtra("surcharge", "NO");// Should be passed YES if surcharge is applicable else pass NO
            newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param");
            newPayIntent.putExtra("customerMobileNo",mobile_no.getText().toString().trim());
            newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");// Can pass any data
            newPayIntent.putExtra("discriminator", "All"); //NB or IMPS or All ONLY (value should be same as commented)
            newPayIntent.putExtra("signature_request", "30b9415cb7ee313262");
            newPayIntent.putExtra("signature_response", "00e7291c96efb88dc1");
            Log.e("abc","i am here ------------------------");
            Log.e("PaytmData", newPayIntent.getExtras().toString());

            startActivityForResult(newPayIntent, 1);
            Log.e("abc","i am done ------------------------");

               /* Card Number:5555 5555 5555 4444
                Expiry 12/16
                Cvv:123*/

        }
        else
        {
            Toast.makeText(getActivity(), "Select Branch", Toast.LENGTH_SHORT).show();
        }
        }catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }

    }

               /* if (payment_mode.getSelectedTabPosition()==0)
                {
                    strPaymentMode="CC";

                }else if (payment_mode.getSelectedTabPosition()==1)
                {
                    strPaymentMode="DC";
                }


                if (type.getSelectedTabPosition()==0)
                {
                    strCardType="VISA";

                }else if (type.getSelectedTabPosition()==1)
                {
                    strCardType="MASTER";
                }else if (type.getSelectedTabPosition()==2)
                {
                    strCardType="MAESTRO ONLY";
                }

                Date dNow = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yy/MM/dd- hh:mm:ss");
                String datetime = ft.format(dNow);

                Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
                newPayIntent.putExtra("merchantId", "459");
                newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
                newPayIntent.putExtra("loginid", "459");
                newPayIntent.putExtra("password", "Test@123");
                newPayIntent.putExtra("prodid", "NSE");
                newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
                newPayIntent.putExtra("clientcode", "007");
                newPayIntent.putExtra("custacc", "100000036600");
                newPayIntent.putExtra("channelid", "INT");
                newPayIntent.putExtra("amt",  total_fee.getText().toString().trim());//Should be 3 decimal number i.e 100.000
                newPayIntent.putExtra("txnid", "2365F315");
                newPayIntent.putExtra("date", datetime);//Should be in same format
                newPayIntent.putExtra("cardtype", strPaymentMode);// CC or DC ONLY (value should be same as commented)
                newPayIntent.putExtra("cardAssociate", strCardType);// for VISA and MASTER.  MAESTRO ONLY (value should be same as commented)
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

        }
        else
        {
            if (!total_fee.getText().toString().trim().equals("0.0"))
            {

                prepare_reg_data();

                Date dNow = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yy/MM/dd- hh:mm:ss");
                String datetime = ft.format(dNow);

                Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
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
                // newPayIntent.putExtra("type","NBFundTransfer");
                newPayIntent.putExtra("discriminator", "NBFundTransfer");
                newPayIntent.putExtra("ru","https://payment.atomtech.in/mobilesdk/param");
                newPayIntent.putExtra("customerMobileNo", "9550479500");
                Log.e("PaytmData", newPayIntent.getExtras().toString());
                newPayIntent.putExtra("billingAddress", "Mumbai");
                startActivityForResult(newPayIntent, 1);

            }else
            {
                Toast.makeText(getActivity(), "Select Branch", Toast.LENGTH_SHORT).show();
            }*/








    public void generate_checksum (JSONObject jo, String url,final String order_id,final String tot) {

        CustomAsync ca = new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
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
                            paramMap.put("CUST_ID","20");
                            paramMap.put("CHANNEL_ID", "WAP");
                            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                            paramMap.put("WEBSITE", "APP_STAGING");
                            paramMap.put("TXN_AMOUNT",total_fee.getText().toString().trim());
                            //paramMap.put("THEME", "merchant");
                            // paramMap.put("EMAIL", "parvezmd48@gmail.com");
                            //paramMap.put("MOBILE_NO", "9908819653");
                            paramMap.put("CHECKSUMHASH",jo.getString("CHECKSUMHASH"));
                            paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
                            paramMap.put("MOBILE_NO","9550479500");
                            paramMap.put("EMAIL","absabc@gmail.com");

                            Log.e("Paytmdata", paramMap.toString());

                            PaytmPGService Service = PaytmPGService.getStagingService();

                            PaytmOrder Order = new PaytmOrder(paramMap);

                            PaytmMerchant Merchant = new PaytmMerchant("http://fudyz.com/users/generatechecksum_service", "http://fudyz.com/users/verifychecksum_service");

                            Service.initialize(Order, null);

                            Service.startPaymentTransaction(getActivity(), true, true, new PaytmPaymentTransactionCallback() {
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

                                            SharedPreferences member_datails=getActivity().getSharedPreferences("member_datails", Context.MODE_PRIVATE);
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
                                            jo.put("total_amount",total_fee.getText().toString().trim());
                                            jo.put("final_amount", total_fee.getText().toString().trim());
                                            jo.put("member_id", member_datails.getString("member_id","0"));
                                            jo.put("invoice_id","1");
                                            jo.put("amount", "100");
                                            jo.put("enroll_student_id","2");
                                            Log.e("PaytmData", jo.toString());

                                            //   historyData(jo, getResources().getString(R.string.server)+Constants.payment_success_service);



                                            final SharedPreferences sp = getActivity().getSharedPreferences(MyFirebaseInstanceIDService.pref, Context.MODE_PRIVATE);
                                            final String Notification = sp.getString("Notification", "");
                                            StringBuilder stringBuilder=new StringBuilder();
                                            if (news.isChecked())stringBuilder.append(news.getText().toString().trim()).append(",");
                                            if (friend.isChecked())stringBuilder.append(friend.getText().toString().trim()).append(",");
                                            if (just_dial.isChecked())stringBuilder.append(just_dial.getText().toString()).append(",");
                                            if (other.isChecked())stringBuilder.append(other.getText().toString().trim()).append(",");
                                            if (internet.isChecked())stringBuilder.append(internet.getText().toString().trim()).append(",");
                                            JSONObject jsonObject = new JSONObject();
                                            try
                                            {
                                                jsonObject.put("name", student_name.getText().toString().trim());
                                                jsonObject.put("mobile", mobile_no.getText().toString().trim());
                                                jsonObject.put("whatsapp_number", whtapp_nom.getText().toString().trim());
                                                jsonObject.put("email", email.getText().toString().trim());
                                                jsonObject.put("age", age.getText().toString().trim());
                                                jsonObject.put("organization_name", organization_name.getText().toString().trim());
                                                jsonObject.put("branch_id",branch_id);
                                                jsonObject.put("known_from",stringBuilder.toString());
                                                jsonObject.put("registration_amount", reg_fee.getText().toString().trim());
                                                jsonObject.put("gcm_id",Notification );
                                                jsonObject.put("device_id", "");
                                                jsonObject.put("final_amount",total_fee.getText().toString().trim());
                                                jsonObject.put("promotional_id",Promotional_id);
                                                jsonObject.put("promotional_amount",Promotional_amount);
                                                Log.e("jsonObject",""+jsonObject.toString());

                                                //   reg_send_data(jsonObject,getResources().getString(R.string.server) + Constants.user_registration_service);

                                            }
                                            catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }


                                        }catch (Exception e) {

                                            e.printStackTrace();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "Your payment transaction failed please retry", Toast.LENGTH_SHORT).show();
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

    public void historyData (JSONObject jo, String url,final JSONObject j) {

        CustomAsync ca = new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {

                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);

                        String Status = jo.getString("success");
                        if (Status.equals("1"))
                        {
                            Log.e("checking","checking");
                            DataBase_Helper dh=new DataBase_Helper(getActivity());
                            int c=dh.getUserCount();
                            if(c==0)
                            {
                                dh.insertUserId(new User("1",j.getString("id"),j.getString("mobile")));
                                Log.e("yyyyy","xxxx");
                            }


                            else
                            {
                                dh.updatetUserId(new User("1",j.getString("id"),j.getString("mobile")));
                            }
                            //dh.insertData(new User(name,email,))
                            List<User> li=dh.getUserData();
                            for( User u: li)
                            {
                                String log="username"+" "+u.getName()+" mobile "+u.getMobile()+" user id "+u.getUid();
                                  Log.e("db_data",log);
                            }
                            SharedPreferences.Editor editorr=sp.edit();
                            editorr.putString("email",j.getString("email"));
                            editorr.commit();

                            getActivity().finish();
                            getActivity().startActivity(new Intent(getActivity(),MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            SharedPreferences.Editor editor=member_datails.edit();
                            editor.putString("member_id",j.getString("member_id"));
                            editor.commit();

                            Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();


                            //  Toast.makeText(getActivity(), "Your Order Is confirmed", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            //Log.e("ElseCondition", "WhatHappen"+ Status);

                           /* Toast.makeText(Cart_Page.this, ""+Status, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Cart_Page.this, Cart_Page.class);
                            startActivity(intent);
                       */ }
                    }catch (Exception e)
                    {

                        Log.e("Exceptioneded",e.toString());
                        e.printStackTrace();
                    }


                }

            }

        });ca.execute();

    }



    private  void reg_send_data(JSONObject jo, String url,final JSONObject  reg_data)
    {
        CustomAsync ca=new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub

                if(result==null||result.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try

                    {
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)

                        {
                            Log.e("check","check");



                            reg_data.put("user_id", j.getString("id"));
                            reg_data.put("member_id", j.getString("member_id"));
                            reg_data.put("email", j.getString("email"));
                            reg_data.put("payment_through","2");

                            Log.e("registration_exception2",reg_data+"bvbv");


                            historyData(reg_data, getResources().getString(R.string.server) + Constants.payment_success_service, j);


                        }
                        else if(status==3)
                        {
                            Toast.makeText(getActivity(), "User Already Exist Please Login", Toast.LENGTH_SHORT).show();

                        }




                    }
                    catch(Exception e)
                    {
                        Log.e("registration_exception",""+e.toString());
                        e.printStackTrace();
                    }
                }
            }

        });
        ca.execute();
    }
    private  void checkUserMobileNumber(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                        if(status==1)
                        {
                            user_status=true ;
                            //Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();

                        }
                        else  if(status==3)
                        {
                            user_status=false ;

                            Toast.makeText(getActivity(), "User Already Exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
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
    private  void validate_coupon(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                        if(status==1)
                        {
                            int tot=0;
                            tot=((Integer.parseInt(reg_fee.getText().toString().trim()))-(Integer.parseInt(j.getString("promotional_amount"))));
                            total_fee.setText(""+tot);
                            Promotional_id=j.getString("promotion_id");
                            Promotional_amount=j.getString("promotional_amount");

                            Toast.makeText(getActivity(), "Coupon applied successfully", Toast.LENGTH_SHORT).show();

                        }
                        else  if(status==4)
                        {

                            Toast.makeText(getActivity(), "Invalid/Expired Coupon", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
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
        CustomAsync ca=new CustomAsync(getActivity(), jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Retry", Toast.LENGTH_SHORT).show();
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

                                    final Dialog dialog = new Dialog(getActivity());
                                    dialog.requestWindowFeature(1);
                                    dialog.setContentView(R.layout.branch_list_popup);
                                    ListView listView=(ListView)dialog.findViewById(R.id.list_branches);
                                    listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,b_names));
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                        {

                                            branch.setText(b_names.get(position));
                                            reg_fee.setText(""+branches_models.get(position).getReg_fee());
                                            total_fee.setText(""+branches_models.get(position).getReg_fee());
                                            branch_id = branches_models.get(position).getBranch_id();
                                            apply_coupon.setText("");
                                            dialog.dismiss();
                                        }
                                    });
                                    Window window = dialog.getWindow();

                                    window.setGravity(Gravity.CENTER);
                                    dialog.show();
                                }
                            });


                            // Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
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

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {     if (data != null)
        {      String message = data.getStringExtra("status");
            String[] resKey = data.getStringArrayExtra("responseKeyArray");
            String[] resValue = data.getStringArrayExtra("responseValueArray");
            if(resKey!=null && resValue!=null)
            {
                final  JSONObject jsonObjectt=new JSONObject();
                for(int i=0; i<resKey.length; i++)
                {
                    if (resKey[i].equals("f_code"))
                    {
                        stats=resValue[i];
                    }
                    try {
                        jsonObjectt.put(resKey[i],resValue[i]);
                        Log.e("dtaaar",resKey[i]+" resValue : "+resValue[i]+"kkkkk   "+stats);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {
                    jsonObjectt.put("total_amount",total_fee.getText().toString().trim());
                    jsonObjectt.put("final_amount", total_fee.getText().toString().trim());
                }catch (Exception e)
                {

                }


                if (stats.equals("success_00")||stats.equals("Ok"))
                {
                    try
                    {
                        //  Log.e("registration_exception0",reg_data+"bvbv");


                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(1);
                        dialog.setContentView(R.layout.payment_confirmation_popup);
                        Button proceed=(Button)dialog.findViewById(R.id.proceed);
                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                reg_send_data(reg_data,getResources().getString(R.string.server) + Constants.user_registration_service,jsonObjectt);
                                dialog.dismiss();
                            }
                        });
                        Window window = dialog.getWindow();

                        window.setGravity(Gravity.CENTER);
                        dialog.show();



                    }
                    catch (Exception e)
                    {
                        Log.e("ExceptionEf",""+e.toString());
                        e.printStackTrace();
                    }

                }else if (stats.equals("F"))
                {
                    Toast.makeText(getActivity(), "Payment declined please retry..", Toast.LENGTH_LONG).show();
                }

            }

        }
        }



    }
}
