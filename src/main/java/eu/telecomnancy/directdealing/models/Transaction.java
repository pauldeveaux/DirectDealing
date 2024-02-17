package eu.telecomnancy.directdealing.models;

import java.time.LocalDate;

public class Transaction {

    private int prix;
    private LocalDate date;
    private User offrant;
    private User demandeur;
    private boolean finalise;


    public Transaction(User offrant, User demandeur, int prix){
        this.offrant = offrant;
        this.demandeur = demandeur;
        this.prix = prix;
        this.finalise = false;
    }


    public void finaliserTransaction(){
        // TODO ajouter/retirer solde
    }
}
