package tetris;

/**
 *
 * @author JacksonTrudel
 */
public class Point {
    public int row;
    public int col;
    public boolean isBase;
    
    public Point(int a, int b, boolean root){
        row = a;
        col = b;
        isBase = root;
    }
}
