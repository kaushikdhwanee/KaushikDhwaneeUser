package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.utils.RslvItem;

/**
 * Created by TechIq on 3/17/2017.
 */

public class Reschedule extends AppCompatActivity {
    MaterialCalendarView widget;
    ListView slot_lv;

    String [] batches={"Batch1","Batch2","Batch3","Batch4"};
    String [] times={"6 AM - 7 AM","8 AM - 9 AM","6 PM - 7 PM","8 PM - 9 PM"};
    boolean[] sta={false,false,false,false};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reschedule);
        widget=(MaterialCalendarView)findViewById(R.id.rs_cal);
        Calendar cal = Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int monthOfYear=cal.get(Calendar.MONTH);
        int dayOfMonth=cal.get(Calendar.DATE);
        widget.setMinimumDate(CalendarDay.from(year, monthOfYear, dayOfMonth));
        slot_lv=(ListView)findViewById(R.id.rs_lv);
        slot_lv.setVerticalScrollBarEnabled(false);
        slot_lv.setHorizontalFadingEdgeEnabled(false);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<RslvItem> rsal=new ArrayList<>();
        for(int i=0;i<batches.length;i++)
        {
            RslvItem rs=new RslvItem();
            rs.setBatch(batches[i]);
            rs.setStatus(sta[i]);
            rs.setTime(times[i]);
            rsal.add(rs);
        }

        slot_lv.setAdapter(new TimesAdapter(Reschedule.this,rsal));
        setDynamicHeight(slot_lv,2);


    }

    public static void setDynamicHeight(ListView listView, int x) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        if(x==1) {
            layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1));
        }
        else{
            layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 1))+height;

        }
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }


    public class TimesAdapter extends BaseAdapter {

        //String[] titles,price;
        ArrayList<RslvItem> SlotData;
        ArrayList<Boolean> tss;
        Context context;
        boolean xx;
        private int selectedPosition = -1;
        ViewHolder vh;
        public TimesAdapter(Context con, ArrayList<RslvItem> SlotData)
        {
            this.context=con;
            this.SlotData=SlotData;
            this.tss=tss;
            //   this.xx=xx;
            // this.price=price;

        }

        @Override
        public int getCount() {
            return SlotData.size();
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
            vh = null;
Log.e("sdata",SlotData.get(i).getBatch()+"");

            if (view == null) {
                vh = new ViewHolder();
                LayoutInflater li = LayoutInflater.from(context);
                view = li.inflate(R.layout.rs_lv_row, null);

                vh.name = (CheckedTextView) view.findViewById(R.id.rs_ctv);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }


            RslvItem rs=SlotData.get(i);
            vh.name.setText(rs.getBatch()+" - "+rs.getTime());

            if(rs.isStatus())
            {
                vh.name.setBackgroundColor(Color.BLACK);
                vh.name.setTextColor(Color.WHITE);
            }
            else{
                vh.name.setBackgroundColor(Color.WHITE);
                vh.name.setTextColor(Color.BLACK);
            }
            // Log.e("CompleteDataOfSlot", "123      "+ SlotData);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RslvItem rs=SlotData.get(i);
                    if(rs.isStatus())
                    {
                        rs.setStatus(false);
                    }
                    else{
                        rs.setStatus(true);
                    }
                    notifyDataSetChanged();
                }
            });

            return view;
        }

        class ViewHolder{
            CheckedTextView name;
        }
    }
}
