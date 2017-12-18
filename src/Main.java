import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/* TODO list (j'ai pas trouvé comment ajouter un fichier texte et j'avais peur de tout casser
*
* - vérifier que le code fonctionne sur window
* - arranger le code du main pour avoir un truc propre en fonction du nombre d'argument au lieu d'un IF ELSE moche
* - recréer des fonction dans la classe Main pour avoir une méthode main plus belle que cette horreur
* - améliorer le main pour récupérer les argument au lieu de hard coder les chemins des fichiers
* - commenter le code
* - faire le rapport
* - fournir un build.xml
* - forunir un INSTALL file si nécessaire
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*/


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








