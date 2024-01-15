package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TutorialActivityNote extends MyActivity {

	//Buttons that move back and forth in the tutorial
	private ImageButton homeButton;
	private ImageButton backButton;
	private Button nextButton;

	//Keeps track of which page of the tutorial you're on
	private final static int thisPage = 5;
	private int nextPage = 5;
	private Maze maze;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial_note);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		homeButton = (ImageButton) findViewById(R.id.home);
		backButton = (ImageButton) findViewById(R.id.back_tutorial);
		nextButton = (Button) findViewById(R.id.next_tutorial);
		maze = ((Maze) getApplication());

		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nextPage--;
				toPage();
			}
		});

		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toHomePage();
			}
		});

		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nextPage++;
				toPage();
			}
		});

		//https://github.com/tneller/jump-maze-android.git
	}


	private void toPage(){
		if(nextPage < thisPage){
			Intent intent = new Intent(this, TutorialActivity4.class);
			startActivity(intent);
		}
		else if(nextPage > thisPage){
			Intent intent = new Intent(this, MazeActivity.class);
			startActivity(intent);
		}
	}
}