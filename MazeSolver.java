package maze;
import java.util.ArrayList;
import java.util.Stack;

public class MazeSolver {

	Stack<Square> intersections = new Stack<>();

	//List of all the current paths we've found
	ArrayList<Stack<Square>> foundPaths = new ArrayList<>();
	
	

	public void solve(ArrayList<ArrayList<Square>> m, Square start) {
		
		//used for back tracking
		Stack<Square>currentPath = new Stack<Square>();

		boolean finishedBacktracking = false;
		
		//keep track of current square
		Square current = start;
				
		ArrayList<ArrayList<Square>> maze = setVisited(m);
		
		while (!current.getChar().equals("*")) {
			
			int i = current.x;
			int j = current.y;
							
			//current square coordinates (i,j)
			//i > 0 and j < maze.size() in order to stay in bounds

			//check north (i-1,j) (0,5)
			if ((maze.get(i-1).get(j).location.equals("open") && maze.get(i-1).get(j).visited == false) || maze.get(i-1).get(j).location.equals("finish")) {

				if(maze.get(i-1).get(j).location.equals("finish")) {
					currentPath.push(maze.get(i).get(j));
					currentPath.push(maze.get(i-1).get(j));
					foundPaths.add(currentPath);
					break;
				}

				findIntersections(maze, i, j);

				if(finishedBacktracking == false){
					//add current square to the stack so we can back track
					currentPath.push(current);
					current.visited = true;
				}
				else{
					finishedBacktracking = false;
				}

				current = maze.get(i-1).get(j);
			}	
			//check east (i, j+1) (1,6)
			else if ((maze.get(i).get(j+1).location.equals("open") && maze.get(i).get(j+1).visited == false) || maze.get(i).get(j+1).location.equals("finish")) {

				if(maze.get(i).get(j+1).location.equals("finish")) {
					currentPath.push(maze.get(i).get(j));
					currentPath.push(maze.get(i).get(j+1));
					foundPaths.add(currentPath);
					break;
				}

				if(finishedBacktracking == false){
					//add current square to the stack so we can back track
					currentPath.push(current);
					current.visited = true;
				}
				else{
					finishedBacktracking = false;
				}

				current = maze.get(i).get(j+1);
			}	
			//check west (i,j-1) (1,4)
			else if ((maze.get(i).get(j-1).location.equals("open") && maze.get(i).get(j-1).visited == false) || maze.get(i).get(j-1).location.equals("finish")) {

				if(maze.get(i).get(j-1).location.equals("finish")) {
					currentPath.push(maze.get(i).get(j));
					currentPath.push(maze.get(i).get(j-1));
					foundPaths.add(currentPath);
					break;
				}

				if(finishedBacktracking == false){
					//add current square to the stack so we can back track
					currentPath.push(current);
					current.visited = true;
				}
				else{
					finishedBacktracking = false;
				}

				current = maze.get(i).get(j-1);
			}
			//check south (i+1,j) (2,5)
			else if ((maze.get(i+1).get(j).location.equals("open") && maze.get(i+1).get(j).visited == false) || maze.get(i+1).get(j).location.equals("finish")) {

				if(maze.get(i+1).get(j).location.equals("finish")) {
					currentPath.push(maze.get(i).get(j));
					currentPath.push(maze.get(i+1).get(j));
					foundPaths.add(currentPath);
					break;
				}

				if(finishedBacktracking == false){
					//add current square to the stack so we can back track
					currentPath.push(current);
					current.visited = true;
				}
				else{
					finishedBacktracking = false;
				}

				current = maze.get(i+1).get(j);
			}
			else {
				while (!(currentPath.peek().x == intersections.peek().x) || !(currentPath.peek().y == intersections.peek().y)) {
					currentPath.pop();
				}

				current = currentPath.peek();
				finishedBacktracking = true;
			}				
		}

		printResult(currentPath);
  } // solve() function brace
	
	
	public void findIntersections(ArrayList<ArrayList<Square>> maze, int i, int j){
		int counter = 0;

		if ((maze.get(i-1).get(j).location.equals("open") && maze.get(i-1).get(j).visited == false))
			counter++;
		if ((maze.get(i).get(j+1).location.equals("open") && maze.get(i).get(j+1).visited == false))
			counter++;
		if ((maze.get(i).get(j-1).location.equals("open") && maze.get(i).get(j-1).visited == false))
			counter++;
		if ((maze.get(i+1).get(j).location.equals("open") && maze.get(i+1).get(j).visited == false))
			counter++;

		if(counter > 1){
			intersections.push(maze.get(i).get(j));
		}
	}
	
	public ArrayList<ArrayList<Square>> setVisited(ArrayList<ArrayList<Square>> maze){
		ArrayList<ArrayList<Square>> returnMaze = maze;
		
		for (int i = 0; i < returnMaze.size(); i++) {
			for (int j = 0; j < returnMaze.size(); j++) {
				if (returnMaze.get(i).get(j).getChar().equals(".")) {
					returnMaze.get(i).get(j).setVisited(false);
				}
			}
		}

		return returnMaze;
	}

//	public void findNewPaths(){
//
//	}

	public void printResult(Stack<Square> currentPath){

		System.out.println("Printing path from FINISH to START as coordinate pairs: ");
		int c = currentPath.size();
		for(int i = 0; i < c; i++){
			System.out.println("(" + currentPath.peek().x + ", " + currentPath.peek().y + ")");
			currentPath.pop();
		}
		System.out.println("\n\n");
	}
	
}//class brace
