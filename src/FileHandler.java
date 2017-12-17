import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private int[][] sudoku;

    public FileHandler()
    {
        sudoku = new int[9][9];
    }

    public void readSudoku(String Filename)
    {
        try {
            Scanner scanner = new Scanner(new File(Filename));

            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    sudoku[i][j] = scanner.nextInt();

        }catch (FileNotFoundException e)
        {
            System.out.println("File not Found");
        }
    }

    public int[][] getSudoku()
    {
        return sudoku;
    }

}