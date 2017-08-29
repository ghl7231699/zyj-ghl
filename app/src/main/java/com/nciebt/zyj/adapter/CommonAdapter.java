package com.nciebt.zyj.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.AddProductActivity;
import com.nciebt.zyj.activity.PdfActivity;
import com.nciebt.zyj.activity.PptActivity;
import com.nciebt.zyj.activity.VideoActivity;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.GlideImageLoaderStrategy;
import com.nciebt.zyj.utils.ResUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名称：CommonAdapter
 * 类描述：常用界面适配器
 * 创建人：ghl
 * 创建时间：2017/4/24 10:42
 * 修改人：ghl
 * 修改时间：2017/4/24 10:42
 *
 * @version v1.0
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {
    private List<FileDetails> mTList;
    private Context mContext;

    public CommonAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<FileDetails> TList) {
        mTList = TList;
        notifyDataSetChanged();
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final CommonViewHolder holder = new CommonViewHolder(LayoutInflater.from(mContext).inflate(R.layout.product_fragment_item_layout, parent, false));
        detailOnClick(holder);
        return holder;
    }

    private void detailOnLongClick(final CommonViewHolder holder) {
        holder.mRl.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final int position = holder.getAdapterPosition();
                        final FileDetails o = mTList.get(position);
                        try {
                            String type = o.getType();
                            if (type != null && !type.equals("ADD")) {
                                new AlertDialog.Builder(mContext).setTitle("警告")
                                        .setMessage("是否从常用列表删除？")
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mTList.remove(o);
                                                notifyItemRemoved(position);
                                                //更新数据库状态，并刷新数据
                                                DataRepository.updateCommonRecords(o, 0);
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }

        );
    }

    private void detailOnClick(final CommonViewHolder holder) {
        holder.mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final FileDetails details = mTList.get(position);
                try {
                    String type = details.getType();
                    if (type.equals("ADD")) {
                        toDetailActivity(AddProductActivity.class, null);
                    } else {
                        List<FileDetails> fileDetailses = DataRepository
                                .getClusterQuery("CONTENTID = ?", details.getContentId())
                                .find(FileDetails.class);
                        if (fileDetailses.size() > 0) {
                            switch (type) {
                                case "PDF":
                                    toDetailActivity(PdfActivity.class, details);
                                    break;
                                case "PPT":
                                    toDetailActivity(PptActivity.class, details);
                                    break;
                                case "FLASH":
                                case "VIDEO":
//                                    toDetailActivity(PdfActivity.class, details);
                                    toDetailActivity(VideoActivity.class, details);
                                    break;
                                default:
                                    break;
                            }
                        } else
                            DialogUtils.showOneBtnDialog("提醒", "已下架,请删除", "删除",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mTList.remove(details);
                                            notifyItemRemoved(position);
                                            //更新数据库状态，并刷新数据
                                            DataRepository.updateCommonRecords(details, 0);
                                        }
                                    });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void toDetailActivity(Class<? extends Activity> clazz, FileDetails s) {
        Intent intent = new Intent(mContext, clazz);
        //通过获取的数据，启动具体页面的内容
        Bundle bundle = new Bundle();
        if (s != null) {
            bundle.putSerializable("key", s);
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        FileDetails fileDetails = mTList.get(position);
        holder.mContent.setText(fileDetails.getTitle());
        String content = fileDetails.getIconId();
        String type = fileDetails.getType();
        if (type != null && !type.equals("ADD")) {
            loadAnimation(holder, content);
        } else {
            Glide.with(mContext).load(R.drawable.ebt_zyj_common_add).into(holder.mImageView);
        }
        detailOnLongClick(holder);
    }

    /**
     * 作者：ghl
     * 描述：加载图片的动画
     * 创建时间：2017/4/24 15:12
     *
     * @Params:
     * @Return:
     */
    private void loadAnimation(CommonViewHolder holder, String imageUrl) {
        GlideImageLoaderStrategy gls = new GlideImageLoaderStrategy();
        ViewPropertyAnimation.Animator animator = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                view.setScaleX(1);
                view.setRotationX(0);
                ObjectAnimator fadeAnim1 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim1.setDuration(2000);
                fadeAnim1.start();
            }
        };
//        ImageLoaderOptions options = new ImageLoaderOptions(R.drawable.ebt_zyj_product_description,
//                R.drawable.ebt_zyj_product_description, false, true, animator, 0);
//        ImageLoaderOptions options = new ImageLoaderOptions(R.drawable.ebt_zyj_common_add,
//                R.drawable.add_more, false, true, null, 0);
//        gls.showImage(holder.mImageView, R.drawable.ebt_zyj_product_terms, options);
        int drawableId = ResUtil.stringToDrawable(imageUrl);
        Glide.with(mContext).load(drawableId).into(holder.mImageView);
        int contentId = ResUtil.stringToDrawable(imageUrl + "_content");
        holder.mContent.setBackgroundResource(contentId);
    }


    @Override
    public int getItemCount() {
        return mTList.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_fragment_item_rl_content)
        TextView mContent;
        @BindView(R.id.product_fragment_item_rl)
        RelativeLayout mRl;
        @BindView(R.id.product_fragment_item_img)
        ImageView mImageView;
        @BindView(R.id.product_fragment_item_ll)
        LinearLayout mLinearLayout;

        public CommonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
