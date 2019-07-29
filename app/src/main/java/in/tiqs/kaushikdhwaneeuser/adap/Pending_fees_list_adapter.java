package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.models.Pending_amount_model;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by TechIq on 2/14/2017.
 */

public class Pending_fees_list_adapter extends BaseAdapter {
    ArrayList<Pending_amount_model> amount_pending;
    ArrayList<String> amounts=new ArrayList<>();
    ArrayList<String> invoice_ids=new ArrayList<>();
    ArrayList<String> enrollment_ids=new ArrayList<>();
    Context context;
    Pending_fees_listener pending_fees_listener;
    double tot=0;

   public Pending_fees_list_adapter(Context con, ArrayList<Pending_amount_model>amount_pending)
    {
        this.context=con;
        this.amount_pending =amount_pending;
    }
    public interface Pending_fees_listener
    {
        public void setCustomButtonListner(ArrayList<String> amounts,ArrayList<String> invoice_ids,ArrayList<String> enrollment_ids,double tot);

    }

    public void setCustomButtonListner(Pending_fees_listener listener) {
        this.pending_fees_listener = listener;
    }
    @Override
    public int getCount() {
        return amount_pending.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
//        Typeface tf= Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");

        View v = view;

        if (v == null) {
            vh=new ViewHolder();
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.payment_pending_list_item, null);

            vh.student_name = (TextView) v.findViewById(R.id.student_name);
            vh.class_name=(TextView)v.findViewById(R.id.class_name) ;
            vh.due_amount=(TextView)v.findViewById(R.id.due_amount) ;
            vh.month=(TextView)v.findViewById(R.id.month) ;
            vh.select=(CheckBox) v.findViewById(R.id.select) ;
            vh.start_date=(TextView) v.findViewById(R.id.start);
            vh.end_date=(TextView) v.findViewById(R.id.end);
            vh.total_sessions=(TextView) v.findViewById(R.id.total_sessions);
            vh.sessions=(TextView) v.findViewById(R.id.sessions_week);


            v.setTag(vh);

        }
        else
        {
            vh =   (ViewHolder) v.getTag();
        }

        vh.student_name.setText(amount_pending.get(i).getStudent_name());
        vh.class_name.setText(amount_pending.get(i).getClass_name());
        vh.due_amount.setText("₹ "+amount_pending.get(i).getPenidn_amount());
       // vh.month.setText(amount_pending.get(i).getStart_date()+" - "+amount_pending.get(i).getEnd_date());
        vh.start_date.setText("Start Date:"+" "+amount_pending.get(i).getStart_date());
        vh.end_date.setText("End Date:"+" "+amount_pending.get(i).getEnd_date());
        vh.select.setChecked(amount_pending.get(i).isSelection_status());
        vh.total_sessions.setText("Total Sessions:"+" "+amount_pending.get(i).getTotal_sessions());
        vh.sessions.setText("Session / Week:"+" "+amount_pending.get(i).getSessions_week());

        vh.select.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox checkBox=(CheckBox)v;
                if (checkBox.isChecked())
                {
                    amount_pending.get(i).setSelection_status(true);
                }else
                {
                    amount_pending.get(i).setSelection_status(false);

                }
                get_selection_data(i);
                notifyDataSetChanged();
            }
        });


        return v;

    }
    public void get_selection_data(int position)
    {
        amounts.clear();
        invoice_ids.clear();
        enrollment_ids.clear();

        tot=0;
        for (int i=0;i<amount_pending.size();i++)
        {
          if (amount_pending.get(i).isSelection_status()==true)
          {
              amounts.add(amount_pending.get(i).getPenidn_amount());
              invoice_ids.add(amount_pending.get(i).getInvoice_id());
              enrollment_ids.add(amount_pending.get(i).getEnroll_student_id());
              tot=tot+Double.parseDouble(amount_pending.get(i).getPenidn_amount());

          }


        }

        pending_fees_listener.setCustomButtonListner(amounts ,invoice_ids ,enrollment_ids,tot);

    }

          static class ViewHolder
        {
            TextView student_name,class_name,due_amount,month,start_date,end_date,total_sessions,sessions;
           CheckBox select;

    }

}
