// Generated code from Butter Knife. Do not modify!
package com.nciebt.zyj.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nciebt.zyj.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoActivity_ViewBinding implements Unbinder {
  private VideoActivity target;

  @UiThread
  public VideoActivity_ViewBinding(VideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VideoActivity_ViewBinding(VideoActivity target, View source) {
    this.target = target;

    target.mVideoPlayer = Utils.findRequiredViewAsType(source, R.id.video_activity_content, "field 'mVideoPlayer'", JCVideoPlayerStandard.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mVideoPlayer = null;
  }
}
