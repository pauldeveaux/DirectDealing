package eu.telecomnancy.directdealing.models.deals;


import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Deal {

    public static final int DEAL_EN_ATTENTE = 0, DEAL_VALIDE = 1, DEAL_FINALISE = 2, DEAL_CANCEL = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne(fetch = FetchType.EAGER)
    protected Annonce annonce;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeur_id")
    protected User demandeur;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "offrant_id")
    protected User offrant;

    protected int prix;
    
    /**
     * 0 -> En attente
     * 1 -> Validé par l'offrant
     * 2 -> Finalisé
     * 3 -> Cancel
     */
    protected int state;
    protected LocalDate date_debut;
    protected LocalDate date_fin;

    
    public Deal(User offrant, User demandeur, Annonce annonce, int prix){
        this.offrant = offrant;
        this.demandeur = demandeur;
        this.annonce = annonce;
        this.prix = prix;
        this.state = DEAL_EN_ATTENTE;
    }

    public Deal() {

    }

    public Deal(User activeUser, User author, Annonce pret, int newPrix, LocalDate begin, LocalDate end) {
        this.demandeur = activeUser;
        this.offrant = author;
        this.annonce = pret;
        this.prix = newPrix;
        this.state = DEAL_EN_ATTENTE;
        this.date_debut = begin;
        this.date_fin = end;
    }

    public void valider() throws Exception {
        if(this.state == 0)
            this.state = 1;
        else if(this.state == 3)
            throw new Exception("Cancel");
        else
            throw new Exception("Déjà validé");
    }

    public void finaliser() throws Exception {
        if(this.state == 1)
            this.state = 2;
        else if(this.state == 2)
            throw new Exception("Déjà fini");
        else if (this.state == 0)
            throw new Exception("Pas encore validé");
        else if(this.state == 3)
            throw new Exception("Cancel");
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public User getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(User demandeur) {
        this.demandeur = demandeur;
    }

    public User getOffrant() {
        return offrant;
    }

    public void setOffrant(User offrant) {
        this.offrant = offrant;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

   public String toString(){
        return String.format("""
                Deal : id %d, prix %d""", id, prix);
    }
}
