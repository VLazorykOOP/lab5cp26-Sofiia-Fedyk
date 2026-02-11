import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class FractalPanel extends JPanel {
    
    private JSpinner spinSideA, spinSideB, spinOrder;
    private JButton btnDraw;
    private FractalCanvas canvas;

    public FractalPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
    }

    private void initComponents() {
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(200, 0));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Параметри"));

        spinSideA = createSpinner(150, 10, 500, 1);
        spinSideB = createSpinner(100, 10, 500, 1);
        spinOrder = createSpinner(8, 1, 12, 1);

        controlPanel.add(createControlPanel("Сторона A:", spinSideA));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createControlPanel("Сторона B:", spinSideB));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(createControlPanel("Порядок K:", spinOrder));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        btnDraw = new JButton("Намалювати");
        btnDraw.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDraw.addActionListener(e -> canvas.repaint());
        controlPanel.add(btnDraw);

        JTextArea hint = new JTextArea(
            "Підказка:\n" +
            "Порядок 1-4: просте дерево\n" +
            "Порядок 5-8: оптимально\n" +
            "Порядок 9-12: детальне\n" +
            "(може працювати повільно)"
        );
        hint.setEditable(false);
        hint.setOpaque(false);
        hint.setFont(new Font("Arial", Font.PLAIN, 10));
        hint.setWrapStyleWord(true);
        hint.setLineWrap(true);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(hint);

        canvas = new FractalCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(controlPanel, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }

    private JSpinner createSpinner(int value, int min, int max, int step) {
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        JSpinner spinner = new JSpinner(model);
        spinner.setMaximumSize(new Dimension(150, 25));
        return spinner;
    }

    private JPanel createControlPanel(String label, JSpinner spinner) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(spinner);

        return panel;
    }

    private class FractalCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                RenderingHints.VALUE_ANTIALIAS_ON);

            float a = ((Number) spinSideA.getValue()).floatValue();
            float b = ((Number) spinSideB.getValue()).floatValue();
            int order = ((Number) spinOrder.getValue()).intValue();

            int width = getWidth();
            int height = getHeight();
            float startX = width / 2.0f;
            float startY = height - 50;

            drawArchimedeanTree(g2d, startX, startY, a, b, -90, order);
        }

        private void drawArchimedeanTree(Graphics2D g2d, float x, float y, 
                                        float a, float b, float angle, int order) {
            
            if (order == 0 || a < 1 || b < 1) {
                return;
            }

            double radAngle = Math.toRadians(angle);
            float x2 = x + (float)(b * Math.cos(radAngle));
            float y2 = y + (float)(b * Math.sin(radAngle));

            double perpAngle = Math.toRadians(angle + 90);
            float dx = (float)(a / 2 * Math.cos(perpAngle));
            float dy = (float)(a / 2 * Math.sin(perpAngle));

            Path2D.Float rect = new Path2D.Float();
            rect.moveTo(x - dx, y - dy);
            rect.lineTo(x + dx, y + dy);
            rect.lineTo(x2 + dx, y2 + dy);
            rect.lineTo(x2 - dx, y2 - dy);
            rect.closePath();

            Color treeColor;
            if (order > 4) {
                treeColor = new Color(139, 90, 43);  
            } else if (order > 2) {
                treeColor = new Color(107, 142, 35); 
            } else {
                treeColor = new Color(34, 139, 34); 
            }

            g2d.setColor(treeColor);
            g2d.fill(rect);

            g2d.setColor(new Color(80, 50, 20));
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(rect);

            if (order > 1) {
                float newA = (float)(a / Math.sqrt(2));
                float newB = (float)(b / Math.sqrt(2));

                drawArchimedeanTree(g2d, x2, y2, newA, newB, angle - 45, order - 1);

                drawArchimedeanTree(g2d, x2, y2, newA, newB, angle + 45, order - 1);
            }
        }
    }
}
