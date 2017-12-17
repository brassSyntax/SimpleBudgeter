package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar extends JMenuBar{

    private JMenu       fileMenu;
    private JMenuItem   newBudgetItem;
    private JMenuItem   loadBudgetItem;

    private JMenu       statsMenu;
    private JMenuItem   showStatsItem;

    private JMenu       helpMenu;
    private JMenuItem   aboutItem;

    public MainMenuBar() {
        fileMenu = new JMenu("File");
        newBudgetItem = new JMenuItem("New Budget");
        loadBudgetItem = new JMenuItem("Load Budget");

        statsMenu = new JMenu("Statistics");
        showStatsItem = new JMenuItem("Show Statistics");

        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");

        fileMenu.add(newBudgetItem);
        fileMenu.add(loadBudgetItem);

        statsMenu.add(showStatsItem);

        helpMenu.add(aboutItem);

        setUpListeners();
        this.add(fileMenu);
        this.add(statsMenu);
        this.add(helpMenu);
    }

    private void setUpListeners()
    {
        this.aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame helpWindow = new JFrame("Help Window");
                helpWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                helpWindow.setSize(new Dimension(300, 200));
                helpWindow.setVisible(true);
            }
        });


    }
}
