// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfitBookActivity_ViewBinding implements Unbinder {
  private ProfitBookActivity target;

  private View view2131493021;

  @UiThread
  public ProfitBookActivity_ViewBinding(ProfitBookActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfitBookActivity_ViewBinding(final ProfitBookActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.profit_book_back, "field 'mBack' and method 'onViewClicked'");
    target.mBack = Utils.castView(view, R.id.profit_book_back, "field 'mBack'", TextView.class);
    view2131493021 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mEv = Utils.findRequiredViewAsType(source, R.id.profit_book_activity_ev, "field 'mEv'", ExpandableListView.class);
    target.mWebView = Utils.findRequiredViewAsType(source, R.id.profit_book_activity_wv, "field 'mWebView'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfitBookActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBack = null;
    target.mEv = null;
    target.mWebView = null;

    view2131493021.setOnClickListener(null);
    view2131493021 = null;
  }
}
