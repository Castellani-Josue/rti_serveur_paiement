package ServeurPaiement;

import Interface.Logger;
import Interface.Protocole;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadPoolServeur extends ThreadServeur
{
    private FileDattente connexionsEnAttente;
    private ThreadGroup pool;
    private int taillePool;

    public ThreadPoolServeur(int port, Protocole protocole, int taillePool, Logger
            logger) throws IOException
    {
        super(port, protocole, logger);

        connexionsEnAttente = new FileDattente();
        pool = new ThreadGroup("POOL");
        this.taillePool = taillePool;
    }

    public ThreadPoolServeur(int port, Protocole protocole, Logger logger) throws IOException {
        super(port, protocole, logger);
    }

    @Override
    public void run()
    {
        logger.Trace("Démarrage du TH Serveur (Pool)...");
        // Création du pool de threads
        try
        {
            for (int i=0 ; i<taillePool ; i++)
                new ThreadPoolClient(protocole,connexionsEnAttente,pool,logger).start();
        }
        catch (IOException ex)
        {
            logger.Trace("Erreur I/O lors de la création du pool de threads");
            return;
        }

        // Attente des connexions
        while(!this.isInterrupted())
        {
            Socket sClient;
            try
            {
                sServeur.setSoTimeout(2000);
                sClient = sServeur.accept();
                logger.Trace("Connexion acceptée, mise en file d'attente.");
                connexionsEnAttente.addConnexion(sClient);
            }
            catch (SocketTimeoutException ex)
            {
                // Pour vérifier si le thread a été interrompu
            }
            catch (IOException ex)
            {
                logger.Trace("Erreur I/O");
            }
        }
        logger.Trace("TH Serveur (Pool) interrompu.");
        pool.interrupt();
    }
}
