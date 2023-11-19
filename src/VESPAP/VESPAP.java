package VESPAP;

import Interface.Protocole;
import Interface.Reponse;
import Interface.Requete;

import java.net.Socket;
import java.sql.SQLException;
import Messages.*;
import Bean.FctProtocole;
import ServeurGeneriqueTCP.FinConnexionException;

public class VESPAP implements Protocole
{

    Reponse reponse;

    public VESPAP()
    {

    }
    @Override
    public synchronized String getNom()
    {
        return "Le protocole s'appele Topito.";
    }
    @Override
    public synchronized Reponse TraiteRequete(Requete requete, Socket socket) throws SQLException, ClassNotFoundException, FinConnexionException
    {
        FctProtocole methode = FctProtocole.getInstance();
        if(requete instanceof RequeteLOGIN)
        {
            RequeteLOGIN requeteLOGIN = (RequeteLOGIN) requete;
            if(methode.estPresent(requeteLOGIN.getLogin(), requeteLOGIN.getPassWord()))
            {
                if(methode.MdpCorrect(requeteLOGIN.getLogin(), requeteLOGIN.getPassWord()))
                {
                    ReponseLOGIN reponseLOGIN = new ReponseLOGIN(true);
                    reponse = reponseLOGIN;
                }
                else
                {
                    ReponseLOGIN reponseLOGIN = new ReponseLOGIN(false);
                    reponseLOGIN.setCas(1);
                    reponse = reponseLOGIN;
                }
            }
            else
            {
                ReponseLOGIN reponseLOGIN = new ReponseLOGIN(false);
                reponseLOGIN.setCas(2);
                reponse = reponseLOGIN;
            }
        }
        if(requete instanceof RequeteLOGOUT)
        {
            System.out.println("Logout reçu.");
            throw new FinConnexionException();
        }
        if(requete instanceof RequeteRECHERCHE)
        {
            RequeteRECHERCHE requeteRECHERCHE = (RequeteRECHERCHE) requete;
            ReponseRECHERCHE reponseRECHERCHE = new ReponseRECHERCHE(methode.Recherche(requeteRECHERCHE.getIdClient()));
            reponse = reponseRECHERCHE;
        }
        if(requete instanceof RequetePAYE)
        {
            RequetePAYE requetePAYE = (RequetePAYE) requete;
            ReponsePAYE reponsePAYE;
            if(methode.TestVisa(requetePAYE.getVisa()))
            {
                System.out.println("Visa valide.");
                reponsePAYE = new ReponsePAYE(true);

                System.out.println("Gestion de la facture.");
                if(methode.GestionFacture(requetePAYE.getIdFacture()))
                {
                    System.out.println("Gestion réussie.");
                }
                else
                {
                    System.out.println("Erreur de gestion.");
                }
            }
            else
            {
                System.out.println("Visa invalide.");
                reponsePAYE = new ReponsePAYE(false);
            }

            reponse = reponsePAYE;
        }
        return reponse;
    }
}