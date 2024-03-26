package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomePage extends CoreWarJFrame {
    private FileControler pageControl;

    public WelcomePage() {
        super("Welcome page");
        GestionPage gp = new GestionPage(this);
        AddCodePage addCodePage = new AddCodePage(this);
        pageControl = new FileControler(this);

        ImageIcon coreWarIcon = new ImageIcon("graphique/image/Core_Wars_banner.jpg");

        Image image = coreWarIcon.getImage();
        Dimension size = getSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        // Redimensionnement de l'image
        int newWidth = width / 2; // Largeur souhaitée
        int newHeight = height / 3; // Hauteur souhaitée
        Image newImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);

        JLabel imageLabel = new JLabel(newImageIcon);
        imageLabel.setBackground(new Color(47, 48, 46));

        JButton simulatorButton = new JButton("simuler");
        styleText(simulatorButton, new Color(30, 177, 255), new Font("Comic Sans MS", Font.BOLD, 50), orangeF);

        JButton gerer = new JButton("gerer");
        styleText(gerer, new Color(255, 232, 145), new Font("Comic Sans MS", Font.BOLD, 50), Color.BLACK);

        JButton writeCode = new JButton("ajouter");
        styleText(writeCode, new Color(255, 232, 145), new Font("Comic Sans MS", Font.BOLD, 50), Color.BLACK);

        JButton algo = new JButton("algorithme");
        styleText(algo, new Color(210, 210, 210), new Font("Comic Sans MS", Font.BOLD, 50), Color.BLACK);

        WelcomePage wp1 = this;

        simulatorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setPagControle(new FileControler(wp1));
                pageControl.setVisible(true);
            }
        });

        writeCode.addActionListener(new NavigationListener(this, addCodePage));

        gerer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                gp.miseAJour();
                gp.setVisible(true);
            }
        });

        algo.addActionListener(new NavigationListener(this, new SelectFileFrame(this)));

        JPanel conteneurButton = new JPanel(new GridLayout(4, 1, 0, height / 100));
        conteneurButton.setBorder(BorderFactory.createEmptyBorder(10, width / 4, 10, width / 4));

        conteneurButton.setBackground(blackF);
        conteneurButton.add(simulatorButton);
        conteneurButton.add(gerer);
        conteneurButton.add(writeCode);
        conteneurButton.add(algo);

        getContentPane().add(imageLabel, BorderLayout.NORTH);

        getContentPane().add(conteneurButton, BorderLayout.CENTER);
    }

    public void setPagControle(FileControler newPage) {
        this.pageControl = newPage;
    }
}