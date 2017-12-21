import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RecordFormatter {

    private static final int COLUMN_COUNT = 4;

    public static ArrayList<Record> toRecordObjects(String str) throws IOException
    {
        ArrayList<Record> Records = new ArrayList<>();
        String[] rawFields = StringUtils.substringsBetween(str,"\"", "\"");

        if(rawFields.length % COLUMN_COUNT == 0)
        {
            for(int i = 0; i < rawFields.length; i += 4)
            {
                String name;
                LocalDate date;
                RecordType type;
                double amount;

                name = rawFields[i];
                date = LocalDate.parse(rawFields[i+1]);
                type = RecordType.valueOf(rawFields[i+2]);
                amount = Double.parseDouble(rawFields[i+3]);

                Records.add(new Record(name,date,type,amount));

                // TODO: TEST THIS
            }
        }
        else
        {
            throw new IOException("Wrong file format: improper number of fields.");
        }

        return Records;
    }
}
