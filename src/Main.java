// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import ServeurGeneriqueTCP.Affichage;
import ServeurGeneriqueTCP.ThreadServeurPool;
import VESPAP.VESPAP;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        VESPAP vespap = new VESPAP();
        Affichage affichage = new Affichage();

        try
        {
            ThreadServeurPool threadPoolServeur =  new ThreadServeurPool(50001, vespap, 10, affichage);
            threadPoolServeur.run();
        }
        catch (IOException io)
        {
            System.err.println("Erreur threadPoolServeur : " + io.getMessage());
            System.exit(1);
        }
    }
}