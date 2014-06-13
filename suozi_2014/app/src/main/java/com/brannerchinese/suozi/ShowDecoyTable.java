package com.brannerchinese.suozi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowDecoyTable extends Activity {

	
	// Fields related to table dimensions and loops
	// Note: the value of cols is supplied via putExtra() on an intent, and 
	//    intents require context and so there can be no getIntent()
	//    before onCreate(). However, by declaring cols here, we avoid
	//    having to make it final, which would be obligatory if it were
	//    declared inside of onCreate.
	int cols;
	int col_i, row_i, index;
	int minCol = 2, maxCol = 20;

	// Fields related to scoring and timing (NOTE: not yet implemented)
	// Using arrays, since each value of cols has different results.
//	int[] numTriesPerColValue = new int[maxCol];
	// For counting time we will record average only, hence float.
//	float[] timePerColValue = new float[maxCol];
//	float startTime, endTime, timeElapsed;
	
	// Field related to font size
	float fontSize;
	
	// Field related to kanji-generator
	// As to why it is declared here an assigned within onCreate,
	//     see the explanation above for cols.
	// targetIndex is private since it is the secret correct answer to the game.
	private int targetIndex;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// Fields related to table dimensions and loops
		cols = this.getIntent().getIntExtra("cols", 7);
		int rows = cols;
		
		// Fields related to kanji-generator
		GenerateKanji g = new GenerateKanji();
		String[] kanji = g.fillArray(cols);
		String target = g.target;
		// It is not possible to access g.targetIndex directly within an inner
		//    class, so a second variable targetIndex is declared for this purpose.
		targetIndex = g.targetIndex;

		ActionBar actionBar = getActionBar();
		actionBar.show();
		
		LinearLayout theLayout = new LinearLayout(this);
		theLayout.setOrientation(LinearLayout.VERTICAL);
		
		TableLayout theDecoyTable = new TableLayout(this);
		
		// In order to decide font size, first find screen width, 
		// (see http://stackoverflow.com/a/1016941/621762)
		// then divide by (approx.) number of columns.
		Point screenDimensions = new Point();
		Display display = getWindowManager().getDefaultDisplay();
		display.getSize(screenDimensions);
		// Font size in pixels — this is more useful than sp.
		// We use cols + 1 in order to force room for an extra row at bottom.
		fontSize = screenDimensions.x/((float)cols+1);

		// Add views
		for (row_i = 0 ; row_i < rows ; row_i++) {
			// A TableRow's span can be declared explicitly, but here we allow
			//    the platform to arrange cells. The combination of the correct 
			//    number of cells and the correct font size ensures a square
			//    grid arrangement.
			TableRow theRow = new TableRow(this);
			for (col_i = 0 ; col_i < cols ; col_i++) {
				// As above, though we have two dimensions,  
				//    format as one-dimensional array and compute index.
				index = (row_i * cols) + col_i;

				TextView theView = new TextView(this);
				// The table cell will contain one of the decoys (or the target).
				theView.setText(kanji[index]);
				theView.setGravity(Gravity.CENTER);
				// We assign an ID to each view so that we can reformat some later.
				theView.setId(index);
				// We want to control absolute size, not sp!
				theView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
				theView.setClickable(true);
				theView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// If pressed, change color of kanji[g.targetIndex].
						// Change to red on black if mistaken.
						int indexClicked = v.getId();
						if (indexClicked != targetIndex) {
							((TextView) v).setTextColor(getResources().
									getColor(R.color.red));
							((TextView) v).setBackgroundColor(getResources().
									getColor(R.color.black));
						}
						else {
							// Change to blue if correct.
							((TextView) v).setTextColor(getResources().
									getColor(R.color.bluish));
							// In future: update score in ActionBar,
							//   wait two? seconds, then launch new activity.
						}
					}
				});
				theRow.addView(theView);
			}
			theDecoyTable.addView(theRow);
		}
		
		// Three-cell row for navigation.
//		TableLayout theBottom = (TableLayout) findViewById(R.id.table_layout2);
		TableLayout theNavigationTable = new TableLayout(this); 
		TableRow theBottomRow = new TableRow(this);

		Button rowsButton = new Button(this);
		rowsButton.setText("Settings");
		rowsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Simply close this screen.
				finish();
            }
		});
		TextView theTargetView = new TextView(this);;
		theTargetView.setText(target + "?");
		// We want the same size font as in the decoy grid itself.
		theTargetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
		theTargetView.setBackgroundColor(getResources().getColor(R.color.yellowish));
		theTargetView.setGravity(Gravity.CENTER);

		Button repopulateButton = new Button(this);
		repopulateButton.setText("New choices");
		repopulateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowDecoyTable.this, ShowDecoyTable.class);
				intent.putExtra("cols", cols);
				startActivity(intent);
				//    Note: the following line not always render this instance
				//    unreachable when the next instance has been instantiated.
				//    If the "New choices" button has been hit too many times in too short
				//    an interval, finish() s apparently never called and hitting the 
				//    back button retrieves a previous instantiation of ShowDecoyTable. 
				finish();
            }
        });
		theBottomRow.addView(rowsButton);
		theBottomRow.addView(theTargetView);
		theBottomRow.addView(repopulateButton);

		theDecoyTable.setStretchAllColumns(true);
		theLayout.addView(theDecoyTable);
		theNavigationTable.setStretchAllColumns(true);
		theNavigationTable.addView(theBottomRow);		
		theLayout.addView(theNavigationTable);
		
		// Start counting tries and measuring time here
//		this.numTriesPerColValue[cols]++;
//		startTime = System.currentTimeMillis();

		this.setContentView(theLayout);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
