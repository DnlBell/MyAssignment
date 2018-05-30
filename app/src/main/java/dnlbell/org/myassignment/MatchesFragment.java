package dnlbell.org.myassignment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.provider.Settings;
import android.content.pm.PackageManager;


import dnlbell.org.myassignment.ViewModel.MatchesViewModel;
import dnlbell.org.myassignment.Model.Match;

public class MatchesFragment extends Fragment {

    private static MatchesViewModel viewModel = new MatchesViewModel();
    LocationManager locationManager;
    Location myLocation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationlistenNetwork);
        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Bundle bundle = new Bundle();
        viewModel.getMatches(
                (ArrayList<Match> matches) -> {
                    ArrayList<Match> inMatches = new ArrayList<>();
                    for(int i = 0; i < matches.size(); ++i){
                        double matchLat = Double.parseDouble(matches.get(i).lat);
                        double matchLon = Double.parseDouble(matches.get(i).longitude);
                        double netLat = myLocation.getLatitude();
                        double netLong = myLocation.getLongitude();

                        double distance = distFrom(matchLat,matchLon,netLat,netLong);
                        if(distance <= 10 ){
                            inMatches.add(matches.get(i));
                        }
                    }

                    bundle.putParcelableArrayList("matches", inMatches);
                    ContentAdapter adapter = new ContentAdapter(bundle);
                    recyclerView.setAdapter(adapter);

                }

        );

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ImageButton likeButton;
        public Match match;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
            likeButton = itemView.findViewById(R.id.like_button);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    CharSequence text = "You Liked " + new StringBuilder().append(name.getText()).append("!").toString();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    likeButton.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                    match.liked = true;
                    viewModel.updateMatch(match);
                }

            });

        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 6;
        private ArrayList<Match> myMatches;

        public ContentAdapter(Bundle matches) {

            myMatches = matches.getParcelableArrayList("matches");
            //a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //setup picture
            holder.match = myMatches.get(position);
            String url = holder.match.imageUrl;
            Picasso.get().load(url).into(holder.picture);
            holder.name.setText(holder.match.name);
            boolean liked = holder.match.liked;
            if (liked) {
                holder.likeButton.setColorFilter(R.color.red);
            }else{
                holder.likeButton.setColorFilter(R.color.dark_grey);
            }
        }


        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationlistenNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }

}

