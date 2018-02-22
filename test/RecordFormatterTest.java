import Record.Record;
import Record.RecordFormatter;
import Record.RecordType;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class RecordFormatterTest {
    @Test
    public void toRecordObjects() throws Exception {
        String text = "\"Monthly salary\";\"2017-12-21\";\"SALARY\";\"300\";";

        String str = "Monthly salary";
        LocalDate date = LocalDate.parse("2017-12-21");
        RecordType type = RecordType.SALARY;
        double amount = 300;

        Record r = RecordFormatter.toRecordObjects(text).get(0);

        Assert.assertEquals(r.getName(), str);
        Assert.assertEquals(r.getDate(), date);
        Assert.assertEquals(r.getType(), type);
        Assert.assertEquals(r.getAmount(), amount, 0.00001);
    }

}