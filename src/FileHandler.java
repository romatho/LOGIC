import java.io.*;

public class FileHandler {

    public FileHandler(String Filename)
    {
        File file = new File(Filename);
        FileReader fileReader ;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();

        }

    }


}
