// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddProductActivity_ViewBinding implements Unbinder {
  private AddProductActivity target;

  private View view2131493023;

  private View view2131493021;

  @UiThread
  public AddProductActivity_ViewBinding(AddProductActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddProductActivity_ViewBinding(final AddProductActivity target, View source) {
    this.target = target;

    View view;
    target.mEditText = Utils.findRequiredViewAsType(source, R.id.add_product_fragment_edit_search, "field 'mEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.add_product_fragment_btn_search, "field 'mButton' and method 'onViewClicked'");
    target.mButton = Utils.castView(view, R.id.add_product_fragment_btn_search, "field 'mButton'", Button.class);
    view2131493023 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.add_product_fragment_recycler, "field 'mRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.profit_book_back, "method 'onViewClicked'");
    view2131493021 = view;
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
    AddProductActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEditText = null;
    target.mButton = null;
    target.mRecyclerView = null;

    view2131493023.setOnClickListener(null);
    view2131493023 = null;
    view2131493021.setOnClickListener(null);
    view2131493021 = null;
  }
}
