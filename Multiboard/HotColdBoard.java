
import java.util.Random;
/**
 * Creates a board with one "hot"(white) cell, and hides the temperatures of all cells. Clicking on a cell reveals its temperature.
 * The closer to the hottest cell, the lighter the cell.
 * @author GavinRB
 *
 */
public class HotColdBoard extends ArrayBoard<HotColdCell> {
    
    Random rand;
    
    public HotColdBoard(int width, int height, long seed) {
        super(width, height);
        rand = new Random(seed);
        reset();
    }

    
    /**
     * Finds what corner of the board the specified point is farthest from and returns the distance between this and that cell.
     * @param col
     * @param row
     * @return
     */
    public double distanceToFarCorner(int col, int row) {
        double furthest = 0;
        double temp;
        
        furthest = euclidian(col, row, 0, 0);
        
        temp = euclidian(col, row, 0, height() - 1);
        if (temp > furthest) {
            furthest = temp;
        }
        
        temp = euclidian(col, row, width() - 1, height() - 1);
        if (temp > furthest) {
            furthest = temp;
        }
        
        temp = euclidian(col, row, width() - 1, 0);
        if (temp > furthest) {
            furthest = temp;
        }
        
        return furthest;
        
    }
    
    /**
     * Distance formula
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return the distance between the two points
     */
    private double euclidian(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    

    
    /**
     * Resets the board.
     */
    @Override
    public void reset() {
       int col = rand.nextInt(width());
       int row = rand.nextInt(height());
       
       System.out.println(col + " " + row);
       
       for (int i = 0; i < width(); i++) {
           for (int e = 0; e < height(); e++) {
               double distToCell = euclidian(i, e, col, row);
               
               set(i, e, new HotColdCell(distToCell / distanceToFarCorner(col, row)));
               
               
           }
       }
        
    }
    
    
    
    public static void main(String[] args) {
        HotColdBoard board = new HotColdBoard(3, 10, 5); // Tweak these parameters
        BoardFrame<HotColdCell> boardFrame = new BoardFrame<HotColdCell>(board);
    }

}
