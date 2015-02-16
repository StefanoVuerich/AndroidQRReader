package com.lqc.androidqrreaderproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmURLDeleteFragment extends DialogFragment {

	private static final String TMP_ID = "TMP_ID";
	public static final String _TAG = "ConfirmURLDeleteFragment";
	private IActionSelected mCallback;
	private long tmpID;

	public static ConfirmURLDeleteFragment getInstance(long id) {
		ConfirmURLDeleteFragment fragment = new ConfirmURLDeleteFragment();
		Bundle vBundle = new Bundle();
		vBundle.putLong(TMP_ID, id);
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

		Bundle vBundle = getArguments();
		if (vBundle != null) {
			tmpID = vBundle.getLong(TMP_ID);
		}

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		vBuilder.setTitle("Confirm URL deletion")
				.setMessage("Continue to delete ?")
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
								mCallback.OnDeleteConfirmed(tmpID);
							}
						});

		Dialog vDialog = vBuilder.create();

		return vDialog;

	}

}
