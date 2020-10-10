package maze;

import javax.swing.*;
import java.awt.*;
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
                MazeSolver solver = new MazeSolver();
                displayMaze(maze, solver);

                f.dispose();
            });
        });
    }

    public void displayMaze(Maze m, MazeSolver s){
        List<List<JLabel>> mazeLabels = new ArrayList<>();

        long startTime = System.nanoTime();
        List<Square> shortestPath = s.solve(m);
        long endTime = System.nanoTime();

        Collections.reverse(shortestPath);
        List<Square> visited = s.getVisitOrder();

        JFrame f = new JFrame("Maze App");
        f.setLayout(new BorderLayout());

        JPanel mazeVisual = new JPanel();
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

        mazeVisual.setLayout(new GridLayout(m.getRows(), m.getColumns()));

        for(int i = 0; i < m.getRows(); i++){
            List<JLabel> mazeLabelRow = new ArrayList<>();

            for (int j = 0; j < m.getColumns(); j++){
                JLabel label = makeLabel(m.getSquareByCoordinate(j, i));
                mazeLabelRow.add(label);

                mazeVisual.add(label);
            }

            mazeLabels.add(mazeLabelRow);
        }

        startButton.addActionListener(actionEvent -> {
            //Make startButton idempotent
            startButton.setVisible(false);

            //Because startButton was clicked, hide stepButton
            stepButton.setVisible(false);

            Timer t1 = new Timer(100, actionEvent12 -> {
                if(!visited.isEmpty()){
                    mazeLabels.get(visited.get(0).getCoordinate().getY()).get(visited.get(0).getCoordinate().getX()).setBackground(Color.GRAY);
                    visited.remove(0);

                    statusLabel.setText("Solution in Progress");
                }
                else{
                    for(Square s12 : shortestPath){
                        mazeLabels.get(s12.getCoordinate().getY()).get(s12.getCoordinate().getX()).setBackground(Color.PINK);
                    }

                    startButton.setVisible(false);
                    stepButton.setVisible(false);
                    resetButton.setVisible(true);

                    if(mazeLabels.get(m.endCoordinate.getY()).get(m.endCoordinate.getX()).getBackground() == Color.PINK)
                        statusLabel.setText("Solution Found in " + (endTime - startTime)/1000000 + "ms");
                    else{
                        //statusLabel.setText("No Solution Found");
                    }
                }
            });
            t1.start();
        });

        stepButton.addActionListener(actionEvent -> {
            if(!visited.isEmpty()){
                mazeLabels.get(visited.get(0).getCoordinate().getY()).get(visited.get(0).getCoordinate().getX()).setBackground(Color.GRAY);
                visited.remove(0);
            }
            else{
                Timer t2 = new Timer(100, actionEvent1 -> {
                    if(!shortestPath.isEmpty()){
                        for(Square s1 : shortestPath){
                            mazeLabels.get(s1.getCoordinate().getY()).get(s1.getCoordinate().getX()).setBackground(Color.PINK);
                        }

                        statusLabel.setText("Solution in Progress");
                    }
                    else{
                        startButton.setVisible(false);
                        stepButton.setVisible(false);
                        resetButton.setVisible(true);

                        if(mazeLabels.get(m.endCoordinate.getY()).get(m.endCoordinate.getX()).getBackground() == Color.PINK)
                            statusLabel.setText("Solution Found in " + (endTime - startTime)/1000000 + "ms");
                        else{
                            //statusLabel.setText("No Solution Found");
                        }
                    }
                });
                t2.start();

                startButton.setVisible(false);
                stepButton.setVisible(false);
                resetButton.setVisible(true);
            }
        });

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
