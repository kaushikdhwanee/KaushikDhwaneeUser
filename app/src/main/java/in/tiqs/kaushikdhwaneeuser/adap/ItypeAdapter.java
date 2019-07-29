package in.tiqs.kaushikdhwaneeuser.adap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.utils.RowItem;


public class ItypeAdapter extends BaseAdapter {

    Context context;

    Typeface tf;
    List<RowItem> rowItem;

    public ItypeAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;

        if (convertView == null) {
            vh=new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.itype_row, null);
            vh.name = (TextView) convertView.findViewById(R.id.fbi_title);
            //  TextView txtBullet =(TextView) convertView.findViewById(R.id.i_icon);
              vh.ivi = (ImageView) convertView.findViewById(R.id.itype_img);


            convertView.setTag(vh);
        }
        else{
            vh =   (ViewHolder) convertView.getTag();
        }
//tf=Typeface.createFromAsset(context.getAssets(), "fonts/Futura Bk BT Book.ttf");
        //tf=Typeface.createFromAsset(context.getAssets(), "fonts/futura_bold_bt.TTF");
        //   ImageView imgIcon = (ImageView) convertView.findViewById(R.id.sicon);


        RowItem row_pos = rowItem.get(position);

        Drawable d= VectorDrawableCompat.create(context.getResources(),
                row_pos.getIcon(), null);

        vh.ivi.setImageDrawable(d);


        vh.name.setText(row_pos.getTitle());
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


    static class ViewHolder
    {
        TextView name;

        ImageView ivi;


    }


}