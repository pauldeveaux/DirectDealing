package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.exceptions.UserAlreadyExistsException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.utils.Hash;
import eu.telecomnancy.directdealing.utils.InformationVerifier;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.Alerts;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class CreationCompteController {
    private DirectDealing instance;
    @FXML
    private TextField pseudo;

    @FXML
    private TextField mail;

    @FXML
    private TextField adr;

    @FXML
    private TextField num;
    @FXML
    private PasswordField mdp1;
    @FXML
    private PasswordField mdp2;

    @FXML
    private Button retourButton;

    public CreationCompteController(){
        this.instance=DirectDealing.getInstance();
    }


    public void inscripiton() throws UserAlreadyExistsException {
        String numText = num.getText();
        String pseudoText = pseudo.getText();
        String mailText = mail.getText();
        String adrText = adr.getText();
        String mdp1Text = mdp1.getText();
        String mdp2Text = mdp2.getText();

        if (pseudoText.isEmpty() || mailText.isEmpty() || adrText.isEmpty() || mdp1Text.isEmpty() || mdp2Text.isEmpty()) {
            Alerts.showAlert("Empty fields", "You must fill every field to register.");
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("CreationCompteView.fxml")));
            return;
        }
        if(!InformationVerifier.isValidPseudo(pseudoText)){
            Alerts.showAlert("Invalid Pseudo", "Please enter a valid pseudo, it has to be at least 3 characters-long.");
            return;
        }
        if(!InformationVerifier.isValidPhoneNumber(numText)){
            Alerts.showAlert("Invalid Phone Number","The phone number is incorrect.");
            return;
        }
        if(!InformationVerifier.isValidEmail(mailText)){
            Alerts.showAlert("Invalid Email", "Please enter a email address.");
            return;
        }
        if(!InformationVerifier.isPasswordValid(mdp1Text)){
            Alerts.showAlert("Invalid Password", "Please enter a valid password, it has to be at least 5 characters-long.");
            return;
        }
        if (!mdp1Text.equals(mdp2Text)) {
            Alerts.showAlert("Different Passwords", "The passwords must be identical.");
            return;
        }

        try{
            User user = instance.inscrireUser(pseudoText,mailText,adrText,numText, Hash.hash(mdp1Text));
            JsonUtils.toJson(user);
            Alerts.showValidation("Compte créé","Vous allez être redirigé vers la page de connexion, vous pourrez vous y connecter.");
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("ConnexionView.fxml")));
        }
        catch (UserAlreadyExistsException e){
            Alerts.showAlert("Un tel utilisateur existe déjà","Veuillez vous connecter avec le bon mot de passe.");
        }
    }

    @FXML
    void handleRetourButton(ActionEvent event) {
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ConnexionView.fxml")));
    }
}
