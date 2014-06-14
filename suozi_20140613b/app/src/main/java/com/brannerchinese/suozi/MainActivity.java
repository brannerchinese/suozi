package com.brannerchinese.suozi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		
		LinearLayout theLayout = new LinearLayout(this);
		theLayout.setOrientation(LinearLayout.VERTICAL);
		TextView theView = new TextView(this);
		theView.setGravity(Gravity.LEFT);
		theView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		theView.setText("This game (currently in prototype) helps you practice " +
				"recognizing whole " +
				"Chinese characters.\n\nMany are rare. The point is not to learn " +
				"them, just to improve your speed at " +
				"spotting them.\n\nAt the " +
				"bottom of the screen is a character on a yellow " +
				"background — find it among the others and touch " +
				"it. If you're right, it will turn blue on a " +
				"grey background; if you're wrong, red on black. " +
				"\n\nClick the button below to choose the number of columns and " +
				"rows in the field and start the game.\n");
		theLayout.addView(theView);
		
		Button settingsButton = new Button(this);
		settingsButton.setGravity(Gravity.CENTER);
		settingsButton.setText("Click for settings");
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SetSettings.class);
				startActivity(intent);
				finish();
            }
        });
		theLayout.addView(settingsButton);

		this.setContentView(theLayout);	
	}
 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
}
