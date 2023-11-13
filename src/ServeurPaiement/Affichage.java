package ServeurPaiement;

import Interface.Logger;

public class Affichage implements Logger {
    public Affichage();
    @Override
    public void Trace(String message) {
        System.out.println(message);
    }
}
