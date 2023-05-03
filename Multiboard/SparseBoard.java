

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
/**
 * A class used for a massive board with a few select cells. Used in this project for ZukeiSolver
 * @author GavinRB
 *
 * @param <T>
 */
public class SparseBoard<T extends Cell> extends Board<T>{

    HashMap<Point, T> board = new HashMap<Point, T>();
    
    public SparseBoard(int width, int height) {
        super(width, height);
    }

    /**
     * Tries to get the cell spaced on the coordinates.
     */
    @Override
    public Cell get(int col, int row) {
        if (col < 0 || col >= width() ||
                row < 0 || row >= height()) {
            return null;
        } else {
            return board.get(new Point(col, row));
        }
    }


    /**
     * Returns an HashSet that contains the locations of all the set cells.
     */
    @Override
    public Iterable<Point> locations() {
        HashSet<Point> set = new HashSet<Point>();
        
        for(Map.Entry<Point, T> entry : board.entrySet()) {
            Point key = entry.getKey();
            set.add(key); 
        }
        
        return set;
    }


    @Override
    public void reset() {
        board.clear();
    }


    /**
     * Puts the point on the board.
     */
    @Override
    public void set(int col, int row, T toSet) throws IndexOutOfBoundsException {
        if (col < 0 || col >= width() ||
                row < 0 || row >= height()) {
            throw new IndexOutOfBoundsException();
        } else {
            board.put(new Point(col, row), toSet);
        }
        
        
            
    }

    @Override
    public Iterable<T> queryCells(Predicate<T> pred) {
            // TODO Auto-generated method stub
        return new cellPointsAble(pred, this);
    }
    
    




}

/**
 * An Iterable that contains all the cells created on the board.
 * @author GavinRB
 *
 * @param <T>
 */
class cellPointsAble<T extends Cell> implements Iterable<T> {

    CellsLocation<T> iter;
    
    public cellPointsAble(Predicate<Cell> pred, SparseBoard<T> sparseBoard) {
        iter = new CellsLocation<T>(sparseBoard);
        for (int i = 0; i < sparseBoard.width(); i++) {
            for (int e = 0; e < sparseBoard.height(); e++) {
                if (pred.test(sparseBoard.get(i, e))) {
                    iter.add(new Point(i, e));
                }
            }
        }
    }

    public Iterator<T> iterator() {
        return iter;
    }
    
}


class CellsLocation<T extends Cell> implements Iterator<T> {

    ArrayList<Point> list;
    SparseBoard<T> board;
    int index;
    Point lastPoint;
    
    public CellsLocation(SparseBoard<T> board) {
        index = 0;
        this.board = board;
        list = new ArrayList<Point>(0);
        lastPoint = null;
    }
    
    public void add(Point toAdd) {
        list.add(toAdd);
    }
    
    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public T next() {
        if (hasNext()) {
            lastPoint = list.get(index);
            index++;
            Cell toReturn = board.get(lastPoint.x, lastPoint.y);
            return (T) toReturn;
        } else {
            throw new NoSuchElementException();
        }
    }
    
    public void remove() {
        
        if (lastPoint != null) {
            board.set((int)lastPoint.getX(), (int)lastPoint.getY(), null);
            lastPoint = null;
        } else {
            throw new IllegalStateException();
        }
    }
    
}