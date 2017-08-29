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

public class ProductAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ProductAdapter.ViewHolder target;

  @UiThread
  public ProductAdapter$ViewHolder_ViewBinding(ProductAdapter.ViewHolder target, View source) {
    this.target = target;

    target.mProductContent = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_rl_content, "field 'mProductContent'", TextView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_rl, "field 'mLayout'", RelativeLayout.class);
    target.mImageView = Utils.findRequiredViewAsType(source, R.id.product_fragment_item_img, "field 'mImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mProductContent = null;
    target.mLayout = null;
    target.mImageView = null;
  }
}
