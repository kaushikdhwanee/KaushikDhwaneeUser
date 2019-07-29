package in.tiqs.kaushikdhwaneeuser.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import in.tiqs.kaushikdhwaneeuser.Branches_model;
import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.ExpandableHeightGridView;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.models.schedule_data;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;

/**
 * New and improved. Feel free to contact me if you have any questions.
 */

public class ThreeLevelExpandableListView extends AppCompatActivity {

    public static  int FIRST_LEVEL_COUNT = 6;
    public static  int SECOND_LEVEL_COUNT = 4;
    public static  int THIRD_LEVEL_COUNT = 20;
    private ExpandableListView expandableListView;

   //String [] first={"main1","main2"};
    String [] second={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    String [] third={"Batch 1","Batch 2"};
    String [] time={"12:00-01:30","01:30-02:00"};
    String [] teachers={"Rakesh Sharma","Rajesh Gupta"};
    ArrayList<String>first=new ArrayList<>();
    HashMap<String,ArrayList<schedule_data>>schedule_datas=new HashMap<>();
    int mainGroup_index;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
        expandableListView = (ExpandableListView) findViewById(R.id.mainList);






        if (new ConnectionDetector(ThreeLevelExpandableListView.this).isConnectingToInternet())
        {
                try
                {
                    JSONObject jsonObject=new JSONObject();

                    jsonObject.put("branch_id",getIntent().getStringExtra("branch_id"));
                    Log.e("jsonObject",""+jsonObject.toString());
                    getBranches(jsonObject,getResources().getString(R.string.server)+ Constants.getallbatchesbybranch_service);
                }
                catch(Exception e)
                {
                    Log.e("exception",""+e.toString());

                }
           }
        else
        {
            Toast.makeText(ThreeLevelExpandableListView.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }





    }

    public class ParentLevel extends BaseExpandableListAdapter

    {

        private Context context;

        public ParentLevel(Context context) {
            this.context = context;
        }

        @Override
        public Object getChild(int arg0, int arg1) {
            return arg1;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(ThreeLevelExpandableListView.this);
            secondLevelELV.setAdapter(new SecondLevelAdapter(context,groupPosition));
            secondLevelELV.setGroupIndicator(null);
            return secondLevelELV;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return FIRST_LEVEL_COUNT;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_first, null);
                TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
                text.setText(first.get(groupPosition));


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations. 
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        private int  position;

        public SecondLevelAdapter(Context context,int position) {
            this.context = context;
            this.position = position;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount()
        {
            return SECOND_LEVEL_COUNT;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_second, null);


            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
if (schedule_datas.get(first.get(position)+second[groupPosition]).size()!=0)
{
    text.setVisibility(View.VISIBLE);
    text.setText(second[groupPosition]+" ( "+schedule_datas.get(first.get(position)+second[groupPosition]).size()+" )");

}else
{
    text.setVisibility(View.GONE);
}



            return convertView;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ExpandableHeightGridView  simpleListView=null;

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_third, null);
               // TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
               // text.setText("THIRD LEVEL");



            Log.e("asdsasdsa",first.get(position)+second[groupPosition]+"   "+groupPosition+"   "+position);

            simpleListView = (ExpandableHeightGridView) convertView.findViewById(R.id.list);
           ArrayList<schedule_data> schedule_data=schedule_datas.get(first.get(position)+second[groupPosition]);

            ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
            for (int i=0;i<schedule_data.size();i++)
            {
                HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
                hashMap.put("batch_name",schedule_data.get(i).getBatch_name());
                hashMap.put("teacher_name",schedule_data.get(i).getTeacher_name());
                hashMap.put("Time",schedule_data.get(i).getTime());
                arrayList.add(hashMap);//add the hashmap into arrayList
            }
            String[] from={"batch_name","teacher_name","Time"};//string array
            int[] to={R.id.batch_name,R.id.teacher_name,R.id.Time};//int array of views id's
            SimpleAdapter simpleAdapter=new SimpleAdapter(ThreeLevelExpandableListView.this,arrayList,R.layout.teach_sched_child_list_item,from,to);//Create object and set the parameters for simpleAdapter
            simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView


            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private  void getBranches(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(ThreeLevelExpandableListView.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(ThreeLevelExpandableListView.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                        if(status==1)
                        {
                            JSONArray jsonArray=j.getJSONArray("classes");
                            schedule_datas.clear();
                            first.clear();
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                first.add(jsonObject.getString("class_name"));

                                for (int jJ=0;jJ<jsonObject.getJSONObject("batches").length();jJ++)
                                {
                                    JSONArray jsonObject1=jsonObject.getJSONObject("batches").getJSONArray((jJ+1)+"");
                                    ArrayList<schedule_data>schedule_datass=new ArrayList<>();
                                    for (int k=0;k<jsonObject1.length();k++)
                                    {

                                        JSONObject jsonObject2=jsonObject1.getJSONObject(k);
                                        schedule_data schedule_data=new schedule_data();
                                        schedule_data.setBatch_name("Batch "+(k+1));
                                        schedule_data.setTime(format2.format(format.parse(jsonObject2.getString("start_time")))+" - "+format2.format(format.parse(jsonObject2.getString("end_time"))));
                                        schedule_data.setTeacher_name(jsonObject2.getString("teachers"));
                                        schedule_datass.add(schedule_data);

                                    }
                                    schedule_datas.put(jsonObject.getString("class_name")+second[jJ],schedule_datass);
                                    Log.e("child_data",jsonObject.getString("class_name")+second[jJ]+"    "+schedule_datas.get(jsonObject.getString("class_name")+second[jJ]));
                                }



                            }
                            FIRST_LEVEL_COUNT=first.size();
                            SECOND_LEVEL_COUNT=second.length;
                            Log.e("size",FIRST_LEVEL_COUNT+"    "+SECOND_LEVEL_COUNT+"   "+schedule_datas.toString());
                            //  THIRD_LEVEL_COUNT=third.length;

                            expandableListView.setAdapter(new ParentLevel(ThreeLevelExpandableListView.this));




                            // Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            Toast.makeText(ThreeLevelExpandableListView.this, ""+status, Toast.LENGTH_SHORT).show();
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