import org.sat4j.core.VecInt;
import org.sat4j.specs.*;
import org.sat4j.tools.SingleSolutionDetector;


public class Grid {
    private int[][] sudoku;
    private int size;
    private int subsize;
    int[] clause;

    public Grid(int[][] pSudoku) {
        sudoku = pSudoku;
        size = 9;
        subsize = (int) Math.sqrt(size);
        clause = new int[size];
    }

    /**
     * Display the grid in the console
     */
    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(sudoku[i][j] + " ");
            System.out.println();
        }
    }

    /**
     * Add general clauses (and particular clauses if the sudoku is not empty) in a solver
     * @param solver the solver where the clause are added
     */
    public void addClause(ISolver solver) {

        /*
         * A sudoku grid is divided in 9 blocks and each block is divided in 9 squares
         */

        /*
         * GENERAL CLAUSES (sudoku rules)
         */


        /*
         * Each square must contain at least a number
         */

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++)
                    clause[k] = (i * size + j) * size + 1 + k;
                try {
                    solver.addClause(new VecInt(clause));
                } catch (ContradictionException e) {
                    System.out.println(" Unsatisfiable");
                }
            }

        /*
         * Each lines must contain at least one time each number
         */

        for (int i = 0; i < size; i++)
            for (int j = 1; j <= size; j++) {
                for (int k = 0; k < size; k++)
                    clause[k] = (i * size + k) * size + j;
                try {
                    solver.addClause(new VecInt(clause));
                } catch (ContradictionException e) {
                    System.out.println(" Unsatisfiable");
                }
            }

        /*
         * Each column must contain at least one time each number
         */

        for (int i = 0; i < size; i++)
            for (int j = 1; j <= size; j++) {
                for (int k = 0; k < size; k++)
                    clause[k] = (k * size + i) * size + j;
                try {
                    solver.addClause(new VecInt(clause));
                } catch (ContradictionException e) {
                    System.out.println(" Unsatisfiable");
                }
            }

        /*
         * Each block must contain at least one time each number
         */

        for (int i = 0; i < subsize; i++)
            for (int j = 0; j < subsize; j++)
                for (int k = 1; k <= size; k++) {
                    for (int l = 0; l < subsize; l++)
                        for (int m = 0; m < subsize; m++)
                            clause[l * subsize + m] = ((i * subsize + l) * size + j * subsize + m) * size + k;
                    try {
                        solver.addClause(new VecInt(clause));
                    } catch (ContradictionException e) {
                        System.out.println(" Unsatisfiable");
                    }
                }

        /*
         * Each square must contain at most a number
         */

        clause = new int[2];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size - 1; k++) {
                    clause[0] = -((i * size + j) * size + 1 + k);
                    for (int l = 1; l < size - k; l++) {
                        clause[1] = -((i * size + j) * size + 1 + k + l);
                        try {
                            solver.addClause(new VecInt(clause));
                        } catch (ContradictionException e) {
                            System.out.println(" Unsatisfiable");
                        }
                    }
                }

        /*
         * Each line must contain at most a number
         */

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size - 1; k++) {
                    clause[0] = -((i * size + k) * size + 1 + j);
                    for (int l = 1; l < size - k; l++) {
                        clause[1] = -((i * size + k + l) * size + 1 + j);
                        try {
                            solver.addClause(new VecInt(clause));
                        } catch (ContradictionException e) {
                            System.out.println(" Unsatisfiable");
                        }
                    }
                }

        /*
         * Each column must contain at most a number
         */

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size - 1; k++) {
                    clause[0] = -((k * size + i) * size + 1 + j);
                    for (int l = 1; l < size - k; l++) {
                        clause[1] = -(((l + k) * size + i) * size + 1 + j);
                        try {
                            solver.addClause(new VecInt(clause));
                        } catch (ContradictionException e) {
                            System.out.println(" Unsatisfiable");
                        }

                    }
                }

        /*
         * Each block must contain at most a number
         */

        for (int i = 0; i < subsize; i++)
            for (int j = 0; j < subsize; j++)
                for (int m = 0; m < size; m++)
                    for (int k = 0; k < size; k++) {
                        clause[0] = -(((i * subsize + k % subsize) * size + j * subsize + k / subsize) * size + 1 + m);
                        for (int l = k + 1; l < size; l++) {
                            clause[1] = -(((i * subsize + l % subsize) * size + j * subsize + l / 3) * size + 1 + m);
                            try {
                                solver.addClause(new VecInt(clause));
                            } catch (ContradictionException e) {
                                System.out.println(" Unsatisfiable");
                            }
                        }
                    }

        /*
         * PARTICULAR CLAUSES (data from the number in the sudoku)
         */

        /*
         * For each non-zero value in a square, the corresponding boolean must be true
         */

        int[] miniClause = new int[1];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (sudoku[i][j] != 0) {
                    miniClause[0] = (i * size + j) * size + sudoku[i][j];
                    try {
                        solver.addClause(new VecInt(miniClause));
                    } catch (ContradictionException e) {
                        System.out.println(" Unsatisfiable");
                    }
                }
    }

    /**
     * Fill an empty sudoku with numbers until the solver says there is only one solution
     * @param solver the solver used to check the number of solution
     */

    public void fillSudoku(ISolver solver) {
        int x = (int) (Math.random()) % size;
        int y = (int) (Math.random()) % size;
        int value;
        int[] clauseToAdd = new int[1];
        IConstr idClause;
        SingleSolutionDetector problem = new SingleSolutionDetector(solver);
        value = (int) (Math.random()*size);
        clauseToAdd[0] = (x*size+y)*size + value + 1;
        try {
            idClause = solver.addClause(new VecInt(clauseToAdd));
        } catch (ContradictionException e) {
            System.out.println("A contradiction exception occurs");
            return;
        }

        while(true)
        {
            try {

                if (problem.isSatisfiable()){

                    /*
                     * If the problem is satisfiable and have only one solution, the grid is filled and the function ends
                     */

                    if (problem.hasASingleSolution())
                    {
                        System.out.println("Solution unique trouvée");
                        return;
                    }
                    else
                    {
                        /*
                         * If the problem is satisfiable and have multiple solutions, a number is added to the grid
                         */

                        /*
                         * To reduce the number of loop, the square to fill remains the same until an acceptable value
                         * (value that satisfied the model) is chosen
                         */
                        while(sudoku[x][y] != 0)
                        {
                            x = (int) (Math.random()*size);
                            y = (int) (Math.random()*size);
                        }

                        /*
                         * The square is filled with a random value and the corresponding clauses is add in the solver
                         */

                        value = (int) (Math.random()*size);
                        clauseToAdd[0] = (x*size+y)*size + value + 1;
                        try {
                            idClause = solver.addClause(new VecInt(clauseToAdd));
                            sudoku[x][y] = value + 1;
                        } catch (ContradictionException e) {
                            System.out.println("A contradiction exception occurs");
                            return;
                        }
                    }
                }
                else
                {

                    /*
                     * If there is no solution, the last clauses is removed from the solver and the corresponding square
                     * is filled with a 0
                     */
                    solver.removeConstr(idClause);
                    sudoku[x][y] = 0;
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get the sudoku grid
     * @return
     */

    public int[][] getSudoku()
    {
        return sudoku;
    }

    /**
     * get the size of the sudoku grid
     * @return
     */

    public int getSize()
    {
        return size;
    }


}
