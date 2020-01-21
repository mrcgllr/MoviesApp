package com.example.moviesapp.fragments;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviesapp.OnItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.GridSpacingItemDecoration;
import com.example.moviesapp.adapter.MoviePageListAdapter;
import com.example.moviesapp.model.Result;
import com.example.moviesapp.viewmodel.MainViewModel;
import com.example.moviesapp.viewmodel.MainViewModelFactory;

public class UpcomingMoviesFragment extends Fragment implements OnItemClick {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private MoviePageListAdapter adapter;
    private MainViewModel viewModel;
    private String sort_criteria="upcoming";

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);

        viewModel = ViewModelProviders.of(this,new MainViewModelFactory(sort_criteria)).get(MainViewModel.class);

        recyclerView = view.findViewById(R.id.upcomingRecycler);
        if(getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){
            layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        } else {
            gridLayoutManager = new GridLayoutManager(getActivity(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,9,false));
        }



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
        Toast.makeText(getContext(),"Movie id : "+movie.getId(),Toast.LENGTH_SHORT).show();

    }
}
