package VESPAP;

import Interface.Protocole;
import Interface.Requete;
import Interface.Reponse;

import java.net.Socket;
import java.sql.SQLException;

public class VESPAP implements Protocole
{

    Reponse reponse;

    public VESPAP()
    {

    }
    @Override
    public String getNom()
    {
        return "";
    }
    @Override
    public Reponse TraiteRequete(Requete requete, Socket socket) throws SQLException, ClassNotFoundException
    {
        return reponse;
    }
}
