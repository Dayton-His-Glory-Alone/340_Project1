package maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * IT 340 Project 01
 * 
 * @author Seth, James, and Dayton
 *
 */
public class MazeReader {

	//Using ArrayList instead of 2d Array to account for varying maze sizes
	
	//ArrayList<ArrayList<Marker>> maze;
	public ArrayList<ArrayList<Square>> maze;
	public Square start;
	
	
	/**
	 * Seth--> read the file
	 */
	public MazeReader() {
		try {
			//create maze in constructor
			maze = new ArrayList<>();
			
			//Read in file name from console and attempt to open file
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the input file name: ");
			String fileName = sc.nextLine();
			BufferedReader input = new BufferedReader(new FileReader(fileName));

			//Loop while there isn't a null line in the file
			String strLine = input.readLine();
			int i = 0;
			while(strLine != null) {
				//ArrayList<Marker> mazeLine = new ArrayList<>();
				ArrayList<Square> mazeLine = new ArrayList<>();
				String[] arrLine = strLine.split("");

				//Loop through each character of the String[]
				for(int j = 0; j < strLine.length(); j++) {
					Square sq;
					if(strLine.charAt(j) == '#') {
						//mazeLine.add(Marker.WALL);
						sq = new Square('#', i, j);
						mazeLine.add(sq);
					}
					else if(strLine.charAt(j) == '.') {
						//mazeLine.add(Marker.OPEN_SPACE);
						sq = new Square('.', i, j);
						mazeLine.add(sq);
					}
					else if(strLine.charAt(j)== 'o') {
						// mazeLine.add(Marker.START);
						sq = new Square('o', i, j);
						mazeLine.add(sq);
						start = new Square('o', i, j);
					}
					else if(strLine.charAt(j) == '*') {
						//mazeLine.add(Marker.FINISH);
						sq = new Square('*',i, j);
						mazeLine.add(sq);
					}
				}

				//Add line to maze object
				maze.add(mazeLine);

				// Increment to next line of text file
				strLine = input.readLine();
				i++;
			}

			//Close the scanners
			sc.close();
			input.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
//	Walls	# (hash mark)
//	Open spaces	. (period)
//	Start	o (lower case ‘O’)
//	Finish (or goal)	* (asterisk)

	/*
	 * ToString toString method that makes a String representation of this Maze in
	 * the same format as the input.
	 */
	public String toString() {
		
		//array lists of array lists 
		for (int i = 0; i < maze.size(); i++) {	
			//maze.get(i) returns an array list. j loops through that array list's elements fetching a Square object from each element. For each Square object, the toString() prints out the Square in it's character formation (i.e. *, #, o, .)
			for (int j = 0; j < maze.get(i).size(); j++) {		
				System.out.print(maze.get(i).get(j));
			}
			System.out.print("\n");
		}
		return null;
	}

	public Square getStartSquare() {
		return start;
	}
	
	
	/**
	 * toString method that returns a one-character-long string containing only the
	 * character corresponding to a location
	 */
	public String toString1() {
		
		// I think this is supposed to be in handled in Square class?	
		return null;
	}

	/**
	 * returns the type of square associated with the given char
	 * 
	 * @return char
	 */
	public static Square fromChar(char ch) {
		Square obj = null;
		try {
			obj = new Square(ch, 0, 0);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return obj;
	}
	


	
	

}