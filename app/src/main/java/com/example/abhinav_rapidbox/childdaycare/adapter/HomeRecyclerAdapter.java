package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.LatLng;
import com.example.abhinav_rapidbox.childdaycare.utill.Constants;
import com.example.abhinav_rapidbox.childdaycare.utill.GPSTracker;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.example.abhinav_rapidbox.childdaycare.activity.MainActivity.imageLoader;
import static com.example.abhinav_rapidbox.childdaycare.activity.MainActivity.options;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.homeViewHolder> {

    Context context;
    OnFragmentListItemSelectListener listener;
    ArrayList<DayCareListModel> dayCareListModels;

    public HomeRecyclerAdapter(Context context, ArrayList<DayCareListModel> dayCareListModels) {
        this.context = context;
        this.dayCareListModels = dayCareListModels;
    }

    @Override
    public homeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_row, parent, false);
        HomeRecyclerAdapter.homeViewHolder cartViewHolder = new HomeRecyclerAdapter.homeViewHolder(view);
        return cartViewHolder;
    }

    public void updateHome(ArrayList<DayCareListModel> cartItems) {
        this.dayCareListModels = cartItems;
        notifyDataSetChanged();
    }

    public void setListner(OnFragmentListItemSelectListener listner) {
        this.listener = listner;
    }

    @Override
    public void onBindViewHolder(homeViewHolder holder, final int position) {
        final DayCareListModel listModel = dayCareListModels.get(position);
        holder.text_name.setText(listModel.getName());
        holder.textRating.setText(listModel.getRating() + "");
        holder.textViewFee.setText(listModel.getFee() + "");
        if (listModel.getHomeImage() != null) {
            //imageLoader.displayImage(listModel.getHomeImage(), holder.image_icon, options);
            Glide.with(context).load(listModel.getHomeImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image_icon);
        } else {
            holder.image_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dummy1));
        }

        holder.cardID1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemSelected(R.id.cardID1, listModel);
            }
        });
        GPSTracker mGPS = new GPSTracker(context);

        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            System.out.print("lat :" + mGPS.getLatitude() + "jshvjfd" + "," + "long:" + mGPS.getLongitude());
            LatLng latLng = new LatLng();
            latLng.setLatitude(mGPS.getLatitude());
            latLng.setLongitude(mGPS.getLongitude());
            LatLng latLng1 = new LatLng();
            latLng1.setLongitude(listModel.getLongitude());
            latLng1.setLatitude(listModel.getLatitude());
            System.out.println("Distance is :" + getDistance(latLng, latLng1));
            if (String.valueOf(CalculationByDistance(latLng, latLng1)) != null)
                holder.textViewdistance.setText(String.format("%.2f", CalculationByDistance(latLng, latLng1)) + " km");
            System.out.println("New Distance is pleas corect :" + CalculationByDistance(latLng, latLng1));

            System.out.print("Dis :::::Tance::" + distance(mGPS.getLatitude(), mGPS.getLongitude(), listModel.getLatitude(), listModel.getLatitude()));
        }

    }


    @Override
    public int getItemCount() {

        return dayCareListModels.size();
    }


    public class homeViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private LinearLayout cardID1;
        ImageView image_icon;
        TextView text_name, text_description, textRating, textViewFee, textViewdistance;


        public homeViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            image_icon = itemView.findViewById(R.id.image_icon);
            text_description = itemView.findViewById(R.id.text_description);
            text_name = itemView.findViewById(R.id.text_name);
            textRating = itemView.findViewById(R.id.textRating);
            textViewFee = itemView.findViewById(R.id.textFee);
            textViewdistance = itemView.findViewById(R.id.textDistance);
            cardID1=itemView.findViewById(R.id.cardID1);
        }
    }

    /*  private String getDistanceOnRoad(double latitude, double longitude,
                                       double prelatitute, double prelongitude) {
          String result_in_kms = "";
          String url = "http://maps.google.com/maps/api/directions/xml?origin="
                  + latitude + "," + longitude + "&destination=" + prelatitute
                  + "," + prelongitude + "&sensor=false&units=metric";
          String tag[] = { "text" };
          HttpResponse response = null;
          try {
              HttpClient httpClient = new DefaultHttpClient();
              HttpContext localContext = new BasicHttpContext();
              HttpPost httpPost = new HttpPost(url);
              response = httpClient.execute(httpPost, localContext);
              InputStream is = response.getEntity().getContent();
              DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                      .newDocumentBuilder();
              Document doc = builder.parse(is);
              if (doc != null) {
                  NodeList nl;
                  ArrayList args = new ArrayList();
                  for (String s : tag) {
                      nl = doc.getElementsByTagName(s);
                      if (nl.getLength() > 0) {
                          Node node = nl.item(nl.getLength() - 1);
                          args.add(node.getTextContent());
                      } else {
                          args.add(" - ");
                      }
                  }
                  result_in_kms = String.format("%s", args.get(0));
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          return result_in_kms;
      }*/
    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.getLatitude());
        locationA.setLongitude(LatLng1.getLongitude());
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.getLatitude());
        locationB.setLongitude(LatLng2.getLongitude());
        distance = locationA.distanceTo(locationB);

        return distance;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.getLatitude();
        double lat2 = EndP.getLatitude();
        double lon1 = StartP.getLongitude();
        double lon2 = EndP.getLongitude();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.d("Radius Value", "Vikram jha" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
