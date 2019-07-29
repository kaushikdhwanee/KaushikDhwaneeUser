package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.FaqAdap;
import in.tiqs.kaushikdhwaneeuser.utils.Qns;


/**
 * Created by techiq123 on 2/29/2016.
 */
public class Faq extends AppCompatActivity {
    ExpandableListView expandableList;
    private int lastExpandedPosition = -1;
    TextView count_tv;
    FrameLayout count_lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText("Faqs");

//        Typeface tf= Typeface.createFromAsset(getAssets(), "fonts/futura_bold_bt.TTF");
//        Typeface tf1= Typeface.createFromAsset(getAssets(), "fonts/Futura Bk BT Book.ttf");
//        title.setTypeface(tf1);


        expandableList =  (ExpandableListView) findViewById(R.id.f_list);

        expandableList.setDividerHeight(5);

        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        expandableList.setChildIndicator(null);
        expandableList.setVerticalScrollBarEnabled(false);
        expandableList.setHorizontalScrollBarEnabled(false);


       /* setGroupParents();
        setChildData();*/
        ArrayList<String> parentItems=new ArrayList<>(Arrays.asList(Qns.parent));
        // ArrayList<String> childItems=new ArrayList<>(Arrays.asList(Qns.child));
        ArrayList<Object> childItems = new ArrayList<Object>();

        for(int i=0;i<Qns.child.length;i++)
        {
            ArrayList<String> child = new ArrayList<String>();
            child.add(Qns.child[i]);
            childItems.add(child);
        }




        FaqAdap adapter = new FaqAdap(Faq.this,parentItems, childItems);
        ExpandableListView.OnGroupExpandListener grpExpLst = new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
               // Toast.makeText(Faq.this, "" + groupPosition, Toast.LENGTH_LONG).show();
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                /* this one is not required of course, you can delete it from the RootAdapter Constructor
                 * it is just an example as to how to implement Listeners on the second level items */
            }
        };

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnGroupExpandListener(grpExpLst);
        // expandableList.setOnChildClickListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



}
