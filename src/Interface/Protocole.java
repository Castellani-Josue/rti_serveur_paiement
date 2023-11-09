package Interface;

import Interface.Reponse;
import Interface.Requete;
import ServeurPaiement.ExceptionConnexionFinie;

import java.net.Socket;
import java.sql.SQLException;

public interface Protocole
{
    String getNom();
    Reponse TraiteRequete(Requete requete, Socket socket) throws
            ExceptionConnexionFinie, SQLException, ClassNotFoundException;
}
