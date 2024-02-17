package eu.telecomnancy.directdealing.controllers.home;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.utils.InformationVerifier;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HomeLeft {

    @FXML
    private TextField filtre_eval;

    @FXML
    private Button confirm;

    private HomeCenterController control;

    public HomeLeft(HomeCenterController home_control){
        this.control = home_control;
    }

    @FXML
    private void initialize() {
        InformationVerifier.formatIntTextField(filtre_eval);
    }

    public void choice() throws IOException {
        System.out.println(this.control);
        this.control.update_eval(filtre_eval.getText());
    }



}
