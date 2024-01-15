package edu.gettysburg.cs.jumpmaze;

public class LegalMoves {
	static Node goalNode;

	//Adds all legal moves to the arrayList legalMoves associated with each node.
	//Given:i			the row position of the node
	//		j			the column position of the node
	//		node		the node at the given position
	public static Node allLegalMoves(Node[][] maze){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				Node node = maze[i][j];
				int nodeKey = node.key;
				if (nodeKey == 0){
					goalNode = node;
				}else{
					if (i+nodeKey < 5){
						node.legalMoves.add(maze[i + nodeKey][j]);
					}if (j + nodeKey < 5){
						node.legalMoves.add(maze[i][j + nodeKey]);
					}if (i - nodeKey >= 0){
						node.legalMoves.add(maze[i - nodeKey][j]);
					}if (j - nodeKey >= 0){
						node.legalMoves.add(maze[i][j - nodeKey]);
					}
				}
			}
		}
		return goalNode;
	}

}
