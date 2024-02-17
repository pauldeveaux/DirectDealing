package eu.telecomnancy.directdealing.controllers;

import java.io.IOException;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.exceptions.WrongPasswordException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.utils.Hash;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ConnexionController {
    private final DirectDealing instance;
    
    @FXML
    VBox root;
    @FXML
    public Button connexion;


    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField mdp;
    @FXML
    private Button togglePasswordButton;

    public ConnexionController(){
        this.instance=DirectDealing.getInstance();
    }

    @FXML
    public void initialize(){
        root.setOnKeyPressed(this::handleEnterKeyPressed);




    }


    @FXML
    private void showPassword(){
        mdp.setPromptText(mdp.getText());
        mdp.clear();
    }

    @FXML
    private void hidePassword(){
        mdp.setText(mdp.getPromptText());
        mdp.setPromptText("");
    }

    private void handleEnterKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                onConnexionButtonClick();
            } catch (WrongPasswordException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onConnexionButtonClick() throws WrongPasswordException, IOException {
        String psdo = pseudo.getText();
        String pass = mdp.getText();
        try{
        instance.connectUser(psdo, Hash.hash(pass));
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("Home.fxml")));
    }

        catch(NotAUserException e) {
            showAlert("Utilisateur non reconnu","Le pseudo n'est pas reconnu.");
        }
        catch(WrongPasswordException b){
            showAlert("Mot de passe erroné","Le mot de passe saisi ne correspond pas au pseudo.");
        }
    }

    public void inscriptionPage(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("CreationCompteView.fxml")));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error (t'es vraiment trop nul)");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
