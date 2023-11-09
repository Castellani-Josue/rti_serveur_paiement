package ServeurPaiement;

import Interface.Reponse;

public class ExceptionConnexionFinie extends Exception
{
    private Reponse reponse;

    public ExceptionConnexionFinie(Reponse reponse) {
        super("Fin de Connexion décidée par protocole");
        this.reponse = reponse;
    }
    public
    Reponse getReponse()
    {
        return reponse;
    }
}
