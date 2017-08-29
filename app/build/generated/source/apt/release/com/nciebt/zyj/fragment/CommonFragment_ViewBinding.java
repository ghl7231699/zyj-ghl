// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CommonFragment_ViewBinding implements Unbinder {
  private CommonFragment target;

  @UiThread
  public CommonFragment_ViewBinding(CommonFragment target, View source) {
    this.target = target;

    target.mRecycler = Utils.findRequiredViewAsType(source, R.id.common_fragment_recycler, "field 'mRecycler'", RecyclerView.class);
    target.mTextView = Utils.findRequiredViewAsType(source, R.id.common_fragment_content, "field 'mTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CommonFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecycler = null;
    target.mTextView = null;
  }
}
