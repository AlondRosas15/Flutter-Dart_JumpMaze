package edu.gettysburg.cs.jumpmaze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

	//Performs a Breadth-First-Search on the maze to find the shortest path
	//Given:Maze                a maze with jump values
	//Return:Node                Returns the goalNode
	public static Node bfs(Node[][] maze){
		Node goalNode = null;
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				Node node = maze[i][j];
				node.color = 1;
				node.parent = null;
				if(node.isGoal){
					goalNode = node;
				}
			}
		}
		Node startNode = maze[0][0];
		startNode.color = 2;
		startNode.parent = null;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(startNode);
		//assigns a parent to each node that is seen in the shortest path
		while(queue.size() != 0){
			Node u = queue.poll();
			for (int i = 0; i < u.legalMoves.size(); i++){
				Node v = u.legalMoves.get(i);
				if (v.color == 1){
					v.color = 2;
					v.parent = u;
					queue.add(v);
				}
			}
			u.color = 3;
		}
		return goalNode;
	}
	//Returns the length of the shortest path
	//Given:Maze                a maze with jump values
	//Return:int                Returns the length of the shortest path from starting position to the goal
	public static int getShortestPath(Node[][] maze){
		//LegalMoves.allLegalMoves(maze);
		Node goalNode = bfs(maze); //calls bfs to set up the path
		//if the goal node was never reached
		if(goalNode.color == 1){
			return -1;
		}else{ //there is a path to the goal node
			ArrayList<Node> stack = new ArrayList<Node>();
			Node node = goalNode;
			while(node != null){
				stack.add(node);
				node = node.parent;
			}                        
			return stack.size() - 1;
		}                
	}
}