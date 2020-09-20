package me.elcoach;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.text.NumberFormat;

import androidx.annotation.NonNull;

public class RnCrashlyticsModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;
	private final FirebaseCrashlytics crashlytics;

	public RnCrashlyticsModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
		crashlytics = FirebaseCrashlytics.getInstance();
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

	// TODO: support ios
	// @ReactMethod
	// public void logError(String errorMsg) {
	// 	crashlytics.recordException(new RuntimeException(errorMsg));
	// }

	@ReactMethod
	public void log(String message) {
		crashlytics.log(message);
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
