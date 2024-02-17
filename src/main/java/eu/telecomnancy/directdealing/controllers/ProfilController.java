package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.exceptions.UserAlreadyExistsException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.utils.Alerts;
import eu.telecomnancy.directdealing.utils.InformationVerifier;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class ProfilController {


    @FXML
    private Pane rootPane;
    @FXML
    private TextField usernameField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField passwordField;

    @FXML
    private ImageView imageView;

    private DirectDealing dd;
    private User user;

    public ProfilController(){
        dd = DirectDealing.getInstance();
        user = dd.getActiveUser();
    }

    @FXML
    private void initialize(){
        rootPane.requestFocus();
        usernameField.setPromptText(user.getUsername());
        numeroField.setPromptText(user.getNum());
        adresseField.setPromptText(user.getAdresse());
        mailField.setPromptText(user.getMail());
        passwordField.setPromptText("*******");

        // Image view
        Image img = null;

        try {
            img = new Image(String.valueOf(user.getImgUrl().toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        System.out.println(img.getUrl());
        imageView.setImage(img);

    }

    @FXML
    private void changeUsername(){
        if(usernameField.getText().isEmpty()){
            Alerts.showAlert("Erreur de saisie","Pas de pseudo renseigné.");
        }

        try {
            dd.changeUsername(dd.getActiveUser(), usernameField.getText());
            usernameField.setText("");
            usernameField.setPromptText(user.getUsername());
        } catch (UserAlreadyExistsException e) {
            // TODO afficher popup
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void changeNumero(){
        if(numeroField.getText().isEmpty()){
            Alerts.showAlert("Erreur de saisie","Pas de numéro de téléphone renseigné.");
        }

        if(!InformationVerifier.isValidPhoneNumber(numeroField.getText())){
            Alerts.showAlert("Erreur de saisie","Le numéro de téléphone n'est pas valide.");
            return;
        }

        user.setNum(numeroField.getText());
        numeroField.setText("");
        numeroField.setPromptText(user.getNum());
    }

    @FXML
    private void changeMail(){
        if(mailField.getText().isEmpty()){
            Alerts.showAlert("Erreur de saisie","Pas d'email renseigné.");
        }

        if(!InformationVerifier.isValidEmail(mailField.getText())){
            Alerts.showAlert("Erreur de saisie","L'email n'est pas valide.");
            return;
        }

        user.setMail(mailField.getText());
        mailField.setText("");
        mailField.setPromptText(user.getMail());
    }

    @FXML
    private void changeAdresse(){
        if(adresseField.getText().isEmpty()){
            errorWindowPop("Le champs adresse ne doit pas être vide");
        }

        user.setAdresse(adresseField.getText());
        adresseField.setText("");
        adresseField.setPromptText(user.getAdresse());
    }

    @FXML
    private void changePassword(){
        if(passwordField.getText().isEmpty()){
            Alerts.showAlert("Erreur","Le champs password ne doit pas être vide");
        }

        user.setPassword(passwordField.getText());
        passwordField.setText("");
    }

    @FXML
    private void imageClick(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Import an image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images","*.png", "*.jpg", "*.gif");
        fc.getExtensionFilters().add(extensionFilter);
        File file = fc.showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            // Load the image
            Image image = new Image(file.toURI().toString());

            // Draw the image
            imageView.setImage(image);
        }
    }


    @FXML
    private void changeImage(){
        try {
            user.setImgUrl(new URI(imageView.getImage().getUrl()).toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification");
        alert.setContentText("L'image a bien été modifiée");
        alert.showAndWait();
    }

    public void errorWindowPop(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur dans la modification");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void removeUser() {
        if(Alerts.showConfirmation("Processus irréversible","Votre compte sera totalement supprimé si vous appuyez sur OK.")){
            dd.removeUser(dd.getActiveUser());
            dd.deconnexion();
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("ConnexionView.fxml")));
        }
    }

}