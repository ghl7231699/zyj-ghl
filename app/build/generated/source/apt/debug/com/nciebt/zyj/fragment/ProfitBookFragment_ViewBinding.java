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

public class ProfitBookFragment_ViewBinding implements Unbinder {
  private ProfitBookFragment target;

  @UiThread
  public ProfitBookFragment_ViewBinding(ProfitBookFragment target, View source) {
    this.target = target;

    target.mTextView = Utils.findRequiredViewAsType(source, R.id.common_fragment_content, "field 'mTextView'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.common_fragment_recycler, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfitBookFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTextView = null;
    target.mRecyclerView = null;
  }
}
