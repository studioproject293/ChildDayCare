package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.activity.DemoLoginActivity;
import com.example.abhinav_rapidbox.childdaycare.adapter.HomeRecyclerAdapter;
import com.example.abhinav_rapidbox.childdaycare.adapter.ProductViewPagerAdapter;
import com.example.abhinav_rapidbox.childdaycare.cache.AppCache;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.abhinav_rapidbox.childdaycare.service.ApiServices.REQUEST_DAYCARE;

public class HomeFragment extends BaseFragment implements OnFragmentListItemSelectListener, View.OnClickListener, EventListner {
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    HomeRecyclerAdapter homeRecyclerAdapter;
    ProductViewPagerAdapter productViewPagerAdapter;
    SpringDotsIndicator springDotsIndicator;
    ViewPager product_viewPager;
    ArrayList<DayCareListModel> dayCareListModels;
    String checkValue = "";
    PrefManager prefManager;
    Dialog sortdialog;
    private View rootView;
    private TextView filter, textViewshort;
    private RecyclerView recyclerViewHome;
    private int currentPage = 0;
    private int imageArra[] = {R.drawable.imagecurosial1, R.drawable.dummy2, R.drawable.dummy3};

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setId();
        prefManager = PrefManager.getInstance();
        productViewPagerAdapter = new ProductViewPagerAdapter(getActivity(), imageArra);
        product_viewPager.setAdapter(productViewPagerAdapter);
        product_viewPager.setCurrentItem(0);
        springDotsIndicator.setViewPager(product_viewPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageArra.length) {
                    currentPage = 0;
                }
                product_viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        filter.setOnClickListener(this);
        textViewshort.setOnClickListener(this);
        return rootView;
    }

    private void setId() {
        recyclerViewHome = rootView.findViewById(R.id.recyclerviewhome);
        filter = rootView.findViewById(R.id.filter);
        textViewshort = rootView.findViewById(R.id.sortby);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        product_viewPager = (ViewPager) rootView.findViewById(R.id.product_viewPager);
        springDotsIndicator = (SpringDotsIndicator) rootView.findViewById(R.id.dots_indicator);

    }

    protected void showsortDialog() {
        sortdialog = new BottomSheetDialog(getContext());
        sortdialog.setCancelable(true);
        View view = getActivity().getLayoutInflater().inflate(R.layout.bottomdialog_sort, null);
        sortdialog.setContentView(view);
        RadioButton radioButtonFee = (RadioButton) view.findViewById(R.id.radiofee);
        RadioButton radioButtonRating = (RadioButton) view.findViewById(R.id.radioRating);
        Button buttonSubmit = view.findViewById(R.id.submit);
        Button buttonCancel = view.findViewById(R.id.cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortdialog.cancel();
            }
        });
        radioButtonFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    checkValue = "yes";
                }
            }
        });
        radioButtonRating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    checkValue = "no";
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortdialog.cancel();
                ArrayList<DayCareListModel> arrayList = new ArrayList<>();
                if (AppCache.getInstance().getDayCareListModels() != null && AppCache.getInstance().getDayCareListModels().size() > 0) {
                    for (int i = 0; i < AppCache.getInstance().getDayCareListModels().size(); i++) {
                        DayCareListModel dayCareListModel = AppCache.getInstance().getDayCareListModels().get(i);
                        if (checkValue.equals("yes"))
                            dayCareListModel.setFeeOrNot("yes");
                        else
                            dayCareListModel.setFeeOrNot("no");

                        arrayList.add(i, dayCareListModel);
                        Collections.sort(arrayList);
                        updateList(arrayList);
                    }
                }
            }
        });
        sortdialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData("Day Care List"));
        DialogUtil.displayProgress(getActivity());
        TransportManager.getInstance(this).getDayCareList(getActivity());

    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        final DayCareListModel dayCareListModel = (DayCareListModel) data;
        switch (itemId) {
            case R.id.list_item:
                if (TextUtils.isEmpty(prefManager.getUsername())) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Alert !");
                    alert.setMessage("Sorry you don't have  permission to see details.Please login or signup.");
                    alert.setPositiveButton("LogIn",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(getActivity(), DemoLoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                    alert.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alert.show();
                } else {
                    mListener.onFragmentInteraction(AppConstant.PRODUCT_DETAILS_FRAGMENT, dayCareListModel);
                }
                break;
            case R.id.image_icon:
                if (TextUtils.isEmpty(prefManager.getUsername())) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Alert !");
                    alert.setMessage("Sorry you don't have  permission to see details.Please login or signup.");
                    alert.setPositiveButton("LogIn",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(getActivity(), DemoLoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                    alert.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alert.show();
                } else {
                    prefManager.setAmount(String.valueOf(dayCareListModel.getFee()));
                    mListener.onFragmentInteraction(AppConstant.PRODUCT_DETAILS_FRAGMENT, dayCareListModel);
                }
                break;
            case R.id.textDistance:
                String geoUri = "http://maps.google.com/maps?q=loc:" + dayCareListModel.getLatitude() + "," + dayCareListModel.getLongitude() + " (" + dayCareListModel.getName() + ")";
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", dayCareListModel.getLatitude(), dayCareListModel.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    public void logoutApp() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(getString(R.string.logout_message));

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                prefManager.setUsername(null);
                Intent intent = new Intent(getActivity(), DemoLoginActivity.class);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter:
                mListener.onFragmentInteraction(AppConstant.FilterFragment, null);
                break;
            case R.id.sortby:
                // Sorting
               /* final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(false);

                // there are a lot of settings, for dialog, check them all out!
                // set up radiobutton
                RadioButton radioButtonFee = (RadioButton) dialog.findViewById(R.id.radiofee);
                RadioButton radioButtonRating = (RadioButton) dialog.findViewById(R.id.radioRating);
                Button buttonSubmit = dialog.findViewById(R.id.submit);
                Button buttonCancel = dialog.findViewById(R.id.cancel);
                // now that the dialog is set up, it's time to show it
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                radioButtonFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            checkValue = "yes";
                        }
                    }
                });
                radioButtonRating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            checkValue = "no";
                        }
                    }
                });
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        ArrayList<DayCareListModel> arrayList = new ArrayList<>();
                        if (AppCache.getInstance().getDayCareListModels() != null && AppCache.getInstance().getDayCareListModels().size() > 0) {
                            for (int i = 0; i < AppCache.getInstance().getDayCareListModels().size(); i++) {
                                DayCareListModel dayCareListModel = AppCache.getInstance().getDayCareListModels().get(i);
                                if (checkValue.equals("yes"))
                                    dayCareListModel.setFeeOrNot("yes");
                                else
                                    dayCareListModel.setFeeOrNot("no");

                                arrayList.add(i, dayCareListModel);
                                Collections.sort(arrayList);
                                updateList(arrayList);
                            }
                        }
                    }
                });
                dialog.show();*/
                showsortDialog();

                break;
        }
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        switch (reqType) {
            case REQUEST_DAYCARE:
                DialogUtil.stopProgressDisplay();
                ArrayList<DayCareListModel> dayCareListModels = (ArrayList<DayCareListModel>) data.getData();
                AppCache.getInstance().setDayCareListModels(dayCareListModels);
                updateList(dayCareListModels);

                break;
        }
    }

    private void updateList(ArrayList<DayCareListModel> dayCareListModels) {
        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(), dayCareListModels);
        homeRecyclerAdapter.setListner(this);
        recyclerViewHome.setAdapter(homeRecyclerAdapter);


    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
    }
}
