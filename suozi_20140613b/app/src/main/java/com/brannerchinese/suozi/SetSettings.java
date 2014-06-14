package com.brannerchinese.suozi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SetSettings extends Activity {

	// Fields related to table dimensions and loops
	int cols = 7;
	int i;
	int minCol = 2, maxCol = 20;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		
		LinearLayout theLayout = new LinearLayout(this);
		theLayout.setOrientation(LinearLayout.VERTICAL);
		theLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		theLayout.setGravity(Gravity.CENTER_VERTICAL);
		// Prevent keyboard pop-up by distracting layout's focus.
		// following http://stackoverflow.com/a/10614108/621762 (accessed 20121221)
		theLayout.setFocusable(true);
		theLayout.setFocusableInTouchMode(true);
		
		TextView theView = new TextView(this);
		theView.setGravity(Gravity.CENTER);
		theView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		theView.setText("How many rows and columns\nin your decoy grid?");
		theLayout.addView(theView);

		final NumberPicker theNumberPicker = new NumberPicker(this);
		theNumberPicker.setMinValue(minCol);
		theNumberPicker.setMaxValue(maxCol);
		theNumberPicker.setValue(cols);
		
		Button theNumberButton = new Button(this);
		theNumberButton.setGravity(Gravity.CENTER);
		int pickerHeight = theNumberPicker.getHeight();
		theNumberButton.setHeight(pickerHeight);
	
		LinearLayout numbPickrAndButton = new LinearLayout(this);
		numbPickrAndButton.addView(theNumberPicker);
		numbPickrAndButton.addView(theNumberButton);
		numbPickrAndButton.setGravity(Gravity.CENTER);
		theLayout.addView(numbPickrAndButton);
	
		theNumberButton.setText("Click for the game.");
		theNumberButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// If user clicks the button, then call the game
				theNumberPicker.clearFocus();
				cols = theNumberPicker.getValue();
				Intent intent = new Intent(SetSettings.this, ShowDecoyTable.class);
				intent.putExtra("cols", cols);
				startActivity(intent);
	        }
	    });
		this.setContentView(theLayout);		
	}
}
