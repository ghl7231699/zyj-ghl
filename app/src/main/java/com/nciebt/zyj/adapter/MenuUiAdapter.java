package com.nciebt.zyj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nciebt.zyj.R;
import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.utils.ResUtil;

import java.util.List;

/**
 * 类名称：MenuUiAdapter
 * 类描述：TODO(二级菜单适配器)
 * 创建人：x_s
 * 创建时间：2017年4月7日 上午10:00:09
 * 修改人：x_s
 * 修改时间：2017年4月7日 上午10:00:09
 * 修改备注：
 * @version v1.0
 */
public class MenuUiAdapter extends BaseExpandableListAdapter
{

	private LayoutInflater			mInflater;
	private Context					mContext;

	private List<MenuTable>	mList;

	private int						mChildPosition	= -1;
	private int						mGroupPosition	= -1;
	// 是否有子菜单
	public boolean					isChild			= true;

	/**
	 * 构造方法<br>
	 * MenuTag = 二级菜单标识
	 * */
	public MenuUiAdapter( Context context, String menuId )
	{
		this.mContext = context;
		mInflater = LayoutInflater.from( context );
		this.mList = DataCenter.getInstance().getmMenuNodeManager().getMenuNodeById( menuId ).getmChildList();

	}

	/**
	 * 获取子菜单的当前数量
	 * */
	@Override
	public int getChildrenCount( int groupPosition )
	{
		if ( null == mList || mList.size() == 0 )
			return 0;
		return mList.get( groupPosition ).getmChildList().size();
	}

	/**
	 * 返回子菜单获取的数据
	 * */
	@Override
	public Object getChild( int groupPosition, int childPosition )
	{
		return mList.get( groupPosition ).getmChildList().get( childPosition ).getAuthName();
	}

	/**
	 * 获取子菜单的当前条目
	 * */
	@Override
	public long getChildId( int groupPosition, int childPosition )
	{
		return childPosition;
	}

	/**
	 * 加载子菜单View,并设置他的属性
	 * */
	@Override
	public View getChildView( final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
	{
		if ( convertView == null )
		{
			mViewChild = new ViewChild();
			convertView = mInflater.inflate( R.layout.ex_lv_menu_child_item, null );
			mViewChild.textView = ( TextView ) convertView.findViewById( R.id.ebt_child_name );
			mViewChild.imageView = ( ImageView ) convertView.findViewById( R.id.ebt_child_image );
			mViewChild.layout = convertView.findViewById( R.id.ebt_child_name_layout );
			convertView.setTag( mViewChild );
		}
		else
		{
			mViewChild = ( ViewChild ) convertView.getTag();
		}
		mViewChild.textView.setText( mList.get( groupPosition ).getmChildList().get( childPosition ).getAuthName() );
		// TODO
		if ( mGroupPosition == groupPosition && mChildPosition == childPosition )
		{
			mViewChild.layout.setBackgroundResource( R.drawable.ebt_menu_child_bg_selected );
			mViewChild.textView.setTextColor( mContext.getResources().getColor( R.color.white ) );
		}
		else
		{
			mViewChild.layout.setBackgroundResource( R.drawable.ebt_menu_child_bg_normal );
			mViewChild.textView.setTextColor( mContext.getResources().getColor( R.color.ebt_ebt_second_level_color ) );
		}

		int resid = ResUtil.stringToDrawable(mList.get( groupPosition ).getmChildList().get( childPosition ) .getIconId());
		if ( resid > 0 )
		{
			mViewChild.imageView.setImageResource( resid );
			mViewChild.imageView.setVisibility( View.VISIBLE );
		}
		else
		{
			mViewChild.imageView.setVisibility( View.INVISIBLE );
		}
		return convertView;
	}

	/**
	 * 获取母菜单的当前条目
	 * */
	@Override
	public long getGroupId( int groupPosition )
	{
		return groupPosition;
	}

	/**
	 * 返回母菜单获取的数据
	 * */
	@Override
	public Object getGroup( int groupPosition )
	{
		return mList.get( groupPosition );
	}

	/**
	 * 获取的母菜单的数量
	 * */
	@Override
	public int getGroupCount()
	{
		return mList.size();
	}

	/**
	 * 获取母菜单的view,并将数据绑定
	 * */
	@Override
	public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
	{
		if ( convertView == null )
		{
			mViewChild = new ViewChild();
			convertView = mInflater.inflate( R.layout.ex_lv_menu_group_item, null );
			mViewChild.iconView = ( ImageView ) convertView.findViewById( R.id.ebt_group_icon_iv );
			mViewChild.textView = ( TextView ) convertView.findViewById( R.id.ebt_group_name );
			mViewChild.imageView = ( ImageView ) convertView.findViewById( R.id.channel_imageview_orientation );
			convertView.setTag( mViewChild );
		}
		else
		{
			mViewChild = ( ViewChild ) convertView.getTag();
		}

		if ( groupPosition == 0 )
		{
			convertView.setBackgroundResource( R.drawable.ebt_drawable_menu_choice_item_icon );
		}
		else
		{
			convertView.setBackgroundResource( R.drawable.ebt_drawable_menu_choice_item_center );
		}
		if ( isChild )
		{
			if ( isExpanded )
			{

				mViewChild.imageView.setImageResource( R.drawable.ebt_drawable_item_top_icon );
			}
			else
			{
				mViewChild.imageView.setImageResource( R.drawable.ebt_drawable_item_bottom_icon );

			}
		}
		else
		{
			mViewChild.imageView.setImageResource( R.drawable.ebt_drawable_item_bottom_icon );
		}

		int resid = ResUtil.stringToDrawable(mList.get( groupPosition ).getIconId());
		if ( resid > 0 )
		{
			mViewChild.iconView.setImageResource( resid );
		}
		else
		{
			mViewChild.iconView.setImageResource( 0x00000000 );
		}
		mViewChild.textView.setText( mList.get( groupPosition ).getAuthName() );
		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{

		return true;
	}

	/**
	 * 子菜单是可选的
	 * */
	@Override
	public boolean isChildSelectable( int groupPosition, int childPosition )
	{

		return true;
	}

	public void setRefresh( int groupPosition, int childPosition )
	{
		this.mChildPosition = childPosition;
		this.mGroupPosition = groupPosition;
		notifyDataSetChanged();
	}

	ViewChild	mViewChild;

	static class ViewChild
	{
		ImageView iconView;
		ImageView	imageView;
		TextView textView;
		View		layout;
	}

}
