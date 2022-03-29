package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class Controller extends Canvas {


    private final double START_X = 14.0;
    private final double START_Y = 59.0;

    private boolean mIsBoardGenerated = false;
    private final Random mRandom = new Random();

    //View
    private final Button[][] mButtons = new Button[10][10];
    //Logic
    private final boolean[][] mBoardLogic = new boolean[10][10];

    @FXML
    AnchorPane anchorPane;

    @FXML
    Canvas mCanvas;


    public void onGenerateButtonClicked() {
        if (!mIsBoardGenerated) {
            createRandomBoard();
            createLinesForBoard();
            mIsBoardGenerated = true;
        } else {
            performLogic();
        }
    }

    private void createLinesForBoard() {
        double startX = START_X;
        double startY = START_Y;
        GraphicsContext gc = mCanvas.getGraphicsContext2D() ;
        gc.setLineWidth(3);

        for(int i= 0 ; i < 11 ; i++){
            gc.strokeLine(startX - 5, startY ,startX - 5,startY + 390);
            startX+= 50;
        }
        startX = START_X;
        for(int j = 0 ; j < 11 ; j++ ){
            gc.strokeLine(startX - 5, startY - 7 ,startX + 490,startY - 7);
            startY+= 40;

        }


        //draw lines :) with canvas stroke line
    }

    private boolean shouldDeadButton(int i, int j) {
        int numberOfAliveButtons = checkHowManyNeiboursAlive(i, j);
        return !(numberOfAliveButtons <= 1 || numberOfAliveButtons >= 4);
    }

    private boolean shouldAliveButton(int i, int j) {
        return checkHowManyNeiboursAlive(i, j) == 3;
    }

    private int checkHowManyNeiboursAlive(int row, int col) {
        int counter = 0;
        for (int nextR = row - 1; nextR <= row + 1; nextR++) {
            if (nextR < 0 || nextR >= 10)
                continue;  //row out of bound
            for (int nextC = col - 1; nextC <= col + 1; nextC++) {
                if (nextC < 0 || nextC >= 10)
                    continue;  //col out of bound
                if (nextR == row && nextC == col)
                    continue;    //current cell
                if (mBoardLogic[nextR][nextC]) {
                    counter++;
                }

            }
        }
        return counter;
    }

    private boolean isButtonAlive(int i, int j) {
        return mBoardLogic[i][j];
    }


    void performLogic() {
        boolean[][] mCopiedLogic = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!isButtonAlive(i, j)) {
                    //If button is dead ->
                    mCopiedLogic[i][j] = shouldAliveButton(i, j);
                } else {
                    //Button is alive
                    mCopiedLogic[i][j] = shouldDeadButton(i, j);
                }
            }
        }
        setNewData(mCopiedLogic);

    }

    private void setNewData(boolean[][] newData) {
        System.out.println("#####OLD######");
        printLoggedBoard(mBoardLogic);
        System.out.println("#####NEW######");
        printLoggedBoard(newData);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mBoardLogic[i][j] = newData[i][j];
                mButtons[i][j].setVisible(newData[i][j]);
            }
        }
    }

    private void printLoggedBoard(boolean arr[][]) {


    }

    // generate the first play adding button and put them in array
    public void createRandomBoard() {
        double startY = START_Y;
        for (int i = 0; i < 10; i++) {
            double startX = START_X;
            for (int j = 0; j < 10; j++) {
                Button button = generateButton(startX, startY);
                anchorPane.getChildren().add(button);
                mButtons[i][j] = button;
                boolean visibleOrNot = mRandom.nextBoolean();
                mBoardLogic[i][j] = visibleOrNot;
                button.setVisible(visibleOrNot);
                startX += 50;
            }
            startY += 40;
        }
    }

    //generate button
    private Button generateButton(double x, double y) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText("X");
        button.setPrefWidth(40);
        return button;
    }




}











