package com.nciebt.zyj.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.AddProductActivity;
import com.nciebt.zyj.activity.PdfActivity;
import com.nciebt.zyj.activity.PptActivity;
import com.nciebt.zyj.activity.VideoActivity;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.data.sql.CommonFileDetailed;
import com.nciebt.zyj.entity.FileDetails;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类名称：AddProductAdapter
 * 类描述：添加产品适配器
 * 创建人：ghl
 * 创建时间：2017/4/24 16:03
 * 修改人：ghl
 * 修改时间：2017/4/24 16:03
 *
 * @version v1.0
 */

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddViewHolder> {
    private List<FileDetails> mTList;
    private Context mContext;

    public AddProductAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<FileDetails> TList) {
        mTList = TList;
        notifyDataSetChanged();
    }

    private void toDetailActivity(Class<? extends Activity> clazz, FileDetails s) {
        Intent intent = new Intent(mContext, clazz);
        //通过获取的数据，启动具体页面的内容
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", s);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public AddViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final AddViewHolder viewHolder = new AddViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.add_product_fragment_item_layout, parent, false));
        addOnClick(viewHolder);
        checkOnClick(viewHolder);
        return viewHolder;
    }

    private void checkOnClick(final AddViewHolder viewHolder) {
        viewHolder.mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                String type = mTList.get(position).getType();
                try {
                    switch (type) {
                        case "PDF":
                            toDetailActivity(PdfActivity.class, mTList.get(position));
                            break;
                        case "PPT":
                            toDetailActivity(PptActivity.class, mTList.get(position));
                            break;
                        case "FLASH":
                        case "VIDEO":
                        case "type":
                            toDetailActivity(VideoActivity.class, mTList.get(position));
                            break;
                        case "ADD":
                            toDetailActivity(AddProductActivity.class, null);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addOnClick(final AddViewHolder viewHolder) {
        viewHolder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                FileDetails e = mTList.get(position);
                viewHolder.mAdd.setBackgroundResource(R.drawable.add_product_button_shape_unchoice);
                viewHolder.mAdd.setEnabled(false);
                try {
                    DataRepository.updateCommonRecords(e, 1);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(AddViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        FileDetails t = mTList.get(position);
        String contentId = t.getContentId();
        List<CommonFileDetailed> commonFileDetailed = DataSupport.where("CONTENTID = ?", contentId).find(CommonFileDetailed.class);
        if (commonFileDetailed.size() > 0) {
            holder.mAdd.setBackgroundResource(R.drawable.add_product_button_shape_unchoice);
            holder.mAdd.setEnabled(false);
        }
        holder.mName.setText(t.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTList.size();
    }


    public class AddViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_product_add)
        Button mAdd;
        @BindView(R.id.add_product_check)
        Button mCheck;
        @BindView(R.id.add_product_name)
        TextView mName;

        public AddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
