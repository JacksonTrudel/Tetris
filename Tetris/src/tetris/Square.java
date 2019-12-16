package tetris;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author JacksonTrudel
 */
public class Square extends JLabel {
    private boolean taken;
    public final static Color EMPTYCOLOR = new Color(115, 109, 107, 255);
    private Color emptyColor2 = new Color(196, 186, 181, 255);
    private Color takenColor = Color.BLUE;
    private Shape currShape;
    
    public Square(){
        super();
        taken = false;
        initPanel();
        
    }
    
    private void initPanel(){
        setBorder(BorderFactory.createRaisedBevelBorder());
        setBackground(EMPTYCOLOR);
        setOpaque(true);
    }
    
    public boolean isTaken(){
        return taken;
    }
    
    public void paint(Shape shape){
        currShape = shape;
        
        if(shape != null){
            setBackground(shape.getColor());
            taken = true;
        }
        else{ 
            setBackground(EMPTYCOLOR);
            taken = false;
        }
    }
    
    public Shape getShape(){
        return currShape;
    }   
}
