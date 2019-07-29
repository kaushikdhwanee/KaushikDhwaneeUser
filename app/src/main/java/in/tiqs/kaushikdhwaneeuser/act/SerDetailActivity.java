/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import in.tiqs.kaushikdhwaneeuser.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SerDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    ImageView backdrop;
    TextView description;
    CollapsingToolbarLayout collapsing_toolbar;
    String demo="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);
        Button btn=(Button)findViewById(R.id.bad_btn);
        backdrop=(ImageView)findViewById(R.id.backdrop);
        description=(TextView) findViewById(R.id.description);
        description.setText(getIntent().getStringExtra("desc"));
        ImageView back=(ImageView)findViewById(R.id.back);



        Intent intent = getIntent();
        demo=getIntent().getStringExtra("demo");
        if (demo.equals("Y"))
        {
            btn.setBackgroundColor(Color.parseColor("#a9a9a9"));
        }
        else  if (demo.equals("N"))
        {

        }
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  description.setText(getIntent().getStringExtra("desc"));
        Glide.with(this).load(getIntent().getStringExtra("Image"))
                //.bitmapTransform(new CropCircleTransformation(this))
                //.error(R.drawable.profile60)
                .placeholder(R.drawable.banner1)
                //.bitmapTransform(new RoundedCornersTransformation(MainActivity.this,30,30))
                .into(backdrop);



        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getIntent().getStringExtra("title"));

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        loadBackdrop();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (demo.equals("Y"))
                {
                    Toast.makeText(SerDetailActivity.this, "You are already enrolled this class", Toast.LENGTH_SHORT).show();

                }
                else  if (demo.equals("N"))
                {
                    startActivity(new Intent(SerDetailActivity.this,Bademo.class)
                            .putExtra("id",getIntent().getStringExtra("id"))
                            .putExtra("main_cat_id",getIntent().getStringExtra("main_cat_id"))
                    );
                }


            }
        });
    }

    private void loadBackdrop()
    {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

      //  Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
*/
}
