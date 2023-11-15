package ServeurGeneriqueTCP;
import Interface.Logger;
import Interface.Protocole;
import Interface.Reponse;
import Interface.Requete;
import Messages.ReponseLOGIN;
import Messages.RequeteLOGIN;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


public abstract class ThreadClient extends Thread
{
    protected Protocole protocole;
    protected Socket csocket;
    protected Logger logger;
    private int numero;

    private static int numCourant = 1;

    public ThreadClient(Protocole protocole, Socket csocket, Logger logger) throws
            IOException
    {
        super("TH Client " + numCourant + " (protocole=" + protocole.getNom() + ")");
        this.protocole = protocole;
        this.csocket = csocket;
        this.logger = logger;
        this.numero = numCourant++;
    }

    public ThreadClient(Protocole protocole, ThreadGroup groupe, Logger logger)
            throws IOException
    {
        super(groupe,"TH Client " + numCourant + " (protocole=" + protocole.getNom()
                + ")");
        this.protocole = protocole;
        this.csocket = null;
        this.logger = logger;
        this.numero = numCourant++;
    }

    @Override
    public void run()
    {
        try
        {
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            try
            {
                ois = new ObjectInputStream(csocket.getInputStream());
                oos = new ObjectOutputStream(csocket.getOutputStream());

                while (true)
                {
                    Requete requete = (Requete) ois.readObject();
                    Reponse reponse = protocole.TraiteRequete(requete,csocket);
                    oos.writeObject(reponse);
                }
            }
            catch (FinConnexionException ex)
            {
                logger.Trace("Fin connexion demandée par protocole");
                if (oos != null && ex.getReponse() != null)
                    oos.writeObject(ex.getReponse());
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException ex) {
            logger.Trace("Erreur I/O ThreadClient");
            logger.Trace(ex.getMessage());
        }
        catch (ClassNotFoundException ex) { logger.Trace("Erreur requete invalide");
        }
        finally
        {
            try { csocket.close(); }
            catch (IOException ex) { logger.Trace("Erreur fermeture socket"); }
        }
    }
}
