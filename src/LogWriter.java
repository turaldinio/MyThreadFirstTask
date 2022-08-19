import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    private static LogWriter logWriter;
    private File file = new File("logFile.txt");
    private FileWriter writer;

    private LogWriter() {
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            System.out.println("Ошибка создания FileWriter");
        }
        if (file.length() != 0) {
            file.delete();
            file = new File("logFile.txt");

        }
    }

    public static LogWriter getInstance() {
        if (logWriter == null) {
            logWriter = new LogWriter();
            return logWriter;
        }
        return logWriter;
    }

    public void writeLog(String threadType, int size, int time) {
        try {
            writer.write(String.format("%s.\n Кол-во элементов в массиве %d. \n" +
                    " Затрачено времени на операции сложения и поиска сред.арифм %d\n------------\n ", threadType, size, time));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
