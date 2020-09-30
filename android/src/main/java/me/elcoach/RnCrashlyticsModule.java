package me.elcoach;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.text.NumberFormat;
import java.util.Objects;

import androidx.annotation.NonNull;

public class RnCrashlyticsModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;
	private final FirebaseCrashlytics crashlytics;

	public RnCrashlyticsModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
		crashlytics = FirebaseCrashlytics.getInstance();
		crashlytics.setCrashlyticsCollectionEnabled(true);
	}

	@Override
	@NonNull
	public String getName() {
		return "RnCrashlytics";
	}

	@ReactMethod
	public void setUserID(String userID) {
		crashlytics.setUserId(userID);
	}

	@ReactMethod
    public void crash() {
	    throw new RuntimeException("Crash test – Forced crash by react-native-crashlytics");
    }

	@ReactMethod
	public void log(String message) {
		crashlytics.log(message);
	}

	// For internal use only.
	@ReactMethod
	public void logPromise(String message, Promise promise) {
		crashlytics.log(message);
		promise.resolve(null);
	}

	@ReactMethod
	public void recordErrorPromise(ReadableMap jsErrorMap, boolean forceFatal, Promise promise) {
		recordJavaScriptError(jsErrorMap, forceFatal);
		promise.resolve(null);
	}

	private void recordJavaScriptError(ReadableMap jsErrorMap, boolean forceFatal) {
		String message = jsErrorMap.getString("message");


		ReadableArray stackFrames = Objects.requireNonNull(jsErrorMap.getArray("frames"));
		Exception customException = new RuntimeException(message);
		StackTraceElement[] stackTraceElements = new StackTraceElement[stackFrames.size()];

		for (int i = 0; i < stackFrames.size(); i++) {
			ReadableMap stackFrame = Objects.requireNonNull(stackFrames.getMap(i));
			String fn = stackFrame.getString("fn");
			String file = stackFrame.getString("file");
			stackTraceElements[i] = new StackTraceElement("", fn, file, -1);
		}

		customException.setStackTrace(stackTraceElements);
		if (forceFatal) {
			throw new RuntimeException(customException);
		}
		crashlytics.recordException(customException);
	}

	@ReactMethod
	public void setValueForKey(String key, String value) {
		crashlytics.setCustomKey(key, value);
	}

	// TODO: support multiple types.
	// @ReactMethod
	// public void setString(String key, String value) {
	// 	crashlytics.setCustomKey(key, value);
	// }

	// @ReactMethod
	// public void setNumber(String key, String numberString) {
	// 	try {
	// 		Number number = NumberFormat.getInstance().parse(numberString);
	// 		if (number instanceof Double) {
	// 			crashlytics.setCustomKey(key, number.doubleValue());
	// 		} else if (number instanceof Float) {
	// 			crashlytics.setCustomKey(key, number.floatValue());
	// 		} else if (number instanceof Integer) {
	// 			crashlytics.setCustomKey(key, number.intValue());
	// 		} else if (number instanceof Long) {
	// 			crashlytics.setCustomKey(key, number.longValue());
	// 		}
	// 	} catch (Exception ex) {
	// 		Log.e("RN-Crashlytics:", ex.getMessage());
	// 	}
	// }

	// @ReactMethod
	// public void setBool(String key, boolean value) {
	// 	crashlytics.setCustomKey(key, value);
	// }
}
