package com.czy.stepview.stepview;

/**
 * Created by chenzhiyong on 2016/10/29.
 * <p>
 * 暂不考虑 View 回收问题
 */
public interface BaseStepViewAdapter {

    public int getCount();

    public VerticalStepView.ViewHolder getViewHolder(int position);

}
