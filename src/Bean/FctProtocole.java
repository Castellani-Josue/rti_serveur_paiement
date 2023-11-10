package Bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FctProtocole
{
    DatabaseConnection dbConnect;
    private static FctProtocole instance = null;
    private FctProtocole()
    {

        try
        {
            dbConnect = new DatabaseConnection(DatabaseConnection.MYSQL,
                    "192.168.137.129",
                    "PourStudent",
                    "Student",
                    "PassStudent1_");
            System.out.println("db Connect réussie !");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static FctProtocole getInstance()
    {
        if(instance == null)
        {
                instance = new FctProtocole();
            System.out.println("je suis dans le constructeur du singleton");
        }
        return instance;
    }


    private int estPresent(String login)
    {
        int indice = -1;

        String requete = "SELECT * FROM EMPLOYES LIMIT 1";
        ResultSet resultSet = null;
        try
        {
            resultSet = dbConnect.executeQuery(requete);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        while (true)
        {
            try
            {
                if (!resultSet.next()) break;
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
            // Récupération de la valeur de la colonne spécifiée
            String valeurColonne = null;
            try
            {
                valeurColonne = resultSet.getString("login");
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

            // Vérification de la présence de l'élément
            if (valeurColonne.equals(login))
            {
                System.out.println("L'élément est présent dans la base de données.");
            }
            else
            {
                System.out.println("L'élément n'est pas présent dans la base de données.");
            }
        }

        // Fermeture des ressources
        try
        {
            resultSet.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return indice;

    }


}
