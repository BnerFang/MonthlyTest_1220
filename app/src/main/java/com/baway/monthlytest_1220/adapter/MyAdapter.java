package com.baway.monthlytest_1220.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.monthlytest_1220.R;
import com.baway.monthlytest_1220.bean.GoodsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:  方诗康
 * 描述:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<GoodsBean.DataBean> mDataBeans = new ArrayList<>();

    public MyAdapter(Context context, List<GoodsBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
    }

    //添加数据
    public void setData(List<GoodsBean.DataBean> dataBeans) {
        mDataBeans.addAll(dataBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_view, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String replace = mDataBeans.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(mContext).load(replace).into(myViewHolder.mImageView);
        myViewHolder.mTextViewTitle.setText(mDataBeans.get(i).getTitle());
        myViewHolder.mTextViewPrice.setText(mDataBeans.get(i).getPrice() + "");
        myViewHolder.mTextViewSalenum.setText(mDataBeans.get(i).getSalenum() + "");
        //条目点击事件
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoodsClicked != null) {
                    mGoodsClicked.onItemClicked(i);
                }
            }
        });
        //加入购物车点击事件
        myViewHolder.mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoodsClicked != null) {
                    mGoodsClicked.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans == null ? 0 : mDataBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextViewTitle, mTextViewPrice, mTextViewSalenum;
        Button mButtonAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.goods_img);
            mTextViewTitle = itemView.findViewById(R.id.goods_title);
            mTextViewPrice = itemView.findViewById(R.id.goods_price);
            mTextViewSalenum = itemView.findViewById(R.id.goods_salenum);
            mButtonAdd = itemView.findViewById(R.id.add_car);
        }
    }

    private goodsClicked mGoodsClicked;

    public void setGoodsClicked(goodsClicked goodsClicked) {
        mGoodsClicked = goodsClicked;
    }

    //自定义接口
    public interface goodsClicked {
        void onItemClicked(int position);

        void onClick(int position);
    }
}
