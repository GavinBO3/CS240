
import java.awt.Color;

/**
 * Used in HotColdBoard. Each cell has a temperature that is revealed upon being clicked.
 * @author GavinRB
 *
 */
public class HotColdCell implements Cell{

    private double temp;
    Color color;
    
    public HotColdCell(double temp) {
        this.temp = temp;
        color = Color.BLACK;
    }
    
    
    /**
     * Set the color based on the temperature.
     */
    @Override
    public void tick() {
        int r = roundUp(255 - 255 * temp);
        int g = roundUp(255 - 255 * temp);
        int b = 255;
        
        color = new Color(r, g, b);
        
    }

    @Override
    public Color color() {
        return color;
    }
    
    private int roundUp(double input) {
        if (input % 1 != 0) {
            return (int)input + 1;
        } else {
            return (int)input;
        }
        
        
    }

}
