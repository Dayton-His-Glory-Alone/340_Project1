package maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * IT 340 Project 01
 * 
 * @author Seth, James, and Dayton
 *
 */
public class MazeReader {

	public static Maze readMaze(){
		//Initialize the Maze we will return
		Maze returnMaze;

		//Coordinates for the starting and ending points of the maze
		Coordinate startCoordinate = null;
		Coordinate endCoordinate = null;

		//Initialize the temporary ArrayList we will add to
		List<List<Square>> tempList = new ArrayList<>();

		try{
			//Read in file name from console and attempt to open file
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the input file name: ");
			String fileName = sc.nextLine();
			BufferedReader input = new BufferedReader(new FileReader(fileName));

			//Create counter variables to keep track of X and Y coordinates
			//We assume the TOP LEFT corner is (0, 0) and the BOTTOM RIGHT corner is (max, max)
			int xCounter = 0;
			int yCounter = 0;

			//Temporary Square object and attributes that will be later used to add object to list
			Square tempSquare;
			Marker tempMarker;
			List<Square> row;

			//Loop while there isn't a null line in the file
			String strLine = input.readLine();
			while(strLine != null){
				//List to read each character in line
				String[] arrLine = strLine.split("");

				//Initialize the row that will be added to the 2d list
				row = new ArrayList<>();

				//Loop through each character in current line
				for(String s : arrLine){
					//Check for which type the character is
					switch (s) {
						case "#":
							tempMarker = Marker.WALL;
							break;
						case ".":
							tempMarker = Marker.OPEN_SPACE;
							break;
						case "o":
							tempMarker = Marker.START;
							startCoordinate = new Coordinate(xCounter, yCounter);
							break;
						case "*":
							tempMarker = Marker.FINISH;
							endCoordinate = new Coordinate(xCounter, yCounter);
							break;
						default:
							//If these variables are never assigned, fail and assign as null so method can finish
							tempMarker = null;
							break;
					}

					//Create the Square object with the information we found and add it to the list
					//Add the Square to the Row list
					tempSquare = new Square(tempMarker, new Coordinate(xCounter, yCounter));
					row.add(tempSquare);

					//Increment the x counter
					xCounter++;
				}

				//Add the finished row to the 2d list
				tempList.add(row);

				//Increment the Y counter and reset the X counter
				yCounter++;
				xCounter = 0;

				//Move to next line of file
				strLine = input.readLine();
			}

			sc.close();
			input.close();
			returnMaze = new Maze(tempList, startCoordinate, endCoordinate);
			return returnMaze;
		}
		catch(Exception e){
			System.out.println("Error reading in file\n");
			e.printStackTrace();
			return null;
		}
	}
}