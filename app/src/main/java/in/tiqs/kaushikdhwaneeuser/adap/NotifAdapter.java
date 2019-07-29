package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.tiqs.kaushikdhwaneeuser.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by TechIq on 2/14/2017.
 */

public class NotifAdapter extends BaseAdapter {
    ArrayList<String> titles;
    ArrayList<String> dates;
    Context context;

   public NotifAdapter(Context con, ArrayList<String> titles,    ArrayList<String> dates)
    {
        this.context=con;
        this.titles=titles;
        this.dates=dates;
    }
    @Override
    public int getCount() {
        return titles.size();
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
            v = li.inflate(R.layout.notif_row, null);

//            vh.rl=(LinearLayout)v.findViewById(R.id.ofr_lay);
            vh.name = (TextView) v.findViewById(R.id.notif_nmae);

            vh.ivi=(ImageView)v.findViewById(R.id.notif_img) ;
            vh.desc1=(TextView)v.findViewById(R.id.mesg) ;
            vh.notif_dt=(TextView)v.findViewById(R.id.notif_dt) ;
//            vh.desc2=(TextView)v.findViewById(R.id.ofr_sub);


            v.setTag(vh);

        }
        else{
            vh =   (ViewHolder) v.getTag();
        }

//        if(i%2==0)
//        {
//            vh.rl.setBackgroundColor(ContextCompat.getColor(context,R.color.ofr_bg));
//            vh.name.setTextColor(Color.WHITE);
//        }
//        else{
//            vh.rl.setBackgroundColor(Color.WHITE);
//            vh.name.setTextColor(Color.parseColor("#565757"));
//        }
//
     //   vh.name.setText(titles[i]);
        vh.desc1.setText(titles.get(i));

       try
       {
           SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM hh:mm a");

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateInString = dates.get(i);
        Date date = inputFormat.parse(dateInString);
           vh.notif_dt.setText(outputFormat.format(date));

       }catch (Exception e)
       {
           Log.e("ezception",e.toString()) ;
       }
       // vh.fl.setBackgroundResource(R.drawable.ic_circle_flower);
        Glide.with(context).load(R.mipmap.ic_launcher)
                .bitmapTransform(new RoundedCornersTransformation(context,50,0, RoundedCornersTransformation.CornerType.ALL))


                // .error(R.drawable.profile60)
                //  .bitmapTransform(new RoundedCornersTransformation(HomeActivity.this,30,30))
                .into(vh.ivi);
//        vh.name.setTypeface(tf);
//        vh.desc1.setTypeface(tf);
//        vh.desc2.setTypeface(tf);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(titles.get(i));
                builder1.setTitle("Description");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return v;

    }

          static class ViewHolder
        {
            TextView name,desc1,notif_dt;
            LinearLayout rl;
            ImageView ivi;
            FrameLayout fl;

    }

}
