package com.sherwin.rapid.base.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sherwin.rapid.base.util.LogUtil;

import java.util.List;

/**
 * 垂直轮播文字控件
 * @author Sherwin.Ye
 * @data 2016年3月17日 上午10:15:53
 * @desc VerticalScrollTextView.java
 */
public class VerticalBillboardTextView extends TextView {
	private final static String TAG=VerticalBillboardTextView.class.getSimpleName();
	
	private Paint mPaint;
	/**
	 * 数据集合
	 */
	private List<Sentence> list;
	/**
	 * hint字符串
	 */
	private String hintString = "";

	/**
	 * 当前索引位置
	 */
	public int index = 0;

	/**
	 * 当前索引位置偏移量
	 */
	public float offsetY = 0.0f;

	/**
	 * 每一行的间隔
	 */
	private float delatY = 0;
	/**
	 * 显示持续时间
	 */
	private long duration = 3000;
	/**
	 * 滚动动画持续时间
	 */
	private long durationRoll = 500;

	/**
	 * 当前显示的字符串
	 */
	private String currentText = null;
	
	/**
	 * 刷新锁，避免出现闪烁
	 */
	private boolean lockOnDraw=false;

	public VerticalBillboardTextView(Context context) {
		super(context);
		init();
	}

	public VerticalBillboardTextView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	public VerticalBillboardTextView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		init();
	}

	/**
	 * 初始化各项参数
	 */
	private void init() {
		setFocusable(true);
		//		if (list == null) {
		//			list = new ArrayList<Sentence>();
		//			Sentence sen = new Sentence(0, getHint().toString());
		//			list.add(0, sen);
		//		}
		if (getHint() != null) {
			hintString = getHint().toString();
			setHint("");
		}
		delatY = getHeight() / 2 + getTextSize() / 2;
		// 非高亮部分
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(getTextSize());
		mPaint.setColor(getCurrentTextColor());
		mPaint.setTypeface(Typeface.SERIF);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		lockOnDraw=true;
		delatY = getHeight() / 2 + getTextSize() / 2;
		//		canvas.drawColor(0xEFeffff);
		mPaint.setTextAlign(Paint.Align.LEFT);
		mPaint.setTypeface(getTypeface());
		float currentY = delatY - offsetY - 5;
		if (index < 0 || list == null || list.size() == 0) {
			// 先画当前行，之后再画他的前面和后面，这样就保持当前行在中间的位置
			//			canvas.drawText(hintString, mX, currentY, p);
			setHint(hintString);
			currentText = null;
			return;
		} else {
			setHint("");
		}
		int total = list.size();
		index = index % total;
		currentText = list.get(index).getName();
		int drawableW = getCompoundDrawables()[0].getBounds().width();
		int leftOffset = drawableW + getPaddingLeft() + getCompoundDrawablePadding();
		// 先画当前行，之后再画他的前面和后面，这样就保持当前行在中间的位置
		String title = TextUtils.ellipsize(currentText, new TextPaint(mPaint), getWidth() - leftOffset - getPaddingRight(), TextUtils.TruncateAt.END).toString();
		canvas.drawText(title, leftOffset, currentY, mPaint);
		// 画出本句之后的句子
		int i = (index + 1) % total;
		currentY = currentY + delatY;
		title = TextUtils.ellipsize(list.get(i).getName(), new TextPaint(mPaint), getWidth() - leftOffset - getPaddingRight(), TextUtils.TruncateAt.END).toString();
		canvas.drawText(title, leftOffset, currentY, mPaint);
		lockOnDraw=false;
	}

	/**
	 * 更新索引
	 * @param time
	 * @return
	 */
	public long updateIndex(long time) {
		if (time < 0 || list == null || list.size() == 0) {
			index = -1;
			return 0;
		} else {
			this.index = (int) ((time / getTotalDuration()) % list.size());
			long aminTime = time % getTotalDuration() - duration;
			if (aminTime > 0) {
				this.offsetY = delatY * aminTime / durationRoll;
			} else {
				this.offsetY = 0;
			}
			return time;
		}
	}

	/**
	 * 得到总计的持续时间
	 */
	private long getTotalDuration() {
		return duration + durationRoll;
	}

	/**
	 * 得到数据总数
	 * @return
	 */
	private int getTotal() {
		return list == null ? 0 : list.size();
	}

	public List<Sentence> getList() {
		return list;
	}

	public void setList(List<Sentence> list) {
		this.list = list;
	}

	/**
	 * 得到当前
	 * @return
	 */
	public String getCurrentText() {
		return currentText;
	}

	/**
	 * 获取默认显示的Hint
	 * @return
	 */
	public String getHintString() {
		return hintString;
	}

	/**
	 * 设置默认显示的Hint
	 * @param hintString
	 */
	public void setHintString(String hintString) {
		this.hintString = hintString;
	}

	/**
	 * 得到持续时长
	 * @return
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * 设置显示持续时长
	 * @param duration
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * 得到滚动动画时长
	 * @return
	 */
	public long getDurationRoll() {
		return durationRoll;
	}

	/**
	 * 设置滚动动画时长
	 * @param durationRoll
	 */
	public void setDurationRoll(long durationRoll) {
		this.durationRoll = durationRoll;
	}

	/**
	 * 初始化调用
	 */
	public void initScorll() {
		if (scorllThread==null) {
			scorllThread=new Thread(new updateThread());
			scorllThread.start();
		}
	}
	/**
	 * 上下滚动线程
	 */
	private Thread scorllThread;

	/**
	 * 刷新视图线程
	 * @author Sherwin.Ye
	 * @data 2016年3月17日 上午10:44:36
	 * @desc VerticalBillboardTextView.java
	 */
	class updateThread implements Runnable {
		long time = 0; //计步时用
		final static long SLEEP_TIME = 50;//刷新时间

		public void run() {
			while (true) {
				try {
					Thread.sleep(SLEEP_TIME);
					if (lockOnDraw|| getTotal() <= 1) {//上锁时不循环,数据为一条时也不循环
						continue;
					}
					time += SLEEP_TIME;
					time = updateIndex(time);
					
					mHandler.post(mUpdateResults);
				} catch (InterruptedException e) {
					LogUtil.e(TAG, "updateThread InterruptedException"+e);
					break;
				}
			}
		}
	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			invalidate(); // 更新视图
		}
	};

	/**
	 * 每一条的内容
	 * @author Sherwin.Ye
	 * @data 2016年3月17日 上午10:15:31
	 * @desc VerticalScrollTextView.java
	 */
	public static class Sentence {

		private String name;
		private int index;

		public Sentence(int index, String name) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}
}