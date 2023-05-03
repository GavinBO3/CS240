import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardFrame<T extends Cell> extends JFrame {
  private final Board<T> board;
  private final Panel panel;

  public BoardFrame(Board<T> board) {
    this.board = board;

    panel = new Panel();
    panel.setPreferredSize(new Dimension(500, 500));
    panel.addMouseListener(new ClickListener());
    add(panel, BorderLayout.CENTER);

    JButton resetButton = new JButton("reset");
    add(resetButton, BorderLayout.SOUTH);

    resetButton.addActionListener(event -> {
      board.reset();
      repaint();
    });

    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  class ClickListener extends MouseAdapter {
    @Override
    public void mouseReleased(MouseEvent event) {
      double cellWidth = panel.getWidth() / (double) board.width();
      double cellHeight = panel.getHeight() / (double) board.height();
      int column = (int) (event.getX() / cellWidth);
      int row = (int) (event.getY() / cellHeight);
      board.click(column, row);
      repaint();
    }
  }

  class Panel extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponents(g);

      // Paint background black.
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, getWidth(), getHeight());

      int cellWidth = getWidth() / board.width();
      int cellHeight = getHeight() / board.height();

      // Draw each cell according to its color.
      for (int r = 0; r < board.height(); ++r) {
        for (int c = 0; c < board.width(); ++c) {
          g.setColor(board.get(c, r).color());
          g.fillRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
        }
      }

      g.setColor(Color.GRAY);

      // Draw horizontal grid lines.
      for (int r = 1; r < board.height(); ++r) {
        g.drawLine(0, r * cellHeight, getWidth(), r * cellHeight);
      }

      // Draw vertical grid lines.
      for (int c = 1; c < board.width(); ++c) {
        g.drawLine(c * cellWidth, 0, c * cellWidth, getHeight());
      }
    }
  }
}