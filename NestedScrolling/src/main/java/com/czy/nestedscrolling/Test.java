///*
// * Copyright (C) 2015 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//
//import android.content.Context;
//import android.support.v4.view.NestedScrollingChildHelper;
//import android.support.v4.view.NestedScrollingParent;
//import android.support.v4.view.NestedScrollingParentHelper;
//import android.support.v4.view.ViewCompat;
//import android.support.v4.view.ViewGroupCompat;
//import android.support.v4.view.ViewParentCompat;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewParent;
//import android.widget.LinearLayout;
//
//import com.imczy.common_util.log.LogUtil;
//
//public interface NestedScrollingChild {
//
//    /**
//     * 设置嵌套滑动是否可用
//     *
//     * @param enabled
//     */
//    public void setNestedScrollingEnabled(boolean enabled);
//
//    /**
//     * 嵌套滑动是否可用
//     *
//     * @return
//     */
//    public boolean isNestedScrollingEnabled();
//
//
//    /**
//     * 开始嵌套滑动,
//     *
//     * @param axes 表示方向 有一下两种值
//     *             ViewCompat.SCROLL_AXIS_HORIZONTAL 横向哈东
//     *             ViewCompat.SCROLL_AXIS_VERTICAL 纵向滑动
//     */
//    public boolean startNestedScroll(int axes);
//
//    /**
//     * 停止嵌套滑动
//     */
//    public void stopNestedScroll();
//
//    /**
//     * 是否有父View 支持 嵌套滑动,  会一层层的网上寻找父View
//     * @return
//     */
//    public boolean hasNestedScrollingParent();
//
//    /**
//     * 在处理滑动之后 调用
//     * @param dxConsumed x轴上 被消费的距离
//     * @param dyConsumed y轴上 被消费的距离
//     * @param dxUnconsumed x轴上 未被消费的距离
//     * @param dyUnconsumed y轴上 未被消费的距离
//     * @param offsetInWindow view 的移动距离
//     * @return
//     */
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
//                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow);
//
//    /**
//     * 一般在滑动之前调用, 在ontouch 中计算出滑动距离, 然后 调用改 方法, 就给支持的嵌套的父View 处理滑动事件
//     * @param dx x 轴上滑动的距离, 相对于上一次事件, 不是相对于 down事件的 那个距离
//     * @param dy y 轴上滑动的距离
//     * @param consumed 一个数组, 可以传 一个空的 数组,  表示 x 方向 或 y 方向的事件 是否有被消费
//     * @param offsetInWindow   支持嵌套滑动到额父View 消费 滑动事件后 导致 本 View 的移动距离
//     * @return 支持的嵌套的父View 是否处理了 滑动事件
//     */
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);
//
//    /**
//     *
//     * @param velocityX x 轴上的滑动速度
//     * @param velocityY y 轴上的滑动速度
//     * @param consumed 是否被消费
//     * @return
//     */
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);
//
//    /**
//     *
//     * @param velocityX x 轴上的滑动速度
//     * @param velocityY y 轴上的滑动速度
//     * @return
//     */
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY);
//}
//
//
//
//public class Child extends LinearLayout implements android.support.v4.view.NestedScrollingChild {
//    public static final String TAG = "Child";
//
//    private NestedScrollingChildHelper mNestedScrollingChildHelper;
//
//    public Child(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
//    }
//
//
//    @Override
//    public void setNestedScrollingEnabled(boolean enabled) {
//        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
//    }
//
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
//    }
//
//    @Override
//    public boolean startNestedScroll(int axes) {
//        return mNestedScrollingChildHelper.startNestedScroll(axes);
//    }
//
//    @Override
//    public void stopNestedScroll() {
//        mNestedScrollingChildHelper.stopNestedScroll();
//    }
//
//    @Override
//    public boolean hasNestedScrollingParent() {
//        return mNestedScrollingChildHelper.hasNestedScrollingParent();
//    }
//
//    @Override
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
//    }
//}
//
//
//
//
//
//public class NestedScrollingChildHelper {
//    /**
//     * 嵌套滑动的ziView
//     */
//    private final View mView;
//
//    /**
//     * 支持 嵌套滑动的 父View
//     */
//    private ViewParent mNestedScrollingParent;
//
//    /**
//     * 是否支持 嵌套滑动
//     */
//    private boolean mIsNestedScrollingEnabled;
//
//    /**
//     * 是否被消费的一个中变变量
//     */
//    private int[] mTempNestedScrollConsumed;
//
//    public NestedScrollingChildHelper(View view) {
//        mView = view;
//    }
//
//    public void setNestedScrollingEnabled(boolean enabled) {
//        if (mIsNestedScrollingEnabled) {
//            ViewCompat.stopNestedScroll(mView);
//        }
//        mIsNestedScrollingEnabled = enabled;
//    }
//
//    public boolean isNestedScrollingEnabled() {
//        return mIsNestedScrollingEnabled;
//    }
//
//    public boolean hasNestedScrollingParent() {
//        return mNestedScrollingParent != null;
//    }
//
//    /**
//     * 开始嵌套滑动
//     * @param axes 滑动方向
//     * @return 是否有父view 支持嵌套滑动
//     */
//    public boolean startNestedScroll(int axes) {
//        if (hasNestedScrollingParent()) {
//            // 如果已经找到 了嵌套滑动的父View
//            // Already in progress
//            return true;
//        }
//        if (isNestedScrollingEnabled()) {
//            ViewParent p = mView.getParent();
//            View child = mView;
//            // 递归向上寻找 支持 嵌套滑动的父View
//            while (p != null) {
//                // 这里会调用 父View 的NestedScrollingParent.onStartNestedScroll 方法
//                // 如果 父View 返回 false  则再次向上寻找父View , 直到找到支持的fuView
//                if (ViewParentCompat.onStartNestedScroll(p, child, mView, axes)) {
//                    mNestedScrollingParent = p;
//                    // 这里回调 父View 的onNestedScrollAccepted 方法 表示开始接收 嵌套滑动
//                    ViewParentCompat.onNestedScrollAccepted(p, child, mView, axes);
//                    return true;
//                }
//                if (p instanceof View) {
//                    child = (View) p;
//                }
//                p = p.getParent();
//            }
//        }
//        // 没有找到 支持嵌套滑动的父View  则返回false
//        return false;
//    }
//
//    /**
//     * 停止 嵌套滑动, 一般 在 cancel up 事件中 调用
//     */
//    public void stopNestedScroll() {
//        if (mNestedScrollingParent != null) {
//            ViewParentCompat.onStopNestedScroll(mNestedScrollingParent, mView);
//            mNestedScrollingParent = null;
//        }
//    }
//
//    /**
//     *
//     * @param dxConsumed  x 上被消费的距离
//     * @param dyConsumed  y 上被消费的距离
//     * @param dxUnconsumed  x 上未被消费的距离
//     * @param dyUnconsumed  y 上未被消费的距离
//     * @param offsetInWindow  子View 位置的移动距离
//     * @return
//     */
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
//                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
//        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
//            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
//                int startX = 0;
//                int startY = 0;
//                if (offsetInWindow != null) {
//                    mView.getLocationInWindow(offsetInWindow);
//                    startX = offsetInWindow[0];
//                    startY = offsetInWindow[1];
//                }
//
//                // 父View 回调 onNestedScroll 方法, 该放在 主要会处理  dxUnconsumed dyUnconsumed 数据
//                ViewParentCompat.onNestedScroll(mNestedScrollingParent, mView, dxConsumed,
//                        dyConsumed, dxUnconsumed, dyUnconsumed);
//
//                if (offsetInWindow != null) {
//                    // 计算 子View的移动距离
//                    mView.getLocationInWindow(offsetInWindow);
//                    offsetInWindow[0] -= startX;
//                    offsetInWindow[1] -= startY;
//                }
//                return true;
//            } else if (offsetInWindow != null) {
//                // No motion, no dispatch. Keep offsetInWindow up to date.
//                offsetInWindow[0] = 0;
//                offsetInWindow[1] = 0;
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * consumed[0]  为0 时 表示 x 轴方向上事件 没有被消费
//     *              不为0 时 表示 x 轴方向上事件 被消费了, 值表示 被消费的滑动距离
//     * consumed[1]  为0 时 表示 y 轴方向上事件 没有被消费
//     *              不为0 时 表示 y 轴方向上事件 被消费了, 值表示 被消费的滑动距离
//     *
//     *
//     * @param dx
//     * @param dy
//     * @param consumed
//     * @param offsetInWindow
//     * @return
//     */
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
//            if (dx != 0 || dy != 0) {
//                int startX = 0;
//                int startY = 0;
//                // 获取 当前View 初始位置
//                if (offsetInWindow != null) {
//                    mView.getLocationInWindow(offsetInWindow);
//                    startX = offsetInWindow[0];
//                    startY = offsetInWindow[1];
//                }
//
//                // 初始化是否被消费数据
//                if (consumed == null) {
//                    if (mTempNestedScrollConsumed == null) {
//                        mTempNestedScrollConsumed = new int[2];
//                    }
//                    consumed = mTempNestedScrollConsumed;
//                }
//                consumed[0] = 0;
//                consumed[1] = 0;
//
//                // 这里回调 父View 的 onNestedPreScroll 方法,
//                // 父View 或许会处理 相应的滑动事件,
//                // 如果 处理了 则 consumed 会被赋予 相应的值
//                ViewParentCompat.onNestedPreScroll(mNestedScrollingParent, mView, dx, dy, consumed);
//
//                if (offsetInWindow != null) {
//                    // 父View 处理了相应的滑动,  很可能导致 子View 的位置的移动
//                    // 这里计算出  父view 消费 滑动事件后,  导致 子View 的移动距离
//                    mView.getLocationInWindow(offsetInWindow);
//                    // 这里 子View 的移动距离
//                    offsetInWindow[0] -= startX;
//                    offsetInWindow[1] -= startY;
//                }
//                // 如果  xy 方向 上 有不为0 的表示消费了 则返回true
//                return consumed[0] != 0 || consumed[1] != 0;
//            } else if (offsetInWindow != null) {
//                offsetInWindow[0] = 0;
//                offsetInWindow[1] = 0;
//            }
//        }
//        return false;
//    }
//
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
//            return ViewParentCompat.onNestedFling(mNestedScrollingParent, mView, velocityX,
//                    velocityY, consumed);
//        }
//        return false;
//    }
//
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        if (isNestedScrollingEnabled() && mNestedScrollingParent != null) {
//            return ViewParentCompat.onNestedPreFling(mNestedScrollingParent, mView, velocityX,
//                    velocityY);
//        }
//        return false;
//    }
//
//    public void onDetachedFromWindow() {
//        ViewCompat.stopNestedScroll(mView);
//    }
//
//    public void onStopNestedScroll(View child) {
//        ViewCompat.stopNestedScroll(mView);
//    }
//}
//
//
//
//public class Parent extends LinearLayout implements NestedScrollingParent {
//    public static final String TAG = "Parent";
//
//    private NestedScrollingParentHelper mNestedScrollingParentHelper;
//
//    public Parent(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
//    }
//
//
//    /**
//     * 回调开始滑动
//     * @param child 该父VIew 的子View
//     * @param target 支持嵌套滑动的 VIew
//     * @param nestedScrollAxes 滑动方向
//     * @return 是否支持 嵌套滑动
//     */
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return true;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
//        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
//    }
//
//    @Override
//    public void onStopNestedScroll(View target) {
//        mNestedScrollingParentHelper.onStopNestedScroll(target);
//    }
//
//    /**
//     * 这里 主要处理 dyUnconsumed dxUnconsumed 这两个值对应的数据
//     * @param target
//     * @param dxConsumed
//     * @param dyConsumed
//     * @param dxUnconsumed
//     * @param dyUnconsumed
//     */
//    @Override
//    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        LogUtil.d(TAG, "onNestedScroll target = " + target + " , dxConsumed = " + dxConsumed + " , dyConsumed = " + dyConsumed + " , dxUnconsumed = " + dxUnconsumed + " , dyUnconsumed = " + dyUnconsumed);
//    }
//
//    /**
//     * 这里 传来了 x y 方向上的滑动距离
//     * 并且 先与 子VIew  处理滑动,  并且 consumed  中可以设置相应的 除了的距离
//     * 然后 子View  需要更具这感觉, 来处理自己滑动
//     *
//     * @param target
//     * @param dx
//     * @param dy
//     * @param consumed
//     */
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//
//        consumed[1] = dy;
//        LogUtil.d(TAG, "onNestedPreScroll dx = " + dx + " dy = " + dy);
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        return false;
//    }
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        return false;
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        return mNestedScrollingParentHelper.getNestedScrollAxes();
//    }
//}