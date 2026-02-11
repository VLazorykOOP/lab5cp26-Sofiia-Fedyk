import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private BezierPanel bezierPanel;
    private FractalPanel fractalPanel;

    public MainFrame() {
        setTitle("Лабораторна робота №5 - Графіка");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        bezierPanel = new BezierPanel();
        fractalPanel = new FractalPanel();

        tabbedPane.addTab("Крива Безьє", bezierPanel);
        tabbedPane.addTab("Дерево Архімеда", fractalPanel);

        add(tabbedPane);
    }
}
