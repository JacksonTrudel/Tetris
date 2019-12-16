package ActionListeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import tetris.Game;

/**
 *
 * @author JacksonTrudel
 */
public class KeyboardListener implements KeyListener{
    private Game game;
    
    public KeyboardListener(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
       
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            game.shift("RIGHT");
        }
        else if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            game.shift("LEFT");
        }
        else if(ke.getKeyCode() == KeyEvent.VK_UP){
            game.changeDir(0);
        }
        else if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            game.changeDir(1);
        }
        else if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            game.accelDown();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
    
}
