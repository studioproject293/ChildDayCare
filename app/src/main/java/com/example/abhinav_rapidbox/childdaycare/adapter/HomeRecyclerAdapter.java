package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.location.Location;
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
import com.example.abhinav_rapidbox.childdaycare.utill.GPSTracker;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.abhinav_rapidbox.childdaycare.activity.MainActivity.imageLoader;
import static com.example.abhinav_rapidbox.childdaycare.activity.MainActivity.options;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.homeViewHolder> {

    Context context;
    private OnFragmentListItemSelectListener listener;
    private ArrayList<DayCareListModel> dayCareListModels;

    public HomeRecyclerAdapter(Context context, ArrayList<DayCareListModel> dayCareListModels) {
        this.context = context;
        this.dayCareListModels = dayCareListModels;
    }

    @Override
    public homeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_row_new, parent, false);
        HomeRecyclerAdapter.homeViewHolder cartViewHolder = new HomeRecyclerAdapter.homeViewHolder(view);
        return cartViewHolder;
    }

   /* public void updateHome(ArrayList<DayCareListModel> cartItems) {
        this.dayCareListModels = cartItems;
        notifyDataSetChanged();
    }
*/
    public void setListner(OnFragmentListItemSelectListener listner) {
        this.listener = listner;
    }

    @Override
    public void onBindViewHolder(final homeViewHolder holder, final int position) {
        final DayCareListModel listModel = dayCareListModels.get(position);
        holder.text_name.setText(listModel.getDaycareName());
        holder.textRating.setText(listModel.getAverageRating() + "");
        holder.textViewFee.setText("₹ "+listModel.getFeeStructures() + "");
        holder.text_description.setText(listModel.getDescription());
        if (listModel.getImage_url() != null) {
            imageLoader.displayImage(listModel.getImage_url(), holder.image_icon, options);
           /* Glide.with(context).load(listModel.getHomeImage())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image_icon);*/
        } else {
            holder.image_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.dummy1));
        }

        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemSelected(R.id.list_item, listModel);
            }
        });
        holder.textViewdistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemSelected(R.id.textDistance, listModel);
            }
        });
        holder.image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemSelected(R.id.list_item, listModel);
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

        // Getting URL to the Google Directions API


    }


    @Override
    public int getItemCount() {
        return dayCareListModels.size();
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

    public class homeViewHolder extends RecyclerView.ViewHolder {
        ImageView image_icon;
        TextView text_name, text_description, textRating, textViewFee, textViewdistance;
        private View view;
        private LinearLayout cardID1, list_item;


        homeViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            image_icon = itemView.findViewById(R.id.image_icon);
            text_description = itemView.findViewById(R.id.text_description);
            text_name = itemView.findViewById(R.id.text_name);
            textRating = itemView.findViewById(R.id.textRating);
            textViewFee = itemView.findViewById(R.id.textFee);
            textViewdistance = itemView.findViewById(R.id.textDistance);
            cardID1 = itemView.findViewById(R.id.cardID1);
            list_item = itemView.findViewById(R.id.list_item);
        }
    }
  /*  private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.getLatitude()+","+origin.getLongitude();

        // Destination of route
        String str_dest = "destination="+dest.getLatitude()+","+dest.getLongitude();

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }


    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url","bjhjadhf"+ e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        private final homeViewHolder viewHolder;

        public DownloadTask(homeViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask(viewHolder);

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        private final homeViewHolder viewHolder;

        public ParserTask(homeViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if(result.size()<1){
                Toast.makeText(context, "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
               // lineOptions.addAll();
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            Toast.makeText(context, "Distance:"+distance + ", Duration:"+duration, Toast.LENGTH_SHORT).show();
            Log.d("bhfsdfs","fdjshfhd"+"Distance:"+distance + ", Duration:"+duration);

            // Drawing polyline in the Google Map for the i-th route
           // map.addPolyline(lineOptions);
        }
    }*/

}
