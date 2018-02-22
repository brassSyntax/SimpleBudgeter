package GUI;

import Record.Record;
import Record.RecordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.Format;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class BudgetStatsDialog extends JDialog {

    private enum EntryType {
        PROFITS(1),
        EXPENSES(-1);

        public final int sign;

        EntryType(int sign) {
            this.sign = sign;
        }
    }

    private MainWindow target;

    public BudgetStatsDialog(MainWindow target, String title) {
        super(target.getFrame(), title);

        this.target = target;

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //this.setAlwaysOnTop(true);
        this.setSize(new Dimension(300, 200));
        this.setLocationRelativeTo(target.getFrame());
        this.setVisible(true);
        ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));


        JPanel totalNetPanel = new JPanel();
        totalNetPanel.setLayout(new BoxLayout(totalNetPanel, BoxLayout.LINE_AXIS));
        totalNetPanel.add(new JLabel("Total Net Change: "));
        totalNetPanel.add(Box.createHorizontalGlue());
        JFormattedTextField totalNetField = newFormattedField(NumberFormat.getCurrencyInstance(Locale.GERMANY));
        totalNetField.setValue(getTotalNetChange());
        totalNetPanel.add(totalNetField);

        JPanel totalRecordsPanel = new JPanel();
        totalRecordsPanel.setLayout(new BoxLayout(totalRecordsPanel, BoxLayout.LINE_AXIS));
        totalRecordsPanel.add(new JLabel("Total Records: "));
        totalRecordsPanel.add(Box.createHorizontalGlue());
        JFormattedTextField totalRecordsField = newFormattedField();
        totalRecordsField.setValue(getTotalRecords());
        totalRecordsPanel.add(totalRecordsField);

        JPanel mostExpEntryPanel = new JPanel();
        mostExpEntryPanel.setLayout(new BoxLayout(mostExpEntryPanel, BoxLayout.LINE_AXIS));
        mostExpEntryPanel.add(new JLabel("Most Expensive Entry: "));
        mostExpEntryPanel.add(Box.createHorizontalGlue());
        JFormattedTextField mostExpEntryField = newFormattedField();
        mostExpEntryField.setValue(getMostEntry(EntryType.EXPENSES));
        mostExpEntryPanel.add(mostExpEntryField);

        JPanel mostProfEntryPanel = new JPanel();
        mostProfEntryPanel.setLayout(new BoxLayout(mostProfEntryPanel, BoxLayout.LINE_AXIS));
        mostProfEntryPanel.add(new JLabel("Most Profitable Entry: "));
        mostProfEntryPanel.add(Box.createHorizontalGlue());
        JFormattedTextField mostProfEntryField = newFormattedField();
        mostProfEntryField.setValue(getMostEntry(EntryType.PROFITS));
        mostProfEntryPanel.add(mostProfEntryField);

        JPanel mostExpMonthPanel = new JPanel();
        mostExpMonthPanel.setLayout(new BoxLayout(mostExpMonthPanel, BoxLayout.LINE_AXIS));
        mostExpMonthPanel.add(new JLabel("Most Expensive Month of " + LocalDate.now().getYear() + ": "));
        mostExpMonthPanel.add(Box.createHorizontalGlue());
        JFormattedTextField mostExpMonthField = newFormattedField();
        mostExpMonthField.setValue(getMostMonth(EntryType.EXPENSES));
        mostExpMonthPanel.add(mostExpMonthField);

        JPanel mostProfMonthPanel = new JPanel();
        mostProfMonthPanel.setLayout(new BoxLayout(mostProfMonthPanel, BoxLayout.LINE_AXIS));
        mostProfMonthPanel.add(new JLabel("Most Profitable Month of " + LocalDate.now().getYear() + ": "));
        mostProfMonthPanel.add(Box.createHorizontalGlue());
        JFormattedTextField mostProfMonthField = newFormattedField();
        mostProfMonthField.setValue(getMostMonth(EntryType.PROFITS));
        mostProfMonthPanel.add(mostProfMonthField);

        this.getContentPane().add(totalNetPanel);
        this.getContentPane().add(totalRecordsPanel);
        this.getContentPane().add(mostExpEntryPanel);
        this.getContentPane().add(mostProfEntryPanel);
        this.getContentPane().add(mostExpMonthPanel);
        this.getContentPane().add(mostProfMonthPanel);
    }

    private double getTotalNetChange() {
        double sum = 0.;

        for (Record i : target.getRecordsTableModel().getRecords()) {
            if (i.getType() != RecordType.UNDEFINED) {
                sum += i.getAmount() * i.getType().sign;
            }
        }

        return sum;
    }

    private int getTotalRecords() {
        return target.getRecordsTableModel().getRecords().size();
    }

    private String getMostEntry(EntryType type) {
        Record maxEntry = new Record();

        for (Record i : target.getRecordsTableModel().getRecords()) {
            if (i.getType().sign == type.sign) {
                if (i.getAmount() > maxEntry.getAmount()) {
                    maxEntry = i;
                }
            }
        }

        if (maxEntry.getType() != null) {
            return maxEntry.getDate() + ": " + maxEntry.getAmount() + " €";
        } else return "N/A";
    }

    private String getMostMonth(EntryType type) {
        int currentYear = LocalDate.now().getYear();

        double[] Months = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (Record i : target.getRecordsTableModel().getRecords()) {
            if (i.getDate().getYear() == currentYear && i.getType().sign == type.sign) {
                switch (i.getDate().getMonth().getValue()) {
                    case 1:
                        Months[0] += i.getAmount();
                        break;
                    case 2:
                        Months[1] += i.getAmount();
                        break;
                    case 3:
                        Months[2] += i.getAmount();
                        break;
                    case 4:
                        Months[3] += i.getAmount();
                        break;
                    case 5:
                        Months[4] += i.getAmount();
                        break;
                    case 6:
                        Months[5] += i.getAmount();
                        break;
                    case 7:
                        Months[6] += i.getAmount();
                        break;
                    case 8:
                        Months[7] += i.getAmount();
                        break;
                    case 9:
                        Months[8] += i.getAmount();
                        break;
                    case 10:
                        Months[9] += i.getAmount();
                        break;
                    case 11:
                        Months[10] += i.getAmount();
                        break;
                    case 12:
                        Months[11] += i.getAmount();
                        break;
                }
            }
        }

        Integer month = null;

        double max = 0.;

        for (int i = 0; i < 12; i++) {
            if (Months[i] > max) {
                max = Months[i];
                month = i;
            }
        }

        if (month != null) {
            return Month.of(month + 1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        } else return "N/A";
    }


    private JFormattedTextField newFormattedField() {
        JFormattedTextField field = new JFormattedTextField();
        field.setBorder(null);
        field.setMaximumSize(new Dimension(500, 20));
        field.setPreferredSize(new Dimension(500, 20));
        field.setHorizontalAlignment(SwingConstants.RIGHT);
        field.setEditable(false);

        return field;
    }

    private JFormattedTextField newFormattedField(Format format) {
        JFormattedTextField field = new JFormattedTextField(format);
        field.setBorder(null);
        field.setMaximumSize(new Dimension(500, 20));
        field.setPreferredSize(new Dimension(500, 20));
        field.setHorizontalAlignment(SwingConstants.RIGHT);
        field.setEditable(false);

        return field;
    }
}
