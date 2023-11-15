package ServeurGeneriqueTCP;

import Interface.Reponse;

public class FinConnexionException extends Exception {
    private Reponse reponse;

    public FinConnexionException() {
        super("Fin de Connexion décidée par protocole");
        this.reponse = reponse;
    }
    public Reponse getReponse() {
        return reponse;
    }
}