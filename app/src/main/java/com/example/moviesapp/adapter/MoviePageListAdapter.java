package com.example.moviesapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.OnItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.databinding.MovieItemsBinding;
import com.example.moviesapp.model.Result;
import com.squareup.picasso.Picasso;

import static com.example.moviesapp.constant.Constant.IMAGE_SIZE;
import static com.example.moviesapp.constant.Constant.IMAGE_URL;

public class MoviePageListAdapter extends PagedListAdapter<Result,MoviePageListAdapter.MViewHolder> {

    private OnItemClick itemClick;
    public MoviePageListAdapter(OnItemClick onItemClick) {
        super(diffCallback);
        this.itemClick=onItemClick;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemsBinding movieItemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.movie_items,parent,false);

        return new MViewHolder(movieItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {

        holder.bind(getItem(position));

    }
    public static DiffUtil.ItemCallback<Result> diffCallback=
            new DiffUtil.ItemCallback<Result>() {
                @Override
                public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MovieItemsBinding  itemsBinding;

        public MViewHolder(MovieItemsBinding movieItemsBinding) {
            super(movieItemsBinding.getRoot());

            itemsBinding=movieItemsBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(Result item) {
            if(item!=null){
                String thumbUrl =IMAGE_URL + IMAGE_SIZE+item.getBackdropPath();
                Picasso.get().load(thumbUrl).into(itemsBinding.thumb);
                itemsBinding.textView.setText(item.getTitle());
            }

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Result result = getItem(position);
            itemClick.onMovieItemClick(result);
        }
    }
}
