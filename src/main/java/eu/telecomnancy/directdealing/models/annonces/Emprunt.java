package eu.telecomnancy.directdealing.models.annonces;

import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Emprunt")
public class Emprunt extends Annonce {

    public Emprunt() {
        super();
    }

    public Emprunt(String titre, String description, int prix, String adresse, User user, LocalDate date_debut, LocalDate date_fin) {
        super(titre, description, prix, adresse, user, date_debut, date_fin, "");
    }

}
