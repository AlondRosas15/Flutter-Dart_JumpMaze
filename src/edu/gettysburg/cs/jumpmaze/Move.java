package edu.gettysburg.cs.jumpmaze;

public class Move {
	Node player;
	Node[][] maze;
	int count;

	public Move(Node[][] maze, Node p){
		this.maze = maze;
		player = p;
		count = 0;
	}

	//Checks to see if the player is at the goal
	//Given:void                
	//Return:boolean        Returns true if the player is at the goal
	public boolean isFinished(){
		//if the name of the node is goal, that means player is at goal
		if(player.isGoal){
			return true;
		}else{
			return false;
		}
	}

	//Checks to see if the move is legal
	//If move is valid:        Updates player to new position
	//                                        Sets the visited attribute to true
	//                                        Updates the count
	//Given:i                        Row position of desired move
	//                j                        Column position of desired move
	//Return:boolean        Returns true if the move is valid
	public boolean movePlayer(int i, int j){
		Node node = maze[i][j];
		if(player.legalMoves.contains(node)){
			player = node;
			player.visited = true;
			count++;
			//place holder code. 
			//checks if the player has won the game.
			//if the player wins, prints out message.
			if(isFinished()){
				System.out.println("YOU WIN!!");
				System.out.println("It took you " + count + " moves to complete the maze.");
				System.out.println("The shortest path was " + BreadthFirstSearch.getShortestPath(maze) + " moves.");                                
			}
			return true;
		}
		else{
			return false;
		}
	}
	//Returns the number to moves currently
	//Given:void                
	//Return:int        Returns the count for number of moves
	public int getMoves(){
		return count;
	}

	//Resets: the maze keeping the same maze
	//                  player back to original position (top, left corner)
	//                  the visited attribute of the nodes back to false
	//Given:Maze                a maze with jump values
	//Return:void
	public void reset(){        
		player = maze[0][0];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				maze[i][j].visited = false;
			}
		}
		player.visited = true;
		count = 0;
	}
}