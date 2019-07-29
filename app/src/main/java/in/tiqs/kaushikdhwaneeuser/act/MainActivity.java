package in.tiqs.kaushikdhwaneeuser.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import in.tiqs.kaushikdhwaneeuser.Branches_model;
import in.tiqs.kaushikdhwaneeuser.CustomAsync;
import in.tiqs.kaushikdhwaneeuser.CustomAsync_2;
import in.tiqs.kaushikdhwaneeuser.Moviee;
import in.tiqs.kaushikdhwaneeuser.MyExpandableListAdapter;
import in.tiqs.kaushikdhwaneeuser.NonScrollExpandableListView;
import in.tiqs.kaushikdhwaneeuser.R;
import in.tiqs.kaushikdhwaneeuser.adap.PayAdapter;
import in.tiqs.kaushikdhwaneeuser.adap.SectionedRecyclerViewAdapter;
import in.tiqs.kaushikdhwaneeuser.adap.SmAdapter;
import in.tiqs.kaushikdhwaneeuser.cviews.CircularImageView;
import in.tiqs.kaushikdhwaneeuser.cviews.StatelessSection;
import in.tiqs.kaushikdhwaneeuser.cviews.shadow.ShadowProperty;
import in.tiqs.kaushikdhwaneeuser.cviews.shadow.ShadowViewDrawable;
import in.tiqs.kaushikdhwaneeuser.data_base.DataBase_Helper;
import in.tiqs.kaushikdhwaneeuser.frag.AutoScrollPagerFragment;
import in.tiqs.kaushikdhwaneeuser.frag.TextFragment;
import in.tiqs.kaushikdhwaneeuser.models.Members_Model;
import in.tiqs.kaushikdhwaneeuser.utils.Constants;
import in.tiqs.kaushikdhwaneeuser.utils.OnAsyncCompleteRequest;
import in.tiqs.kaushikdhwaneeuser.utils.RowItem;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {
    private ViewPager contentPager;
    DrawerLayout mDrawerLayout;
    RelativeLayout mDrawerList_Right;
    private List<RowItem> rowItems;
    private SmAdapter adapter;
    ListView mDrawerList;
    RelativeLayout rlay,ab_lay;
    ImageView mmenu;
    String cat_id = "",UserId="";
    TextView reg,user_name,version_name;
    View view;
    boolean isNet;
    CircularImageView profile_pic;

    Button b;
    private SectionedRecyclerViewAdapter sectionAdapter;
    ScrollView sv;
    ArrayList<String>headers=new ArrayList<>();
    ArrayList<ArrayList<Moviee>> home_arrays=new ArrayList<>();
    ArrayList<Moviee> home_cat_data ;
    ArrayList<Movie> home_cat_data2 ;

    MyExpandableListAdapter myExpandableListAdapter;
    NonScrollExpandableListView home_cat_list;
    ArrayList<Members_Model >members_models=new ArrayList<>();

    static {
        // AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }
    ArrayList<String> ids=new ArrayList<>();
    SharedPreferences sp;
    ArrayList<Branches_model> branches_models=new ArrayList<>();
    ArrayList<String>b_names=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
       // home_cat_data =new ArrayList<>();
        sp=getSharedPreferences("classes",Context.MODE_PRIVATE);
        mmenu=(ImageView)findViewById(R.id.mmenu);
        view=(View)findViewById(R.id.hmp_drawer_layout);
        profile_pic=(CircularImageView) findViewById(R.id.circularImageView);
        ab_lay=(RelativeLayout)findViewById(R.id.main_hm_rlay);
        reg=(TextView)findViewById(R.id.hm_regbtn);
        user_name=(TextView)findViewById(R.id.user_name);
        version_name=(TextView)findViewById(R.id.version_name);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.hmp_drawer_layout);
        home_cat_list=(NonScrollExpandableListView) findViewById(R.id.home_cat_list);

        mDrawerList_Right=(RelativeLayout)findViewById(R.id.hmp_drawer_right);
        mDrawerList=(ListView)findViewById(R.id.hmp_main_grid);
        rlay=(RelativeLayout)findViewById(R.id.sm_prfl_rlay);
        sv=(ScrollView)findViewById(R.id.hm_sv);
        sv.setHorizontalScrollBarEnabled(false);
        sv.setVerticalScrollBarEnabled(false);

        String versionName = "";
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = "ver : " + packageInfo.versionName;

            version_name .setText(versionName);

           // Log.e("vesion", "192   "+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            Log.e("exception", "196   "+e);

        }
        DataBase_Helper dh=new DataBase_Helper(this);
        final int c=dh.getUserCount();
        if (c!=0){


        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try {

                JSONObject jo = new JSONObject();
                jo.put("user_id", dh.getUserId("1"));
                Log.e("id",""+jo.toString());
                get_myclass_page(jo, getResources().getString(R.string.server) + Constants.getmyclasses_service);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        else {

            Snackbar snackBar = Snackbar.make(view, "No Internent Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finish();
                            startActivity(getIntent());
                        }
                    });
            snackBar.setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackBar.show();

        }
        }else
        {

        }





        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        isNet = cd.isConnectingToInternet();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

              if (reg.getText().toString().trim().equals("LOGOUT"))
              {

                  DataBase_Helper dataBase_helper=new DataBase_Helper(MainActivity.this);
                  SQLiteDatabase db=dataBase_helper.getWritableDatabase();
                  db.execSQL("delete  from User ");
                  db.close();

                  Intent i=new Intent(MainActivity.this,Login_page.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(i);
              }else
              {
                  finish();
                  startActivity(new Intent(MainActivity.this,Reg.class));
              }
            }
        });




        rlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this, Profile.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        ShadowProperty sp = new ShadowProperty()
//                .setShadowColor(0x77000000)
//                .setShadowDy(dip2px(this, 0.1f))
//                .setShadowRadius(dip2px(this, 3))
//                .setShadowSide(ShadowProperty.BOTTOM);
//        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);
//        ViewCompat.setBackground(ab_lay, sd);
//        ViewCompat.setLayerType(ab_lay, ViewCompat.LAYER_TYPE_SOFTWARE, null);




        mDrawerList.setVerticalScrollBarEnabled(false);
        mDrawerList.setHorizontalScrollBarEnabled(false);

        rowItems = new ArrayList<RowItem>();
        mmenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mDrawerLayout.isDrawerOpen(mDrawerList_Right)){
                    mDrawerLayout.closeDrawer(mDrawerList_Right);
                }
                else
                    // drawerLayout.openDrawer(mDrawerList_Right);
                    mDrawerLayout.openDrawer(mDrawerList_Right);
            }
        });

        String[] menutitles1 = getResources().getStringArray(R.array.smtitles1);
        String[] menutitles = getResources().getStringArray(R.array.smtitles);
        // int[] menuIcons=getResources().getIntArray(R.array.smicons);

        int[] menuIcons={R.drawable.ic_classes,R.drawable.ic_notification,R.drawable.ic_feedback,R.drawable.calender,R.drawable.ic_about_kd,R.drawable.ic_faqs};
        // places = savedInstanceState.getString("PLACES");

        if (c!=0)
        {
            for (int i = 0; i < menutitles.length; i++) {
                RowItem items = new RowItem(menutitles[i],menuIcons[i]);
                rowItems.add(items);
            }
        }else
        {
            for (int i = 0; i < menutitles1.length; i++) {
                RowItem items = new RowItem(menutitles1[i],menuIcons[i]);
                rowItems.add(items);
            }
        }

        //  menuIcons.recycle();

        adapter = new SmAdapter(getApplicationContext(), rowItems);

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideitemListener());
        mDrawerLayout.setDrawerListener(myDrawerListener);


        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);

        b=(Button)findViewById(R.id.btn_title);
//        Typeface tf= Typeface.createFromAsset(getAssets(), "fonts/Coco Gothic Bold-trial.ttf");
//        Typeface tf1= Typeface.createFromAsset(getAssets(), "fonts/Coco Gothic Ultralight-trial.ttf");
//        b.setTypeface(tf);

        contentPager = (ViewPager) findViewById(R.id.pager);
        contentPager.setOffscreenPageLimit(2);
        final Bundle bun=new Bundle();
        String[] imgs = {"http://h.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=fed1392e952bd40742c7d7f449b9a532/e4dde71190ef76c6501a5c2d9f16fdfaae5167e8.jpg",
                "http://a.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=25d477ebe51190ef01fb96d6fc2ba675/503d269759ee3d6df51a20cd41166d224e4adedc.jpg",
                "http://c.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=70d2b81e60d0f703e6b291d53aca6a5e/0ff41bd5ad6eddc4ab1b5af23bdbb6fd5266333f.jpg"};

        int [] images={R.drawable.banner_o,R.drawable.bannerp,R.drawable.bannerq};
       // bun.putStringArray("arr",imgs);
        bun.putIntArray("arr",images);

        contentPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int i) {
                if (i == 0) {

                    AutoScrollPagerFragment aspf=new AutoScrollPagerFragment();
                    aspf.setArguments(bun);

                    return aspf;
                    // return new AutoScrollPagerFragment().setArguments(bun);
                }
                return TextFragment.newInstance("Fragment " + i);
            }

            @Override
            public int getCount() {
                return 3;
            }
        }  );

        sectionAdapter = new SectionedRecyclerViewAdapter();

        if (isNet) {

            try {

                JSONObject jo = new JSONObject();
                jo.put("user_id", "3");
                Log.e("id",""+jo.toString());
                get_data_home_page_2(jo, getResources().getString(R.string.server) + Constants.getclasses_service);


            }catch (Exception e) {

                e.printStackTrace();
            }
        }

        else
        {

        //    Toast.makeText(MainActivity.this, "No Internent Connection!", Toast.LENGTH_SHORT).show();
            Snackbar snackBar = Snackbar.make(view, "No Internent Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finish();
                            startActivity(getIntent());
                        }
                    });
            snackBar.setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackBar.show();

        }
       /* sectionAdapter.addSection(new MovieSection("Music", getTopRatedMoviesList()));
        sectionAdapter.addSection(new MovieSection("Dance", getMostPopularMoviesList()));
        sectionAdapter.addSection(new MovieSection("Art",getArtList()));
        sectionAdapter.addSection(new MovieSection("Others",getOthersList()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(sectionAdapter);

*/

    }
    public void get_myclass_page (JSONObject jo, String url) {

        CustomAsync ca = new CustomAsync(MainActivity.this, jo, url, new OnAsyncCompleteRequest() {
            @Override
            public void asyncResponse(String result) {

                if (result.equals("") || result == null) {


                    Snackbar snackBar = Snackbar.make(MainActivity.this.findViewById(R.id.main_content), "Please try Again!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View sbView = snackBar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackBar.show();

                } else {
                    try {

                        JSONObject jo = new JSONObject(result);
                        String status = jo.getString("success");
                        ArrayList<String> anames=new ArrayList<>();
                        ArrayList<String> asession=new ArrayList<>();
                        ArrayList<String> alogos=new ArrayList<>();
                        ArrayList<String> acids=new ArrayList<>();

                        if (status.equals("1"))
                        {

                            String  image_url = jo.getString("image_url");

                            HashMap<String,String> cnames=new HashMap<>();
                            HashMap<String,String> clogos=new HashMap<>();
                            HashMap<String,String> csession=new HashMap<>();
                            ArrayList<String> snames_list=new ArrayList<>();
                            ArrayList<String> slogos_list=new ArrayList<>();
                            ArrayList<String> ssession_list=new ArrayList<>();


                            ArrayList<ArrayList<String >> adays=new ArrayList<>();
                            ArrayList<ArrayList<String >> atimes=new ArrayList<>();

                            ArrayList<String> days=new ArrayList<>();
                            ArrayList<String> times=new ArrayList<>();

                            JSONArray cjar=jo.getJSONArray("classes");

                            for(int c=0;c<cjar.length();c++)
                            {
                                JSONObject cj=cjar.getJSONObject(c);

                                cnames.put(cj.getString("id"),cj.getString("class_name"));
                                clogos.put(cj.getString("id"),image_url+"/"+cj.getString("logo"));

                            }
                            JSONArray sjar=jo.getJSONArray("sessions");
                            Log.e("eee","eee0");

                            for(int c=0;c<sjar.length();c++)
                            {
                                Log.e("aag","aaag1");
                                JSONObject sj=sjar.getJSONObject(c);
                                Log.e("eeeaag","aag2");


                                csession.put(sj.getString("id"),sj.getString("total_sessions"));
                                Log.e("eeeaga","aag2");
                            }

                            JSONArray ujar=jo.getJSONArray("members");
                            members_models.clear();
                            for(int members=0;members<ujar.length();members++)
                            {

                                JSONObject ujab = ujar.getJSONObject(members);
                                if (jo.has(ujab.getString("id")))
                                {
                                    JSONObject jsonObject= jo.getJSONObject(ujab.getString("id"));



                                    for (int m=0;m<jsonObject.length();m++)
                                    {
                                        Members_Model members_model=new Members_Model();
                                        members_model.setId(ujab.getString("id"));
                                        members_model.setName(ujab.getString("name"));
                                        members_models.add(members_model);
                                      //  Log.e("arrays_keys",members_models.get(m).getId()+""+members_models.size());


                                    }


                                    Iterator<String> mkeys=jsonObject.keys();

                                    while(mkeys.hasNext())
                                    {
                                        String key=mkeys.next();
                                        JSONArray jsonArray=jsonObject.getJSONArray(key);
                                        JSONArray jsonArray1=jo.getJSONArray("classes");
                                        JSONArray jsonArray2=jo.getJSONArray("sessions");
                                        snames_list.add(cnames.get(key));
                                        slogos_list.add(clogos.get(key));
                                        ssession_list.add(csession.get(key));
                                        ids.add(key);
                                    }
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("clas_id",ids.toString().replace("]","").replace("[","").replace(" ",""));
                                    editor.commit();
                                    Log.e("classssseesss",sp.getString("clas_id","aa"));



                                }
                            }





                        }
                        else
                        {

                            Toast.makeText(MainActivity.this, "" + status, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e)
                    {

                        e.printStackTrace();
                        Log.e("Exception",e.toString()+"   "+e.getStackTrace()[0].getLineNumber());
                    }
                }

            }
        });
        ca.execute();
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        DataBase_Helper dh=new DataBase_Helper(this);
        final int c=dh.getUserCount();
        if(c!=0)
        {
            DataBase_Helper db = new DataBase_Helper(this);
            UserId = db.getUserId("1");
            Log.e("aacc", "user_id" + UserId);
            reg.setText("LOGOUT");
            user_name.setBackgroundColor(Color.TRANSPARENT);

        }
        else
        {
            user_name.setText("LOGIN");
            user_name.setTextColor(Color.WHITE);
            user_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg));
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if(c==0)
                    {
                        startActivity(new Intent(MainActivity.this,Login_page.class));
                    }
                    else
                    {
                        startActivity(new Intent(MainActivity.this, Profile.class));

                    }
                }
            });
        }




        if(c!=0)
        {
        if (new ConnectionDetector(this).isConnectingToInternet())
        {
            try
            {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("user_id",new DataBase_Helper(this).getUserId("1"));
                get_profile(jsonObject, getResources().getString(R.string.server)+Constants.get_userprofile_service);

            }
            catch (Exception e)
            {
                Log.e("exception",e.toString());
            }

        }else
        {
            Toast.makeText(MainActivity.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
        }
        }


    }
    private  void get_profile(JSONObject jo, String url)
    {
        CustomAsync_2 ca=new CustomAsync_2(MainActivity.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int  status=j.getInt("success");
                        if(status==1)
                        {
                        Glide.with(MainActivity.this)
                                .load(j.get("profile_pic"))
                                .placeholder(R.drawable.user_default)
                                .into(profile_pic);
                            user_name.setText(j.getString("name"));

                            SharedPreferences member_datails=getSharedPreferences("member_datails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=member_datails.edit();
                            editor.putString("member_id",j.getString("member_id"));
                            editor.commit();


                        }
                        else if(status==5)
                        {

                            Toast.makeText(MainActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();

                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        ca.execute();
    }


    public	void get_data_home_page (JSONObject jo, final String url) {

        CustomAsync ca = new CustomAsync(MainActivity.this, jo, url, new OnAsyncCompleteRequest() {

            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
            public void asyncResponse(String result) {

                if (result == null || result.equals("")) {

                    Snackbar snackBar = Snackbar.make(view, "Please try again!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    finish();
                                    startActivity(getIntent());
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View sbView = snackBar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackBar.show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);
                        String  image_url = jo.getString("image_url");
                        String status = jo.getString("success");

                        if (status.equals("1")) {
                            Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray=jo.getJSONArray("categories");
                            for(int i = 0; i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                JSONArray  jsonArray1=jo.getJSONArray(jsonObject.getString("id"));
                                home_cat_data2=new ArrayList<>();
                                for (int j=0;j<jsonArray1.length();j++)
                                {
                                    JSONObject jsonObject1=jsonArray1.getJSONObject(j);
                                    Movie movie =new Movie();
                                    movie.setMain_cat_id(jsonObject.getString("id"));
                                    movie.setCategory(jsonObject1.getString("class_name"));
                                    movie.setId(jsonObject1.getString("id"));
                                    movie.setImage(image_url+"/"+jsonObject1.getString("logo"));
                                    movie.setDesc(jsonObject1.getString("description"));

                                    Log.e("Ecxcep","bbbbb");
                                    home_cat_data2.add(movie);
                                }
                              //  home_arrays.add(home_cat_data);
                                sectionAdapter.addSection(new MovieSection(jsonObject.getString("category_name"),home_cat_data2));

                            }


                           /* sectionAdapter.addSection(new MovieSection("Music", getTopRatedMoviesList()));
                            sectionAdapter.addSection(new MovieSection("Dance", getMostPopularMoviesList()));
                            sectionAdapter.addSection(new MovieSection("Art",getArtList()));
                            sectionAdapter.addSection(new MovieSection("Others",getOthersList()));*/

                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

                            GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 3);
                            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    switch(sectionAdapter.getSectionItemViewType(position)) {
                                        case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                                            return 3;
                                        default:
                                            return 1;
                                    }
                                }
                            });
                            recyclerView.setLayoutManager(glm);
                            recyclerView.setAdapter(sectionAdapter);


                        }

                        else {

                            Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e) {

                        e.printStackTrace();
                        Log.e("escsses","bbbbb"+e.toString());

                    }


                }

            }
        });
        ca.execute();
    }
    public	void get_data_home_page_2 (JSONObject jo, final String url) {

        CustomAsync ca = new CustomAsync(MainActivity.this, jo, url, new OnAsyncCompleteRequest() {

            Intent intent2 = getIntent();
            Bundle b = intent2.getExtras();
            public void asyncResponse(String result) {

                if (result == null || result.equals("")) {

                    Snackbar snackBar = Snackbar.make(view, "Please try again!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    finish();
                                    startActivity(getIntent());
                                }
                            });
                    snackBar.setActionTextColor(Color.RED);
                    View sbView = snackBar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackBar.show();
                }

                else {

                    try {

                        JSONObject jo = new JSONObject(result);
                        String  image_url = jo.getString("image_url");
                        String status = jo.getString("success");

                        if (status.equals("1")) {
                         //   Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray=jo.getJSONArray("categories");
                            for(int i = 0; i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                JSONArray  jsonArray1=jo.getJSONArray(jsonObject.getString("id"));
                                home_cat_data=new ArrayList<>();
                                for (int j=0;j<jsonArray1.length();j++)
                                {
                                    JSONObject jsonObject1=jsonArray1.getJSONObject(j);
                                    Moviee movie =new Moviee();
                                    movie.setMain_cat_id(jsonObject.getString("id"));
                                    movie.setCategory(jsonObject1.getString("class_name"));
                                    movie.setId(jsonObject1.getString("id"));
                                    movie.setImage(image_url+"/"+jsonObject1.getString("logo"));
                                    movie.setDesc(jsonObject1.getString("description"));

                                    Log.e("Ecxcep","bbbbb");
                                    home_cat_data.add(movie);
                                }
                                headers.add(jsonObject.getString("category_name"));
                                  home_arrays.add(home_cat_data);


                            }
                            myExpandableListAdapter=new MyExpandableListAdapter(MainActivity.this,
                                    headers,home_arrays);
                            home_cat_list.setAdapter(myExpandableListAdapter);

                          for (int i=0;i<home_arrays.size();i++)
                          {
                              home_cat_list.expandGroup(i);
                          }

                            home_cat_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView parent, View v,
                                                            int groupPosition, long id) {
                                    return true; // This way the expander cannot be collapsed
                                }
                            });



                        }

                        else {

                            Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e) {

                        e.printStackTrace();
                        Log.e("escsses","bbbbb"+e.toString());

                    }


                }

            }
        });
        ca.execute();
    }



    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Throwable throwable) {
            // igonre
        }
        return 0;
    }

    class SlideitemListener implements ListView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            DataBase_Helper dh=new DataBase_Helper(MainActivity.this);
            int c=dh.getUserCount();
           if (c!=0) {
               updateDisplay(position);
           } else
           {
               updateDisplay1(position);

           }
        }

    }

    private void updateDisplay(int position) {
        //  Fragment fragment = null;
        //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
        DataBase_Helper dh=new DataBase_Helper(this);
        int c=dh.getUserCount();
        switch (position) {
            case 0://cta.calProfile();

                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,SubCat.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }

//                if(mDrawerLayout.isDrawerOpen(mDrawerList_Right))
//                    mDrawerLayout.closeDrawer(mDrawerList_Right);

                break;
            case 1://cta.calPost();                 //cta.calRent();
                //              startActivity(new Intent(MainActivity.this,FeedBack.class));

                // cta.calOffers();
                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,Notif.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
               // startActivity(new Intent(MainActivity.this,MyCal.class));



                break;
            case 2:
              //  startActivity(new Intent(MainActivity.this,PayFees.class));
                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,FeedBack.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }

                //cta.calOView();

                //cta.calRent();
                break;
            case 3:
                // show_popup();
                 if (new ConnectionDetector(MainActivity.this).isConnectingToInternet())
                {
                    if(c!=0)
                    {
                        try
                        {
                            JSONObject jsonObject=new JSONObject();

                            jsonObject.put("mobile",new DataBase_Helper(MainActivity.this).getUserMobileNumber("1"));
                            Log.e("jsonObject",""+jsonObject.toString());
                            getBranches(jsonObject,getResources().getString(R.string.server)+Constants.getbranches_service);
                        }catch ( Exception e)
                        {
                            Log.e("exception",""+e.toString());

                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please check your internet connectivity", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
              //  startActivity(new Intent(MainActivity.this,Notif.class));
                startActivity(new Intent(MainActivity.this,AboutKD.class));

                // cta.calHelp();
                //cta.calSale();
                break;
            case 5:	//cta.calCC();
                startActivity(new Intent(MainActivity.this,Faq.class));
                //startActivity(new Intent(MainActivity.this,Offers.class));

                break;

            case 6: //cta.calFaq();
                startActivity(new Intent(MainActivity.this,AboutApp.class));
                break;


        }
    }
    private void updateDisplay1(int position)
    {
        //  Fragment fragment = null;
        //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
        DataBase_Helper dh=new DataBase_Helper(this);
        int c=dh.getUserCount();
        switch (position) {
            case 0://cta.calProfile();

                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,SubCat.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }

//                if(mDrawerLayout.isDrawerOpen(mDrawerList_Right))
//                    mDrawerLayout.closeDrawer(mDrawerList_Right);

                break;
            case 1://cta.calPost();                 //cta.calRent();
                //              startActivity(new Intent(MainActivity.this,FeedBack.class));

                // cta.calOffers();
                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,Notif.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
               // startActivity(new Intent(MainActivity.this,MyCal.class));



                break;
            case 2:
              //  startActivity(new Intent(MainActivity.this,PayFees.class));
                if(c!=0)
                {
                    startActivity(new Intent(MainActivity.this,FeedBack.class));

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }

                //cta.calOView();

                //cta.calRent();
                break;
          /*  case 3:
                 show_popup();
                break;*/
            case 3:
              //  startActivity(new Intent(MainActivity.this,Notif.class));
                startActivity(new Intent(MainActivity.this,AboutKD.class));

                // cta.calHelp();
                //cta.calSale();
                break;
            case 4:	//cta.calCC();
                startActivity(new Intent(MainActivity.this,Faq.class));
                //startActivity(new Intent(MainActivity.this,Offers.class));

                break;

            case 5: //cta.calFaq();
                startActivity(new Intent(MainActivity.this,AboutApp.class));
                break;


        }
    }


    class MovieSection extends StatelessSection {

        String title;
        List<Movie> list;

        public MovieSection(String title, List<Movie> list) {
            super(R.layout.section_ex5_header, R.layout.section_ex5_item);

            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            // String name = list.get(position).getName();
            String category = list.get(position).getCategory();

            itemHolder.tvSubItem.setText(category);
            // itemHolder.imgItem.setImageResource(R.mipmap.ic_launcher);
            Drawable d= VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_circle_flower, null);

            itemHolder.imgV.setBackgroundResource(R.drawable.ic_circle_flower);

            Glide.with(MainActivity.this).load(list.get(position).getImage())
                    .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                    //.error(R.drawable.profile60)
                    .placeholder(R.drawable.sm1)
                    //.bitmapTransform(new RoundedCornersTransformation(MainActivity.this,30,30))
                    .into(itemHolder.imgItem);

                    //  .bitmapTransform(new RoundedCornersTransformation(HomeActivity.this,30,30))




            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                    Toast.makeText(MainActivity.this, String.format("Clicked on position #%s of Section %s",
                            sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title),
                            Toast.LENGTH_SHORT).show();
*/

                    startActivity(new Intent(MainActivity.this,SerDetailActivity.class)
                    .putExtra("main_cat_id",list.get(position).getMain_cat_id())
                    .putExtra("id",list.get(position).getId())
                    .putExtra("Image",list.get(position).getImage())
                    .putExtra("title",list.get(position).getCategory())
                    .putExtra("desc",list.get(position).getDesc()));
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

          //  headerHolder..setText(title);
            headerHolder.tvTitle.setText(title);

//            headerHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, String.format("Clicked on more button from the header of Section %s",
//                            title),
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        // private final Button btnMore;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            //  btnMore = (Button) view.findViewById(R.id.btnMore);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        //  private final TextView tvItem;
        private final TextView tvSubItem;
        private final FrameLayout imgV;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgV=(FrameLayout)view.findViewById(R.id.imgView);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            //  tvItem = (TextView) view.findViewById(R.id.tvItem);
            tvSubItem = (TextView) view.findViewById(R.id.tvSubItem);
        }
    }

    class Movie
    {
        public String getMain_cat_id() {
            return main_cat_id;
        }

        public void setMain_cat_id(String main_cat_id) {
            this.main_cat_id = main_cat_id;
        }

        //  String name;
       private String main_cat_id;
        private String category;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        String image;

        public void Movie(String category)
        {
            // this.name = name;
            this.category = category;
        }

        //  public String getName() {

        //return name;
        //  }

        //   public void setName(String name) {
        //      this.name = name;
        //  }
//
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    DrawerLayout.DrawerListener myDrawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerClosed(View drawerView) {
            // rideLater.setText("onDrawerClosed");
        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {


        }

        @Override
        public void onDrawerStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

    };

    public void show_popup()
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.schedule_popup);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        Button kondapur=(Button)dialog.findViewById(R.id.kondapur);
        Button nallagandla=(Button)dialog.findViewById(R.id.nallagandla);

        kondapur.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                 startActivity(new Intent(MainActivity.this,schedule_images.class)
                 .putExtra("status","0"));
                dialog.dismiss();

            } });

        nallagandla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,schedule_images.class)
                        .putExtra("status","1"));

                dialog.dismiss();

            }
        });
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(
                Color.WHITE));
        //   window.setWindowAnimations(R.style.ActivityAnimations);
        dialog.show();


    }

    private  void getBranches(JSONObject jo, String url)
    {
        CustomAsync ca=new CustomAsync(MainActivity.this, jo, url, new OnAsyncCompleteRequest() {

            @Override
            public void asyncResponse(String result) {
                // TODO Auto-generated method stub
                if(result==null||result.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject j=new JSONObject(result);
                        int status=j.getInt("success");
                        if(status==1)
                        {
                            b_names.clear();
                                    branches_models.clear();
                            for (int i=0;i<j.length()-1;i++)
                            {
                                JSONObject jsonObject=j.getJSONObject(""+i);
                                Branches_model branches_model=new Branches_model();
                                branches_model.setBranch_id(jsonObject.getString("id"));
                                branches_model.setBranch_name(jsonObject.getString("branch_name"));
                                if (!jsonObject.getString("registration_amount").equals(null))
                                {
                                    branches_model.setReg_fee(jsonObject.getString("registration_amount"));
                                }
                                else
                                {
                                    branches_model.setReg_fee("0");

                                }
                                b_names.add(jsonObject.getString("branch_name"));
                                branches_models.add(branches_model);

                            }


                                    final Dialog dialog = new Dialog(MainActivity.this);
                                    dialog.requestWindowFeature(1);
                                    dialog.setContentView(R.layout.branch_list_popup);
                                    TextView title =(TextView)dialog.findViewById(R.id.title);

                                    title.setText("Schedule");

                                    ListView listView=(ListView)dialog.findViewById(R.id.list_branches);
                                    listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.branch_lits_item,b_names));
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                        {
                                            startActivity(new Intent(MainActivity.this,ThreeLevelExpandableListView.class)
                                                    .putExtra("branch_id",branches_models.get(position).getBranch_id()));

                                            dialog.dismiss();
                                        }
                                    });
                                    Window window = dialog.getWindow();

                                    window.setGravity(Gravity.CENTER);
                                    dialog.show();


                            // Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            Toast.makeText(MainActivity.this, ""+status, Toast.LENGTH_SHORT).show();
                        }



                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("breamch_exception",""+e.toString());
                    }
                }
            }

        });
        ca.execute();
    }



}
