package com.wyp.chalkitup.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyp.chalkitup.ChatActivity;
import com.wyp.chalkitup.MainActivity;
import com.wyp.chalkitup.ProjectDetailActivity;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yatinkaushal on 2/18/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context context;
    private List<ProjectItem> projects = new ArrayList<>();
    private User mUser;

    public ProjectAdapter(final Context context, final List<ProjectItem> projects, final User mUser) {
        this.context = context;
        this.projects = projects;
        this.mUser = mUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("ProjectAdapter", position + " : " + projects.get(position).name);
        holder.name.setText(projects.get(position).name);
        Glide.with(context)
                .load(projects.get(position).photoUrl)
                .into(holder.circleImageView);
        holder.username.setText(projects.get(position).username);
        holder.reward.setText(projects.get(position).description);
        DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
        holder.timestamp.setText(oneDigit.format(distanceFromMe(projects.get(position).latitude, projects.get(position).longitude)) + " miles away");
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public double distanceFromMe(double lat, double lon) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location loc1 = new Location("");
            loc1.setLatitude(lat);
            loc1.setLongitude(lon);
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

            Location loc2 = new Location("");
            loc2.setLatitude(location.getLatitude());
            loc2.setLongitude(location.getLongitude());
            float distanceInMeters = loc1.distanceTo(loc2);
            return distanceInMeters / 1609.344;
        }
        return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView cardView;
        CircleImageView circleImageView;
        TextView username;
        TextView reward;
        TextView timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            username = (TextView) itemView.findViewById(R.id.textView5);
            reward = (TextView) itemView.findViewById(R.id.textView6);
            timestamp = (TextView) itemView.findViewById(R.id.textView7);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProjectDetailActivity.class);
                    intent.putExtra("mUser", mUser);
                    intent.putExtra("project", projects.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
