package GUI;

import Record.Record;
import Record.RecordType;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;

public class RecordTableModel extends AbstractTableModel{

    private String[] colNames = {"Name", "Date", "Type", "Balance Change"};
    private ArrayList<Record> Records;

    public RecordTableModel() {
        this.Records = new ArrayList<Record>();
    }

    public RecordTableModel(ArrayList<Record> Records)
    {
        this.Records = Records;
    }

    public ArrayList<Record> getRecords() {
        return Records;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return Records.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Record record = Records.get(rowIndex);

            switch (columnIndex) {
                case 0: return record.getName();
                case 1: return record.getDate();
                case 2: return record.getType();
                case 3: return record.getAmount();
                default:return null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Record record = Records.get(rowIndex);

        switch (columnIndex) {
            case 0: record.setName(String.class.cast(value));
                    break;
            case 1: record.setDate(LocalDate.class.cast(value));
                    break;
            case 2: record.setType(RecordType.class.cast(value));
                    break;
            case 3: record.setAmount((double) value);
                    break;
        }

        fireTableCellUpdated(rowIndex,columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (Records.size() > 0) {
            return getValueAt(0,columnIndex).getClass();
        }
        else return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addRecord()
    {
        int rowCount = getRowCount();

        Records.add(new Record("", LocalDate.now(), RecordType.UNDEFINED,0));

        fireTableRowsInserted(rowCount,rowCount);
    }

    public void removeRecords(int[] Selection)
    {
        if (Selection.length > 0) {
            for(int i = 0; i < Selection.length; i++)
            {
                Records.remove(Selection[i] - i);
            }

            fireTableRowsDeleted(Selection[0], Selection[Selection.length - 1]);
        }
    }
}
