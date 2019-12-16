package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JacksonTrudel
 */
public class Shape {
    final Color col;
    final Shapes form;
    private Orientation dir;
    private Point base;
    
    
    enum Shapes{
        S, T, J, L, O, I, Z;
    }
    
    enum Orientation{
        UP, DOWN, LEFT, RIGHT;
    }
    
    public Shape(Shapes c){
        
        switch(c){
            case S:
                col = new Color(0, 255, 0);
                form = Shapes.S;
                break;
            case T:
                col = new Color(100, 0, 255);
                form = Shapes.T;
                break;
            case J:
                col = new Color(255, 0, 234);
                form = Shapes.J;
                break;
            case L:
                col = new Color(255, 175, 0);
                form = Shapes.L;
                break;
            case O:
                col = new Color(255, 255, 0);
                form = Shapes.O;
                break;
            case I:
                col = new Color(0, 255, 255);
                form = Shapes.I;
                break;
            case Z:
                col = new Color(255, 0, 0);
                form = Shapes.Z;
                break;
            default:
                //default shape: S
                col = new Color(0, 255, 0);
                form = Shapes.S;
                
        }
        
        dir = Orientation.UP;
        base = new Point(1, 4, true);
                
        

    }
    
    
    
    public static ArrayList<Shape> getBag(){
        ArrayList<Shape> bag = new ArrayList<>();
        Shape addition;
        
        int num, ind = 0;
        Random numGen = new Random();
        
        fillBag: //while loop identifier
        while(bag.size() != 7){
            num = numGen.nextInt(7) + 1;
            
            switch(num){
                case 1:
                    addition = new Shape(Shapes.S);
                    break;
                case 2:
                    addition = new Shape(Shapes.T);
                    break;
                case 3:
                    addition = new Shape(Shapes.O);
                    break;
                case 4:
                    addition = new Shape(Shapes.I);
                    break;
                case 5:
                    addition = new Shape(Shapes.L);
                    break;
                case 6:
                    addition = new Shape(Shapes.J);
                    break;
                case 7:
                    addition = new Shape(Shapes.Z);
                    break;
                default:
                    addition = new Shape(Shapes.Z);
                    break;
            }
            
            //Make sure addition is a unique Shape in bag
            for(int i = 0; i < bag.size(); i++){
                if(addition.getForm() == bag.get(i).getForm()){
                    continue fillBag;
                }
            }
            
            bag.add(addition);
            ind++;
         
        }
        return bag;
    }
    
    public Shapes getForm(){
        return form;
    }
    
    public Orientation getDir(){
        return dir;
    }
    
    // Pre-condition: none
    // Post-condition: Returns an ArrayList of points which denote the
    //                  spaces occupied by the shape
    public ArrayList<Point> getPoints(){
        ArrayList<Point> points = new ArrayList<Point>();
        
        points.add(base);
        
        //handles case for O shapes
        if(form == Shapes.O){
            points.add(new Point(base.row - 1, base.col, false));
            points.add(new Point(base.row - 1, base.col + 1, false));
            points.add(new Point(base.row, base.col + 1, false));
            
            return points;   
        }
        else if(form == Shapes.I){
            if(dir == Orientation.UP){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row, base.col + 2, false));
            }
            else if(dir == Orientation.RIGHT){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row + 2, base.col, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col - 2, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else{
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row - 2, base.col, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
            return points;
        }
        else if(form == Shapes.J){
            if(dir == Orientation.UP){
                points.add(new Point(base.row - 1, base.col - 1, false));
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else if(dir == Orientation.LEFT){
                points.add(new Point(base.row + 1, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row - 1, base.col, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row + 1, base.col + 1, false));
            }
            else{
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row - 1, base.col + 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
        }
        else if(form == Shapes.L){
            if(dir == Orientation.RIGHT){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row + 1, base.col + 1, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else if(dir == Orientation.LEFT){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row - 1, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
            else {
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row - 1, base.col + 1, false));
            }
        }
        else if(form == Shapes.S){
            if(dir == Orientation.UP){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row - 1, base.col + 1, false));
            }
            else if(dir == Orientation.RIGHT){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row + 1, base.col + 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row + 1, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else{
                points.add(new Point(base.row - 1, base.col - 1, false));
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
        }
        else if(form == Shapes.Z){
            if(dir == Orientation.UP){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row - 1, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else if(dir == Orientation.RIGHT){
                points.add(new Point(base.row - 1, base.col + 1, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row + 1, base.col + 1, false));
            }
            else{
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row - 1, base.col + 1, false));
            }
        }
        else{
            if(dir == Orientation.UP){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else if(dir == Orientation.RIGHT){
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row, base.col + 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
            else if(dir == Orientation.DOWN){
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
                points.add(new Point(base.row, base.col + 1, false));
            }
            else{
                points.add(new Point(base.row - 1, base.col, false));
                points.add(new Point(base.row, base.col - 1, false));
                points.add(new Point(base.row + 1, base.col, false));
            }
        }
        
        
        return points;
    }
   
    public Color getColor(){
        return col;
    }
    
    public void setBase(Point base){
        this.base = base;
    }
    
    public Point getBase(){
        return base;
    }
    
    public void setDir(Orientation or){
        dir = or;
    }  
}
