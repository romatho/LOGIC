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

    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(sudoku[i][j] + " ");
            System.out.println();
        }
    }

    public void addClause(ISolver solver) {

        /*
         * Chaque case doit contenir au moins une valeur
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
        * Chaque ligne doit contenir au moins une fois chaque chiffre
        *
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
        * Chaque colonne doit contenir au moins une fois chaque chiffre
        *
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
         * Chaque carré doit contenir au moins une fois chaque chiffre
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
         * Chaque case sans chiffre doit contenir au plus une valeur
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
        * Chaque ligne doit contenir au plus une fois chaque chiffre
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
        * Chaque colonne doit contenir au plus une fois chaque chiffre
        *
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
         * Chaque carré doit contenir au plus une fois chaque chiffre
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
         * Chaque nombre déjà écrit dans la grille correspond à une variable qui doit être vraie
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

                    if (problem.hasASingleSolution())
                    {
                        System.out.println("Solution unique trouvée");
                        return;
                    }
                    else
                    {
                        while(sudoku[x][y] != 0)
                        {
                            x = (int) (Math.random()*size);
                            y = (int) (Math.random()*size);
                        }
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
                    //if the problem have no solution, remove the last clause
                    solver.removeConstr(idClause);
                    sudoku[x][y] = 0;
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public int[][] getSudoku()
    {
        return sudoku;
    }
    public int getSize()
    {
        return size;
    }


}
