package edu.gettysburg.cs.jumpmaze;

import java.util.ArrayList;

public class Node{		
	Node parent;
	ArrayList<Node> legalMoves;
	int key;
	int color;
	boolean visited;
	int rowPos;
	int colPos;

	public Node(){
		parent = null;
		legalMoves = new ArrayList<Node>();
		key = -1;
		color = 0;		
		visited = false;
		rowPos = -1;
		colPos = -1;		
	}
}
