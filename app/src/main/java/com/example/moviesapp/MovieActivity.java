package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity {

    ImageView imageThumb;
    TextView textTitle;
    TextView textOverview;
    DonutProgress donutProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle bundle = getIntent().getExtras();
        imageThumb=findViewById(R.id.movieThumb);
        textTitle=findViewById(R.id.textTitle);
        donutProgress = findViewById(R.id.donut);
        textOverview=findViewById(R.id.textDescription);

        if(bundle!=null){

            Picasso.get().load(bundle.getString("imageUrl")).into(imageThumb);
            textTitle.setText(bundle.getString("title"));
            textOverview.setText(bundle.getString("description"));


            donutProgress.setMax(100);
            donutProgress.setProgress(bundle.getFloat("progress"));

        }




    }
}
