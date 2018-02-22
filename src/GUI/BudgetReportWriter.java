package GUI;

import Record.Record;
import Record.RecordType;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class BudgetReportWriter {

    private MainWindow target;

    public BudgetReportWriter(MainWindow target) {
        this.target = target;

        ArrayList<Record> ReportedRecords = getRecordsCopy();

        if (ReportedRecords.size() > 0) {
            printReport(buildReport(ReportedRecords));

            JOptionPane.showMessageDialog(target.getFrame(),
                    "Report printed in program directory.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else JOptionPane.showMessageDialog(target.getFrame(),
                "No defined records detected.",
                "Failure",
                JOptionPane.ERROR_MESSAGE);
    }

    private ArrayList<Record> getRecordsCopy() {
        ArrayList<Record> RecordsOriginal = target.getRecordsTableModel().getRecords();
        ArrayList<Record> RecordsCopy = new ArrayList<Record>();

        for (Record i : RecordsOriginal) {
            if (i.isDefined()) {
                RecordsCopy.add(i);
            }
        }

        return RecordsCopy;
    }

    private double getTotalNetChange(ArrayList<Record> Records) {
        double total = 0.;

        for (Record i : Records) {
            total += i.getAmount() * i.getType().sign;
        }

        return total;
    }

    private void sortRecords(ArrayList<Record> Records) {
        // sorts by newest dates
        Records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                LocalDate date1, date2;
                date1 = o1.getDate();
                date2 = o2.getDate();

                if (date1.isBefore(date2)) return 1;
                else if (date1.isEqual(date2)) return 0;
                else return -1;
            }
        });
    }

    private String buildReport(ArrayList<Record> Records) {
        sortRecords(Records);

        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("Report created on: ").append(new Date().toString()).append("\n");
        reportBuilder.append("\n");

        String nameFormat = "| %1$-40s | ";
        String dateFormat = " %2$tb %2$td, %2$tY  | ";
        String typeFormat = " %3$9s | ";
        String amountFormat = " %4$10.2f € |\n";
        String format = nameFormat + dateFormat + typeFormat + amountFormat;
        String line = new String(new char[90]).replace('\0', '-');

        reportBuilder.append(String.format("Total Number of Records: %d\n", Records.size()));
        reportBuilder.append(line).append("\n");
        reportBuilder.append(String.format("|%s|%s|%s|%s|\n",
                StringUtils.center("Name", 42),
                StringUtils.center("Date", 16),
                StringUtils.center("Type", 12),
                StringUtils.center("Amount", 15)));

        for (Record i : Records) {
            String name = i.getName();
            LocalDate date = i.getDate();
            RecordType type = i.getType();
            double amount = i.getAmount() * type.sign;

            reportBuilder.append(String.format(format, name, date, type, amount));
        }

        reportBuilder.append(line).append("\n");
        reportBuilder.append(String.format("%74s%12.2f €", "Total:", getTotalNetChange(Records)));

        return reportBuilder.toString();
    }

    private void printReport(String report) {
        String fileName = System.getProperty("user.dir") + "\\";
        fileName += "Budget-Report-" + String.format("%1$tb-%1$td-%1$tY", LocalDate.now()) + ".txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(report);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(report);
    }
}
