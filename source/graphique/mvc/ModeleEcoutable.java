package graphique.mvc;

public interface ModeleEcoutable {
    public void ajoutEcouteur(EcouteurModele e);

    public void retraitEcouteur(EcouteurModele e);
}