package Record;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Record {

    private String name;
    private LocalDate date;
    private RecordType type;
    private double amount;

    public Record()
    {
        this.name = "N/A";
        this.date = LocalDateTime.now().toLocalDate();
        this.type = null;
        this.amount = 0.;
    }

    public Record(String name, LocalDate date, RecordType type, double amount) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
