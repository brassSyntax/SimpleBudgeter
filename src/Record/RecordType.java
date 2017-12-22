package Record;

public enum RecordType {
    SALARY      (1),
    INSURANCE   (1),
    TAXES       (-1),
    LEISURE     (-1),
    SAVINGS     (-1),
    SUPPLIES    (-1),
    EMERGENCY   (-1);

    public final int sign;

    RecordType(int sign)
    {
        this.sign = sign;
    }
}
