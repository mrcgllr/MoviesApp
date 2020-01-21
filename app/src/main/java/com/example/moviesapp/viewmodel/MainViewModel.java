package com.example.moviesapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviesapp.data.MovieDataSourceFactory;
import com.example.moviesapp.model.Result;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.moviesapp.constant.Constant.NUMBER_OF_THREAD_FIVE;
import static com.example.moviesapp.constant.Constant.PAGE_LOAD_SIZE_HINT;
import static com.example.moviesapp.constant.Constant.PAGE_SIZE;
import static com.example.moviesapp.constant.Constant.Prefetch_Distance;

public class MainViewModel extends ViewModel {

    private String sort_criteia;
    private LiveData<PagedList<Result>> listLiveData;

    public MainViewModel(String sort_criteia) {
        this.sort_criteia = sort_criteia;

        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD_FIVE);
        MovieDataSourceFactory sourceFactory=new MovieDataSourceFactory(sort_criteia);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PAGE_LOAD_SIZE_HINT).setPageSize(PAGE_SIZE)
                .setPrefetchDistance(Prefetch_Distance).build();

        listLiveData=new LivePagedListBuilder<>(sourceFactory,config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Result>> getListLiveData(){
        return listLiveData;

    }
}
