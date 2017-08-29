package com.nciebt.zyj.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.ProfitBookActivity;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.utils.ResUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名称：ProfitAdapter
 * 类描述：分红宝典适配器
 * 创建人：ghl
 * 创建时间：2017/7/7 11:37
 * 修改人：ghl
 * 修改时间：2017/7/7 11:37
 *
 * @version v1.0
 */

public class ProfitAdapter extends RecyclerView.Adapter<ProfitAdapter.ViewHolder> {

    private Context mContext;
    private String[] menu = {"分红宝典"};
    private List<FileDetails> mList;

    public ProfitAdapter(Context context, List<FileDetails> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.profit_book_menu_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileDetails fileDetails = mList.get(position);
        if (fileDetails != null) {
            String name = fileDetails.getTitle();
            String iconId = fileDetails.getIconId();
            int drawable = ResUtil.stringToDrawable(iconId);
            holder.mTv.setText(name);
            Glide.with(mContext).load(drawable).into(holder.mIv);
            int iv = ResUtil.stringToDrawable(iconId + "_content");
            holder.mTv.setBackgroundResource(iv);
            itemOnclick(holder);
        }
    }

    private void itemOnclick(ViewHolder holder) {
        holder.mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfitBookActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return menu.length;
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profit_book_fragment_item_img)
        ImageView mIv;
        @BindView(R.id.profit_book_fragment_item_rl_content)
        TextView mTv;
        @BindView(R.id.profit_book_fragment_item_rl)
        RelativeLayout mRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
