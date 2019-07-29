package in.tiqs.kaushikdhwaneeuser.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.NotifAdapter;
import in.tiqs.kaushikdhwaneeuser.adap.OfrAdapter;

/**
 * Created by TechIq on 2/27/2017.
 */

public class Offers extends AppCompatActivity {
    ListView lv;
    String []names={"Raj","Rahul","Chandu"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ofrs);

        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText(getString(R.string.ofrs));
        lv=(ListView)findViewById(R.id.ofrs_lv);
lv.setHorizontalScrollBarEnabled(false);
        lv.setVerticalScrollBarEnabled(false);
        lv.setAdapter(new OfrAdapter(Offers.this,names));
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
