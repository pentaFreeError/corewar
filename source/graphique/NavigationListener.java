package graphique;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;

public class NavigationListener implements ActionListener {

    private JFrame page1;
    private JFrame page2;

    public NavigationListener(JFrame page1, JFrame page2) {
        this.page1 = page1;
        this.page2 = page2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        page1.setVisible(false);
        page2.setVisible(true);
    }

}
