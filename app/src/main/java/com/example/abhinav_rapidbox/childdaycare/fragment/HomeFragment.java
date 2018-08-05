package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.adapter.HomeRecyclerAdapter;
import com.example.abhinav_rapidbox.childdaycare.adapter.ProductViewPagerAdapter;
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
import java.util.Timer;
import java.util.TimerTask;

import static com.example.abhinav_rapidbox.childdaycare.service.ApiServices.REQUEST_DAYCARE;

public class HomeFragment extends BaseFragment implements OnFragmentListItemSelectListener, View.OnClickListener, EventListner {
    private View rootView;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private TextView login, textViewshort;
    private RecyclerView recyclerViewHome;
    HomeRecyclerAdapter homeRecyclerAdapter;
    ProductViewPagerAdapter productViewPagerAdapter;
    SpringDotsIndicator springDotsIndicator;
    ViewPager product_viewPager;
    private int currentPage = 0;
    private int imageArra[] = {R.drawable.dummy1, R.drawable.dummy2, R.drawable.dummy3};
    public static HomeFragment newInstance() {
        return  new HomeFragment();
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

        login.setOnClickListener(this);
        textViewshort.setOnClickListener(this);
        return rootView;
    }

    private void setId() {
        recyclerViewHome = rootView.findViewById(R.id.recyclerviewhome);
        login = rootView.findViewById(R.id.login);
        textViewshort = rootView.findViewById(R.id.sortby);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        product_viewPager = (ViewPager) rootView.findViewById(R.id.product_viewPager);
        springDotsIndicator = (SpringDotsIndicator) rootView.findViewById(R.id.dots_indicator);

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
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData("Day Care"));
        DialogUtil.displayProgress(getActivity());
        TransportManager.getInstance(this).getDayCareList(getActivity());


    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        final DayCareListModel dayCareListModel= (DayCareListModel) data;
        switch (itemId) {
            case R.id.cardID1:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Stop On The Go!");
                alert.setMessage("Sorry you don't have to permission to see details.Please login or signup.");
                alert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                mListener.onFragmentInteraction(AppConstant.PRODUCT_DETAILS_FRAGMENT, dayCareListModel);
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

                break;
        }
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mListener.onFragmentInteraction(AppConstant.SignInFragment, null);
                break;
            case R.id.sortby:

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
                updateList(dayCareListModels);
                break;
        }
    }

    private void updateList(ArrayList<DayCareListModel> dayCareListModels) {
        homeRecyclerAdapter = new HomeRecyclerAdapter(getActivity(),dayCareListModels);
        homeRecyclerAdapter.setListner(this);
        recyclerViewHome.setAdapter(homeRecyclerAdapter);


    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
      DialogUtil.stopProgressDisplay();
    }
}
