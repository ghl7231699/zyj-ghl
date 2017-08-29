// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.itsrts.pptviewer.PPTViewer;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PptActivity_ViewBinding implements Unbinder {
  private PptActivity target;

  private View view2131493031;

  @UiThread
  public PptActivity_ViewBinding(PptActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PptActivity_ViewBinding(final PptActivity target, View source) {
    this.target = target;

    View view;
    target.mPPTViewer = Utils.findRequiredViewAsType(source, R.id.ppt_activity_content, "field 'mPPTViewer'", PPTViewer.class);
    view = Utils.findRequiredView(source, R.id.ppt_activity_content_fab, "method 'onViewClicked'");
    view2131493031 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PptActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPPTViewer = null;

    view2131493031.setOnClickListener(null);
    view2131493031 = null;
  }
}
