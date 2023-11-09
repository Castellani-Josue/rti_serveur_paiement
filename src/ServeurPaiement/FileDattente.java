package ServeurPaiement;

import java.net.Socket;
import java.util.LinkedList;

public class FileDattente
{
    private LinkedList<Socket> fileDattente;

    public FileDattente()
    {
        fileDattente = new LinkedList<>();
    }

    public synchronized void addConnexion(Socket socket)
    {
        fileDattente.addLast(socket);
        notify();
    }

    public synchronized Socket getConnexion() throws InterruptedException
    {
        while(fileDattente.isEmpty()) wait();
        return fileDattente.remove();
    }
}
