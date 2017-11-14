package com.sherwin.rapid.base.util;

import android.util.Log;
import android.widget.Toast;

import com.sherwin.rapid.base.core.RapidDroid;


public class LogUtil {

	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int ASSERT = 6;
	public static final int NONE = 7;
	public static final int LEVEL_DEFAULT = NONE;
	public static int LEVEL = RapidDroid.logLevel;

	private static final String TAG_DEFAULT = "ants";

	public static void v(String msg) {
		v(TAG_DEFAULT, msg);
	}

	public static void v(String tag, String msg) {
		if (VERBOSE >= LEVEL) {
			Log.v(tag, msg);
		}
	}

	public static void d(String msg) {
		d(TAG_DEFAULT, msg);
	}

	public static void d(String tag, String msg) {
		if (DEBUG >= LEVEL) {
			Log.d(tag, msg);
		}
	}

	public static void i(String msg) {
		i(TAG_DEFAULT, msg);
	}

	public static void i(String tag, String msg) {
		if (INFO >= LEVEL) {
			Log.i(tag, msg);
		}
	}

	public static void w(String msg) {
		w(TAG_DEFAULT, msg);
	}

	public static void w(String tag, String msg) {
		if (WARN >= LEVEL) {
			Log.w(tag, msg);
		}
	}

	public static void e(String msg) {
		e(TAG_DEFAULT, msg, null);
	}

	public static void e(String tag,String msg) {
		e(tag, msg, null);
	}
	public static void e(Throwable e) {
		e(TAG_DEFAULT, e.getMessage(), e);
	}
	public static void e(String msg,Throwable e) {
		e(TAG_DEFAULT, msg, e);
	}

	public static void e(String tag, String msg, Throwable e) {
		if (ERROR >= LEVEL) {
			if (e == null) {
				Log.e(tag, msg);
			} else {
				Log.e(tag, msg, e);
			}

		}
	}

	public static void toast(String msg) {
		if (DEBUG >= LEVEL) {
			Toast.makeText(RapidDroid.application, msg, Toast.LENGTH_LONG).show();
		}
	}

}
