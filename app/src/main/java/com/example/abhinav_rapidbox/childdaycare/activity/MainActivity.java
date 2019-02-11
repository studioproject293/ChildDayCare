package com.example.abhinav_rapidbox.childdaycare.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.fragment.AddReviewFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.EnquiryFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.FilterFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.HomeFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.ImageFullScreenFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.ProductDetailsFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.SignInFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.SignupFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.SignupFragmentChild;
import com.example.abhinav_rapidbox.childdaycare.fragment.UserProfileFragment;
import com.example.abhinav_rapidbox.childdaycare.fragment.UserSignUpFragment;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentInteractionListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
import com.example.abhinav_rapidbox.childdaycare.pojo.ZoomImage;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.Constants;
import com.example.abhinav_rapidbox.childdaycare.utill.HashCodeFileNameWithDummyExtGenerator;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MainActivity extends BaseActivity
        implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    public static DisplayImageOptions options;
    public static ImageLoader imageLoader;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    PrefManager prefManager;
    Toolbar toolbar_home;
    ImageView sidemenu;
    TextView toolbar_title;
    DrawerLayout drawer;
    private FragmentManager mFragmentManager;
    private int mCurrentFragment;
    private TextView textheader, text;
    private String mFragmentTag;
    private ImageView imageViewSideMenu;
    private BottomNavigationView navigation;
    public static BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setId();
        prefManager = PrefManager.getInstance();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true).build();
        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).diskCache(new UnlimitedDiskCache(cacheDir, null, new HashCodeFileNameWithDummyExtGenerator())).defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
        /*bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        final Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.home).setIcon(R.drawable.ic_home_black_24dp);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_home_black_24dp);
                        if (mCurrentFragment != AppConstant.HOME_FRAGMENT)
                            clearHomeScreen(AppConstant.HOME_FRAGMENT);
                        break;

                    case R.id.action_refer:

                        break;
                    case R.id.cart:

                        break;
                }
                return true;
            }
        });*/
        onFragmentInteraction(AppConstant.HOME_FRAGMENT, null);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    sidemenu.setImageResource(R.drawable.ic_icons_back);
                } else {
                    sidemenu.setImageResource(R.drawable.ic_icons_menu);
                }
            }
        });
        drawer.closeDrawer(GravityCompat.START);

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count <= 1) {
                    if (drawer.isDrawerOpen(Gravity.LEFT)) {
                        drawer.closeDrawer(Gravity.LEFT);
                    } else drawer.openDrawer(Gravity.LEFT);
                } else {
                    onBackPressed();
                }
            }
        });

    }
    public void clearHomeScreen(int fragmentId) {
        try {
            if (getSupportFragmentManager().findFragmentByTag(String.valueOf(fragmentId)) != null) {
                FragmentManager manager = getSupportFragmentManager();

                for (int i = manager.getBackStackEntryCount() - 1; i > 0; i--) {
                    String fragment = manager.getBackStackEntryAt(i).getName();
                    if (!fragment.equals(fragmentId)) {
                        manager.popBackStack();
                    } else {
                        break;
                    }
                }
            } else {
                onFragmentInteraction(fragmentId, null);
            }
        } catch (Exception e) {

        }
    }

    private void setId() {
        drawer = findViewById(R.id.drawer_layout);
        toolbar_home = findViewById(R.id.toolbar_home);
        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar_home);
        textheader = findViewById(R.id.textheader);
        sidemenu = findViewById(R.id.side_menu);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.addHeaderView(setUpHeaderView());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }


    @Override
    public void onFragmentInteraction(int fragmentId, Object data) {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = fragmentId;
        mFragmentTag = String.valueOf(fragmentId);
        switch (fragmentId) {
            case AppConstant.HOME_FRAGMENT:
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new HomeFragment().newInstance(),
                                mFragmentTag).commit();
                break;
            case AppConstant.PRODUCT_DETAILS_FRAGMENT:
                DayCareListModel dayCareListModel = (DayCareListModel) data;
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new ProductDetailsFragment().newInstance(dayCareListModel),
                                mFragmentTag).commit();
                break;
            case AppConstant.SignupFragment:

                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new SignupFragment(),
                                mFragmentTag).commit();
                break;
            case AppConstant.SignInFragment:
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new SignInFragment(),
                                mFragmentTag).commit();
                break;
            case AppConstant.ENQUIRY_FRAGMENT:
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new EnquiryFragment(),
                                mFragmentTag).commit();
                break;
            case AppConstant.SIGNUP_FRAGMENT_CHILD:
                ChildSignUp childSignUp = (ChildSignUp) data;
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new SignupFragmentChild().newInstance(childSignUp),
                                mFragmentTag).commit();
                break;
           /* case AppConstant.FRAGMENT_OTP:
                UserSignUpModel userSignUpModel = (UserSignUpModel) data;
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new OtpFragment().newInstance(userSignUpModel),
                                mFragmentTag).commit();
                break;*/
            case AppConstant.FRAGMENT_USER_SIGNUP:
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new UserSignUpFragment().newInstance(),
                                mFragmentTag).commit();
                break;

            case AppConstant.USER_PROFILE:
                mFragmentManager
                        .beginTransaction()
                        .addToBackStack(mFragmentTag)
                        .replace(R.id.fragment_main, new UserProfileFragment().newInstance(),
                                mFragmentTag).commit();
                break;
            case AppConstant.IMAGEFULLSCREENFRAGMENT:
                // ArrayList<String> imageList = (ArrayList<String>) data;
                ZoomImage zoomImage = (ZoomImage) data;
                // mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.left_enter, R.anim.right_out).replace(R.id.fragment_main, ImageFullScreenFragment.newInstance(imageList), mFragmentTag).commit();
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.left_enter, R.anim.right_out).replace(R.id.fragment_main, ImageFullScreenFragment.newInstance(zoomImage), mFragmentTag).commit();

                break;
            case AppConstant.FilterFragment:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.left_enter, R.anim.right_out).replace(R.id.fragment_main, FilterFragment.newInstance(), mFragmentTag).commit();
                break;
            case AppConstant.AddReviewFragment:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).setCustomAnimations(R.anim.right_enter, R.anim.left_out, R.anim.left_enter, R.anim.right_out).replace(R.id.fragment_main, AddReviewFragment.newInstance(), mFragmentTag).commit();
                break;


        }
    }

    @Override
    public void onFragmentUpdate(int type, Object data) {
        switch (type) {
            case AppConstant.UPDATE_TOOLBAR:
                HeaderData headerData = (HeaderData) data;
                updateToolbar(headerData);
                break;
        }
    }

    private void updateToolbar(HeaderData headerData) {
        if (headerData.getText() != null)
            toolbar_title.setText(headerData.getText());
        else
            toolbar_title.setText("Ninos");


    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            closeApp();
        } else
            super.onBackPressed();
    }

    public void closeApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.exit_message));

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.navigation_home:
                //onFragmentInteraction(AppConstant.HOME_FRAGMENT, null);
                break;
            case R.id.navigation_dashboard:
                break;
            case R.id.navigation_notifications:
                onFragmentInteraction(AppConstant.USER_PROFILE, null);
                break;
            case R.id.navigation_logout:
                logoutApp();
                break;
            case R.id.rating:
                onFragmentInteraction(AppConstant.AddReviewFragment, null);
                break;
//            case R.id.about_us:
//                onFragmentInteraction(FRAGMENT_ABOUT_US, null);
//                break;

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;

    }

    public void logoutApp() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.logout_message));

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                prefManager.setUsername(null);
                Intent intent = new Intent(MainActivity.this, DemoLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public View setUpHeaderView() {
        View header = getLayoutInflater().inflate(R.layout.nav_header_home, navigationView, false);
        TextView text_navigation = (TextView) header.findViewById(R.id.text_navigation);
        LinearLayout layout_profile = (LinearLayout) header.findViewById(R.id.layout_profile);


        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onFragmentInteraction(FRAGMENT_PROFILE, null);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        return header;
    }

}
