package ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import tetris.TetrisUI;

/**
 *
 * @author JacksonTrudel
 */
public class MenuListener implements ActionListener{

    TetrisUI ui;
    JMenuItem mi;
    int control;
    
    public MenuListener(TetrisUI ui, JMenuItem mi, int a){
        this.ui = ui;
        this.mi= mi;
        control = a;
        mi.addActionListener(this);
        
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(control == 0){
            ui.pauseGame();
            if(mi.getText().equals("Pause Game")){
                mi.setText("Resume Game");
            }
            else
                mi.setText("Pause Game");
        }
        else{
            ui.quitGame();
        }
    
    }
    
}
