import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class RecordTableReader {

    private static RecordTableReader readerInstance = new RecordTableReader();

    private RecordTableReader() {}

    public static RecordTableReader getInstance()
    {
        return readerInstance;
    }

    public String readFile(String filename) {
        FileReader fr;
        Scanner fileInput;
        StringBuilder text = new StringBuilder();

        try {
            fr = new FileReader(filename);
            fileInput = new Scanner(fr);

            while (fileInput.hasNext()) {
                text.append(fileInput.nextLine());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return text.toString();
    }

    public String readFile(File file) {
        Scanner fileInput;
        StringBuilder text = new StringBuilder();

        try {
            fileInput = new Scanner(file);

            while (fileInput.hasNext()) {
                text.append(fileInput.nextLine());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return text.toString();
    }
}
