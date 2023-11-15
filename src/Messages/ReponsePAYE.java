package Messages;

import Interface.Reponse;

public class ReponsePAYE implements Reponse {
    //Format reponse = PAYFACTURE#ok
    private boolean valide;
    public ReponsePAYE(boolean Valide) {
        valide = Valide;
    }

    public String getValide() {
        if(valide)
            return "PAYFACTURE#ok";
        else
            return "PAYFACTURE#ko";
    }
}
