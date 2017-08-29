package com.nciebt.zyj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.entity.ProfitBookEntity;

import java.util.List;
import java.util.Map;

/**
 * 类名称：ProfitBookAdapter
 * 类描述：分红宝典详情适配器
 * 创建人：ghl
 * 创建时间：2017/7/5 10:43
 * 修改人：ghl
 * 修改时间：2017/7/5 10:43
 *
 * @version v1.0
 */

public class ProfitBookAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private Map<String, List<ProfitBookEntity>> map;
    private List<String> mList;
    private int defItem;//子菜单位置
    private int parentItem;//父级菜单位置
    // 是否有子菜单
    public boolean isChild = true;

    public ProfitBookAdapter(Context context, Map<String, List<ProfitBookEntity>> map, List<String> list) {
        mContext = context;
        this.map = map;
        mList = list;
    }

    public void setPaDefSelect(int position, int item) {
        defItem = item;
        parentItem = position;
        notifyDataSetChanged();
    }

    //获取父项的数目
    @Override
    public int getGroupCount() {
        return map.size();
    }

    //获取子项的数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(mList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return map.get(mList.get(groupPosition));
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return map.get(mList.get(groupPosition)).get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.profit_book_parent_item, null);
            holder = new GroupHolder();
            holder.mTv = (TextView) convertView.findViewById(R.id.profit_book_parent_item_tv);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.profit_book_parent_item_iv);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
            convertView.setBackgroundResource(R.drawable.ebt_drawable_menu_choice_item_icon);
        } else {
            convertView.setBackgroundResource(R.drawable.ebt_drawable_menu_choice_item_center);
        }
        if (isChild) {
            if (isExpanded) {
                holder.mImageView.setImageResource(R.drawable.ebt_drawable_item_top_icon);
            } else {
                holder.mImageView.setImageResource(R.drawable.ebt_drawable_item_bottom_icon);
            }
        }
        holder.mTv.setText(mList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.profit_book_child_item, null);
            holder = new ChildHolder();
            holder.mContent = (TextView) convertView.findViewById(R.id.profit_book_child_item_tv);
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.profit_book_child_item_ll);
            convertView.setTag(holder);
        } else
            holder = (ChildHolder) convertView.getTag();
        ProfitBookEntity entity = map.get(mList.get(groupPosition)).get(childPosition);
        if (parentItem == groupPosition && defItem == childPosition) {
            holder.mLayout.setBackgroundResource(R.drawable.ebt_menu_child_bg_selected);
            holder.mContent.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.mLayout.setBackgroundResource(R.drawable.ebt_menu_child_bg_normal);
            holder.mContent.setTextColor(mContext.getResources().getColor(R.color.ebt_ebt_second_level_color));
        }
        if (entity != null) {
            holder.mContent.setText(entity.getMenu());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView mTv;
        ImageView mImageView;
    }

    class ChildHolder {
        TextView mContent;
        LinearLayout mLayout;
    }
}
