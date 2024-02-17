package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import eu.telecomnancy.directdealing.controllers.preview.PretPreviewController;
import eu.telecomnancy.directdealing.models.annonces.Pret;

public class NotificationListController {

    @FXML
    private ListView<HBox> notificationListView;

    public NotificationListController() {
        // Ajoutez une initialisation ici si nécessaire
    }

    @FXML
    void initialize() throws IOException {
        System.out.println("NotificationListController initialize");
        User user = DAO.getUser(DirectDealing.getInstance().getActiveUser().getUsername());

        List<Deal> deals = user.getDealsOffrant().stream().filter(deal -> deal.getState() == Deal.DEAL_EN_ATTENTE).toList();
        System.out.println("Liste deals \n" + deals);
        for (Deal deal:deals){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/NotificationView.fxml"));
            loader.setControllerFactory(iC -> new NotificationController(deal));
            HBox notification = loader.load();
            notificationListView.getItems().add(notification);
        }
    }

}