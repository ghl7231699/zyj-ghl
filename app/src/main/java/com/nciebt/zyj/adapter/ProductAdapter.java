package com.nciebt.zyj.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.AddProductActivity;
import com.nciebt.zyj.activity.PdfActivity;
import com.nciebt.zyj.activity.PptActivity;
import com.nciebt.zyj.activity.VideoActivity;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.utils.ResUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名称：ProductAdapter<T>
 * 类描述：产品界面adapter
 * 创建人：ghl
 * 创建时间：2017/4/12 15:29
 * 修改人：ghl
 * 修改时间：2017/4/12 15:29
 *
 * @version v1.0
 */

public class ProductAdapter<T> extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<T> mTList;
    private Context mContext;

    public ProductAdapter(List<T> TList, Context context) {
        mTList = TList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.product_fragment_item_layout, parent, false));
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入具体页面
                int position = holder.getAdapterPosition();
                FileDetails t = (FileDetails) mTList.get(position);
                String type = t.getType();
                try {
                    switch (type) {
                        case "PDF":
                            toDetailActivity(PdfActivity.class, t);
                            break;
                        case "PPT":
                            toDetailActivity(PptActivity.class, t);
                            break;
                        case "FLASH":
                        case "VIDEO":
                            toDetailActivity(VideoActivity.class, t);
                            break;
                        case "ADD":
                            toDetailActivity(AddProductActivity.class, null);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return holder;
    }

    /**
     * 通过获取的数据，启动具体页面的内容
     *
     * @param clazz
     * @param s
     */
    private void toDetailActivity(Class<? extends Activity> clazz, FileDetails s) {
        Intent intent = new Intent(mContext, clazz);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", s);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileDetails t = (FileDetails) mTList.get(position);
        holder.mProductContent.setText(t.getTitle());
        String content = t.getIconId();
        int drawableId = ResUtil.stringToDrawable(content);
        int contentId = ResUtil.stringToDrawable(content + "_content");
        holder.mProductContent.setBackgroundResource(contentId);
        Glide.with(mContext).load(drawableId).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mTList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_fragment_item_rl_content)
        TextView mProductContent;
        @BindView(R.id.product_fragment_item_rl)
        RelativeLayout mLayout;
        @BindView(R.id.product_fragment_item_img)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
