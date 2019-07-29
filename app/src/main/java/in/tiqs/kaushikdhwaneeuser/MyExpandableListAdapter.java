package in.tiqs.kaushikdhwaneeuser;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.tiqs.kaushikdhwaneeuser.adap.Home_sub_cat_adapter;

public class MyExpandableListAdapter extends BaseExpandableListAdapter
    {
        ArrayList<ArrayList<Moviee>> arrayLists;
ArrayList<String>headers;
        private LayoutInflater inflater;
        Context context;

        public MyExpandableListAdapter(Context context ,ArrayList<String>headers, ArrayList<ArrayList<Moviee>> arrayLists)
         {
            // Create Layout Inflator
             this.arrayLists=arrayLists;
             this.context=context;
             this.headers=headers;

            inflater = LayoutInflater.from(this.context);

        }


        // This Function used to inflate parent rows view

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView)
        {


            // Inflate grouprow.xml file for parent rows
            convertView = inflater.inflate(R.layout.parent, parentView, false);

            // Get grouprow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.leauge_title)).setText(headers.get(groupPosition));
            /* NonScrollExpandableListView mExpandableListView = (NonScrollExpandableListView) parentView;
            mExpandableListView.expandGroup(groupPosition);

            mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    return true; // This way the expander cannot be collapsed
                }
            });*/
            return convertView;
        }


        // This Function used to inflate child rows view
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView)
        {



            final ArrayList<Moviee> child = arrayLists.get(groupPosition);

            // Inflate childrow.xml file for child rows
            convertView = inflater.inflate(R.layout.child, parentView, false);

          ExpandableHeightGridView1 expandableHeightGridView=(ExpandableHeightGridView1)convertView.findViewById(R.id.leagues);
            expandableHeightGridView.setAdapter(new Home_sub_cat_adapter(context,child));
            expandableHeightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {







                }
            });
            expandableHeightGridView.setFocusableInTouchMode(false);
            // Get childrow.xml file elements and set values
          /*  ((TextView) convertView.findViewById(R.id.amount)).setText(child.getDiscription());
            ((TextView) convertView.findViewById(R.id.event_date)).setText(child.getEvent_date());
            ((TextView) convertView.findViewById(R.id.event_time)).setText(child.getEvent_time());
            ((TextView) convertView.findViewById(R.id.total_amount)).setText(child.getTotal_amount());
            ((TextView) convertView.findViewById(R.id.service_tax)).setText(child.getService_tax());
            ((TextView) convertView.findViewById(R.id.grand_totl)).setText(child.getGrand_Totl());
      */      return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
            return arrayLists.get(groupPosition).get(childPosition);
        }

        //Call when child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {     return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size=0;
            if(arrayLists.get(groupPosition)!=null)
                size = arrayLists.get(groupPosition).size();
            return 1;
        }


        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroup ");

            return headers.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return headers.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition)
        {
           /* Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

            if(groupPosition==2 && ParentClickStatus!=groupPosition){

                //Alert to user
                Toast.makeText(getApplicationContext(), "Parent :"+groupPosition ,
                        Toast.LENGTH_LONG).show();
            }*/



            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((arrayLists == null) || arrayLists.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }



        /******************* Checkbox Checked Change Listener ********************/


    }
