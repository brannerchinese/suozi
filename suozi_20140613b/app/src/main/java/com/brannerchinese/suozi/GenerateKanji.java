package com.brannerchinese.suozi;

//GenerateKanji.java
//David Prager Branner
//20121114, works.
//Contains methods related to generating random characters.

import java.util.Random;

public class GenerateKanji {

	// Fields related to table dimensions and loops
	public int cols, rows;
	public int col_i, row_i, index;
	
	// Fields related to the generation of characters
	public Random generator = new Random( System.currentTimeMillis() );
	// "target" is the actual target character
	public String target;
	// "targetIndex" is the index number of "target" in "kanji"
	// It is protected, since it is the secret answer to the game.
	protected int targetIndex;
	// "kanji" is the array holding the list of characters
	public String[] kanji = new String[cols*rows];
	
	// Fields related to testing; not used in Suozi project
	public String clicked;
	public int numbKanji = 0;

	
	public char generateRandKanji() {
		// Generate a random integer and from it call codepointToChar.
		
		// 1. choose random integer in range of Chinese codepoints
		// range of normal CJK is hex 3400 to 9fc6, or decimal 13312 to 40902
		// But actually those below hex 4E00 (decimal 19968) are all rare
		// moreover, those above 9fb4 (dec. 40884) are also relatively rare
		// However, we will use both planes and then remove a few.
//		int min = 13312, max= 40884, randCodepoint;
		int min = 19968, max= 40884, randCodepoint;

		randCodepoint = generator.nextInt(max - min + 1) + min;

		// (The following block is for use when min = 13312.)
//		while (true) {
//			// Generate random codepoint between these bounds
//			// (http://stackoverflow.com/a/363692/621762, accessed 20121113)
//			randCodepoint = generator.nextInt(max - min + 1) + min;
//			// Discard those that do not appear in the Android font
//			if ( randCodepoint < 19894  || randCodepoint >= 19968 ) {
//				break;
//			}
//		}

		// 2. convert to char
		return codepointToChar( randCodepoint );
	}
	
	public char codepointToChar(int iCodepoint){
		// Given a decimal codepoint, return a char containing the kanji.
		
		// 1. convert to hex string (http://stackoverflow.com/a/516614; accessed 20121113)
		// and then append UTF prefix 
		String uCodepoint = "\\u" + Integer.toHexString( iCodepoint );

		// 2. "convert" to char (http://stackoverflow.com/a/2126394 ; accessed 20121113)
		// The following fails because \\u is not recognized as the UTF prefix
		//     char theKanji = uCodepoint.toCharArray()[0];
		// Instead, do more complex substring process
		// (http://stackoverflow.com/a/2126404 ; accessed 20121113)
		char theKanji = (char) Integer.parseInt( uCodepoint.substring(2), 16 );
		return theKanji;
	}
	
	public String[] fillArray(int cols) {
		// Generate array of random characters
		//   and also set target and targetIndex
		rows = cols;
		String[] kanji = new String[cols*rows];
		for (row_i = 0 ; row_i < rows ; row_i++) {
			for (col_i = 0 ; col_i < cols ; col_i++) {
				// Though we have two dimensions, format 
				//    as one-dim. array and compute index.
				index = (row_i * cols) + col_i;
				final char currentKanji = generateRandKanji();
				kanji[index] = String.valueOf(currentKanji);
			}
		}
		this.targetIndex = generator.nextInt(cols*cols);
		this.target = kanji[targetIndex];
		return kanji;
	}

// The methods below are not used in the Suozi project
// and are not shown in the UML diagrams.
	
	public void avoidSpecialTzyh(char tzyhToAvoid) {
		// Not used.
		// Generate random characters until the specified one is reached.
		
		// 1. Print the one to avoid.
		System.out.println("Random character: " + tzyhToAvoid + "\n");
		// 2. Set counter, to avoid "Reveal end of document" error in Eclipse.
		int counter = 0;
		// 3. Run loop until specified character is reached.
		while (true) {
			char tempTzyh = generateRandKanji();
			if (tempTzyh != tzyhToAvoid) {
				System.out.print(tempTzyh);
				counter++;
				if (counter % 10 == 0) {
					System.out.print("\n");
				}
				if (counter == numbKanji) {
					break;
					// with counter not set, = 0, never breaks until tzyh re-encountered.
				}
			}
			else {
				System.out.println("\n\n" + tzyhToAvoid + " generated at counter = " +
						counter + "; breaking now.");
				break;
			}
		}
	}
		
	public boolean checkIfMatch(String aKanji) {
		// Not used.
		return ( target == clicked );
	}

	public String getTarget(int cols) {
		// chooses a random index from the actual array
		// Not used.
		return fillArray(cols)[generator.nextInt(cols*cols)];
	}
	
}
