package ServeurPaiement;

import Interface.Logger;
import Interface.Protocole;

import java.io.IOException;
import java.net.ServerSocket;

public class ThreadServeur extends Thread
{
    protected int port;
    protected Protocole protocole;
    protected Logger logger;

    protected ServerSocket sServeur;

    public ThreadServeur(int port, Protocole protocole, Logger logger) throws
            IOException
    {
        super("TH Serveur (port=" + port + ",protocole=" + protocole.getNom() + ")");
        this.port = port;
        this.protocole = protocole;
        this.logger = logger;

        sServeur = new ServerSocket(port);
    }
}
