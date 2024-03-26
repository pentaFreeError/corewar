package graphique;

import javax.swing.*;
import java.awt.*;

public class CoreWarJFrame extends JFrame {
    protected static Color orangeF = new Color(247, 142, 5);
    protected static Color blackF = new Color(47, 48, 46);

    public CoreWarJFrame(String title) {
        super();

        this.setTitle(title);
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("graphique/image/coreWarLogo.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(blackF);
        this.setBackground(blackF);

        UIManager.put("OptionPane.background", orangeF);
        UIManager.put("Panel.background", orangeF);
        UIManager.put("OptionPane.messageForeground", blackF);
        this.setBackground(blackF);
    }

    public static JButton createWarPageButton(String title, int size) {
        JButton newButton = new JButton(title);
        styleText(newButton, new Color(30, 177, 255), new Font("Comic Sans MS", Font.BOLD, size), orangeF);
        return newButton;
    }

    public static JButton createWarPageButton(String title) {
        return createWarPageButton(title, 30);
    }

    public static JButton getBackJButton(CoreWarJFrame page1, CoreWarJFrame page2, int size) {
        JButton back = createWarPageButton("BACK", size);
        back.addActionListener(new NavigationListener(page1, page2));
        return back;
    }

    public static JButton getBackJButton(CoreWarJFrame page1, CoreWarJFrame page2) {
        return getBackJButton(page1, page2, 30);
    }

    public static void styleText(JComponent component, Color backGround, Font font, Color foreGround) {
        component.setBackground(backGround);
        component.setFont(font);
        component.setForeground(foreGround);
    }

    public static void styleText(JComponent component, int fontSize) {
        styleText(component, blackF, new Font("Comic Sans MS", Font.BOLD, fontSize), orangeF);
    }

    public static void styleText(JComponent component) {
        styleText(component, 30);
    }
}
