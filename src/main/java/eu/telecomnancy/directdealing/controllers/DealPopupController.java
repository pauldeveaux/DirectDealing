package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.Alerts;
import eu.telecomnancy.directdealing.utils.InformationVerifier;
import eu.telecomnancy.directdealing.utils.db.DAO;
import eu.telecomnancy.directdealing.utils.db.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class DealPopupController {

    @FXML
    private TextField prixField;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    private Annonce annonce;

    public DealPopupController(Annonce annonce) {
        this.annonce = annonce;
    }


    @FXML
    private void initialize() {
    // Créer un filtre pour n'accepter que les entiers positifs
        InformationVerifier.formatIntTextField(prixField);
    }

    @FXML
    private void validate() {
        User offrant = DAO.getUser(annonce.getAuthor().getUsername());
        User demandeur = DAO.getUser(DirectDealing.getInstance().getActiveUser().getUsername());

        if(this.prixField.getText().isEmpty() || this.dateDebut.getValue() == null || this.dateFin.getValue() == null){

            Alerts.showAlert("Erreur", "Veuillez remplir tous les champs");
            return;
        }



        int prix = Integer.parseInt(this.prixField.getText());
        LocalDate begin = this.dateDebut.getValue();
        LocalDate end = this.dateFin.getValue();
        User user = DirectDealing.getInstance().getActiveUser();

        if(prix <= 0){
            Alerts.showAlert("Erreur", "Veuillez entrer un prix valide");
            return;
        }
        if(user.getSolde() < prix){
            Alerts.showAlert("Erreur", "Vous n'avez pas assez d'argent pour faire cette demande");
            return;
        }
        if(dateDebut.getValue().isAfter(dateFin.getValue())){
            Alerts.showAlert("Erreur", "La date de début doit être avant la date de fin");
            return;
        }


        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Deal deal = new Deal(null, null,  annonce, prix, begin, end);
        deal.setDemandeur(demandeur);
        deal.setOffrant(offrant);

        offrant.getDealsOffrant().add(deal);
        demandeur.getDealsDemandeur().add(deal);
        demandeur.setSolde(demandeur.getSolde() - prix);

        session.merge(deal);

        transaction.commit();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Votre demande a été envoyée");
        alert.setContentText("Vous serez notifié lorsque l'auteur de l'annonce aura accepté ou refusé votre demande");
        alert.showAndWait();

        close();
    }


    public void close(){
        Stage stage = (Stage) prixField.getScene().getWindow();
        stage.close();
    }
}
