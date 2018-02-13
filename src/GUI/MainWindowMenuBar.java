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
import java.text.NumberFormat;
import java.util.Locale;

public class MainWindowMenuBar extends JMenuBar{

    private MainWindow  target;

    private JMenu       fileMenu;
    private JMenuItem   newBudgetItem;
    private JMenuItem   loadBudgetItem;
    private JMenuItem   saveBudgetItem;

    private JMenu       statsMenu;
    private JMenuItem   showStatsItem;

    private JMenu       helpMenu;
    private JMenuItem   aboutItem;

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

        fileMenu.add(newBudgetItem);
        fileMenu.add(loadBudgetItem);
        fileMenu.add(saveBudgetItem);

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
                target.setRecordsTableModel(new RecordTableModel());
            }
        });

        this.loadBudgetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fc.setDialogType(JFileChooser.OPEN_DIALOG);
                fc.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));

                int returnVal = fc.showOpenDialog(MainWindowMenuBar.this);
                File file = fc.getSelectedFile();

                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
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
                fc.setFileFilter(new FileNameExtensionFilter("Text File","txt"));

                // TODO: change .txt to .sb maybe?

                int returnVal = fc.showSaveDialog(MainWindowMenuBar.this);

                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        FileWriter fileWriter = new FileWriter(fc.getSelectedFile()+".txt");
                        fileWriter.write(RecordFormatter.toString(target.getRecordsTableModel().getRecords()));
                        fileWriter.close();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        this.showStatsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog statsWindow = new JDialog(target.getFrame(), "Budget Statistics");
                statsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                //statsWindow.setAlwaysOnTop(true);
                statsWindow.setSize(new Dimension(300, 200));
                statsWindow.setLocationRelativeTo(target.getFrame());
                statsWindow.setVisible(true);
                ((JPanel) statsWindow.getContentPane()).setBorder(new EmptyBorder(10,10,10,10));

                statsWindow.getContentPane().setLayout(new BoxLayout(statsWindow.getContentPane(), BoxLayout.PAGE_AXIS));
                JPanel panel = new JPanel();
                panel.add(new JLabel("Total money gained from salary: "));
                panel.add(Box.createHorizontalGlue());
                JFormattedTextField totalSalary = newCurrencyField();

                // TODO: finish this

                panel.add(totalSalary);
                panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
                statsWindow.add(panel);
            }
        });

        this.aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog helpWindow = new JDialog(target.getFrame(), "Help Window");
                helpWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                helpWindow.setAlwaysOnTop(true);
                helpWindow.setSize(new Dimension(300, 200));
                helpWindow.setLocationRelativeTo(target.getFrame());
                helpWindow.setVisible(true);
            }
        });
    }

    private JFormattedTextField newCurrencyField()
    {
        JFormattedTextField field = new JFormattedTextField(NumberFormat.getCurrencyInstance(Locale.GERMANY));
        field.setMaximumSize(new Dimension(500,20));
        field.setPreferredSize(new Dimension(500,20));
        field.setHorizontalAlignment(SwingConstants.RIGHT);
        field.setEditable(false);

        return field;
    }
}
