// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.fragment.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseMenuFragment_ViewBinding implements Unbinder {
  private BaseMenuFragment target;

  @UiThread
  public BaseMenuFragment_ViewBinding(BaseMenuFragment target, View source) {
    this.target = target;

    target.huanyingLayout = Utils.findRequiredViewAsType(source, R.id.huanying_layout, "field 'huanyingLayout'", LinearLayout.class);
    target.frameLayout1 = Utils.findRequiredViewAsType(source, R.id.frameLayout1, "field 'frameLayout1'", FrameLayout.class);
    target.ebtSecondLevelMenuListView = Utils.findRequiredViewAsType(source, R.id.ebt_second_level_menu_listView, "field 'ebtSecondLevelMenuListView'", ExpandableListView.class);
    target.mMenuLayout = Utils.findRequiredViewAsType(source, R.id.ebt_second_level_fl, "field 'mMenuLayout'", RelativeLayout.class);
    target.layoutContent = Utils.findRequiredViewAsType(source, R.id.layout_content, "field 'layoutContent'", FrameLayout.class);
    target.mContentLayoutBg = Utils.findRequiredViewAsType(source, R.id.layout_content_bg, "field 'mContentLayoutBg'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseMenuFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.huanyingLayout = null;
    target.frameLayout1 = null;
    target.ebtSecondLevelMenuListView = null;
    target.mMenuLayout = null;
    target.layoutContent = null;
    target.mContentLayoutBg = null;
  }
}
