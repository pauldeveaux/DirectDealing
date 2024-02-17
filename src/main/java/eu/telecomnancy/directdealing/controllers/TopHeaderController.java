package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.controllers.home.HomeTopController;
import eu.telecomnancy.directdealing.controllers.preview.PretPreviewController;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TopHeaderController {

    @FXML
    private Pane pane;


    @FXML
    private void initialize(){
        DirectDealing dd = DirectDealing.getInstance();

        pane.visibleProperty().bind(dd.isUserConnectedPropertyProperty());
    }

    @FXML
    public void switchToHome(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("Home.fxml")));
    }

    @FXML
    public void switchToNotification(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/NotificationListView.fxml"));
        loader.setControllerFactory(iC -> new NotificationListController());
        PageManager.switchPage(loader);
        System.out.println("Test");        
    }

    @FXML
    public void switchToActivites(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ActivitesView.fxml")));
    }

    @FXML
    public void switchToProfile(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ProfilView.fxml")));
    }

    @FXML
    public void disconnect(){
        DirectDealing dd = DirectDealing.getInstance();
        dd.deconnexion();
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ConnexionView.fxml")));
    }
}
