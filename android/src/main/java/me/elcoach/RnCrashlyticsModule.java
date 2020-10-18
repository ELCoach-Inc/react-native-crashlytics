package me.elcoach;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import androidx.annotation.NonNull;

public class RnCrashlyticsModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;
	private final FirebaseCrashlytics crashlytics;
	private boolean isAutoCollectionEnabled;

	public RnCrashlyticsModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
		crashlytics = FirebaseCrashlytics.getInstance();
		isAutoCollectionEnabled = isManifestAutoCollectionEnabled();
	}

	@Override
	@NonNull
	public String getName() {
		return "RnCrashlytics";
	}

	private boolean isManifestAutoCollectionEnabled() {
		try {
			ApplicationInfo app = reactContext.getPackageManager().getApplicationInfo(reactContext.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = app.metaData;
			return bundle.getBoolean("firebase_crashlytics_collection_enabled", true);
		} catch (PackageManager.NameNotFoundException | NullPointerException e) {
			e.printStackTrace();
		}
		return true;
	}

	@ReactMethod
	public void isCrashlyticsCollectionEnabledAsync(Promise promise) {
		promise.resolve(isAutoCollectionEnabled);
	}

	@ReactMethod
	public void setCrashlyticsCollectionEnabled(boolean value) {
		isAutoCollectionEnabled = value;
		crashlytics.setCrashlyticsCollectionEnabled(value);
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
	public void logAsync(String message, Promise promise) {
		crashlytics.log(message);
		promise.resolve(null);
	}

	@ReactMethod
	public void recordErrorAsync(ReadableMap jsErrorMap, Promise promise) {
		recordJavaScriptError(jsErrorMap);
		promise.resolve(null);
	}

	private void recordJavaScriptError(ReadableMap jsErrorMap) {
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
		crashlytics.recordException(customException);
	}

	@ReactMethod
	public void setValueForKey(String key, String value) {
		crashlytics.setCustomKey(key, value);
	}
}
