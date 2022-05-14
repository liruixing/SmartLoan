package com.mmt.smartloan.utils;

import android.view.View;

/**
 * Created by Ellen on 2017/9/11.
 * Copyright (C) 2017 Tungee, Inc.
 * http://www.tungee.com
 */

public class FastDoubleClickUtils {
  private static int VIEW_TAG_CLICK = 2 << 24;

  /**
   * 全局抖动时间
   */
  private volatile static long mGlobalClickTime = 0;

  public static boolean isFastDoubleClick(View view) {
    Long lastClickTime = (Long) view.getTag(VIEW_TAG_CLICK);
    if (null == lastClickTime) {
      lastClickTime = 0L;
    }

    long time = System.currentTimeMillis();

    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 500) {
      return true;
    }else {
      view.setTag(VIEW_TAG_CLICK, time);
    }
    return false;
  }

  /**
   * 用于全局的防抖动
   *
   * @return
   */
  public static boolean isFastGlobalDoubleClick() {
    long time = System.currentTimeMillis();
    long timeD = time - mGlobalClickTime;

    if (0 < timeD && timeD < 500) {
      return true;
    }else {
      mGlobalClickTime = time;
    }
    return false;
  }

  public static boolean isFastDoubleClickLong(View view) {
    Long lastClickTime = (Long) view.getTag(VIEW_TAG_CLICK);
    if (null == lastClickTime) {
      lastClickTime = 0L;
    }

    long time = System.currentTimeMillis();

    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 800) {
      return true;
    }else {
      view.setTag(VIEW_TAG_CLICK, time);
    }
    return false;
  }

  /**
   * 用于全局的防抖动
   *
   * @return
   */
  public static boolean isFastGlobalDoubleClickLong() {
    long time = System.currentTimeMillis();
    long timeD = time - mGlobalClickTime;

    if (0 < timeD && timeD < 800) {
      return true;
    }else {
      mGlobalClickTime = time;
    }
    return false;
  }

}
