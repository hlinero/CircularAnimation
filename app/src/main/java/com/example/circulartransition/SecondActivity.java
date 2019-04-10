package com.example.circulartransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

public class SecondActivity extends AppCompatActivity {

	// Keys
	public static final String KEY_POSITION_X = "KEY_POSITION_X";
	public static final String KEY_POSITION_Y = "KEY_POSITION_Y";

	// Variables
	private View rootView;
	private int positionX;
	private int positionY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status bar
		hideStatusBar();

		setContentView(R.layout.activity_second);

		// Initializing the root view
		rootView = findViewById(R.id.rootView);

		// Getting the intent
		Intent intent = getIntent();

		// Show the activity based on the SDK level
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			// Set initial visibility
			rootView.setVisibility(View.INVISIBLE);

			// Getting intent data
			positionX = intent.getIntExtra(KEY_POSITION_X, 0);
			positionY = intent.getIntExtra(KEY_POSITION_Y, 0);

			// Getting the view observer
			ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();

			// Showing the activity
			if (viewTreeObserver.isAlive()) {
				viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						showActivityWithAnimation(positionX, positionY);
						rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
				});
			}
		} else {

			// Only show the root view
			rootView.setVisibility(View.VISIBLE);
		}
	}

	@Override public void onBackPressed() {

		// Closing the activity with animation
		closeActivityWithAnimation();
	}

	protected void showActivityWithAnimation(int x, int y) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			float finalRadius = (float) (Math.max(rootView.getWidth(), rootView.getHeight()) * 1.1);

			// create the animator for this view (the start radius is zero)
			Animator circularAnimation = ViewAnimationUtils.createCircularReveal(rootView, x, y, 0, finalRadius);
			circularAnimation.setDuration(400);
			circularAnimation.setInterpolator(new AccelerateInterpolator());

			// make the view visible and start the animation
			rootView.setVisibility(View.VISIBLE);
			circularAnimation.start();
		} else {
			finish();
		}
	}

	protected void closeActivityWithAnimation() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			float finalRadius = (float) (Math.max(rootView.getWidth(), rootView.getHeight()) * 1.1);
			Animator circularAnimation = ViewAnimationUtils.createCircularReveal(rootView, positionX, positionY, finalRadius, 0);

			circularAnimation.setDuration(400);
			circularAnimation.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					rootView.setVisibility(View.INVISIBLE);
					finish();
				}
			});

			circularAnimation.start();
		} else {
			finish();
		}
	}

	private void hideStatusBar(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
