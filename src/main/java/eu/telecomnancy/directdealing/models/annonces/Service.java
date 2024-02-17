package eu.telecomnancy.directdealing.models.annonces;

import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Service")
public class Service extends AnnonceService {

    public Service() {
        super();
    }

    public Service(String titre, String description, int prix, String adresse, User auteur, String type, LocalDate date_debut) {
        super(titre, description, prix, adresse, auteur, type, date_debut);
    }

    public Service(String title, String description, int vbucks, String adresse, User username, String type, LocalDate value, String frequence) {
        super(title, description, vbucks, adresse, username, type, value, frequence);
    }


}
