package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.utils.RowItem;


public class SmAdapter extends BaseAdapter {

 Context context;

 Typeface tf;
    List<RowItem> rowItem;

 public SmAdapter(Context context, List<RowItem> rowItem) {
  this.context = context;
  this.rowItem = rowItem;
 }


 @Override
 public View getView(int position, View convertView, ViewGroup parent) {

if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview, null);
        }
//tf=Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");
     //tf=Typeface.createFromAsset(context.getAssets(), "fonts/futura_bold_bt.TTF");
       ImageView imgIcon = (ImageView) convertView.findViewById(R.id.sicon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.stitle);
      //  TextView txtBullet =(TextView) convertView.findViewById(R.id.i_icon);
      //  String str="<font color='#000000'><h1>&#8226; </h1></font>";
       
      //  ImageView limgIcon = (ImageView) convertView.findViewById(R.id.i_icon);

        RowItem row_pos = rowItem.get(position);
        // setting the image resource and title
      //  imgIcon.setImageResource(R.drawable.myprofile);

     Drawable d= VectorDrawableCompat.create(context.getResources(),
             row_pos.getIcon(), null);

     imgIcon.setImageDrawable(d);

 // Log.e("xxx",""+row_pos.getIcon());
     //   imgIcon.setBackgroundResource(row_pos.getIcon()); //textView.setText("&#8226; hello");
       // txtBullet.setText(Html.fromHtml(row_pos.getLicon()));
        txtTitle.setText(row_pos.getTitle());
       // txtTitle.setTextColor(Color.WHITE);
      // txtTitle.setTypeface(tf);

        return convertView;
 }

 @Override
 public int getCount() {
  return rowItem.size();
 }

 @Override
 public Object getItem(int position) {
  return rowItem.get(position);
 }

 @Override
 public long getItemId(int position) {
  return rowItem.indexOf(getItem(position));
 }

        static {
       // AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        }

}