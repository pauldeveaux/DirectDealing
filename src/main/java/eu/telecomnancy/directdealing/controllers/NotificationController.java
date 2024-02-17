package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.db.DAO;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class NotificationController {


    @FXML
    private HBox boxNotif;

    @FXML
    private ImageView notificationImage;

    @FXML
    private ImageView userImage;

    @FXML
    private Label authorLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label oldPriceLabel;

    @FXML
    private Label newPriceLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button viewButton;

    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    private Deal deal;



    public NotificationController(Deal deal) {
        this.deal = deal;
    }

    @FXML
    void initialize() {
        //titleLabel.setText(deal.getAnnonce().getTitre());
        authorLabel.setText(deal.getDemandeur().getUsername());

        oldPriceLabel.setText("Prix original : " + String.valueOf(deal.getAnnonce().getPrix()));
        newPriceLabel.setText("Prix proposé : " + String.valueOf(deal.getPrix()));

        dateLabel.setText("Date : " + deal.getAnnonce().getDate_debut().toString());

    }


    @FXML
    private void handleViewButtonClick() {
        System.out.println("Clicked on button: Voir l'annonce");
    }

    @FXML
    private void handleAcceptButtonClick() {
        System.out.println("Clicked on button: ACCEPTER");
        updtNotif(Deal.DEAL_VALIDE);
    }

    @FXML
    private void handleRejectButtonClick() {
        System.out.println("Clicked on button: REFUSER");
        updtNotif(Deal.DEAL_CANCEL);
    }

    private void updtNotif(int state){
        try {
            // Obtenez la ListView associée à l'ObservableList
            @SuppressWarnings("unchecked") // Supprime le warning de type non vérifié
            ListView<HBox> listView = (ListView<HBox>) this.boxNotif.getScene().getRoot().lookup("#notificationListView");

            // Obtenez l'ObservableList associée à la ListView
            ObservableList<HBox> items = listView.getItems();

            // Supprimez l'élément de l'ObservableList
            deal.setState(state);
            DAO.updateDeal(deal);
            items.remove(this.boxNotif);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    

}



