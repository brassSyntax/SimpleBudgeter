package GUI;

import javax.swing.table.AbstractTableModel;

public class RecordTableModel extends AbstractTableModel{

    private String[] colNames;


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
