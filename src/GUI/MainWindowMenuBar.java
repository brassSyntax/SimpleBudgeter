package GUI;

import Record.RecordFormatter;
import Record.RecordTableReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class MainWindowMenuBar extends JMenuBar {

    private MainWindow target;

    private JMenu fileMenu;
    private JMenuItem newBudgetItem;
    private JMenuItem loadBudgetItem;
    private JMenuItem saveBudgetItem;

    private JMenu statsMenu;
    private JMenuItem showStatsItem;

    private JMenu helpMenu;
    private JMenuItem aboutItem;
    private JMenuItem reportItem;

    public final static String HELP_PATH = "\\help.html";

    public MainWindowMenuBar(MainWindow target) {
        this.target = target;

        fileMenu = new JMenu("File");
        newBudgetItem = new JMenuItem("New Budget");
        loadBudgetItem = new JMenuItem("Load Budget");
        saveBudgetItem = new JMenuItem("Save As...");

        statsMenu = new JMenu("Statistics");
        showStatsItem = new JMenuItem("Show Statistics");

        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        reportItem = new JMenuItem("Generate Report");

        fileMenu.add(newBudgetItem);
        fileMenu.add(loadBudgetItem);
        fileMenu.add(saveBudgetItem);

        statsMenu.add(showStatsItem);

        helpMenu.add(aboutItem);
        helpMenu.add(reportItem);

        setupListeners();
        this.add(fileMenu);
        this.add(statsMenu);
        this.add(helpMenu);
    }

    private void setupListeners() {
        this.newBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                target.setRecordsTableModel(new RecordTableModel());
            }
        });

        this.loadBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fc.setDialogType(JFileChooser.OPEN_DIALOG);
                fc.setFileFilter(new FileNameExtensionFilter("Simple Budget", "sb"));

                int returnVal = fc.showOpenDialog(MainWindowMenuBar.this);
                File file = fc.getSelectedFile();

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    RecordTableModel model;

                    try {
                        model = new RecordTableModel(RecordFormatter.toRecordObjects(RecordTableReader.getInstance().readFile(file)));
                        target.setRecordsTableModel(model);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(fileMenu, "Test");
                    }
                }
            }
        });

        this.saveBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fc.setDialogType(JFileChooser.SAVE_DIALOG);
                fc.setFileFilter(new FileNameExtensionFilter("Simple Budget", "sb"));

                int returnVal = fc.showSaveDialog(MainWindowMenuBar.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fileWriter = new FileWriter(fc.getSelectedFile() + ".sb");
                        fileWriter.write(RecordFormatter.toString(target.getRecordsTableModel().getRecords()));
                        fileWriter.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        this.showStatsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BudgetStatsDialog(target, "Budget Statistics");
            }
        });

        this.aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog helpWindow = new JDialog(target.getFrame(), "Help Window");
                helpWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                helpWindow.setAlwaysOnTop(true);
                helpWindow.setSize(new Dimension(650, 250));
                helpWindow.setLocationRelativeTo(target.getFrame());
                helpWindow.setVisible(true);

                JEditorPane helpPane;

                try {
                    URL helpURL = Paths.get(System.getProperty("user.dir") + HELP_PATH).toUri().toURL();
                    helpPane = new JEditorPane(helpURL);
                } catch (IOException ex) {
                    ex.printStackTrace();

                    helpPane = new JEditorPane();
                    helpPane.setText("ERROR: MISSING HELP FILE");
                }

                helpPane.setBorder(new EmptyBorder(10, 10, 10, 10));
                helpPane.setBackground(UIManager.getColor("Panel.background"));
                helpPane.setEditable(false);
                // this overrides forced Times New Roman on the pane to swing defaults?
                helpPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
                helpWindow.getContentPane().add(helpPane);
            }
        });

        this.reportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BudgetReportWriter(target);
            }
        });
    }
}
