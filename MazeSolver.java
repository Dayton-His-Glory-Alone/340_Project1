package maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MazeSolver {

    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Square> solve(Maze maze){
        //Create list of squares to visit next (FIFO)
        //Add the start square to the list to start the algorithm
        LinkedList<Square> nextToVisit = new LinkedList<>();
        nextToVisit.add(maze.getStartSquare());

        //List to keep track of which squares have been visited
        List<Square> visited = new ArrayList<>();

        while(!nextToVisit.isEmpty()){
            //Set current square as the bottom square off the list
            //Remove bottom square from list
            Square current = nextToVisit.remove();

            //Check if it's in bounds and hasn't been visited
            if(isInBounds(current, maze) && !visited.contains(current)){
                if(current.getMarker() == Marker.WALL){
                    visited.add(current);
                    continue;
                }

                else if(current.getMarker() == Marker.FINISH){
                    return backtrack(current);
                }

                //Loop through each of the 4 directions
                for(int[] direction : DIRECTIONS){
                    Square newSquare;

                    //If the move it is attempting is in bounds, assign newSquare to square at those coordinates
                    //Otherwise, continue to next iteration of for loop
                    if(checkNextMoveInBounds(current, direction, maze.getRows(), maze.getColumns()))
                        newSquare = maze.getSquareByCoordinate(current.getCoordinate().getX() + direction[0], current.getCoordinate().getY() + direction[1]);
                    else
                        continue;

                    //Dont add new square to list to visit if it is visited
                    if(!visited.contains(maze.getSquareByCoordinate(newSquare.getCoordinate().getX(), newSquare.getCoordinate().getY()))){
                        newSquare.setParent(current);
                        nextToVisit.add(newSquare);
                        visited.add(current);
                    }
                }
            }
        }

        return Collections.emptyList();
    }


    private List<Square> backtrack(Square s){
        List<Square> backtrackPath = new ArrayList<>();
        Square current = s;

        //Populate the backtrackPath with the current square's parent
        //Keep switching current to current.getParent() to eventually exit the loop
        while(current != null){
            backtrackPath.add(current);
            current = current.getParent();
        }

        return backtrackPath;
    }

    private boolean isInBounds(Square s, Maze m){
        if(s.getCoordinate().getX() < 0
                || s.getCoordinate().getX() > m.getRows()
                || s.getCoordinate().getY() < 0
                || s.getCoordinate().getY() > m.getColumns())
            return false;
        else
            return true;
    }

    private boolean checkNextMoveInBounds(Square s, int[] direction, int rows, int columns){
        if(s.getCoordinate().getX() + direction[0] < 0
                || s.getCoordinate().getX() + direction[0] > rows
                || s.getCoordinate().getY() + direction[1] < 0
                || s.getCoordinate().getY() + direction[1] > columns)
            return false;
        else
            return true;
    }
}
