

import java.awt.Color;
/**
 * Simple class that represents a cell that can be on(yellow) or off(black)
 * @author GavinRB
 *
 */
public class OnOffCell implements Cell {

    private boolean on;
    
    
    /**
     * Constructor.
     * 
     * @param on true if cell is on
     */
    public OnOffCell() {
        this.on = false;
    }
    
    
    @Override
    public void tick() {
        this.on = !this.on;
    }

    
    @Override
    public Color color() {
        if(isOn()) {
            return Color.YELLOW;
        } else {
            return Color.BLACK;
        }
    }
    

    public boolean isOn() {
        return this.on;
    }
}
