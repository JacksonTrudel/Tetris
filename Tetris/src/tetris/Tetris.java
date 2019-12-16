
package tetris;

/**
 *
 * @author JacksonTrudel
 */
public class Tetris {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Creates a new game
        Game game = new Game();
        TetrisUI ui = new TetrisUI(game);
       
        // Starts game
        game.playGame();
    }
    
}
