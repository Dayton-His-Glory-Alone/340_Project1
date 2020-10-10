package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Maze {
    //2d list of Square type to represent maze
    List<List<Square>> maze;

    //Start and End coordinate attributes
    Coordinate startCoordinate;
    Coordinate endCoordinate;

    //List of all squares visited and list of shortest path, respectively
    private List<Square> visitOrder;
    private List<Square> shortestPath;

    public Maze(List<List<Square>> m, Coordinate s, Coordinate e){
        this.maze = m;
        this.startCoordinate = s;
        this.endCoordinate = e;
    }

    public Square getSquareByCoordinate(int x, int y){
        for(List<Square> l : maze){
            for(Square s : l){
                if(s.getCoordinate().equals(new Coordinate(x, y)))
                    return s;
            }
        }

        return null;
    }

    public String toString(){
        StringBuilder returnString = new StringBuilder();

        for(List<Square> l : maze){
            StringBuilder row = new StringBuilder();

            for(Square s : l){
                row.append(s.getChar());
            }

            returnString.append(row);
            returnString.append("\n");
        }
        return returnString.toString();
    }

    public Square getStartSquare(){
        return getSquareByCoordinate(this.startCoordinate.getX(), this.startCoordinate.getY());
    }

    public int getRows(){
        return this.maze.size();
    }

    public int getColumns(){ return this.maze.get(0).size(); }

    public void solve(){
        //Make sure maze object has been populated
        if(this.maze == null)
            throw new NullPointerException("solve() method called before maze object was populated");


        //Create list of directions to check
        final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

        //Create list of squares to visit next (FIFO)
        //Add the start square to the list to start the algorithm
        LinkedList<Square> nextToVisit = new LinkedList<>();
        nextToVisit.add(this.getStartSquare());

        //List to keep track of which squares have been visited
        List<Square> visited = new ArrayList<>();

        visitOrder = new ArrayList<>();

        while(!nextToVisit.isEmpty()){
            //Set current square as the bottom square off the list
            //Remove bottom square from list
            Square current = nextToVisit.remove();

            //Check if it's in bounds and hasn't been visited
            if(isInBounds(current) && !visited.contains(current)){
                if(current.getMarker() == Marker.WALL){
                    visited.add(current);
                    continue;
                }

                else if(current.getMarker() == Marker.FINISH){
                    backtrack(current);
                    break;
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
                    if(checkNextMoveInBounds(current, direction))
                        newSquare = this.getSquareByCoordinate(current.getCoordinate().getX() + direction[0], current.getCoordinate().getY() + direction[1]);
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
    }

    private boolean isInBounds(Square s){
        return s.getCoordinate().getX() >= 0
                && s.getCoordinate().getX() <= this.getColumns()
                && s.getCoordinate().getY() >= 0
                && s.getCoordinate().getY() <= this.getRows();
    }

    private void backtrack(Square s){
        List<Square> backtrackPath = new ArrayList<>();
        Square current = s;

        //Populate the backtrackPath with the current square's parent
        //Keep switching current to current.getParent() to eventually exit the loop
        while(current != null){
            backtrackPath.add(current);
            current = current.getParent();
        }

        this.shortestPath = backtrackPath;
    }

    private boolean checkNextMoveInBounds(Square s, int[] direction){
        return s.getCoordinate().getX() + direction[0] >= 0
                && s.getCoordinate().getX() + direction[0] < this.getColumns()
                && s.getCoordinate().getY() + direction[1] >= 0
                && s.getCoordinate().getY() + direction[1] < this.getRows();
    }

    public List<Square> getVisitOrder(){
        if(this.visitOrder == null)
            throw new NullPointerException("visitOrder is NULL at this point");
        else{
            return this.visitOrder;
        }
    }

    public List<Square> getShortestPath(){
        if(this.shortestPath == null)
            throw new NullPointerException("shortestPath is NULL at this point");
        else
            return this.shortestPath;
    }
}
