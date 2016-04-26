/*
 * Copyright (C) 2015 Two Toasters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.czy.stickscrollrecycleview;

import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;


public class ZipperEffect implements JazzyEffect {
	private static final String TAG = "ZipperEffect";

	@Override
	public void initView(View item, int position, int scrollDirection, int touchPos) {
		boolean isEven = position % 2 == 0;
//        item.setTranslationX((isEven ? -1 : 1) * item.getWidth());
		if (scrollDirection > 0) {
			// 向上滑动
			if (position > touchPos) {
				int dis = position - touchPos;
				Log.d(TAG, "initView: 向上滑动 position > touchPos dis = " + dis);
				switch (dis) {
					case 1:
						item.setTranslationY(80);
						break;
					case 2:
						item.setTranslationY(60);
						break;
					case 3:
						item.setTranslationY(30);
						break;
				}

			}
		} else {
			// 向下滑动
			if (position < touchPos) {
				int dis = touchPos - position;
				Log.d(TAG, "initView: 向下滑动 position > touchPos dis = " + dis);
				switch (dis) {
					case 1:
						item.setTranslationY(-80);
						break;
					case 2:
						item.setTranslationY(-60);
						break;
					case 3:
						item.setTranslationY(-30);
						break;
				}
			}
		}
	}

	@Override
	public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
		animator.translationY(0);
	}
}
