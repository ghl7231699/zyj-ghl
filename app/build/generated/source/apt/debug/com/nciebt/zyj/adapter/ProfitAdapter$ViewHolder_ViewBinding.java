// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfitAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ProfitAdapter.ViewHolder target;

  @UiThread
  public ProfitAdapter$ViewHolder_ViewBinding(ProfitAdapter.ViewHolder target, View source) {
    this.target = target;

    target.mIv = Utils.findRequiredViewAsType(source, R.id.profit_book_fragment_item_img, "field 'mIv'", ImageView.class);
    target.mTv = Utils.findRequiredViewAsType(source, R.id.profit_book_fragment_item_rl_content, "field 'mTv'", TextView.class);
    target.mRl = Utils.findRequiredViewAsType(source, R.id.profit_book_fragment_item_rl, "field 'mRl'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfitAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIv = null;
    target.mTv = null;
    target.mRl = null;
  }
}
