package com.dinidininta.spotifyassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinidininta.spotifyassistant.POJO.Track;
import com.dinidininta.spotifyassistant.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TracklistAdapter extends RecyclerView.Adapter<TracklistAdapter.ListViewHolder>{
    private ArrayList<Track> tracklist;
    private OnItemClickCallback onItemClickCallback;

    public TracklistAdapter(ArrayList<Track> list){
        this.tracklist = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_track, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Track track = tracklist.get(position);
        holder.trackTitle.setText(track.name);
        holder.itemView.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(track);
        });
    }

    @Override
    public int getItemCount() {
        return tracklist.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView trackImage;
        TextView trackTitle;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            trackImage = itemView.findViewById(R.id.track_image);
            trackTitle = itemView.findViewById(R.id.track_title);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Track track);
    }
}
