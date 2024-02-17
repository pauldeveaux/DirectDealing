package eu.telecomnancy.directdealing.models.annonces;

import eu.telecomnancy.directdealing.models.User;
import jakarta.persistence.*;

import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 */
@Entity
@Table(name = "annonces")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type1")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String titre;
    @Column(length = 1000)
    protected String description;
    protected int prix;
    protected String adresse;
    protected String imgUrl;
    protected boolean fini;

    @ManyToOne(fetch = FetchType.EAGER)
    protected User author;
    protected LocalDate date_debut;

    protected LocalDate date_fin;
    protected String currencyImageUrl;

    protected String type;

    protected String frequence;


    public Annonce() {
        this.imgUrl = "";
    }

    public Annonce(String titre, String description, int prix, String adresse, User user,LocalDate date_debut, LocalDate date_fin){
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.author = user;
        this.fini = false;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.imgUrl = "";
    }

    public Annonce(String titre, String description, int prix, String adresse, User author, LocalDate date_debut,LocalDate date_fin, String currencyImageUrl){
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.fini = false;
        this.imgUrl = "";
        this.author = author;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.currencyImageUrl = currencyImageUrl;
    }

    public Annonce(String s, String s1, int i, String adresse, User activeUser, LocalDate of, String lien) {
        this.titre = s;
        this.description = s1;
        this.prix = i;
        this.adresse = adresse;
        this.fini = false;
        this.imgUrl = lien;
        this.author = activeUser;
        this.date_debut = of;
        this.date_fin = of;
        this.currencyImageUrl = "";
    }

    public Annonce(String titre, String description, int prix, String adresse, User author, String type,LocalDate date_debut){
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.fini = false;
        this.imgUrl = "";
        this.author = author;
        this.type = type;
        this.date_debut = date_debut;
        this.currencyImageUrl = "";
    }

    public Annonce(String titre, String description, int prix, String adresse, User author,String type, LocalDate date_debut, String frequence){
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.fini = false;
        this.imgUrl = "";
        this.author = author;
        this.type = type;
        this.date_debut = date_debut;
        this.frequence = frequence;
        this.currencyImageUrl = "";
    }

    public Annonce(String titre, String description, int prix, String adresse, LocalDate dateDebut, LocalDate dateFin) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.adresse = adresse;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
        this.fini = false;
        this.imgUrl = "";
    }


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    public String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public User getAuthor() {
        return author;
    }

    public String getCurrencyImageUrl() {
        return currencyImageUrl;
    }

    public String dateToString() {
        return date_debut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public String getType() {
        return type;
    }

    public String getFrequence() {
        return frequence;
    }

    public String toString() {
        return String.format("""
                Annonce :
                Titre : %s
                Description : %s
                Prix : %d
                Adresse : %s
                auteur : %s
                Date de début : %s
                frequence : %s
                type : %s
                Image : %s
                Fini : %b
               """, titre, description, prix, adresse, author.getUsername(), date_debut, frequence, type, imgUrl, fini);
    }

    public int getId() {
        return id;
    }
}
