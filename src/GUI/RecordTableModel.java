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
        Records = new ArrayList<Record>();
//        Records.add(new Record("test", LocalDate.parse("2017-12-22"), RecordType.SAVINGS, 300));
    }

    public RecordTableModel(ArrayList<Record> Records)
    {
        this.Records = Records;
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
        Record record = Records.get(rowIndex);

        switch (columnIndex) {
            case 0: return record.getName();
            case 1: return record.getDate();
            case 2: return record.getType();
            case 3: return record.getAmount();
            default:return null;
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
        return getValueAt(0,columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addRecord()
    {
        Records.add(new Record());
    }
}
