package eu.telecomnancy.directdealing.controllers.home;

import java.io.IOException;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class HomeController {

    @FXML
    private BorderPane pane;

    @FXML
    private HomeCenterController homeCenterController;

    @FXML
    private void initialize(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeCenterView.fxml"));
            fxmlLoader.setControllerFactory(iC -> new HomeCenterController());
            pane.setCenter(fxmlLoader.load());
            homeCenterController = fxmlLoader.getController();
            /*fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeTopView.fxml"));
            fxmlLoader.setControllerFactory(iC -> new HomeTopController());
            pane.setTop(fxmlLoader.load());*/

            fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeLeftView.fxml"));
            fxmlLoader.setControllerFactory(iC -> new HomeLeft(homeCenterController));
            pane.setLeft(fxmlLoader.load());
            System.out.println("home page");
            System.out.println(DirectDealing.getInstance().getActiveUser().getDealsDemandeur());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(DirectDealing.getInstance().getActiveUser().getNewMessages()){
                Alerts.showValidation("Nouveau message","Vous avez de nouveaux messages. S'il y en a plusieurs, le dernier reçu concerne l'annonce "+DirectDealing.getInstance().getActiveUser().getAnnonceMSG()+".");
            }
        }
    }


    /*public static BorderPane getHome() throws IOException{
        BorderPane pane = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeCenterView.fxml"));
        fxmlLoader.setControllerFactory(iC -> new HomeCenterController());
        pane.setCenter(fxmlLoader.load());

        fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeTopView.fxml"));
        fxmlLoader.setControllerFactory(iC -> new HomeTopController());
        pane.setTop(fxmlLoader.load());

        fxmlLoader = new FXMLLoader(Application.class.getResource("/eu/telecomnancy/directdealing/HomeLeftView.fxml"));
        pane.setLeft(fxmlLoader.load());
        return pane;
    }*/
}
