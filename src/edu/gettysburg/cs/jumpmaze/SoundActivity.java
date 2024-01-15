package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SoundActivity extends MyActivity  {
	private Maze maze;

	private CheckBox musicCheckBox;
	private CheckBox soundCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sounds);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		maze = (Maze) getApplication();

		musicCheckBox = (CheckBox) findViewById(R.id.backgroundMusic);
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
					//Toast.makeText(SoundActivity.this, "Music turned OFF", Toast.LENGTH_SHORT).show();
				}
				else
				{
					maze.startMusic();
					//Toast.makeText(SoundActivity.this, "Music turned ON", Toast.LENGTH_SHORT).show();
				}
			}
		});

		soundCheckBox = (CheckBox) findViewById(R.id.soundFX);
		if(maze.effectsOn)
		{
			soundCheckBox.setChecked(true);
		}

		soundCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(!isChecked)
				{
					maze.effectsOn = false;
					//Toast.makeText(SoundActivity.this, "Sound Effects turned OFF", Toast.LENGTH_SHORT).show();
				}
				else
				{
					maze.effectsOn = true;
					//Toast.makeText(SoundActivity.this, "Sound Effects turned ON", Toast.LENGTH_SHORT).show();
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
}