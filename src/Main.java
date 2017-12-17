import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {


    public static void main(String[] args)
    {
        int size = 9;
        boolean mode = false;
        FileHandler fileHandler = new FileHandler();
        if(mode) {
            fileHandler.readSudoku("C:\\Users\\kidre\\sudoku2.in");
            Grid grid = new Grid(fileHandler.getSudoku());
            grid.display();
            try {
                ISolver solver = SolverFactory.newDefault();
                solver.newVar(size * size * size);
                grid.addClause(solver);
                IProblem problem = solver;
                if (problem.isSatisfiable()) {
                    System.out.println(" The solution is:");
                    int[] model = solver.model();
                    for (int l = 0; l < model.length; l++) {
                        if (l % 81 == 0)
                            System.out.println();
                        if (model[l] > 0) {
                            int value = model[l] % 81;
                            value = value % 9;
                            if (value == 0)
                                System.out.print(9 + " ");
                            else
                                System.out.print(value + " ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println(" Unsatisfiable !");
                }
            } catch (TimeoutException e) {
                System.out.println(" Timeout , sorry !");
            }
        }
        else
        {
            Grid grid = new Grid(new int[size][size]);
            ISolver solver = SolverFactory.newDefault();
            solver.newVar(size * size * size);
            grid.addClause(solver);
            grid.fillSudoku(solver);
            fileHandler.writeSudoku("C:\\Users\\kidre\\sudoku3.txt",grid.getSudoku(), grid.getSize());
            grid.display();
        }
    }
}








