package com.czy.verticaltextview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 16/3/26.
 */
public class TextViewWrap extends LinearLayout {
	private static final String TAG = "TextViewWrap";

	public TextViewWrap(Context context) {
		super(context);
		init(context);
	}

	public TextViewWrap(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TextViewWrap(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private String mText;
	private Context mContext;

	private List<TextView> mTextViewList = new ArrayList<>();


	private void init(Context context) {
		mContext = context;
	}


	public TextView generateVerticleTextView() {
		TextView view = new TextView(mContext);
		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.rightMargin = 30;
		view.setLayoutParams(layoutParams);
		view.setEms(1);
		return view;
	}

	public void setText(String text) {
		mText = text;
		int distance = 16000;
		float scale = getResources().getDisplayMetrics().density * distance;


		//TODO  根据标点  行数  高度 决定 生产 TextView 的数量
		TextView textView = generateVerticleTextView();
		textView.setText("在应用开发中，大家会遇到一个问题");
		addView(textView, 0, textView.getLayoutParams());
		mTextViewList.add(textView);

		TextView textView2 = generateVerticleTextView();
		textView2.setText("有时候需要垂直显示一段文字");
		addView(textView2, 0, textView2.getLayoutParams());
		mTextViewList.add(textView2);

		TextView textView3 = generateVerticleTextView();
		textView3.setText("下面我就告诉大家如何做到");
		addView(textView3, 0, textView3.getLayoutParams());
		mTextViewList.add(textView3);


		textView3.setCameraDistance(scale);
		textView2.setCameraDistance(scale);
		textView.setCameraDistance(scale);
	}


	public void hidden2() {
		List<AnimatorSet> animatorArrayList = new ArrayList<>();
		for (int i = mTextViewList.size() - 1; i >= 0; i--) {
			mTextViewList.get(i).setPivotX(mTextViewList.get(i).getWidth());
			mTextViewList.get(i).setPivotY(mTextViewList.get(i).getHeight() / 2);

			AnimatorSet anim = new AnimatorSet();
			ObjectAnimator rotationYAnimator = ObjectAnimator.ofFloat(mTextViewList.get(i), "rotationY", mTextViewList.get(i).getRotationY(), 90);
			ObjectAnimator alphaAnia = ObjectAnimator.ofFloat(mTextViewList.get(i), "alpha", mTextViewList.get(i).getAlpha(), 0);
			anim.playTogether(rotationYAnimator, alphaAnia);

			animatorArrayList.add(anim);
		}

		for (int i = 0; i < animatorArrayList.size() - 1; i++) {
			AnimatorSet animator = animatorArrayList.get(i);
			final AnimatorSet nextAnim = animatorArrayList.get(i + 1);
			ObjectAnimator childAnima = (ObjectAnimator) animator.getChildAnimations().get(0);
			childAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				boolean isNextStart = false;

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					Log.d(TAG, "onAnimationUpdate: animation = " + animation
									+ " getAnimatedFraction =  " + animation.getAnimatedFraction()
									+ " getAnimatedValue =  " + animation.getAnimatedValue()
									+ " getCurrentPlayTime =  " + animation.getCurrentPlayTime()
									+ " getDuration =  " + animation.getDuration()
					);

					if (animation.getCurrentPlayTime() > 230 && !isNextStart) {
						nextAnim.start();
						isNextStart = true;
					}
				}
			});
		}

		animatorArrayList.get(0).start();

	}


	public void hidden() {
		AnimatorSet animatorSet = new AnimatorSet();
		List<Animator> objectAnimators = new ArrayList<>();

		for (int i = mTextViewList.size() - 1; i >= 0; i--) {
			mTextViewList.get(i).setPivotX(mTextViewList.get(i).getWidth());
			mTextViewList.get(i).setPivotY(mTextViewList.get(i).getHeight() / 2);
			AnimatorSet anim = new AnimatorSet();
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextViewList.get(i), "rotationY", mTextViewList.get(i).getRotationY(), 90);
			ObjectAnimator alphaAnia = ObjectAnimator.ofFloat(mTextViewList.get(i), "alpha", mTextViewList.get(i).getAlpha(), 0);
			anim.playTogether(objectAnimator, alphaAnia);
			objectAnimators.add(anim);
		}
		animatorSet.playSequentially(objectAnimators);
		animatorSet.setDuration(300);
		animatorSet.setInterpolator(new DecelerateInterpolator());
		animatorSet.start();

	}

	public void show() {
		AnimatorSet animatorSet = new AnimatorSet();
		List<Animator> objectAnimators = new ArrayList<>();
		for (int i = 0, z = mTextViewList.size(); i < z; i++) {
			mTextViewList.get(i).setPivotX(mTextViewList.get(i).getWidth());
			mTextViewList.get(i).setPivotY(mTextViewList.get(i).getHeight() / 2);
			AnimatorSet anim = new AnimatorSet();
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextViewList.get(i), "rotationY", 90, 0);

			ObjectAnimator alphaAnia = ObjectAnimator.ofFloat(mTextViewList.get(i), "alpha", mTextViewList.get(i).getAlpha(), 1);
			anim.playTogether(objectAnimator, alphaAnia);
			objectAnimators.add(anim);
		}
		animatorSet.playSequentially(objectAnimators);
		animatorSet.setDuration(300);
		animatorSet.setInterpolator(new DecelerateInterpolator());
		animatorSet.start();


	}
}
