package com.yaomei.widget;

import com.yaomei.util.AnimUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;

public class ViewFlipper extends android.widget.ViewFlipper {

	private Animation PreInAnimation, PreOutAnimation, NextInAnimation,
			NextOutAnimation;

	public ViewFlipper(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	/**
	 * 设置上一页动画效果
	 * 
	 * @param inAnimation
	 * @param outAnimation
	 */
	public void setPreviousAnimation(Animation inAnimation,
			Animation outAnimation) {
		this.PreInAnimation = inAnimation;
		this.PreOutAnimation = outAnimation;
	}

	/**
	 * 设置下一页动画效果
	 * 
	 * @param inAnimation
	 * @param outAnimation
	 */
	public void setNextAnimation(Animation inAnimation, Animation outAnimation) {
		this.NextInAnimation = inAnimation;
		this.NextOutAnimation = outAnimation;
	}

	@Override
	public void showPrevious() {
		// TODO Auto-generated method stub
		
		this.setInAnimation(PreInAnimation == null ? AnimUtil
				.inFromLeftAnimation() : PreInAnimation);
		this.setOutAnimation(PreOutAnimation == null ? AnimUtil
				.outToRightAnimation() : PreOutAnimation);
		super.showPrevious();
	}

	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		
		this.setInAnimation(NextInAnimation == null ? AnimUtil
				.inFromRightAnimation() : NextInAnimation);
		this.setOutAnimation(NextOutAnimation == null ? AnimUtil
				.outToLeftAnimation() : NextOutAnimation);
		super.showNext();
	}

}
