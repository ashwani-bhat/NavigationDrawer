
package com.storage.myfirstproject.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.storage.myfirstproject.R;
import com.storage.myfirstproject.fragment.Fragment1;
import com.storage.myfirstproject.fragment.Fragment2;
import com.storage.myfirstproject.fragment.Fragment3;
import com.storage.myfirstproject.fragment.Fragment4;
import com.storage.myfirstproject.fragment.Fragment5;
import com.storage.myfirstproject.other.CircleTransform;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg,imgProfile;
    private TextView txtName,txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    public static int navItemIndex=0;
    private ActionBar actionBar;
    private static final String INDEX_1 = "index1";
    private static final String INDEX_2 = "index2";
    private static final String INDEX_3 = "index3";
    private static final String INDEX_4 = "index4";
    public static String CURRENT_TAG = INDEX_1;

    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // actionBar = getActionBar();
      //  actionBar.setIcon(R.drawable.threelines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile =(ImageView)navHeader.findViewById(R.id.img_profile);
        activityTitles =getResources().getStringArray(R.array.nav_item_activity_titles);
        loadNavHeader();
        setUpNavigationView();
        if(savedInstanceState==null){
            navItemIndex=0;
            CURRENT_TAG=INDEX_1;
            loadHomeFragment();
        }
    }
    private void loadNavHeader(){
        //name,website
        txtName.setText("Home Name");
        txtWebsite.setText("Home Website");
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5F)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
        navigationView.getMenu().getItem(3).setActionView(R.layout.render_dot);
    }
    private void loadHomeFragment(){
        selectNavMenu();  //selecting appropriate nav menu item
        setToolbarTitle(); //set toolbar title
        //if user selects the current navigation menu again, don't do anything
        //just close the navigation drawer
       if(getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
           drawer.closeDrawers();
           //show or hide the fab button
           toggleFab();
           return;
       }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame,fragment,CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if(mPendingRunnable !=null){
            mHandler.post(mPendingRunnable);
        }
        toggleFab();
        drawer.closeDrawers();
        invalidateOptionsMenu();

    }
    private Fragment getHomeFragment(){
        switch(navItemIndex){
            case 0:
                Fragment1 fragment1 =new Fragment1();
                return fragment1;
            case 1:
                Fragment2 fragment2 =new Fragment2();
                return fragment2;
            case 2:
                Fragment3 fragment3 =new Fragment3();
                return fragment3;
            case 3:
                Fragment4 fragment4 =new Fragment4();
                return fragment4;
            case 4:
                Fragment5 fragment5 =new Fragment5();
                return fragment5;
            default:
                return new Fragment1();
        }
    }
    private void setToolbarTitle(){
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    private void selectNavMenu(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if(shouldLoadHomeFragOnBackPress){
            if(navItemIndex != 0){
                navItemIndex=0;
                CURRENT_TAG = INDEX_1;
                loadHomeFragment();
                return ;
            }
        }
        super.onBackPressed();
    }

    private void setUpNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_1:
                        navItemIndex=0;
                        CURRENT_TAG=INDEX_1;
                        break;
                    case R.id.nav_2:
                        navItemIndex=1;
                        CURRENT_TAG=INDEX_2;
                        break;
                    case R.id.nav_3:
                        navItemIndex=2;
                        CURRENT_TAG=INDEX_3;
                        break;
                    case R.id.nav_4:
                        navItemIndex=3;
                        CURRENT_TAG=INDEX_4;
                        break;
                    case R.id.nav_about_us:
                        startActivity(new Intent(MainActivity.this,AboutUs.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_privacy_policy:
                        startActivity(new Intent(MainActivity.this,PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        break;
                    default:
                        navItemIndex=0;
                }
                if(item.isChecked()){
                    item.setChecked(false);
                }
                else{
                    item.setChecked(true);
                }
                item.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(MainActivity.this,drawer,
                        toolbar,R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close){
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(navItemIndex==0)
            getMenuInflater().inflate(R.menu.main, menu);
        if(navItemIndex==3)
            getMenuInflater().inflate(R.menu.notification,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(),"LoggedOut",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==R.id.action_mark_all_read){
            Toast.makeText(getApplicationContext(),"All notifications marked as read",Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.action_clear_notifications)
            Toast.makeText(getApplicationContext(),"Clear all notifications",Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
    private void toggleFab(){
        if(navItemIndex==0)
            fab.show();
        else
            fab.hide();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_1) {
            navItemIndex = 0;
            CURRENT_TAG = INDEX_1;

        } else if (id == R.id.nav_2) {
            navItemIndex=1;
            CURRENT_TAG = INDEX_2;

        } else if (id == R.id.nav_3) {
            navItemIndex =2;
            CURRENT_TAG=INDEX_3;

        } else if (id == R.id.nav_4) {
            navItemIndex=3;
            CURRENT_TAG=INDEX_4;
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(MainActivity.this,AboutUs.class));
            drawer.closeDrawers();
        } else if (id == R.id.nav_privacy_policy) {
            startActivity(new Intent(MainActivity.this,PrivacyPolicyActivity.class));
            drawer.closeDrawers();
        }
        else
            navItemIndex=0;

        if(item.isChecked()){
            item.setChecked(false);
        }
        else{
            item.setChecked(true);
        }
        item.setChecked(true);
        loadHomeFragment();
        return true;
    }
}
