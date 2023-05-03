

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public abstract class ArrayBoard<T extends Cell> extends Board<T>{
    
    private Cell[][] board;
    

    
    
    public ArrayBoard(int width, int height) {
        super(width, height);
        board = new Cell[width][height];
    }

    @Override
    public Cell get(int col, int row) {
        if (col < 0 || col >= width() ||
                row < 0 || row >= height()) {
            return null;
        } else {
            return board[col][row];
        }
    }

    @Override
    public void set(int col, int row, Cell toSet) {
        if (col < 0 || col >= width() ||
                row < 0 || row >= height()) {
            throw new IndexOutOfBoundsException();
        } else {
            board[col][row] = toSet;
        }
       
    }

    @Override
    public Iterable<Point> locations() {
        ArrayList<Point> list = new ArrayList<Point>();
        
        for (int i = 0; i < width(); i++) {
            for (int e = 0; e < height(); e++) {
                if (get(i, e) != null) {
                    list.add(new Point(i, e));
                }
            }
        }
        
        return list;
    }

    @Override
    public Iterable<T> queryCells(Predicate<T> pred) {
        // TODO Auto-generated method stub
        return new queryCellsAble(pred, this);
    }



}


class queryCellsAble<T extends Cell> implements Iterable<T> {

    TrueCellsLocation<T> iter;
    
    public queryCellsAble(Predicate<Cell> pred, ArrayBoard<T> board) {
        iter = new TrueCellsLocation<T>(board);
        for (int i = 0; i < board.width(); i++) {
            for (int e = 0; e < board.height(); e++) {
                T test = (T) board.get(i, e);
                if (pred.test(test) && test != null) {
                    iter.add(new Point(i, e));
                }
            }
        }
    }

    public Iterator<T> iterator() {
        return iter;
    }
    
}


class TrueCellsLocation<T extends Cell> implements Iterator<T> {

    ArrayList<Point> list;
    ArrayBoard<T> board;
    int index;
    Point lastPoint;
    
    public TrueCellsLocation(ArrayBoard<T> board) {
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
            board.set(lastPoint.x, lastPoint.y, null);
            lastPoint = null;
        } else {
            throw new IllegalStateException();
        }
    }
    
}

