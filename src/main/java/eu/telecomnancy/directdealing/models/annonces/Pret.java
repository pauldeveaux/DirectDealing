package eu.telecomnancy.directdealing.models.annonces;

import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Pret")
public class Pret extends Annonce {

    public Pret() {
        super();
    }

    public Pret(String titre, String description, int prix, String adresse, LocalDate date_debut, LocalDate date_fin) {
        super(titre, description, prix, adresse, date_debut, date_fin);
    }

    public Pret(String titre, String description, int prix, String adresse, User author, LocalDate date_debut, LocalDate date_fin, String currencyImageUrl) {
        super(titre, description, prix, adresse, author, date_debut,date_fin, currencyImageUrl);
    }

    public Pret(String s, String s1, int i, String adresse, User activeUser, LocalDate of, String lien) {
        super(s,s1,i,adresse,activeUser,of,lien);
    }


    // public String getCurrencyImageUrl() {
    //     return null;
    // }

    // public String getAuthor() {
    //     return null;
    // }
}
