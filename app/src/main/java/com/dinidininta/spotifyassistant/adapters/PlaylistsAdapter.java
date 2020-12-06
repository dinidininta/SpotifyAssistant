package com.dinidininta.spotifyassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinidininta.spotifyassistant.POJO.Playlist;
import com.dinidininta.spotifyassistant.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ListViewHolder> {
    private ArrayList<Playlist> playlists;
    private OnItemClickCallback onItemClickCallback;

    public PlaylistsAdapter(ArrayList<Playlist> list){
        this.playlists = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_playlist, parent, false);
        return new ListViewHolder(view);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.playlistTitle.setText(playlist.name);
        holder.itemView.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(playlists.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView playlistImage;
        TextView playlistTitle;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistTitle = itemView.findViewById(R.id.playlist_title);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Playlist playlist);
    }
}
