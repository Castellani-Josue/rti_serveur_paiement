package Bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import ServeurGeneriqueTCP.Affichage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FctProtocole
{
    private DatabaseConnection dbConnect;
    private Affichage affichage;

    private static FctProtocole instance = null;
    private FctProtocole()
    {
        affichage = new Affichage();
        try
        {
            dbConnect = new DatabaseConnection(DatabaseConnection.MYSQL,
                    "192.168.146.128",
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


    public boolean estPresent(String login, String password)
    {

        String requete = "SELECT login FROM EMPLOYES WHERE login = '" + login + "' AND password = '" + password + "';";
        ResultSet resultSet = null;
        try
        {
            resultSet = dbConnect.executeQuery(requete);

                if (!resultSet.next())
                {
                    System.out.println("Le ResultSet est vide.");
                    return false;
                }
                else
                {
                    System.out.println("Le ResultSet n'est pas vide.");
                    return true;
                }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean MdpCorrect(String login, String mdp)
    {
        String requete = "SELECT password FROM EMPLOYES WHERE login = '" + login + "';";
        ResultSet resultSet = null;
        try
        {
            resultSet = dbConnect.executeQuery(requete);

            if (!resultSet.next())
            {
                affichage.Trace("Aucun enregistrement trouvé pour ce login.");
                return false;
            }
            else
            {
                String motDePasse = resultSet.getString("password");
                if(motDePasse.equals(mdp))
                {
                    affichage.Trace("Mot de passe correcte.");
                    return true;
                }
                else
                {
                    affichage.Trace("Mot de passe incorrecte.");
                    return false;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String Recherche(int idClient)
    {
        String rechercheConcat = "GETFACTURE#";
        String requete = "SELECT id, idClient, date, montant, paye FROM FACTURES WHERE idClient = '" + idClient + "' AND paye = false;";
        ResultSet resultSet = null;

        String idFacture = "";
        String idClientFacture = "";
        String dateFacture = "";
        String montantFacture = "";
        String paye = "";

        try
        {
            //Format reponse : GETFACTURE#ok#idFacture, idClient, date, montant, paye$idFacture, idClient...

            resultSet = dbConnect.executeQuery(requete);
            if (!resultSet.next())
            {
                affichage.Trace("Aucun enregistrement trouvé pour la recherche.");
                rechercheConcat = rechercheConcat + "ko#Aucun enregistrement trouvé pour ce login.";
            }
            else
            {
                rechercheConcat += "ok#";
                while(resultSet.next())
                {
                    idFacture = resultSet.getString("id");
                    idClientFacture = resultSet.getString("idClient");
                    dateFacture = resultSet.getString("date");
                    montantFacture = resultSet.getString("montant");
                    paye = resultSet.getString("paye");

                    affichage.Trace("idFacture: " + idFacture);
                    affichage.Trace("idClientFacture: " + idClientFacture);
                    affichage.Trace("dateFacture: " + dateFacture);
                    affichage.Trace("montantFacture: " + montantFacture);
                    affichage.Trace("paye: " + paye);

                    rechercheConcat += idFacture + ", " + idClientFacture + ", " + dateFacture + ", " + montantFacture + ", " + paye + "$";
                }

                affichage.Trace("Resultat recherche: " + rechercheConcat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rechercheConcat;
    }

    public boolean TestVisa (String numeroCarte)
    {
        //ici le regex veux dire : 4 est le premier chiffre et puis 12 chiffre de 0 à 9 et 3 chiffre optionnel ?: en fin de
        // string entre 0 et 9 aussi
       String visaCardRegex = "^4[0-9]{12}(?:[0-9]{3})?$";

        Pattern pattern = Pattern.compile(visaCardRegex);
        Matcher matcher = pattern.matcher(numeroCarte);

        return matcher.matches();
    }

    public boolean GestionFacture(int idFacture)
    {
        String requete = "UPDATE FACTURES SET paye = true WHERE id = " + idFacture + ";";

        try
        {
            int modification = dbConnect.executeUpdate(requete);
            if(modification == 0)
            {
                affichage.Trace("Aucune ligne n'a été modifier, facture introuvable.");
                return false;
            }
            else
            {
                affichage.Trace("Gestion de la facture effectué avec succès.");
                return true;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
