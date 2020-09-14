package maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */

/**
 * IT 340 Project 01
 * 
 * @author Seth, James, and Dayton
 *
 */
public class MazeReader {

	//Using ArrayList instead of 2d Array to account for varying maze sizes
	ArrayList<ArrayList<Marker>> maze = new ArrayList<>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MazeReader mazeReader = new MazeReader();
	}

	/**
	 * Seth--> read the file
	 */
	public MazeReader() {
		try{
			//Read in file name from console and attempt to open file
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the input file name: ");
			String fileName = sc.nextLine();
			BufferedReader input = new BufferedReader(new FileReader(fileName));

			//Loop while there isn't a null line in the file
			String strLine = input.readLine();
			while(strLine != null) {
				ArrayList<Marker> mazeLine = new ArrayList<>();
				String[] arrLine = strLine.split("");

				//Loop through each character of the String[]
				for(String s : arrLine) {
					if(s.equals("#"))
						mazeLine.add(Marker.WALL);
					else if(s.equals("."))
						mazeLine.add(Marker.OPEN_SPACE);
					else if(s.equals("o"))
						mazeLine.add(Marker.START);
					else if(s.equals("*"))
						mazeLine.add(Marker.FINISH);
				}

				//Add line to maze object
				maze.add(mazeLine);

				// Increment to next line of text file
				strLine = input.readLine();
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

	/**
	 * ToString toString method that makes a String representation of this Maze in
	 * the same format as the input.
	 * 
	 */
	public String toString() {
		
		//print the matrix
		
		System.out.println();
		return null;

	}

	/**
	 * toString method that returns a one-character-long string containing only the
	 * character corresponding to a location
	 */
	public String toString1() {
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
			obj = new Square(ch);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return obj;
	}

}
