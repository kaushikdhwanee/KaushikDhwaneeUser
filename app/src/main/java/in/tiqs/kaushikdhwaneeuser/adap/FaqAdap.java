package in.tiqs.kaushikdhwaneeuser.adap;

/**
 * Created by techiq123 on 2/29/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import in.tiqs.kaushikdhwaneeuser.R;


public class FaqAdap extends BaseExpandableListAdapter {

    private Activity activity;
   private ArrayList<Object> childtems;
 // private ArrayList<String> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    private Context context;

   public FaqAdap(Context c, ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childtems = childern;
       context=c;
    }
/*  public FaqAdap(ArrayList<String> parents, ArrayList<String> childern) {
      this.parentItems = parents;
      this.childtems = childern;
  }*/
    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

     //   Typeface tf1= Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.faq_g, null);
        }

        textView = (TextView) convertView.findViewById(R.id.faq_gtv);
        textView.setText(child.get(childPosition));
       // textView.setTypeface(tf1);
       // textView.setText(childtems.get(groupPosition));
      //  Tf.setTvSemi(this.activity,new TextView[]{textView});

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
               /* Toast.makeText(activity, child.get(childPosition),
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      //  Typeface tf= Typeface.createFromAsset(context.getAssets(), "fonts/futura_bold_bt.TTF");


        CheckedTextView textView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.faq_r, null);
        }
        textView = (CheckedTextView) convertView.findViewById(R.id.faq_rtv);
        textView.setText(parentItems.get(groupPosition));
        textView.setChecked(isExpanded);
      //  ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
       // ((CheckedTextView) convertView).setTypeface(tf);
      //  ((CheckedTextView) convertView).setChecked(isExpanded);
       // Tf.setTvSemi(this.activity,new TextView[]{((CheckedTextView) convertView)});

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
       // return childtems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
