package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.tiqs.kaushikdhwaneeuser.Moviee;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.act.SerDetailActivity;
import in.tiqs.kaushikdhwaneeuser.utils.RowItem;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class Home_sub_cat_adapter extends BaseAdapter {

 Context context;

 Typeface tf;
    List<Moviee> rowItem;

 public Home_sub_cat_adapter(Context context, List<Moviee> rowItem) {
  this.context = context;
  this.rowItem = rowItem;
 }


 @Override
 public View getView(final int position, View convertView, ViewGroup parent) {

if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.section_ex5_item, null);
        }
       ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgItem);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvSubItem);
     FrameLayout imgV=(FrameLayout)convertView.findViewById(R.id.imgView);


     imgV.setBackgroundResource(R.drawable.ic_circle_flower);

     Glide.with(context).load(rowItem.get(position).getImage())
             .bitmapTransform(new CropCircleTransformation(context))
             .placeholder(R.drawable.calss_default)
             .into(imgIcon);
   //  imgIcon.setImageDrawable(d);
     txtTitle.setText(rowItem.get(position).getCategory());


     convertView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v)
         {
             SharedPreferences sp;
             sp=context.getSharedPreferences("classes",Context.MODE_PRIVATE);
             Intent intent=new Intent(context,SerDetailActivity.class);
             intent.putExtra("main_cat_id",rowItem.get(position).getMain_cat_id());
             intent.putExtra("id",rowItem.get(position).getId());
             intent.putExtra("Image",rowItem.get(position).getImage());
             intent.putExtra("title",rowItem.get(position).getCategory());
             intent.putExtra("desc",rowItem.get(position).getDesc());
             ArrayList<String> list=new ArrayList<String>(Arrays.asList(sp.getString("clas_id","s").split(",")));

             if (list.contains(rowItem.get(position).getId()))
             {
                 intent.putExtra("demo","Y");
             }
             else
             {
                 intent.putExtra("demo","N");

             }

             context.startActivity(intent);





         }
     });




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