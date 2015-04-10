package com.lqc.androidqrreaderproject.services;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class BaseKeepApplicationInFront extends Service {

	private ActivityManager activityManager;
	Thread thread;
	private static final String SETTINGS_PACKAGE_NAME = "com.android.settings.Settings";
	private static final String RESOLVER_ACTIVITY_PACKAGE_NAME = "com.android.internal.app.ResolverActivity";
	private static final String MAIN_ACTIVITY_PACKAGE_NAME = "com.google.zxing.client.android.CaptureActivity";
	private boolean isStopped;

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.isStopped = true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		activityManager = (ActivityManager) getApplicationContext()
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);

		isStopped = false;

		new Thread(new Runnable() {
			public void run() {

				boolean hasFoundBadApplicationOnTop = false;

				while (!isStopped) {

					ComponentName topActivity = activityManager
							.getRunningTasks(1).get(0).topActivity;

					// check if activity on top is main activity
					if (topActivity != null
							&& (topActivity.getClassName()
									.equals(MAIN_ACTIVITY_PACKAGE_NAME))) {

						continue;
					}

					// check if activity on top is settings
					if (topActivity != null
							&& (topActivity.getClassName()
									.equals(SETTINGS_PACKAGE_NAME))) {

						focusOnApp();

						continue;

					}

					// check if activity on top is toogle recents
					if (topActivity != null
							&& (topActivity.getClassName()
									.equals("com.android.systemui.recent.RecentAppFxActivity"))) {
						focusOnApp();
						continue;

					}
					// check if activity on top is something else
					else if (topActivity != null
							&& (!(topActivity.getClassName().equals(
									RESOLVER_ACTIVITY_PACKAGE_NAME) || !(topActivity
									.getClassName().equals(
											SETTINGS_PACKAGE_NAME) || (!(topActivity
									.getClassName()
									.equals(MAIN_ACTIVITY_PACKAGE_NAME))))))) {
						focusOnApp();
					}
				}

			}
		}).start();

		return super.onStartCommand(intent, flags, startId);
	}

	private void focusOnApp() {

		List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(2);
		RunningTaskInfo programActivity = runningTasks.get(1);
		activityManager.moveTaskToFront(programActivity.id,
				ActivityManager.MOVE_TASK_NO_USER_ACTION);

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
