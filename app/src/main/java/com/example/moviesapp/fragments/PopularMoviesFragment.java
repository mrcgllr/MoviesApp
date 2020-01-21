package com.example.moviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.MovieActivity;
import com.example.moviesapp.OnItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.MoviePageListAdapter;
import com.example.moviesapp.model.Result;
import com.example.moviesapp.viewmodel.MainViewModel;
import com.example.moviesapp.viewmodel.MainViewModelFactory;

import static com.example.moviesapp.constant.Constant.IMAGE_SIZE;
import static com.example.moviesapp.constant.Constant.IMAGE_URL;

public class PopularMoviesFragment extends Fragment implements OnItemClick {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MoviePageListAdapter adapter;
    private MainViewModel viewModel;
    private String sort_criteria="popular";


    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies,container,false);


        viewModel = ViewModelProviders.of(this,new MainViewModelFactory(sort_criteria)).get(MainViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerPopular);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MoviePageListAdapter(this);

        recyclerView.setAdapter(adapter);

        viewModel.getListLiveData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                if(results!=null){
                    adapter.submitList(results);
                }
            }
        });

        return view;
    }

    @Override
    public void onMovieItemClick(Result movie) {

        Intent intent = new Intent(getContext(), MovieActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("description",movie.getOverview());
        intent.putExtra("imageUrl",IMAGE_URL+IMAGE_SIZE+movie.getBackdropPath());
        intent.putExtra("progress",movie.getVoteAverage()*10);

        Toast.makeText(getContext(),"Movie id : "+movie.getId()+"\n"+movie.getTitle(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
