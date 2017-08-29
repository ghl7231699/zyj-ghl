// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WorkSpaceActivity_ViewBinding implements Unbinder {
  private WorkSpaceActivity target;

  private View view2131493002;

  private View view2131493001;

  @UiThread
  public WorkSpaceActivity_ViewBinding(WorkSpaceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WorkSpaceActivity_ViewBinding(final WorkSpaceActivity target, View source) {
    this.target = target;

    View view;
    target.mEbtWorkSpaceMenuLv = Utils.findRequiredViewAsType(source, R.id.ebt_work_space_menu_lv, "field 'mEbtWorkSpaceMenuLv'", ListView.class);
    view = Utils.findRequiredView(source, R.id.change_rbt, "method 'onViewClicked'");
    view2131493002 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ebt_workspace_close_app_btn, "method 'onViewClicked'");
    view2131493001 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WorkSpaceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEbtWorkSpaceMenuLv = null;

    view2131493002.setOnClickListener(null);
    view2131493002 = null;
    view2131493001.setOnClickListener(null);
    view2131493001 = null;
  }
}
