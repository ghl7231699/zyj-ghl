// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductFragment_ViewBinding implements Unbinder {
  private ProductFragment target;

  private View view2131493033;

  @UiThread
  public ProductFragment_ViewBinding(final ProductFragment target, View source) {
    this.target = target;

    View view;
    target.mProductFragmentRecycleView = Utils.findRequiredViewAsType(source, R.id.product_fragment_recycle_view, "field 'mProductFragmentRecycleView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.product_fragment_radio_button, "field 'mRadioButton' and method 'onViewClicked'");
    target.mRadioButton = Utils.castView(view, R.id.product_fragment_radio_button, "field 'mRadioButton'", RadioButton.class);
    view2131493033 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mTextView = Utils.findRequiredViewAsType(source, R.id.product_fragment_content, "field 'mTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mProductFragmentRecycleView = null;
    target.mRadioButton = null;
    target.mTextView = null;

    view2131493033.setOnClickListener(null);
    view2131493033 = null;
  }
}
