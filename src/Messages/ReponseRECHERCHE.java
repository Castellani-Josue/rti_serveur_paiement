package Messages;

import Interface.Reponse;

public class ReponseRECHERCHE implements Reponse {
    private String reponse;
    public ReponseRECHERCHE(String Reponse)
    {
        reponse = Reponse;
    }

    public String getReponse() {
        return reponse;
    }
}
