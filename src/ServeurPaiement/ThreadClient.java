package ServeurPaiement;

import Interface.Logger;
import Interface.Protocole;
import Interface.Reponse;
import Interface.Requete;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;


public abstract class ThreadClient extends Thread
{
    protected Protocole protocole;
    protected Socket sClient;
    protected Logger logger;
    private int numero;

    private static int numCourant = 1;

    public ThreadClient(Protocole protocole, Socket sClient, Logger logger) throws
            IOException
    {
        super("TH Client " + numCourant + " (protocole=" + protocole.getNom() + ")");
        this.protocole = protocole;
        this.sClient = sClient;
        this.logger = logger;
        this.numero = numCourant++;
    }

    public ThreadClient(Protocole protocole, ThreadGroup groupe, Logger logger)
            throws IOException
    {
        super(groupe,"TH Client " + numCourant + " (protocole=" + protocole.getNom()
                + ")");
        this.protocole = protocole;
        this.sClient = null;
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
                ois = new ObjectInputStream(sClient.getInputStream());
                oos = new ObjectOutputStream(sClient.getOutputStream());

                while (true)
                {
                    Requete requete = (Requete) ois.readObject();
                    Reponse reponse = protocole.TraiteRequete(requete,sClient);
                    oos.writeObject(reponse);
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        catch (IOException ex) { logger.Trace("Erreur I/O"); }
        catch (ClassNotFoundException ex) { logger.Trace("Erreur requete invalide");
        }
        finally
        {
            try { sClient.close(); }
            catch (IOException ex) { logger.Trace("Erreur fermeture socket"); }
        }
    }
}
