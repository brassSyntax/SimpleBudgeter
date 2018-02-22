package GUI;

import Record.RecordType;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

public class MainWindow {

    private JFrame frame;
    private JPanel contentPane;
    private JPanel buttonPane;
    private JButton createRecordBtn;
    private DatePicker fromDatePicker;
    private DatePicker untilDatePicker;
    private JLabel fromLabel;
    private JLabel untilLabel;
    private JLabel balanceLabel;
    private JFormattedTextField periodBalance;
    private JTable recordsTable;

    private RecordTableModel tableModel;

    private final static Color POSITIVE = new Color(0,153,0);
    private final static Color NEUTRAL = new Color(55, 55, 55);
    private final static Color NEGATIVE = new Color(255,0,0);

    private final static String LOGO_PATH = "plogo2.png";


    public MainWindow() {
        ImageIcon logo = new ImageIcon(LOGO_PATH);

        createRecordBtn = new JButton("New Record");
        fromLabel = new JLabel("From:");
        fromLabel.setLabelFor(fromDatePicker);
        fromDatePicker = new DatePicker();
        fromDatePicker.setMaximumSize(new Dimension(155, 24));
        untilLabel = new JLabel("Until:");
        untilLabel.setLabelFor(untilDatePicker);
        untilDatePicker = new DatePicker();
        untilDatePicker.setMaximumSize(new Dimension(155, 24));
        balanceLabel = new JLabel("Period Net Change:");
        balanceLabel.setFont(new Font("Dialog", Font.PLAIN,12));
        balanceLabel.setLabelFor(periodBalance);

        periodBalance = new JFormattedTextField(NumberFormat.getCurrencyInstance(Locale.GERMANY));
        periodBalance.setEditable(false);
        periodBalance.setColumns(6);
        periodBalance.setForeground(NEUTRAL);
        periodBalance.setMaximumSize(new Dimension(2000,20));

        DatePickerSettings fromDateSettings = new DatePickerSettings();
        DatePickerSettings untilDateSettings = new DatePickerSettings();
        fromDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        untilDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        fromDatePicker.setSettings(fromDateSettings);
        untilDatePicker.setSettings(untilDateSettings);

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
        buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPane.add(periodBalance);
        buttonPane.setBorder(new EmptyBorder(20, 20, 20, 20));

        tableModel = new RecordTableModel();

        DateTableEditor dateTableEditor = new DateTableEditor();
        dateTableEditor.getDatePickerSettings().setAllowKeyboardEditing(false);

        recordsTable = new JTable(tableModel);
        recordsTable.setDefaultEditor(LocalDate.class, dateTableEditor);
        recordsTable.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        recordsTable.setDefaultEditor(Double.class, new CurrencyCellEditor(new JFormattedTextField()));
        recordsTable.setBorder(new EmptyBorder(5, 5, 5, 5));
        recordsTable.getTableHeader().setReorderingAllowed(false);

        setupKeybindings();
        setupColumns();
        setupSorter();
        setupListeners();

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(buttonPane);
        contentPane.add(new JScrollPane(recordsTable));

        frame = new JFrame("Simple Budgeter");
        frame.setContentPane(contentPane);
        frame.setJMenuBar(new MainWindowMenuBar(this));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(logo.getImage());
        frame.setVisible(true);
    }

    public JFrame getFrame()
    {
        return this.frame;
    }

    public RecordTableModel getRecordsTableModel() {
        return tableModel;
    }

    public void setRecordsTableModel(RecordTableModel model) {
        fromDatePicker.clear();
        untilDatePicker.clear();

        this.recordsTable.setModel(model);
        this.tableModel = model;
        setupColumns();
        setupSorter();
    }

    public static void main(String[] Args) {
        new MainWindow();
    }

    public void setupListeners() {
        createRecordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRecord();
            }
        });

        fromDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                recordsTable.getRowSorter().rowsUpdated(0,tableModel.getRowCount() - 1);
            }
        });

        untilDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                recordsTable.getRowSorter().rowsUpdated(0,tableModel.getRowCount() - 1);
            }
        });

        // RowSorterListener moved to setupSorter()
    }

    private void setupKeybindings()
    {
        class DeleteRowsAction extends AbstractAction
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.removeRecords(recordsTable.getSelectedRows());
            }
        }

        recordsTable.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"),"deleteRow");
        recordsTable.getActionMap().put("deleteRow", new DeleteRowsAction());
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

        TableRowSorter<RecordTableModel> tableSorter = new TableRowSorter<>(tableModel);
        tableSorter.setSortsOnUpdates(true);

        tableSorter.addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent e) {

                // Table view's balance sum and color marking
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        double balance = 0.;

                        int rc = recordsTable.getRowCount();

                        if (rc > 0) {
                            for(int i = 0; i < rc; i++)
                            {
                                double value = (double) recordsTable.getValueAt(i,3);
                                RecordType type = (RecordType) recordsTable.getValueAt(i,2);

                                balance += value * type.sign;
                            }

                            if(balance > 0)
                            {
                                periodBalance.setForeground(POSITIVE);
                            }
                            else if(balance < 0)
                            {
                                periodBalance.setForeground(NEGATIVE);
                            }
                            else periodBalance.setForeground(NEUTRAL);

                            periodBalance.setValue(balance);
                        }
                        else periodBalance.setText("");
                    }
                });
            }
        });

        // Not sure if explicit Comparator is useful here or not

        /*Comparator<LocalDate> localDateComparator = new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                if(o1.isBefore(o2)) return -1;
                else if(o1.isEqual(o2)) return 0;
                else return 1;
            }
        };

        tableSorter.setComparator(1,localDateComparator);*/

        RowFilter<RecordTableModel, Integer> filter = new RowFilter<RecordTableModel, Integer>() {
            @SuppressWarnings({"ConstantConditions", "SimplifiableIfStatement"})
            @Override
            public boolean include(Entry<? extends RecordTableModel, ? extends Integer> entry) {
                int modelRow = entry.getIdentifier();
                LocalDate entryDate = (LocalDate) entry.getModel().getValueAt(modelRow, 1);

                // TODO: setup date VETO policies

                if(entryDate == null) return true;

                LocalDate fromDate = fromDatePicker.getDate();
                LocalDate untilDate = untilDatePicker.getDate();

                if(fromDate != null && untilDate == null)
                {
                    return entryDate.compareTo(fromDate) >= 0;
                }
                else if(fromDate == null && untilDate != null)
                {
                    return entryDate.compareTo(untilDate) < 0;
                }
                else if(fromDate != null && untilDate != null)
                {
                    return (entryDate.compareTo(fromDate) >= 0) && (entryDate.compareTo(untilDate) < 0);
                }
                else return true;
            }
        };

        tableSorter.setRowFilter(filter);
        recordsTable.setRowSorter(tableSorter);
    }
}
