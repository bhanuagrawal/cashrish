package com.ultimatix.cashrich.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ultimatix.cashrich.R;
import com.ultimatix.cashrich.data.entity.Article;
import com.ultimatix.cashrich.datamodels.sip.SIPData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdater<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int ARTICLES = 0;
    public static final int SIP = 1;

    private final int itemVIewType;
    private ArrayList<T> mData;
    private ItemAdaterListner itemAdaterListner;
    private Context context;

    public ItemAdater(Context context, ItemAdaterListner itemAdaterListner, int viewType) {
        this.mData = new ArrayList<>();
        this.itemAdaterListner = itemAdaterListner;
        this.context = context;
        this.itemVIewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (itemVIewType){
            case ARTICLES:
                ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
                return itemViewHolder;
            case SIP:
                SIPViewHolder sipViewHolder = new SIPViewHolder(LayoutInflater.from(context).inflate(R.layout.sip_item, parent, false));
                return sipViewHolder;
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case ARTICLES:
                ((ItemViewHolder)holder).bindTo((Article) mData.get(position));
                break;
            case SIP:
                ((SIPViewHolder)holder).bindTo((SIPData) mData.get(position));
                break;
        }



    }


    @Override
    public int getItemViewType(int position) {
        return itemVIewType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onDataChange(ArrayList<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title)
        public TextView titleTV;


        @BindView(R.id.image)
        public ImageView imageView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemAdaterListner.onArticleSelected((Article)mData.get(getAdapterPosition()));
        }

        public void bindTo(final Article article) {
            titleTV.setText(article.getTitle());
            Glide.with(context)
                    .load(article.getUrlToImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

    }


    class SIPViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.date)
        public TextView dateTV;

        @BindView(R.id.layout)
        public LinearLayout layout;


        public SIPViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //onItemSelected();
            itemAdaterListner.onDaeSelected((SIPData)mData.get(getAdapterPosition()));
        }

        private void onItemSelected() {
            dateTV.setBackgroundResource(R.color.colorPrimary);
            dateTV.setTextColor(Color.WHITE);
        }

        public void bindTo(final SIPData sipData) {
            dateTV.setText(sipData.getDate());
        }

    }



    public interface ItemAdaterListner{

        void onArticleSelected(Article article);
        void onDaeSelected(SIPData sipData);
    }
}
