package edu.gettysburg.cs.jumpmaze;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MazeActivity extends Activity {
	//test comment 2

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze, menu);
		return true;
	}

}
