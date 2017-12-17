import java.time.LocalDate;
import java.time.LocalDateTime;

public class Record {

    private String name;
    private LocalDate date;
    private double amount;

    public Record()
    {
        this.name = "N/A";
        this.date = LocalDateTime.now().toLocalDate();
        this.amount = 0.;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
