

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
/**
 * Creates and then solves a Zukei puzzle(calculate how many squares it's possible to make with given points)
 * @author GavinRB
 *
 */
public class ZukeiSolver {

    /**
     * Creates a sparse board based on the boardID(functions as a seed) using an external website. 
     * @param boardID
     * @return
     * @throws IOException if there's an error with the website
     */
    public static SparseBoard<OnOffCell> pullBoard(int boardID) throws IOException {
        SparseBoard<OnOffCell> board;
        
        URL url = new URL("https://ayuda.twodee.org:8443/zukei-square/" + boardID);
        
        Scanner sc = new Scanner(url.openStream());
        
        board = new SparseBoard<OnOffCell>(sc.nextInt(), sc.nextInt());
        
        while (sc.hasNext()) {
            board.set(sc.nextInt(), sc.nextInt(), new OnOffCell());
        }
        
        return board;
    }
    
    
    
    /**
     * 
     * @param board
     * @return the number of squares found on the sparse board
     */
    public static int solveBoard(SparseBoard<OnOffCell> board) {
        int foundSquares = 0;
        
        
        Iterable<Point> points = board.locations();
        Iterable<Point> clone;
        
        Iterator<Point> pointsIter = points.iterator();
        Iterator<Point> cloneIter;

        
        
        while (pointsIter.hasNext()) {
            clone = (Iterable<Point>) ((HashSet<Point>)points).clone();
            cloneIter = clone.iterator();
            
            Point current = pointsIter.next();
            System.out.println(current); // Prints out the coordinates of the current point being examined.
            int col = current.x;
            int row = current.y;
            
            while (cloneIter.hasNext()) {
                Point compare = cloneIter.next();
                
                if (compare.y == row && compare.x > col) { // If the compared point is on the same y axis and is to the right
                    int distance = Math.abs(compare.x - col);
                    if (board.has(col, row - distance) && board.has(compare.x, row - distance)) { // If the board has points the same distance away directly 
                                                                                                  // beneath those two points
                        foundSquares++;
                        
                        board.tick(col, row);
                        board.tick(col, row - distance);
                        board.tick(compare.x, row);
                        board.tick(compare.x, row - distance);
                    }
                    
                }
                
                
                
            }
            
        }
        
        return foundSquares;
        
    }
    
    
    
    
    public static void main(String[] args) throws IOException {
        
        SparseBoard<OnOffCell> pull = pullBoard(3);
        
        SparseBoard<OnOffCell> board = new SparseBoard<OnOffCell>(5, 5);
        
        
        
        board.set(0, 0, new OnOffCell());
        board.set(0, 1, new OnOffCell());
        board.set(1, 0, new OnOffCell());
        board.set(1, 1, new OnOffCell());
        
        
        System.out.println("There are " + solveBoard(pull) + " squares on this sparse board.");
    }
}
