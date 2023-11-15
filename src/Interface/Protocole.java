package Interface;

import Interface.Reponse;
import Interface.Requete;
import ServeurGeneriqueTCP.FinConnexionException;


import java.net.Socket;
import java.sql.SQLException;

public interface Protocole
{
    String getNom();
    Reponse TraiteRequete(Requete requete, Socket socket) throws
             SQLException, ClassNotFoundException, FinConnexionException;
}
