package Record;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RecordFormatter {

    private static final int COLUMN_COUNT = 4;

    public static ArrayList<Record> toRecordObjects(String str) throws IOException {
        ArrayList<Record> Records = new ArrayList<Record>();
        String[] rawFields = StringUtils.substringsBetween(str, "\"", "\"");

        if (rawFields.length % COLUMN_COUNT == 0) {
            for (int i = 0; i < rawFields.length; i += 4) {
                String name;
                LocalDate date;
                RecordType type;
                double amount;

                name = rawFields[i];
                date = LocalDate.parse(rawFields[i + 1]);
                type = RecordType.valueOf(rawFields[i + 2]);
                amount = Double.parseDouble(rawFields[i + 3]);

                Records.add(new Record(name, date, type, amount));
            }
        } else {
            throw new IOException("Wrong file format: improper number of fields.");
        }

        return Records;
    }

    public static String toString(ArrayList<Record> list) {
        StringBuilder text = new StringBuilder();

        for (Record i : list) {
            text.append("\"").append(i.getName()).append("\";");
            text.append("\"").append(i.getDate().toString()).append("\";");
            text.append("\"").append(i.getType().toString()).append("\";");
            text.append("\"").append(i.getAmount()).append("\";");

            if (i != list.get(list.size() - 1)) {
                text.append("\n");
            }
        }

        return text.toString();
    }
}
