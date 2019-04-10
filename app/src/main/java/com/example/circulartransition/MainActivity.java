package com.example.circulartransition;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing the button
		Button buttonCircular = findViewById(R.id.buttonCircular);

		// Presenting the second activity when the button is clicked
		buttonCircular.setOnClickListener(this::presentSecondActivity);
	}

	public void presentSecondActivity(View view) {

		// Creating options to be passed to the new activity
		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");

		// Get X and Y positions of the button
		int revealX = (int) (view.getX() + view.getWidth() / 2);
		int revealY = (int) (view.getY() + view.getHeight() / 2);

		// Passing data to the new activity
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra(SecondActivity.KEY_POSITION_X, revealX);
		intent.putExtra(SecondActivity.KEY_POSITION_Y, revealY);

		// Starting the new activity with the options as a bundle
		ActivityCompat.startActivity(this, intent, options.toBundle());
	}
}
