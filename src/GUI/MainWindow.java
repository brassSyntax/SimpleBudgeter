package GUI;

import Record.RecordType;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.tableeditors.DateTableEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class MainWindow {

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

    private RecordTableModel tableModel;


    public MainWindow() {
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

        setupListeners();

        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(createRecordBtn);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(fromLabel);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(fromDatePicker);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(untilLabel);
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(untilDatePicker);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(balanceLabel);
        buttonPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        tableModel = new RecordTableModel();

        recordsTable = new JTable(tableModel);
        recordsTable.setDefaultEditor(LocalDate.class, new DateTableEditor());
        recordsTable.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        recordsTable.setBorder(new EmptyBorder(5, 5, 5, 5));
        recordsTable.getTableHeader().setReorderingAllowed(false);

        setupColumns();
        setupSorter();

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(buttonPane);
        contentPane.add(new JScrollPane(recordsTable));
    }

    public RecordTableModel getRecordsTableModel() {
        return tableModel;
    }

    public void setRecordsTableModel(RecordTableModel model) {
        this.recordsTable.setModel(model);
        this.tableModel = model;
        setupColumns();
        setupSorter();
    }

    public static void main(String[] Args) {
        ImageIcon logo = new ImageIcon("plogo2.png");

        JFrame frame = new JFrame("Simple Budgeter");
        MainWindow gui = new MainWindow();
        frame.setContentPane(gui.contentPane);
        frame.setJMenuBar(new MainWindowMenuBar(gui));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setIconImage(logo.getImage());
        frame.setVisible(true);
    }

    public void setupListeners() {
        createRecordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRecord();

            }
        });
    }

    private void setupColumns() {
        // Needs to be called again when new TableModel is set, but might be redundant with custom TableColumnModel??

        TableColumn dateColumn = recordsTable.getColumnModel().getColumn(1);
        dateColumn.setCellEditor(recordsTable.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(recordsTable.getDefaultRenderer(LocalDate.class));

        TableColumn typeColumn = recordsTable.getColumnModel().getColumn(2);
        JComboBox<RecordType> typeComboBox = new JComboBox<>();
        typeComboBox.addItem(RecordType.SALARY);
        typeComboBox.addItem(RecordType.INSURANCE);
        typeComboBox.addItem(RecordType.TAXES);
        typeComboBox.addItem(RecordType.LEISURE);
        typeComboBox.addItem(RecordType.SAVINGS);
        typeComboBox.addItem(RecordType.SUPPLIES);
        typeComboBox.addItem(RecordType.EMERGENCY);
        typeColumn.setCellEditor(new DefaultCellEditor(typeComboBox));
    }

    private void setupSorter() {

        TableRowSorter<RecordTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setSortsOnUpdates(true);

        // Not sure if explicit Comparator is useful here or not

        /*Comparator<LocalDate> localDateComparator = new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                if(o1.isBefore(o2)) return -1;
                else if(o1.isEqual(o2)) return 0;
                else return 1;
            }
        };

        sorter.setComparator(1,localDateComparator);*/

        RowFilter<RecordTableModel, Integer> filter = new RowFilter<RecordTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends RecordTableModel, ? extends Integer> entry) {
                int modelRow = entry.getIdentifier();
                LocalDate date = (LocalDate) entry.getModel().getValueAt(modelRow, 1);

                return date.isEqual(LocalDate.now());
            }
        };

        sorter.setRowFilter(filter);
        recordsTable.setRowSorter(sorter);
    }
}
