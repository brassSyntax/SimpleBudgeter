import java.util.ArrayList;

public class RecordManager {
    private ArrayList<Record> recordList;

    public RecordManager()
    {
        this.recordList = new ArrayList<>();
    }

    public void addRecord(Record r)
    {
        recordList.add(r);
    }

    public void removeRecord(int i)
    {
        recordList.remove(i);
    }
}
