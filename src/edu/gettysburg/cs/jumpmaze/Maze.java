package edu.gettysburg.cs.jumpmaze;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Maze {

	public Node[][] getMaze() throws IOException{
		Random rand = new Random();
		int randomNum = rand.nextInt(10000);
		RandomAccessFile raf = new RandomAccessFile("mazes.txt", "r");		
		Node[][] maze = new Node[5][5];
		//need to set the pointer to the correct position.
		int byteNum = 2 * (6 + (randomNum * 26));
		raf.seek(byteNum);
		char content;
		//initialize a maze starting at a certain byte in the file
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				content = raf.readChar();
				Node node = null;
				int num = (int) content;
				node.key = num;
				node.rowPos = i;
				node.colPos = j;
				maze[i][j] = node;
			}
		}
		return maze;
	}
	
	public Position getCurrentState(Node n){
		return new Position(n);
	}
	
	class Position{
		int r;
		int c;
		
		public Position(Node n){
			r = n.rowPos;
			c = n.colPos;
		}		
	}
}
