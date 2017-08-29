// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CommonAdapter$CommonViewHolder_ViewBinding implements Unbinder {
  private CommonAdapter.CommonViewHolder target;

  @UiThread
  public CommonAdapter$CommonViewHolder_ViewBinding(CommonAdapter.CommonViewHolder target,
      View source) {
    this.target = target;

    target.mContent = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_rl_content, "field 'mContent'", TextView.class);
    target.mRl = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_rl, "field 'mRl'", RelativeLayout.class);
    target.mImageView = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_img, "field 'mImageView'", ImageView.class);
    target.mLinearLayout = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_ll, "field 'mLinearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CommonAdapter.CommonViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContent = null;
    target.mRl = null;
    target.mImageView = null;
    target.mLinearLayout = null;
  }
}
