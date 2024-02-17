package eu.telecomnancy.directdealing.models.annonces;

import eu.telecomnancy.directdealing.models.User;

import java.time.LocalDate;

public abstract class AnnonceService extends Annonce{

    protected ServiceStrategy postStrategy;

    public AnnonceService() {
        super();
    }

    public AnnonceService(String titre, String description, int prix, String adresse, User auteur, String type, LocalDate date_debut) {
        super(titre, description, prix, adresse,auteur,type, date_debut);
    }

    public AnnonceService(String title, String description, int vbucks, String adresse, User username, String type, LocalDate value, String frequence) {
        super(title, description, vbucks, adresse, username, type, value, frequence);
    }
}
