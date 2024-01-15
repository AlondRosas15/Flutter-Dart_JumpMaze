package edu.gettysburg.cs.jumpmaze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

	public void bfs(Node[][] maze){		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				Node node = maze[i][j];
				node.color = 0;
				node.parent = null;
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
	}
	public int getShortestPath(Node[][] maze){
		Node goalNode = LegalMoves.allLegalMoves(maze);
		bfs(maze); //calls bfs to set up the path
		if(goalNode.color == 1){
			return -1;
		}else{
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

