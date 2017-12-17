package GUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;

public class Main {

    private JPanel contentPane;
    private JPanel buttonPane;
    private JPanel listPane;
    private JButton createRecordBtn;
    private DatePicker fromDatePicker;
    private DatePicker untilDatePicker;
    private JLabel fromLabel;
    private JLabel untilLabel;
    private JLabel balanceLabel;
    private JLabel periodBalance;
    private JTable recordsTable;

    private RecordEditWindow editWindow;


    public Main() {
        createRecordBtn = new JButton("New Record");
        fromLabel = new JLabel("From:");
        fromDatePicker = new DatePicker();
        fromDatePicker.setMaximumSize(new Dimension(155, 24));
        untilLabel = new JLabel("Until:");
        untilDatePicker = new DatePicker();
        untilDatePicker.setMaximumSize(new Dimension(155, 24));
        balanceLabel = new JLabel("Period Net Change:");

        DatePickerSettings fromDateSettings = new DatePickerSettings();
        DatePickerSettings untilDateSettings = new DatePickerSettings();
        fromDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        untilDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        fromDatePicker.setSettings(fromDateSettings);
        untilDatePicker.setSettings(untilDateSettings);

        setUpListeners();

        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(createRecordBtn);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(fromLabel);
        buttonPane.add(Box.createRigidArea(new Dimension(5,0)));
        buttonPane.add(fromDatePicker);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(untilLabel);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(untilDatePicker);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(balanceLabel);
        buttonPane.setBorder(new EmptyBorder(20,20,20,20));

        recordsTable = new JTable(20,4);
        recordsTable.setBorder(new EmptyBorder(5,5,5,5));

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        contentPane.add(buttonPane);
        contentPane.add(recordsTable);
    }

    public static void main(String[] Args)
    {
        JFrame frame = new JFrame("Simple Budgeter");
        frame.setContentPane(new Main().contentPane);
        frame.setJMenuBar(new MainMenuBar());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setMinimumSize(new Dimension (800, 600));
        frame.setVisible(true);
    }

    public void setUpListeners()
    {
        createRecordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editWindow == null)
                {
                    editWindow = new RecordEditWindow();
                    createRecordBtn.setEnabled(false);

                    editWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);

                            editWindow = null;
                            createRecordBtn.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
