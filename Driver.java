package maze;

import java.util.Collections;
import java.util.List;

public class Driver {

	public static void main(String[] args) {

		Maze maze = MazeReader.readMaze();
		MazeSolver solver = new MazeSolver();
		
		//on sampleinput1.txt, maze starts at (1,5)
		//on sampleinput2.txt maze starts at (8,1)	
		
		//on sampleinput1.txt, MazeSolver should print (2,1)
		//on sampleinput2.txt, MazeSolver should print (4,6)

		System.out.println(maze.toString());
		List<Square> shortestPath = solver.solve(maze);
		Collections.reverse(shortestPath);

		for(Square s : shortestPath){
			System.out.println(s.printCoordinate());
		}
	}
}
