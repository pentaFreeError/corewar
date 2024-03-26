package graphique.mvc;

import java.util.*;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {
    protected List<EcouteurModele> listeEcouteur;

    public AbstractModeleEcoutable() {
        this.listeEcouteur = new ArrayList<EcouteurModele>();
    }

    protected void fireChangement() {
        for (EcouteurModele ecouteur : this.listeEcouteur) {
            ecouteur.modeleMisAJour(this);
        }
    }

    public void ajoutEcouteur(EcouteurModele e) {
        (this.listeEcouteur).add(e);
    }

    public void retraitEcouteur(EcouteurModele e) {
        (this.listeEcouteur).remove(e);
    }
}