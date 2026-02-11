import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class BezierPanel extends JPanel {
   
    private JSpinner spinP1X, spinP1Y, spinP2X, spinP2Y;
    private JSpinner spinP3X, spinP3Y, spinP4X, spinP4Y;
    private JButton btnDraw;
    private DrawingCanvas canvas;

    public BezierPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
    }

    private void initComponents() {
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(200, 0));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Параметри"));

        spinP1X = createSpinner(50);
        spinP1Y = createSpinner(400);
        spinP2X = createSpinner(150);
        spinP2Y = createSpinner(100);
        spinP3X = createSpinner(550);
        spinP3Y = createSpinner(100);
        spinP4X = createSpinner(650);
        spinP4Y = createSpinner(400);

        controlPanel.add(createPointPanel("P1 (X1, Y1):", spinP1X, spinP1Y));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createPointPanel("P2 (X2, Y2):", spinP2X, spinP2Y));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createPointPanel("P3 (X3, Y3):", spinP3X, spinP3Y));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createPointPanel("P4 (X4, Y4):", spinP4X, spinP4Y));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        btnDraw = new JButton("Намалювати");
        btnDraw.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDraw.addActionListener(e -> canvas.repaint());
        controlPanel.add(btnDraw);

        canvas = new DrawingCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(controlPanel, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }

    private JSpinner createSpinner(int value) {
        SpinnerNumberModel model = new SpinnerNumberModel(value, 0, 1000, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setMaximumSize(new Dimension(80, 25));
        return spinner;
    }

    private JPanel createPointPanel(String label, JSpinner spinnerX, JSpinner spinnerY) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPoint = new JLabel(label);
        lblPoint.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblPoint);

        JPanel coordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        coordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coordPanel.add(new JLabel("X:"));
        coordPanel.add(spinnerX);
        coordPanel.add(new JLabel("Y:"));
        coordPanel.add(spinnerY);

        panel.add(coordPanel);

        return panel;
    }

    private class DrawingCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            float x1 = ((Number) spinP1X.getValue()).floatValue();
            float y1 = ((Number) spinP1Y.getValue()).floatValue();
            float x2 = ((Number) spinP2X.getValue()).floatValue();
            float y2 = ((Number) spinP2Y.getValue()).floatValue();
            float x3 = ((Number) spinP3X.getValue()).floatValue();
            float y3 = ((Number) spinP3Y.getValue()).floatValue();
            float x4 = ((Number) spinP4X.getValue()).floatValue();
            float y4 = ((Number) spinP4Y.getValue()).floatValue();

            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[] { 5, 5 }, 0));
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            g2d.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
            g2d.drawLine((int) x3, (int) y3, (int) x4, (int) y4);

            g2d.setColor(Color.RED);
            int pointSize = 8;
            g2d.fillOval((int) (x1 - pointSize / 2), (int) (y1 - pointSize / 2),
                    pointSize, pointSize);
            g2d.fillOval((int) (x2 - pointSize / 2), (int) (y2 - pointSize / 2),
                    pointSize, pointSize);
            g2d.fillOval((int) (x3 - pointSize / 2), (int) (y3 - pointSize / 2),
                    pointSize, pointSize);
            g2d.fillOval((int) (x4 - pointSize / 2), (int) (y4 - pointSize / 2),
                    pointSize, pointSize);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("P1", (int) (x1 + 10), (int) y1);
            g2d.drawString("P2", (int) (x2 + 10), (int) y2);
            g2d.drawString("P3", (int) (x3 + 10), (int) y3);
            g2d.drawString("P4", (int) (x4 + 10), (int) y4);

            drawBezierCurve(g2d, x1, y1, x2, y2, x3, y3, x4, y4);
        }

        private void drawBezierCurve(Graphics2D g2d, float x1, float y1,
                float x2, float y2, float x3, float y3,
                float x4, float y4) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2));

            Path2D.Float path = new Path2D.Float();
            path.moveTo(x1, y1);

            path.curveTo(x2, y2, x3, y3, x4, y4);

            g2d.draw(path);

        }
    }
}
