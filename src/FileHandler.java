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
    public void writeSudoku(String fileName, int[][] sudoku, int size)
    {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++)
                    writer.print(sudoku[i][j] + " ");
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}