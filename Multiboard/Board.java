

import java.awt.Point;
import java.util.function.Predicate;

public abstract class Board<T extends Cell> {
    private int width;
    private int height;
    
    
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        
    }
    
    public int width() {
        return this.width;
    }
    
    public int height() {
        return this.height;
    }
    
    
    public abstract Cell get(int col, int row);
    
    public abstract void set(int col, int row, T toSet) throws IndexOutOfBoundsException;
    
    
    
    public boolean has(int col, int row) {
        if (get(col, row) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public void tick(int col, int row) {
        if(has(col, row)) {
            get(col, row).tick();
        }
    }
    
    
    public void click(int col, int row) {
        if(has(col, row)) {
            get(col, row).tick();
        }
    }
    
    public abstract Iterable<Point> locations();
    
    public abstract Iterable<T> queryCells(Predicate<T> pred);
        
    public abstract void reset();

}


