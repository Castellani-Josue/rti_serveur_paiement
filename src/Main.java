import ServeurGeneriqueTCP.Affichage;
import ServeurGeneriqueTCP.ThreadServeurPool;
import VESPAP.VESPAP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main
{
    public static void main(String[] args)
    {
        Properties properties =new Properties();

        String filePath = "C:\\Users\\josue\\Java_Project_2023_2024\\rti_serveur_paiement\\src\\Config.properties";
        try (FileInputStream input = new FileInputStream(filePath))
        {
            properties.load(input);
        }
        catch (IOException e) {
            String absolutePath = new File("Config.properties").getAbsolutePath();
            System.out.println("Le fichier n'a pas été trouvé. Chemin absolu : " + absolutePath);
            throw new RuntimeException("Erreur lors du chargement du fichier de propriétés", e);
        }

        // Lire les propriétés
        int port = Integer.parseInt(properties.getProperty("port", "50001"));
        int threadCount = Integer.parseInt(properties.getProperty("threadCount", "10"));

        VESPAP vespap = new VESPAP();
        Affichage affichage = new Affichage();
        ThreadServeurPool threadPoolServeur;

        try
        {
            threadPoolServeur = new ThreadServeurPool( port,vespap ,threadCount,affichage);
            threadPoolServeur.run();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}