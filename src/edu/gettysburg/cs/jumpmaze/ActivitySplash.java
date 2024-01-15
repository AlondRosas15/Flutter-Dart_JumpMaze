package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ActivitySplash extends MyActivity  {

	/**
	 * A button that starts the maze game
	 */
	private Button startMazeButton;
	private Button tutorialButton;
	private Button aboutButton;

	private CheckBox hintCheckBox;
	private CheckBox musicCheckBox;
	private CheckBox soundCheckBox;

	private Maze maze;

	static MediaPlayer mp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		maze = (Maze) getApplication();

		startMazeButton = (Button) findViewById(R.id.start_jump_maze_game);
		startMazeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toMazePage();
			}
		});

		tutorialButton = (Button) findViewById(R.id.tutorial_splash);
		tutorialButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toTutorial();
			}
		});

		aboutButton = (Button) findViewById(R.id.about_splash);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toAbout();
			}
		});



		hintCheckBox = (CheckBox) findViewById(R.id.hint_splash);
		if(maze.hints)
		{
			hintCheckBox.setChecked(true);
		}

		hintCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					maze.hints = true;
				}
				else
				{
					maze.hints = false;
				}
			}
		});

		musicCheckBox = (CheckBox) findViewById(R.id.background_music_splash);

		if(maze.serviceRunning)
		{
			musicCheckBox.setChecked(true);
		}
		else
		{
			musicCheckBox.setChecked(false);
		}

		musicCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(!isChecked)
				{
					maze.stopMusic();
					//Toast.makeText(ActivitySplash.this, "Music turned OFF", Toast.LENGTH_SHORT).show();
				}
				else
				{
					maze.startMusic();
					//Toast.makeText(ActivitySplash.this, "Music turned ON", Toast.LENGTH_SHORT).show();
				}
			}
		});

		soundCheckBox = (CheckBox) findViewById(R.id.sound_FX_splash);
		if(maze.effectsOn)
		{
			soundCheckBox.setChecked(true);
		}
		else
		{
			soundCheckBox.setChecked(false);
		}

		soundCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					maze.effectsOn = true;
				}
				else
				{
					maze.effectsOn = false;
				}
			}
		});

	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		if(maze.serviceRunning)
		{
			musicCheckBox.setChecked(true);
		}
		else
		{
			musicCheckBox.setChecked(false);
		}
		
		if(maze.effectsOn)
		{
			soundCheckBox.setChecked(true);
		}
		else
		{
			soundCheckBox.setChecked(false);
		}
	}

	private void toMazePage(){
		Intent intent = new Intent(this, MazeActivity.class);
		startActivity(intent);
	}


	private void toTutorial(){
		Intent intent = new Intent(this, TutorialActivity1.class);
		startActivity(intent);
	}

	private void toAbout(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}





}
