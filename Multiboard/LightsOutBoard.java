

import java.util.Random;
/**
 * Creates a playable "lights out" board, where clicking a cell causes its adjacent cells to flip.
 */
public class LightsOutBoard extends ArrayBoard<OnOffCell>{
    
    Random rand;
    
    public LightsOutBoard(long seed) {
        super(5, 5);
        rand = new Random(seed);
        reset();   
    }
    
    
    /**
     * Flips this and all adjacent cells.
     */
    @Override
    public void click(int col, int row) {
        if(has(col, row)) {
            get(col, row).tick();
        }
        
        if (has(col - 1, row)) {
            tick(col - 1, row);
        }
        
        if (has(col, row - 1)) {
            tick(col, row - 1);
        }
        
        if (has(col + 1, row)) {
            tick(col + 1, row);
        }
        
        if (has(col, row + 1)) {
            tick(col, row + 1);
        }
    }

    /**
     * Resets the board.
     */
    public void reset() {
        for (int i = 0; i < height(); i++) {
            for (int e = 0; e < width(); e++) {
                set(i, e, new OnOffCell());
            }
        }
        
        int row;
        int col;
        
        for (int i = 0; i < 10; i++) {
            col = rand.nextInt(width());
            row = rand.nextInt(height());
            
            
            click(col, row);
            
        }
    }
    
    
    
    
    public static void main(String[] args) {
        
        LightsOutBoard board = new LightsOutBoard(20);
        BoardFrame boardFrame = new BoardFrame(board);
    }
}
