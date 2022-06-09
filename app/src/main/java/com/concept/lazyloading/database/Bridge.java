package com.concept.lazyloading.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.concept.lazyloading.R;

import java.util.List;

public class Bridge extends RecyclerView.Adapter<Bridge.ViewHolder> {

    Context context;
    List<Data> data;

    public Bridge(Context context, List<Data> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_data_ui, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.username.setText("Username : " + data.get(position).getUsername());
        holder.age.setText("Age : " + data.get(position).getAge());
        holder.genre.setText("Favourite Genre : " + data.get(position).getGenre());
        holder.movieName.setText("Movie : " + data.get(position).getMovie_name());
        holder.animeLiked.setText("Like Anime : " + data.get(position).getLike_anime().toUpperCase());
        holder.id.setText("User ID : " + data.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, age, genre, movieName, animeLiked, id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameUi);
            age = itemView.findViewById(R.id.ageUi);
            genre = itemView.findViewById(R.id.genreUI);
            movieName = itemView.findViewById(R.id.movie_nameUi);
            animeLiked = itemView.findViewById(R.id.animeLikedUI);
            id = itemView.findViewById(R.id.userIdUi);

        }
    }
}
