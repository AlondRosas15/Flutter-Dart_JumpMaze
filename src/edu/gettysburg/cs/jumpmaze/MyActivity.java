package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MyActivity extends Activity {

	protected Maze maze;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maze = ((Maze) getApplication());
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if(maze.serviceRunning)
		{
			maze.pauseMusic();
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if(maze.serviceRunning)
		{
			maze.startMusic();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.maze, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.sound_options:
	            toSoundPage();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected void toHomePage(){
		Intent intent = new Intent(this, ActivitySplash.class);
		startActivity(intent);
	}

	protected void toSoundPage(){
		Intent intent = new Intent(this, SoundActivity.class);
		startActivity(intent);
	}
	
}
