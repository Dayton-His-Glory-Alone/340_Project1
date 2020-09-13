package maze;
import java.util.Scanner;

/**
 * 
 */

/**
 * 340 Progject01
 * 
 * @author Dayton
 *
 */
public class MazeReader {

	char matrix [][];
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * Seth--> read the file
	 * @param scan
	 */
	public MazeReader(Scanner scan) {
		
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
