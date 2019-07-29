package in.tiqs.kaushikdhwaneeuser.adap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import in.tiqs.kaushikdhwaneeuser.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by TechIq on 2/14/2017.
 */

public class OfrAdapter extends BaseAdapter {
    String[] titles;
    Context context;

   public OfrAdapter(Context con, String[] titles)
    {
        this.context=con;
        this.titles=titles;
    }
    @Override
    public int getCount() {
        return titles.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
//        Typeface tf= Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");

        View v = view;

        if (v == null) {
            vh=new ViewHolder();
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.ofr_row, null);

//            vh.rl=(LinearLayout)v.findViewById(R.id.ofr_lay);
            vh.name = (TextView) v.findViewById(R.id.ofr_amt);

            vh.ivi=(ImageView)v.findViewById(R.id.ofr_img) ;
//            vh.desc1=(TextView)v.findViewById(R.id.ofr_desc) ;
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
      //  vh.name.setText(titles[i]);

       // vh.fl.setBackgroundResource(R.drawable.ic_circle_flower);
       // Glide.with(context).load(R.drawable.banner1)
              //  .bitmapTransform(new RoundedCornersTransformation(context,70,0, RoundedCornersTransformation.CornerType.ALL))


                // .error(R.drawable.profile60)
                //  .bitmapTransform(new RoundedCornersTransformation(HomeActivity.this,30,30))
              //  .into(vh.ivi);
//        vh.name.setTypeface(tf);
//        vh.desc1.setTypeface(tf);
//        vh.desc2.setTypeface(tf);

        return v;

    }

    static class ViewHolder
    {
        TextView name,desc1,desc2;
        LinearLayout rl;
        ImageView ivi;
        FrameLayout fl;

    }

}
