package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.model.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    private Button button;

    Context context;

    public void MovieDetails(Context context) {
        this.context = context;
    }


    private ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);

        button = (Button) findViewById(R.id.playbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class));
            }
        });

        String imageUrl;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = movie.getBackDropPath();
            Glide.with(this).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).into(binding.ivPoster);
        }
        else{
            imageUrl = movie.getPosterPath();
            Glide.with(this).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).into(binding.ivPoster);
        }

        Glide.with(this)
                .load(R.drawable.youtubeplaybutton)
                .into(binding.youtubeplaybutton);
    }
}