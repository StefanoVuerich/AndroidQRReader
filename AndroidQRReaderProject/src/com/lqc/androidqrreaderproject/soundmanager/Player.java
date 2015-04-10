package com.lqc.androidqrreaderproject.soundmanager;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.lqc.androidqrreaderproject.R;

public class Player {

	private SoundPool soundPool;
	private AudioManager audioManager;
	private int ticketIsValid;
	private int ticketIsNotValid;
	private static Player mInstance;

	private Player() {
	}

	@SuppressWarnings("deprecation")
	private Player(Activity activity) {

		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		audioManager = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);
		ticketIsValid = soundPool.load(activity, R.raw.success, 1);
		ticketIsNotValid = soundPool.load(activity, R.raw.buzzer, 1);
	}

	public static Player get(Activity activity) {
		if (mInstance == null) {
			mInstance = new Player(activity);
		}

		return mInstance;
	}

	public void playTicketIsValidSound() {
		this.soundPool.play(ticketIsValid, 1, 1, 0, 0, 1.0f);
	}

	public void playTicketIsNotValidSound() {
		this.soundPool.play(ticketIsNotValid, 1, 1, 0, 0, 1.0f);
	}
}
