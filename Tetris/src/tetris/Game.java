
package tetris;

import java.util.ArrayList;
import javax.swing.JLabel;
import tetris.Shape.Orientation;
import tetris.Shape.Shapes;

/**
 *
 * @author JacksonTrudel
 */
public class Game {
    private int score;
    private Square board[][];
    private Square nextBoard[][];
    private ArrayList<Point> currPosition;
    private ArrayList<Point> nextPosition; //Used for next grid
    private Shape currShape;
    private Shape nextShape;
    private final int ORIGDROPSPEED = 400;
    private int dropSpeed = ORIGDROPSPEED;
    private final int ACCELDROPSPEED = 100;
    private boolean pause = false;
    private boolean quit = false;
    private boolean acceling = false;
    private JLabel scoreLabel;
    private TetrisUI ui;
    
    public Game(){
        score = 0;  
    }
    
    // Returns current score of game
    public int getScore(){
        return score;
    }
    
    // Initializes game board
    // Called from TetrisUI's initGameBoard() method
    public void setGameBoard(Square square[][]){
        board = square;
    }
    
    // Initializes nextBoard, which displays the next tetris piece
    // Called from TetrisUI's initGameBoard() method
    public void setNextBoard(Square square[][]){
        nextBoard = square;
    }
    
    // Starts the game
    // Called from Tetris.java
    public void playGame(){
        ArrayList<Shape> bag = Shape.getBag(), nextBag = new ArrayList<Shape>();
        
        
        playGame:
        while(true){
            if(quit){
                break;
            }
            if(pause){ //if pausing game
                while(pause){
                    if(System.currentTimeMillis() % 5000 < 1){
                        ;
                    }
                    if(quit){
                        break playGame;
                    }
                }
            }
            
            if(bag.size() == 1){
                nextBag = Shape.getBag();
                nextShape = nextBag.get(0);
            }
            else if(bag.isEmpty()){
                bag = nextBag;
                nextShape = bag.get(1);
            }
            else{
                nextShape = bag.get(1);
            }
            
            displayNext(nextShape);
            
            currShape = bag.remove(0);

            //run current shape
            if(!runShape(currShape)){
                break;
            }

        }
        ui.quitGame();
    }
    
    // This function executes the "dropping" of the tetris piece
    // Pre-conditions: curr is a valid shape
    // Post-condition: returns true 
    private boolean runShape(Shape curr){
        currPosition = curr.getPoints();
        
        for(Point p : currPosition){
            if(board[p.row][p.col].isTaken()){
                return false;
            }
        }
        
        //reset speed
        if(acceling){
            dropSpeed = ORIGDROPSPEED;
            acceling = false;
        }
        displayShape(curr, currPosition);
        
        //move downwards
        
        while(true){
            
            long timeA = System.currentTimeMillis();
            
            
            if(quit){
                return false;
            }
            if(pause){
                while(pause){
                    if(System.currentTimeMillis() % 5000 < 1){
                        ;
                    }
                    if(quit){
                        return false;
                    }
                }
            }
            
            // Moves the piece down if possible, else we quit dropping the piece
            if(!moveDown(curr, currPosition))
                break;
            
            
            while(System.currentTimeMillis() - timeA < dropSpeed){
                ;
            }
        }
        
        // method clears full rows
        checkRows(); 
        return true;
    }
    
    // Displays the shape on the board
    public void displayShape(Shape shape, ArrayList<Point> points){
        //paint each point
        for(Point p : points){
            board[p.row][p.col].paint(shape);
        }
    }
    
    // Moves the shape down one row
    // Pre-condition: shape is a valid shape and its position is denoted by
    //                 points
    // Post-condition: returns true if the shape could be moved down, else 
    //                  returns false
    private boolean moveDown(Shape shape, ArrayList<Point> points){
        ArrayList<Point> newPosition = new ArrayList<Point>();
        

        for(Point p : points){
            Point newPoint = new Point(p.row + 1, p.col, p.isBase);
            if(newPoint.row == 24){
                return false;
            }
            if(board[newPoint.row][newPoint.col].getShape() != null && 
                board[newPoint.row][newPoint.col].getShape() != shape){
                return false;
            }
            
            newPosition.add(newPoint);
        }
        
        
        //unpaint old position
        for(Point p : points){
            board[p.row][p.col].paint((Shape) null);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
        }
        
        //paint new position
        for(Point p : newPosition){
            board[p.row][p.col].paint(shape);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
            if(p.isBase){
                currShape.setBase(p);
            }
        }
        
        currPosition = newPosition;
        
        return true;
    }
   
    // Shifts the shape left or right if possible
    // Possible arguements: "LEFT" / "RIGHT"
    public void shift(String shift){
        int shiftAmt = 0;
        
        if(shift.equals("LEFT")){
            shiftAmt = -1;
        }
        else
            shiftAmt = 1;
        
        ArrayList<Point> newPosition = new ArrayList<Point>();
        
        for(Point p : currPosition){
            if((shift.equals("RIGHT") && p.col == 9) || shift.equals("LEFT") && p.col == 0)
                return;
            if(board[p.row][p.col + shiftAmt].getShape() != board[p.row][p.col].getShape() && board[p.row][p.col + shiftAmt].getShape() != null){
                return;
            }
            newPosition.add(new Point(p.row, p.col + shiftAmt, p.isBase));
        }
        
        
        //unpaint old position
        for(Point p : currPosition){
            board[p.row][p.col].paint((Shape) null);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
        }
        
        //paint new position
        for(Point p : newPosition){
            board[p.row][p.col].paint(currShape);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
            if(p.isBase){
                currShape.setBase(p);
            }
        }
        
        currPosition = newPosition;
    }
    
    // Displays the next shape on the nextBoard
    private void displayNext(Shape shape){
        Shape mock = new Shape(shape.getForm());
        mock.setBase(new Point(2, 1, true));
        if(nextPosition != null){
            for(Point p : nextPosition){
                nextBoard[p.row][p.col].paint((Shape) null);
            }
        }
        nextPosition = mock.getPoints();
        for(Point p : nextPosition){
            nextBoard[p.row][p.col].paint(shape);
        }
    }
    
    // Accelerates the speed at which a piece is dropped
    public void accelDown(){
        acceling = true;
        dropSpeed = ACCELDROPSPEED;
    }
    
    // Changes the orientation of the shape
    // Possible arguements: 0 for up (left), 1 for down (right)
    public void changeDir(int a){
        Point currBase = currShape.getBase();
        Point newBase = null;
        Orientation currDir = currShape.getDir();
        Orientation newDir = currDir;
        
        Shapes currForm = currShape.getForm();
        
        if(a == 0){ // flip left
            switch(currDir){
                case UP:
                    newDir = Orientation.LEFT;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row + 1, currBase.col, true);
                    }
                    break;
                case LEFT:
                    newDir = Orientation.DOWN;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row, currBase.col + 1, true);
                    }
                    break;
                case DOWN:
                    newDir = Orientation.RIGHT;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row - 1, currBase.col, true);
                    }
                    break;
                case RIGHT:
                    newDir = Orientation.UP;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row, currBase.col - 1, true);
                    }
                    break;
            }
        }
        else{  // flip "right"
            switch(currDir){ 
                case UP:
                    newDir = Orientation.RIGHT;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row, currBase.col + 1, true);
                    }
                    break;
                case RIGHT:
                    newDir = Orientation.DOWN;
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row + 1, currBase.col, true);
                    }
                    break;
                case DOWN:
                    newDir = Orientation.LEFT;

                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row, currBase.col - 1, true);
                    }
                    break;
                case LEFT:
                    newDir = Orientation.UP;
                    
                    if(currForm == Shapes.I){
                        newBase = new Point(currBase.row - 1, currBase.col, true);
                    }
                    break;
            }
        }
        
        if(newBase == null){
                newBase = currBase;
        }
        
        
        //test new position
        currShape.setBase(newBase);
        currShape.setDir(newDir);
        ArrayList<Point> newPosition = currShape.getPoints();
        
        boolean possible = true;
        test:
        for(Point p : newPosition){
            //if it is out of bounds
            if(p.row >23 || p.row < 0 || p.col > 9 || p.col < 0){
                possible = false;
                break test;
            }
            //if another square occupies the space
            if(board[p.row][p.col].isTaken() && board[p.row][p.col].getShape() != currShape){
                possible = false;
            }
            
        }
        
        if(!possible){
            currShape.setBase(currBase);
            currShape.setDir(currDir);
            return;
        }
        
        //unpaint old position
        for(Point p : currPosition){
            board[p.row][p.col].paint((Shape) null);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
        }
        
        //paint new position
        for(Point p : newPosition){
            board[p.row][p.col].paint(currShape);
            board[p.row][p.col].repaint();
            board[p.row][p.col].revalidate();
        }
        
        currPosition = newPosition;
    }
    
    // Eliminates complete rows and calculates the points earned
    private void checkRows(){
        int rowsCleared = 0;
        for(int i = 23; i > 0; i--){
            if(checkRowNum(i)){
                rowsCleared++;
                i++;
            }
        }
        
        switch(rowsCleared){
            case 1:
                score += 40;
                break;
            case 2:
                score += 100;
                break;
            case 3: 
                score += 300;
                break;
            case 4:
                score += 1200;
                break;
            default:
                score+= 0;
            
        }
        
        setScore();
    }
    
    private boolean checkRowNum(int row){
        for(int i = 0; i < 10; i++){
            if(!board[row][i].isTaken()){
                return false;
            }
        }
        
        for(int i = 0; i < 10; i++){
            for(int j = row; j > 0; j--){
                board[j][i].paint(board[j - 1][i].getShape());
                board[j][i].repaint();
                board[j][i].revalidate();
            }
        }
        
        return true;
    }
    
    public void setScoreLabel(JLabel label){
        scoreLabel = label;
    }
    
    public void setScore(){
        scoreLabel.setText(Integer.toString(score));
    }
    
    public void pause(){
        if(pause){
            pause = false;
            System.out.println("Resume");
        }
        else
            pause = true;
    }
    
    public void quit(){
        quit = true;
    }
    
    public void setUI(TetrisUI ui){
        this.ui = ui;
    }
    
}
