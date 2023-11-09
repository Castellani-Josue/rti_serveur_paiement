package Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Generique
{
    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.137.129/PourStudent” ,”Student,”PassStudent1_”");

            System.out.println("Connexion à la BD PourStudent OK...");

            // Creation d'un objet Statement permettant d'executer une requete
            Statement stmt = con.createStatement();
            System.out.println("Creation du Statement OK...");

            // Execution requete d'ajout
            String requete = "insert into  values ();";
            int nbLignesAffectees= stmt.executeUpdate(requete);
            System.out.println("ExecuteUpdate OK... nbLignesAffectees = " + nbLignesAffectees);
            System.out.println("--> insert into  values ()");


        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Erreur ClassNotFoundException: "  + e.getMessage() );
        }
        catch (SQLException e)
        {
            System.out.println("Erreur SQLException: " + e.getMessage() );
        }

    }



}
