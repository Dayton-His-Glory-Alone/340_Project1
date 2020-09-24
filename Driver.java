package maze;

public class Driver {

	public static void main(String[] args) {
		
		MazeReader mReader = new MazeReader();
		MazeSolver solver = new MazeSolver();
		
		//on sampleinput1.txt, maze starts at (1,5)
		//on sampleinput2.txt maze starts at (8,1)	
		
		//on sampleinput1.txt, MazeSolver should print (2,1)
		//on sampleinput2.txt, MazeSolver should print (4,6)
		
		solver.solve(mReader.maze, mReader.getStartSquare());

		System.out.println("Original Maze: ");
		mReader.toString();		
	}
}
