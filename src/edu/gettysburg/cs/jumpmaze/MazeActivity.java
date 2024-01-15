package edu.gettysburg.cs.jumpmaze;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MazeActivity extends MyActivity {
	/**
	 * 2D array that holds the association of buttons for the maze.
	 */
	private Button[][] buttons;

	/**
	 * Allows for the access of new random mazes for the GUI
	 */
	private Maze maze;

	/**
	 * 2D array that holds the numbers for the maze.
	 */
	private Node[][] currentMaze;

	/**
	 * A variable that keeps track of the players progress and moves in current game
	 */
	private Move game;

	/**
	 * keeps track of the current node the player is on
	 */
	private Node playerNode;
	
	/**
	 * keeps track of the last node the player was on for drawing purposes
	 */
	private Node oldNode;

	/**
	 * A button that resets the current maze
	 */
	private ImageButton resetButton;


	/**
	 * A button that goes back to home page
	 */
	private ImageButton homeButton;

	/**
	 * A button that moves on to the next maze
	 */
	private ImageButton nextMazeButton;

	/**
	 * A button that moves on to sound settings
	 */
	private ImageButton soundButton;

	/**
	 * TextView that keeps track of the number of moves
	 */
	TextView moveView;

	/**
	 * variable that keeps track of number of moves
	 */
	int moves;

	/**
	 * SoundPool that keeps track of sound
	 */
	static SoundPool soundPool;

	/**
	 * variables that keeps track of sound id linking to the different sounds
	 */
	private int clickId;

	private int winId;

	private int wrongId;

	/**
	 * variable that keeps track of whether or not a sounded is loaded onto the system
	 */
	boolean loaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// Initializes Maze so that new mazes can be generated.
		maze = ((Maze) getApplication());

		// Initializes buttons with 5 rows and 5 columns.


		//sets up the buttons and their listeners
		setupButtonsArray();


		// Calls the method that will create new mazes
		makeNewMaze();

		game = new Move(currentMaze, currentMaze[0][0] );

		resetButton = (ImageButton) findViewById(R.id.reset_maze);
		nextMazeButton = (ImageButton) findViewById(R.id.next_maze_button);
		homeButton = (ImageButton) findViewById(R.id.home_button);
		soundButton = (ImageButton) findViewById(R.id.sound_button);



		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder isSure = new AlertDialog.Builder(MazeActivity.this);
				isSure.setMessage("Are you sure you want to reset the maze?");
				isSure.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						game.reset();
						playerNode = game.player;
						drawMaze();
						moves = game.count;
						moveView.setText("Moves: " + moves);
					}
				});
				isSure.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {  }
				});
				isSure.create();
				isSure.show();


			}
		});

		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toHomePage();
			}
		});

		nextMazeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder isSure = new AlertDialog.Builder(MazeActivity.this);
				isSure.setMessage("Are you sure you want a new maze?");
				isSure.setPositiveButton("New Maze!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						makeNewMaze();
						game = new Move(currentMaze, currentMaze[0][0]);
						moves = game.count;
						moveView.setText("Moves: " + moves);
					}
				});
				isSure.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {  }
				});
				isSure.create();
				isSure.show();

			}
		});

		soundButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toSoundPage();
			}
		});

		moveView = (TextView) findViewById(R.id.moves);


		// Set the hardware buttons to control the music
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// Load the sound
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});

		clickId = soundPool.load(this, R.raw.click, 1);
		wrongId = soundPool.load(this, R.raw.wrong, 1);
		winId = soundPool.load(this, R.raw.win, 1);
	}

	

	private void makeMove(int row, int col)
	{
		boolean moved = game.movePlayer(row, col);

		if(moved)
		{
			// Getting the user sound settings
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			if (loaded) {
				if(maze.effectsOn)
				{
					soundPool.play(clickId, volume, volume, 1, 0, 1f);
				}
			}
			//Toast.makeText(MazeActivity.this, "Valid Move", Toast.LENGTH_SHORT).show();
		}
		else
		{
			// Getting the user sound settings
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			// Is the sound loaded already?
			if (loaded) {
				if(maze.effectsOn)
				{
					soundPool.play(wrongId, volume, volume, 1, 0, 1f);
				}
			}
			//Toast.makeText(MazeActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
		}
		
		oldNode = playerNode;
		playerNode = game.player;

		moves = game.count;

		moveView.setText("Moves: " + moves);

		//drawMaze();
		drawMazeButton(oldNode.rowPos, oldNode.colPos);
		drawMazeButton(playerNode.rowPos, playerNode.colPos);
		
		if(game.isFinished())
		{
			if(maze.serviceRunning)
			{
				maze.pauseMusic();
			}
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			// Is the sound loaded already?
			if (loaded) {
				if(maze.effectsOn)
				{
					soundPool.play(winId, volume, volume, 1, 0, 1f);
				}
			}
			AlertDialog.Builder won = new AlertDialog.Builder(MazeActivity.this);
			int shortest = BreadthFirstSearch.getShortestPath(currentMaze);
			if(moves != shortest)
			{
				won.setMessage("You solved the puzzle in " + moves + " moves. The shortest route was " + shortest +".");
			}
			else
			{
				won.setMessage("You solved the puzzle in " + moves + " moves. That is the shortest route! Good job!");
			}
			won.setPositiveButton("Next Maze", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {
					if(maze.serviceRunning)
					{
						maze.startMusic();
					}
					makeNewMaze();
					game = new Move(currentMaze, currentMaze[0][0]);
					moves = game.count;
					moveView.setText("Moves: " + moves);
				}
			});
			won.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					if(maze.serviceRunning)
					{
						maze.startMusic();
					}
					game.reset();
					playerNode = game.player;
					drawMaze();
					moves = game.count;
					moveView.setText("Moves: " + moves);
				}
			});
			won.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					if(maze.serviceRunning)
					{
						maze.startMusic();
					}
					game.reset();
					playerNode = game.player;
					drawMaze();
					moves = game.count;
					moveView.setText("Moves: " + moves);
				}
			});
			won.create();
			won.show();
		}
	}



	/**
	 * Generates the maze and sets the text on the buttons to be of values generated.
	 *                 Currently not using the Maze class because of issues with finding mazes.txt
	 */
	private void makeNewMaze()
	{
		try {
			//Sets a maze to the new maze being generated.
			currentMaze = maze.getMaze();
			playerNode = currentMaze[0][0];

			drawMaze();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void drawMaze()
	{
		// Iterates though each button and sets the text and color on it.
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons.length; j++)
			{
				drawMazeButton(i,j);
			}
		}

	}
	
	@SuppressWarnings("deprecation")
	private void drawMazeButton(int i, int j)
	{
		// Current node value that is being set on the button.
		Node currNode = currentMaze[i][j];
		// Current button who's value is being set.
		Button currButton = buttons[i][j];

		//1
		if(currNode.key == 1)
		{
			if(currNode == playerNode)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_black_1));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_gray_1));
				}
			}
			else if(maze.hints == true && currNode.visited == true)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_black_1));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_gray_1));
				}
			}
			else
			{
				if ((i+j) % 2 == 0)
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_1));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_1));
				}
			}
		}
		//2
		else if(currNode.key == 2)
		{
			if(currNode == playerNode)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_black_2));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_gray_2));
				}
			}
			else if(maze.hints == true &&  currNode.visited == true)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_black_2));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_gray_2));
				}
			}
			else
			{
				if ((i+j) % 2 == 0)
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_2));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_2));
				}
			}
		}
		//3
		else if(currNode.key == 3)
		{
			if(currNode == playerNode)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_black_3));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_gray_3));
				}
			}
			else if(maze.hints == true &&  currNode.visited == true)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_black_3));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_gray_3));
				}
			}
			else
			{
				if ((i+j) % 2 == 0)
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_3));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_3));
				}
			}
		}
		//4
		else if(currNode.key == 4)
		{
			if(currNode == playerNode)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_black_4));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_gray_4));
				}
			}
			else if(maze.hints == true && currNode.visited == true)
			{
				if ((i+j) % 2 == 0) 
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_black_4));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_gray_4));
				}
			}
			else
			{
				if ((i+j) % 2 == 0)
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.black_4));
				}
				else
				{
					currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_4));
				}
			}
		}
		else
		{
			if ((i+j) % 2 == 0) {
				//currButton.setText("");
				Drawable d = getResources().getDrawable(R.drawable.g_black);
				currButton.setBackgroundDrawable(d);
				//currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
			}
			else {
				//currButton.setText("");
				Drawable d = getResources().getDrawable(R.drawable.g_gray);
				currButton.setBackgroundDrawable(d);
				//currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
			}
		}
	}

	

	public void setupButtonsArray()
	{
		buttons = new Button[5][5];
		/*
		 * Creates a button for each associated spot in the GUI (see activity_maze.xml). 
		 *   Looks up each button id and creates a button for it in the buttons array.
		 */
		buttons[0][0] = (Button) findViewById(R.id.Button1);
		buttons[0][1] = (Button) findViewById(R.id.Button2);
		buttons[0][2] = (Button) findViewById(R.id.Button3);
		buttons[0][3] = (Button) findViewById(R.id.Button4);
		buttons[0][4] = (Button) findViewById(R.id.Button5);

		buttons[1][0] = (Button) findViewById(R.id.Button6);
		buttons[1][1] = (Button) findViewById(R.id.Button7);
		buttons[1][2] = (Button) findViewById(R.id.Button8);
		buttons[1][3] = (Button) findViewById(R.id.Button9);
		buttons[1][4] = (Button) findViewById(R.id.Button10);

		buttons[2][0] = (Button) findViewById(R.id.Button11);
		buttons[2][1] = (Button) findViewById(R.id.Button12);
		buttons[2][2] = (Button) findViewById(R.id.Button13);
		buttons[2][3] = (Button) findViewById(R.id.Button14);
		buttons[2][4] = (Button) findViewById(R.id.Button15);

		buttons[3][0] = (Button) findViewById(R.id.Button16);
		buttons[3][1] = (Button) findViewById(R.id.Button17);
		buttons[3][2] = (Button) findViewById(R.id.Button18);
		buttons[3][3] = (Button) findViewById(R.id.Button19);
		buttons[3][4] = (Button) findViewById(R.id.Button20);

		buttons[4][0] = (Button) findViewById(R.id.Button21);
		buttons[4][1] = (Button) findViewById(R.id.Button22);
		buttons[4][2] = (Button) findViewById(R.id.Button23);
		buttons[4][3] = (Button) findViewById(R.id.Button24);
		buttons[4][4] = (Button) findViewById(R.id.Button25);

		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons.length; j++)
			{
				buttons[i][j].setOnClickListener(new MazeButtonListener(i,j));
			}
		}

	}

	private class MazeButtonListener implements View.OnClickListener
	{
		int row;
		int col;

		public MazeButtonListener(int row, int col)
		{
			this.row = row;
			this.col = col;
		}

		@Override
		public void onClick(View v) {
			//SystemClock.sleep(5000); if you want to sleep
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			makeMove(row, col);
		}

	}
	
	

}