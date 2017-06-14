package sample;

import java.io.*;

/**
 * Created by B'Art on 22.01.2017.
 */
public class WorkFile {
    public WorkFile() {
        File file = new File("res/t2.txt");
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file.getAbsoluteFile());
            printWriter.print("tou");
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}