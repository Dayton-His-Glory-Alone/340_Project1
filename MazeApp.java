package maze;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeApp {
    public MazeApp(){
        EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("Maze App");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(500, 100);

            JLabel fileNameLabel = new JLabel("Enter the file name");
            JTextField fileInput = new JTextField(30);
            JButton loadButton = new JButton("Load");
            loadButton.setVisible(false);

            JPanel panel = new JPanel();
            panel.add(fileNameLabel);
            panel.add(fileInput);
            panel.add(loadButton);

            f.add(panel);
            f.setVisible(true);

            fileInput.addActionListener(actionEvent -> {
                if(MazeReader.isFileValid(fileInput.getText())){
                    loadButton.setVisible(true);
                }
            });

            loadButton.addActionListener(actionEvent -> {
                Maze maze = MazeReader.readMaze(fileInput.getText());
                assert maze != null;
                displayMaze(maze);

                f.dispose();
            });
        });
    }

    public void displayMaze(Maze m){
        List<List<JLabel>> mazeLabels = new ArrayList<>();

        //Solve the maze and record how long it took
        long startTime = System.nanoTime();
        m.solve();
        long endTime = System.nanoTime();

        //Get shortest path and reverse it for the GUI
        List<Square> shortestPath = m.getShortestPath();
        Collections.reverse(shortestPath);

        //Create boolean flag for status label later
        final boolean pathFound = (shortestPath.get(shortestPath.size() - 1).getMarker() == Marker.FINISH);

        //Get list of all squares visited to display on GUI
        List<Square> visited = m.getVisitOrder();

        //Create JFrame and set layout
        JFrame f = new JFrame("Maze App");
        f.setLayout(new BorderLayout());

        JPanel mazeVisual = new JPanel();
        mazeVisual.setLayout(new GridLayout(m.getRows(), m.getColumns()));
        JScrollPane scrollPane = new JScrollPane(mazeVisual);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel footer = new JPanel();
        JButton startButton = new JButton("Start");
        JButton stepButton = new JButton("Step");
        JButton resetButton = new JButton("Reset");
        JLabel statusLabel = new JLabel("Maze Loaded");


        footer.add(startButton);
        footer.add(stepButton);
        footer.add(resetButton);
        footer.add(statusLabel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        Setup the GUI by creating labels in a grid
        Each label represents a square
        Labels will be colored by function on the GUI
         */
        for(int i = 0; i < m.getRows(); i++){
            List<JLabel> mazeLabelRow = new ArrayList<>();

            for (int j = 0; j < m.getColumns(); j++){
                JLabel label = makeLabel(m.getSquareByCoordinate(j, i));
                mazeLabelRow.add(label);

                mazeVisual.add(label);
            }

            mazeLabels.add(mazeLabelRow);
        }

        /*
        ActionListener for startButton
        Set visibility of buttons on GUI
        Color every visited square in order every 100ms
        Once finished, reset visibility as needed and display shortest path
         */
        startButton.addActionListener(actionEvent -> {
            //Pressing start button acts as 'speeding up' the timer
            startButton.setText("Speed Up");

            //Because startButton was clicked, hide stepButton
            stepButton.setVisible(false);

            Timer t1 = new Timer(50, actionEvent12 -> {
                if(!visited.isEmpty()){
                    if(!(visited.get(0).getMarker() == Marker.START || visited.get(0).getMarker() == Marker.FINISH))
                        mazeLabels.get(visited.get(0).getCoordinate().getY()).get(visited.get(0).getCoordinate().getX()).setBackground(Color.GRAY);
                    visited.remove(0);

                    statusLabel.setText("Solution in Progress");
                }
                else{
                    for(Square s : shortestPath){
                        if(!(s.getMarker() == Marker.START || s.getMarker() == Marker.FINISH))
                            mazeLabels.get(s.getCoordinate().getY()).get(s.getCoordinate().getX()).setBackground(Color.PINK);
                    }

                    startButton.setVisible(false);
                    stepButton.setVisible(false);
                    resetButton.setVisible(true);

                    if(pathFound){
                        DecimalFormat df = new DecimalFormat("#.##");
                        float executionTime = (endTime - startTime)/(float)1000000;
                        statusLabel.setText("Solution in " + shortestPath.size() + " steps in " + df.format(executionTime) + " ms");
                    }
                    else{
                        statusLabel.setText("No solution found.");
                    }
                }
            });
            t1.start();
        });

        /*
        ActionListener for stepButton
        Color next visited square by button press
        Once finished, show the shortest path
         */
        stepButton.addActionListener(actionEvent -> {
            if(!visited.isEmpty()){
                if(!(visited.get(0).getMarker() == Marker.START || visited.get(0).getMarker() == Marker.FINISH))
                    mazeLabels.get(visited.get(0).getCoordinate().getY()).get(visited.get(0).getCoordinate().getX()).setBackground(Color.GRAY);
                visited.remove(0);
            }
            else{
                for(Square s : shortestPath){
                    if(!(s.getMarker() == Marker.START || s.getMarker() == Marker.FINISH))
                        mazeLabels.get(s.getCoordinate().getY()).get(s.getCoordinate().getX()).setBackground(Color.PINK);
                }

                startButton.setVisible(false);
                stepButton.setVisible(false);
                resetButton.setVisible(true);

                if(pathFound){
                    DecimalFormat df = new DecimalFormat("#.##");
                    float executionTime = (endTime - startTime)/(float)1000000;
                    statusLabel.setText("Solution in " + shortestPath.size() + " steps in " + df.format(executionTime) + " ms");
                }
                else{
                    statusLabel.setText("No solution found.");
                }
            }
        });

        /*
        ActionListener for resetButton
        Dispose of the JFrame
        Reset the GUI
         */
        resetButton.addActionListener(actionEvent -> {
            f.dispose();
            new MazeApp();
        });

        f.add(scrollPane, BorderLayout.NORTH);
        f.add(footer, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }

    private JLabel makeLabel(Square s){
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(18, 18));

        switch(s.getMarker()){
            case OPEN_SPACE:
                label.setBackground(Color.WHITE);
                break;
            case START:
                label.setBackground(Color.GREEN);
                break;
            case FINISH:
                label.setBackground(Color.RED);
                break;
            default:
                label.setBackground(Color.BLACK);
        }

        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }

    public static void main(String[] args){
        new MazeApp();
    }
}
