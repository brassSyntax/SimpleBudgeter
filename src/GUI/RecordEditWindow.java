package GUI;

import javax.swing.*;
import java.awt.*;

public class RecordEditWindow extends JFrame {

    public RecordEditWindow() throws HeadlessException {
        Container contentPane = new Container();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // TODO: add edit fields for a record


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(300,200));
        this.setVisible(true);
    }
}
