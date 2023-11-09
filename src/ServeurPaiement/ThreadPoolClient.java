package ServeurPaiement;

import Interface.Logger;
import Interface.Protocole;

import java.io.File;
import java.io.IOException;

public class ThreadPoolClient extends ThreadClient
{
    private FileDattente connexionsEnAttente;

    public ThreadPoolClient(Protocole protocole, FileDattente file, ThreadGroup
            groupe, Logger logger) throws IOException
    {
        super(protocole, groupe, logger);
        connexionsEnAttente = file;
    }

    @Override
    public void run()
    {
        logger.Trace("TH Client (Pool) démarre...");
        boolean interrompu = false;
        while(!interrompu)
        {
            try
            {
                logger.Trace("Attente d'une connexion...");
                sClient = connexionsEnAttente.getConnexion();
                logger.Trace("Connexion prise en charge.");
                super.run();
            }
            catch (InterruptedException ex)
            {
                logger.Trace("Demande d'interruption...");
                interrompu = true;
            }
        }
        logger.Trace("TH Client (Pool) se termine.");
    }
}
