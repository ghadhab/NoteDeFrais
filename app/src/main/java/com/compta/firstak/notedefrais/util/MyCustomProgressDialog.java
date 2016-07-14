package com.compta.firstak.notedefrais.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.compta.firstak.notedefrais.R;


public class MyCustomProgressDialog extends ProgressDialog {
	// private AnimationDrawable animation;
	Context myContex;
	// ImageView myImage;
	// Animation myRotation;
	// ObjectAnimator scaleXAnimator;
	static String message;

	public static ProgressDialog ctor(Context context, String msg) {
		MyCustomProgressDialog dialog = new MyCustomProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		message = msg;
		return dialog;
	}

	public MyCustomProgressDialog(Context context) {
		super(context);
		myContex = context;
	}

	public MyCustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_progress_dialog);
		TextView messageView = (TextView) findViewById(R.id.message_progress_bar);
		messageView.setText(message);
		/*ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBar_custom);
		progressBar.getIndeterminateDrawable().setColorFilter(0xFFf7a823,
				android.graphics.PorterDuff.Mode.MULTIPLY);*/
		// myImage = (ImageView) findViewById(R.id.image_view_anim);

		/*
		 * scaleXAnimator = ObjectAnimator.ofFloat(myImage, "rotation", 360f);
		 * scaleXAnimator.setRepeatCount(9); scaleXAnimator.setDuration(1000);
		 */

	}

	@Override
	public void show() {
		super.show();

		// scaleXAnimator.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		// animation.stop();
		// myImage.
	}
}
