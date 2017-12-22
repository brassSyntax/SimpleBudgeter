package GUI;

import Record.RecordFormatter;
import Record.RecordTableReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainWindowMenuBar extends JMenuBar{

    private MainWindow  target;

    private JMenu       fileMenu;
    private JMenuItem   newBudgetItem;
    private JMenuItem   loadBudgetItem;

    private JMenu       statsMenu;
    private JMenuItem   showStatsItem;

    private JMenu       helpMenu;
    private JMenuItem   aboutItem;

    public MainWindowMenuBar(MainWindow target) {
        this.target = target;

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

        setupListeners();
        this.add(fileMenu);
        this.add(statsMenu);
        this.add(helpMenu);
    }

    private void setupListeners()
    {
        this.newBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                target.getRecordsTable().setModel(new RecordTableModel());
            }
        });

        this.loadBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

                int returnVal = fc.showOpenDialog(MainWindowMenuBar.this);
                File file = fc.getSelectedFile();

                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    RecordTableModel model;

                    try {
                        model = new RecordTableModel(RecordFormatter.toRecordObjects(RecordTableReader.getInstance().readFile(file)));
                        target.getRecordsTable().setModel(model);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(fileMenu, "Test");
                    }
                }
            }
        });

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
