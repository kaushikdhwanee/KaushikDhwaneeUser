package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.tiqs.kaushikdhwaneeuser.R;

/**
 * Created by TechIq on 2/27/2017.
 */

public class Support extends AppCompatActivity implements View.OnClickListener {

    static {
        // AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);
        TextView faq,aapp,akd,afb,call,title;
        View v=findViewById(R.id.ab_hv);
       // v.setVisibility(View.INVISIBLE);

        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title=(TextView)findViewById(R.id.ab2_title);
        title.setText(getString(R.string.support));
        faq=(TextView)findViewById(R.id.tv_faq);
        aapp=(TextView)findViewById(R.id.tv_aapp);
        akd=(TextView)findViewById(R.id.tv_akd);
        call=(TextView)findViewById(R.id.tv_scall);
        afb=(TextView)findViewById(R.id.tv_sfb);

        faq.setOnClickListener(this);
        aapp.setOnClickListener(this);
        akd.setOnClickListener(this);
        call.setOnClickListener(this);
        afb.setOnClickListener(this);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0407746584"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_faq: startActivity(new Intent(Support.this,Faq.class));
                break;
            case R.id.tv_sfb: startActivity(new Intent(Support.this,FeedBack.class));
                break;
            case R.id.tv_aapp:startActivity(new Intent(Support.this,AboutApp.class));
                break;
            case R.id.tv_akd:startActivity(new Intent(Support.this,AboutKD.class));
                break;
            case R.id.tv_scall:
                break;
        }
    }
}
