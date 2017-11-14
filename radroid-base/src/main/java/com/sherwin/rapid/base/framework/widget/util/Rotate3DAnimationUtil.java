package com.sherwin.rapid.base.framework.widget.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.sherwin.rapid.base.util.Terminal.TerminalUtil;

/**
 * 自定义实现的3D翻转效果的工具类
 * 
 * @author 熊小松
 * @time 2013-09-30
 * @introduce 实现3D立体翻转效果
 */
public class Rotate3DAnimationUtil extends Animation {
	private final float mFromDegrees;
	private final float mToDegrees;
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;
	private final boolean mReverse;
	private Camera mCamera;
	//新增--像素比例（默认值为1）
	float scale = 1;
	/**
	 * 构造函数 创建一个基于Y轴旋转的三维动画，由传入起始角度旋转到终止角度，旋转中心点由传入的X和Y坐标确定
	 * 
	 * @param fromDegrees
	 *            旋转起始角度
	 * @param toDegrees
	 *            旋转终止角度
	 * @param centerX
	 *            旋转中心X坐标
	 * @param centerY
	 *            旋转中心Y坐标
	 * @param depthZ
	 *            Z轴翻转深度
	 * @param reverse
	 *            如果为true则翻转，false则不翻转
	 */
	public Rotate3DAnimationUtil(float fromDegrees, float toDegrees,
								 float centerX, float centerY, float depthZ, boolean reverse) {
		this.mFromDegrees = fromDegrees;
		this.mToDegrees = toDegrees;
		this.mCenterX = centerX;
		this.mCenterY = centerY;
		this.mDepthZ = depthZ;
		this.mReverse = reverse;
		//获取手机像素比 （即dp与px的比例）
		scale = TerminalUtil.getInfo().getDensity();
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;
		float degrees = fromDegrees
				+ ((mToDegrees - fromDegrees) * interpolatedTime);

		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {
			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}
		camera.rotateY(degrees);
		camera.getMatrix(matrix);
		camera.restore();
//----------------------------------------------------------------------------
		/**
		 * 修复打脸问题		(￣ε(#￣)☆╰╮(￣▽￣///)
		 * 简要介绍：
		 * 原来的3D翻转会由于屏幕像素密度问题而出现效果相差很大
		 * 例如在屏幕像素比为1,5的手机上显示效果基本正常，
		 * 而在像素比3,0的手机上面感觉翻转感觉要超出屏幕边缘，
		 * 有种迎面打脸的感觉、
		 *
		 * 解决方案
		 * 利用屏幕像素密度对变换矩阵进行校正，
		 * 保证了在所有清晰度的手机上显示的效果基本相同。
		 *
		 */
		float[] mValues = {0,0,0,0,0,0,0,0,0};
		matrix.getValues(mValues);			//获取数值
		mValues[6] = mValues[6]/scale;			//数值修正
		matrix.setValues(mValues);			//重新赋值

//		Log.e("TAG", "mValues["+0+"]="+mValues[0]+"------------\t"+"mValues["+6+"]="+mValues[6]);
//----------------------------------------------------------------------------
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}