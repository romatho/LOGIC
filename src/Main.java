import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/* TODO list (j'ai pas trouvé comment ajouter un fichier texte et j'avais peur de tout casser
*
* - vérifier que le code fonctionne sur MS800
* - arranger le code du main pour avoir un truc propre en fonction du nombre d'argument au lieu d'un IF ELSE moche DONE
* - améliorer le main pour récupérer les argument au lieu de hard coder les chemins des fichiers DONE
* - commenter le code
* - faire le rapport => Commencé: sharelatex:  https://www.sharelatex.com/8543838958bqhkfvrrdcqb
* - fournir un build.xml
* - forunir un INSTALL file si nécessaire
* - output le sudoku résolu DONE
*
*
*
*/


public class Main {


    public static void main(String[] args)
    {
        int size = 9;
        FileHandler fileHandler = new FileHandler();
        if(args.length == 2) {
            int[][] temp = new int[size][size];
            /*
             * Reading the input file
             */
            fileHandler.readSudoku(args[0]);
            Grid grid = new Grid(fileHandler.getSudoku());
            grid.display();
            try {
                ISolver solver = SolverFactory.newDefault();
                solver.newVar(size * size * size);
                /*
                 * Add general and particular clauses in the solver
                 */
                grid.addClause(solver);
                if (solver.isSatisfiable()){
                /*
                 * Use the solver to obtain the solution
                 */
                    System.out.println(" The solution is:");
                    int[] model = solver.model();
                    int i=0;
                    int j=0;
                    /*
                     * Display the solution in the console
                     * Store the solution in a matrix
                     */
                    for (int l = 0; l < model.length; l++)
                    {
                        if (l % (size*size) == 0 && l!=0)
                        {
                            i++;j=0;
                            System.out.println();
                        }
                        if (model[l] > 0) {
                            int value = model[l] % (size*size);
                            value = value % size;
                            if (value == 0)
                            {
                                System.out.print(size + " ");
                                temp[i][j]=size;
                                j++;
                            }
                            else
                            {
                                System.out.print(value + " ");
                                temp[i][j]=value;
                                j++;
                            }
                        }
                    }
                    System.out.println();
                    /*
                     * Write the solution in the output file
                     */
                    fileHandler.writeSudoku(args[1], temp, size);
                }
                 else
                {
                    System.out.println(" Unsatisfiable !");
                }

            } catch (TimeoutException e) {
                System.out.println(" Timeout , sorry !");
            }
        }
        else if(args.length==1)
        {
            /*
             * Initialise a empty sudoku and a new solver
             */
            Grid grid = new Grid(new int[size][size]);
            ISolver solver = SolverFactory.newDefault();
            solver.newVar(size * size * size);
            /*
             * Add general clauses in the solver
             */
            grid.addClause(solver);
            /*
             * Fill the sudoku and add the corresponding clauses in the solver
             */
            grid.fillSudoku(solver);
            /*
             * Write the solution in the output file
             */
            fileHandler.writeSudoku(args[0],grid.getSudoku(), grid.getSize());
            grid.display();
        }
        else
            {
                System.out.println(" Wrong number of arguments. If you want to:\n" +
                        " generate a sudoku you should enter the output file as argument\n " +
                        " sovle a sudoku you shoudl enter the imput file followed by the output file");
            }
    }
}








