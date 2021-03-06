package maze;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    //2d list of Square type to represent maze
    List<List<Square>> maze;

    //Start and End coordinate attributes
    Coordinate startCoordinate;
    Coordinate endCoordinate;

    //Labels shown in Maze GUI
    List<List<JLabel>> mazeLabels;

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
        String returnString = "";

        for(List<Square> l : maze){
            String row = "";

            for(Square s : l){
                row += s.getChar();
            }

            returnString += row;
            returnString += "\n";
        }
        return returnString;
    }

    public Square getStartSquare(){
        return getSquareByCoordinate(this.startCoordinate.getX(), this.startCoordinate.getY());
    }

    public JLabel getJLabelByCoordinate(Coordinate c){
        return mazeLabels.get(c.getY()).get(c.getX());
    }

    public int getRows(){
        return this.maze.size();
    }

    public int getColumns(){ return this.maze.get(0).size(); }
}
