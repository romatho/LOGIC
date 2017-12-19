import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private int[][] sudoku;

    public FileHandler()
    {
        sudoku = new int[9][9];
    }

    /**
     * Read the sudoku from a file
     * @param Filename of the file to read
     */

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

    /**
     * Get the sudoku read
     * @return
     */
    public int[][] getSudoku()
    {
        return sudoku;
    }

    /**
     * Write a sudoku in a file
     * @param fileName of the file where the sudoku must be written
     * @param sudoku sudoku to write
     * @param size size of the sudoku
     */

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
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}