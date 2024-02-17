package eu.telecomnancy.directdealing.models.annonces;

public class Annonce_sing {

    private Annonce annonce;

    private static final Annonce_sing INSTANCE = new Annonce_sing();

    private Annonce_sing() {
    }

    public static Annonce_sing getInstance() {
        return INSTANCE;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Annonce getAnnonce() {
        return annonce;
    }


}
