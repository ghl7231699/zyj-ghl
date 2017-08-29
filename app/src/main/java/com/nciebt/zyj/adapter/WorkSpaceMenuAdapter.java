package com.nciebt.zyj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.utils.ResUtil;

import java.util.List;

/**
 * 类名称：WorkSpaceMenuAdapter
 * 类描述：(一级菜单adapter)
 * 创建人：xs
 * 创建时间：2017/4/14 10:32
 * 修改人：xs
 * 修改时间：2017/4/14 10:32
 *
 * @version v1.0
 */
public class WorkSpaceMenuAdapter extends BaseAdapter {

    private Context mContext;

    private List<MenuTable> mList;

    private LayoutInflater mInflater;

    private int mPosition;

    private ViewChild mViewChild;

    public WorkSpaceMenuAdapter(Context context, List<MenuTable> list) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mList = list;
    }

    public void setPosition(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewChild = new ViewChild();
            convertView = mInflater.inflate(R.layout.ebt_layout_work_space_menu_item,
                    null);
            mViewChild.textView = (TextView) convertView
                    .findViewById(R.id.ebt_work_space_tv);
            mViewChild.imageView = (ImageView) convertView
                    .findViewById(R.id.ebt_work_space_iv);
            mViewChild.layout = convertView
                    .findViewById(R.id.ebt_work_space_ll);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (ViewChild) convertView.getTag();
        }

        if (null == mList || mList.size() <= position) {
            return convertView;
        }
        MenuTable menu = mList.get(position);
        mViewChild.textView.setText(menu.getAuthName());
        int n = ResUtil.stringToDrawable(menu.getIconId());
        mViewChild.imageView.setBackgroundResource(n);
        if (this.mPosition == position) {
            mViewChild.layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ebt_drawable_work_space_menu_item_bg));
        } else {
            mViewChild.layout.setBackgroundColor(mContext.getResources().getColor(R.color.alpha));
        }
        return convertView;
    }


    static class ViewChild {
        ImageView imageView;
        TextView textView;
        View layout;
    }
}
