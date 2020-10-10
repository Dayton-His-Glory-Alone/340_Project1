package maze;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MazeSolver {

    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private List<Square> visitOrder;
    private List<Square> visited;

    public List<Square> solve(Maze maze) {
        //Create list of squares to visit next (FIFO)
        //Add the start square to the list to start the algorithm
        LinkedList<Square> nextToVisit = new LinkedList<>();
        nextToVisit.add(maze.getStartSquare());

        //List to keep track of which squares have been visited
        visited = new ArrayList<>();

        visitOrder = new ArrayList<>();

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

                else if(current.getMarker() == Marker.OPEN_SPACE){
                    if(!visitOrder.contains(current))
                        visitOrder.add(current);
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
                    if(!visited.contains(newSquare)){
                        newSquare.setParent(current);
                        nextToVisit.add(newSquare);
                        visited.add(current);

                        if(!visitOrder.contains(current))
                            visitOrder.add(current);
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

        //Returned list will not contain start and end nodes
        backtrackPath.remove(backtrackPath.size() - 1);
        backtrackPath.remove(0);

        return backtrackPath;
    }

    private boolean isInBounds(Square s, Maze m){
        if(s.getCoordinate().getX() < 0
                || s.getCoordinate().getX() > m.getColumns()
                || s.getCoordinate().getY() < 0
                || s.getCoordinate().getY() > m.getRows())
            return false;
        else
            return true;
    }

    private boolean checkNextMoveInBounds(Square s, int[] direction, int rows, int columns){
        if(s.getCoordinate().getX() + direction[0] < 0
                || s.getCoordinate().getX() + direction[0] >= columns
                || s.getCoordinate().getY() + direction[1] < 0
                || s.getCoordinate().getY() + direction[1] >= rows)
            return false;
        else
            return true;
    }

    public List<Square> getVisitOrder(){
        if(this.visitOrder == null)
            throw new NullPointerException("visitOrder is NULL");
        else{
            //Returned list won't contain start and end nodes
            this.visitOrder.remove(this.visitOrder.size() - 1);
            this.visitOrder.remove(0);

            return this.visitOrder;
        }
    }
}
