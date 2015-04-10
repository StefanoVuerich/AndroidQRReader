package com.lqc.androidqrreaderproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmURLDeleteFragment extends DialogFragment {

	private static final String ITEM_ID = "ITEM_ID";
	private static final String TO_REMOVE_FLAG = "AllFlag";
	public static final String ALL_TAG = "AllTag";
	public static final String _TAG = "ConfirmURLDeleteFragment";
	private IActionSelected mCallback;
	private long tmpID;

	private boolean deleteAllFlag = false;

	public static ConfirmURLDeleteFragment getInstance(String allFlag) {
		ConfirmURLDeleteFragment fragment = new ConfirmURLDeleteFragment();

		Bundle vBundle = new Bundle();
		vBundle.putString(TO_REMOVE_FLAG, allFlag);

		fragment.setArguments(vBundle);
		return fragment;
	}

	public static ConfirmURLDeleteFragment getInstance(long id, String url) {
		ConfirmURLDeleteFragment fragment = new ConfirmURLDeleteFragment();

		Bundle vBundle = new Bundle();
		vBundle.putString(TO_REMOVE_FLAG, url);
		vBundle.putLong(ITEM_ID, id);

		fragment.setArguments(vBundle);
		return fragment;
	}

	public interface IActionSelected {
		public void OnDeleteConfirmed(long id);

		public void OnDeleteCancelled();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof IActionSelected) {
			mCallback = (IActionSelected) activity;
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		String title = "";
		String message = "";

		Bundle vBundle = getArguments();
		if (vBundle != null) {

			String whatSholudBeCancelled = vBundle.getString(TO_REMOVE_FLAG);

			if (whatSholudBeCancelled.equals(ALL_TAG)) {
				title = "Confirm all URLs deletion";
				message = "Continue to delete all record ?";
				deleteAllFlag = true;
			} else {
				tmpID = vBundle.getLong(ITEM_ID);
				title = "Confirm single URL deletion";
				message = "Continue to delete " + whatSholudBeCancelled + " ?";
				deleteAllFlag = false;
			}
		}

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		vBuilder.setTitle(title)
				.setMessage(message)
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								if (deleteAllFlag)
									mCallback.OnDeleteConfirmed(-1);
								else
									mCallback.OnDeleteConfirmed(tmpID);
							}
						})
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mCallback.OnDeleteCancelled();
							}
						});

		Dialog vDialog = vBuilder.create();

		return vDialog;

	}
}
