import Interface.Logger;
import ServeurPaiement.Affichage;
import ServeurPaiement.ThreadPoolServeur;
import VESPAP.VESPAP;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main
{
    public static void main(String[] args)
    {
        Properties properties =new Properties();


        try (FileInputStream input = new FileInputStream("config.properties"))
        {
            properties.load(input);
        }
        catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier de propriétés", e);
        }

        // Lire les propriétés
        int port = Integer.parseInt(properties.getProperty("port", "50001"));
        int threadCount = Integer.parseInt(properties.getProperty("thread", "10"));

        VESPAP vespap = new VESPAP();
        Affichage affichage = new Affichage();
        ThreadPoolServeur threadPoolServeur;

        try
        {
             threadPoolServeur = new ThreadPoolServeur( port,vespap ,threadCount,affichage);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}