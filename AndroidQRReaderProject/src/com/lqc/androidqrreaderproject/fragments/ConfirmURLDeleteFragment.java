package com.lqc.androidqrreaderproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmURLDeleteFragment extends DialogFragment {

	private static final String ALL_FLAG = "AllFlag";
	public static final String ALL_TAG = "AllTag";
	private static final String TMP_ID = "TMP_ID";
	public static final String _TAG = "ConfirmURLDeleteFragment";
	private IActionSelected mCallback;
	private long tmpID;
	private String tmpAll;
	private boolean deleteAllFlag = false;

	public static ConfirmURLDeleteFragment getInstance(long id) {
		ConfirmURLDeleteFragment fragment = new ConfirmURLDeleteFragment();
		Bundle vBundle = new Bundle();
		vBundle.putLong(TMP_ID, id);
		fragment.setArguments(vBundle);
		return fragment;
	}

	public static ConfirmURLDeleteFragment getInstance(String all) {
		ConfirmURLDeleteFragment fragment = new ConfirmURLDeleteFragment();
		Bundle vBundle = new Bundle();
		vBundle.putString(ALL_TAG, ALL_FLAG);
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
			tmpID = vBundle.getLong(TMP_ID);
			//maybe bug above
			if (tmpID != 0) {
				title = "Confirm single URL deletion";
				message = "Continue to delete one record ?";
				deleteAllFlag = false;
			} else {
				tmpAll = vBundle.getString(ALL_TAG);
				if (tmpAll.equals(ALL_FLAG)) {
					title = "Confirm all URLs deletion";
					message = "Continue to delete one record ?";
					deleteAllFlag = true;
				}
			}
		}

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		vBuilder.setTitle(title)
				.setMessage(message)
				.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mCallback.OnDeleteCancelled();
							}
						})
				.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								if (deleteAllFlag)
									mCallback.OnDeleteConfirmed(-1);
								else
									mCallback.OnDeleteConfirmed(tmpID);
							}
						});

		Dialog vDialog = vBuilder.create();

		return vDialog;

	}
}
