
/**
 * Seam carving implementation based on the algorithm discovered by Shai Avidan
 * and Ariel Shamir (SIGGRAPH 2007).
 * 
 * @author Gavin Boland
 * @version 2/11/2022
 */
public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;

    /**
     * Construct a SeamCarver object.
     * 
     * @param picture initial picture
     */
    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture getPicture() {
        return picture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Computes the energy of a pixel.
     * 
     * @param x column index
     * @param y row index
     * @return energy value
     */
    public int energy(int x, int y) {
        int rx;
        int gx;
        int bx;
        int ry;
        int gy;
        int by;
        int deltaX;
        int deltaY;
        if (x < 0 || x >= width) {
            throw new IndexOutOfBoundsException(
                String.format("x = %d, width = %d", x, width));
        }
        if (y < 0 || y >= height) {
            throw new IndexOutOfBoundsException(
                String.format("y = %d, height = %d", y, height));
        }
        try {
            rx = picture.get(x + 1, y).getRed() 
                    - picture.get(x - 1, y).getRed();
            gx = picture.get(x + 1, y).getGreen()
                    - picture.get(x - 1, y).getGreen();
            bx = picture.get(x + 1, y).getBlue() 
                    - picture.get(x - 1, y).getBlue();
        } catch (IllegalArgumentException e) {
            if (x == 0 && width > 1) {
                rx = picture.get(x + 1, y).getRed()
                        - picture.get(width - 1, y).getRed();
                gx = picture.get(x + 1, y).getGreen()
                        - picture.get(width - 1, y).getGreen();
                bx = picture.get(x + 1, y).getBlue()
                        - picture.get(width - 1, y).getBlue();
            } else if (width == 1) {
                rx = 0;
                gx = 0;
                bx = 0;
            } else {
                rx = picture.get(0, y).getRed()
                        - picture.get(x - 1, y).getRed();
                gx = picture.get(0, y).getGreen()
                        - picture.get(x - 1, y).getGreen();
                bx = picture.get(0, y).getBlue() 
                        - picture.get(x - 1, y).getBlue();
            }
        }

        try {
            ry = picture.get(x, y + 1).getRed()
                    - picture.get(x, y - 1).getRed();
            gy = picture.get(x, y + 1).getGreen()
                    - picture.get(x, y - 1).getGreen();
            by = picture.get(x, y + 1).getBlue() 
                    - picture.get(x, y - 1).getBlue();
        } catch (IllegalArgumentException e) {
            if (y == 0 && height > 1) {
                ry = picture.get(x, y + 1).getRed() 
                        - picture.get(x, height - 1).getRed();
                gy = picture.get(x, y + 1).getGreen()
                        - picture.get(x, height - 1).getGreen();
                by = picture.get(x, y + 1).getBlue()
                        - picture.get(x, height - 1).getBlue();
            } else if (height == 1) {
                ry = 0;
                gy = 0;
                by = 0;
            } else {
                ry = picture.get(x, 0).getRed() 
                        - picture.get(x, y - 1).getRed();
                gy = picture.get(x, 0).getGreen()
                        - picture.get(x, y - 1).getGreen();
                by = picture.get(x, 0).getBlue()
                        - picture.get(x, y - 1).getBlue();
            }
        }

        deltaX = rx * rx + gx * gx + bx * bx;
        deltaY = ry * ry + gy * gy + by * by;

        return deltaX + deltaY;
    }

    /**
     * Computes the energy matrix for the picture.
     * 
     * @return current energy of each pixel
     */
    public int[][] energyMatrix() {
        int[][] matrix = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int e = 0; e < height; e++) {
                matrix[i][e] = energy(i, e);
            }
        }
        return matrix;
    }

    /**
     * Removes a horizontal seam from current picture.
     * 
     * @param seam sequence of row indices
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException("null");
        }
        if (seam.length != width || width == 1) {
            throw new IllegalArgumentException(
                    "Seam in innapropriate place"); 
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (!(seam[i] == seam[i + 1] || seam[i] == seam[i + 1] + 1
                    || seam[i] == seam[i + 1] - 1)) {
                throw new IllegalArgumentException(
                        "seam not continuous");
            }
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] >= width || seam[i] < 0) {
                throw new IllegalArgumentException(
                        "seam not continuous");
            }
        }

        int row = 0;
        
        Picture replacement = new Picture(width, height - 1);
        for (int i = 0; i < width; i++) {
            row = 0;
            for (int e = 0; e < height; e++) {
                if (e != seam[i]) {
                    replacement.set(i, row, picture.get(i, e));
                    row++;
                } 
            }
        }
        picture = replacement;
        height = picture.height();
        width = picture.width();

    }

    /**
     * Removes a vertical seam from the current picture.
     * 
     * @param seam sequence of column indices
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException("null");
        }
        if (seam.length != height || height == 1) {
            throw new IllegalArgumentException(
                    "Seam in innapropriate place"); 
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (!(seam[i] == seam[i + 1] || seam[i] == seam[i + 1] + 1
                    || seam[i] == seam[i + 1] - 1)) {
                throw new IllegalArgumentException(
                        "seam not continuous");
            }
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] >= width || seam[i] < 0) {
                throw new IllegalArgumentException(
                        "aaaaa! bats!");
            }
        }
        

        int col = 0;
        
        Picture replacement = new Picture(width - 1, height);
        for (int i = 0; i < height; i++) {
            col = 0;
            for (int e = 0; e < width; e++) {
                if (e != seam[i]) {
                    replacement.set(col, i, picture.get(e, i));
                    col++;
                } 
            }
        }
        picture = replacement;
        height = picture.height();
        width = picture.width();
    }

}
