package eu.telecomnancy.directdealing.controllers.annonce;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Annonce_sing;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewAnnonceController {

    @FXML
    private RadioButton radio_mat;

    @FXML
    private RadioButton radio_service;

    @FXML
    private VBox pane;

    private int numero;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;



    public NewAnnonceController(int id){
        this.numero = id;
    }

    @FXML
    public void initialize() throws IOException {
        if(numero == 1){
            radio_mat.setSelected(true);
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NouvelleAnnoncePretView.fxml"));
            pane.getChildren().add(fxmlLoader.load());
            AnnonceController control = fxmlLoader.getController();
        }
        else if(numero == 2){
            radio_service.setSelected(true);
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("NouvelleAnnonceService.fxml"));
            pane.getChildren().add(fxmlLoader.load());
            AnnonceController control = fxmlLoader.getController();

        }
        else{
            System.out.println("Erreur");
        }
    }

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public int getId() {
        return this.numero;
    }

}

