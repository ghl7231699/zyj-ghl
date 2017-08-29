// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddProductAdapter$AddViewHolder_ViewBinding implements Unbinder {
  private AddProductAdapter.AddViewHolder target;

  @UiThread
  public AddProductAdapter$AddViewHolder_ViewBinding(AddProductAdapter.AddViewHolder target,
      View source) {
    this.target = target;

    target.mAdd = Utils.findRequiredViewAsType(source, R.id.add_product_add, "field 'mAdd'", Button.class);
    target.mCheck = Utils.findRequiredViewAsType(source, R.id.add_product_check, "field 'mCheck'", Button.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.add_product_name, "field 'mName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddProductAdapter.AddViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAdd = null;
    target.mCheck = null;
    target.mName = null;
  }
}
