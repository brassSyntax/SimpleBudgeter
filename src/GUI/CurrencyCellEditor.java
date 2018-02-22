package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CurrencyCellEditor extends DefaultCellEditor {

    private static final Border redBorder = new LineBorder(Color.red);
    private static final Border blackBorder = new LineBorder(Color.black);

    private JTextField textField;

    public CurrencyCellEditor(JTextField textField) {
        super(textField);

        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }

    @Override
    public boolean stopCellEditing() {
        try {
            double value = Double.valueOf(textField.getText());

            if(value < 0)
            {
                throw new NumberFormatException("Value less than 0");
            }
        } catch (NumberFormatException e) {
            textField.setBorder(redBorder);
            return false;
        }

        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setBorder(blackBorder);

        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return Double.valueOf(textField.getText());
    }
}
