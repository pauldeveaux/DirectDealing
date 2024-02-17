package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.controllers.deals.DealsController;
import eu.telecomnancy.directdealing.controllers.preview.PretPreviewController;
import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.chat.Chat;
import eu.telecomnancy.directdealing.models.chat.Message;
import eu.telecomnancy.directdealing.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    @FXML
    private TextField messageTextField;

    private User demandeur;
    private User proposeur;

    private Annonce annonce;

    private Chat activeChat;

    @FXML
    ListView<String> messages;

    private DealsController dealsController;

    public ChatController(User demandeur, Annonce annonce, DealsController dealsController) throws NotAUserException {
        this.demandeur = demandeur;
        this.proposeur = DirectDealing.getInstance().getUser(annonce.getAuthor().getUsername());
        this.annonce=annonce;
        this.dealsController=dealsController;
        this.activeChat=this.getOrCreateChat(this.getOtherUser());
    }

    public ChatController(Chat chat,DealsController dealsController){
        this.proposeur=DirectDealing.getInstance().getActiveUser();
        this.demandeur=chat.getDemandeur();
        this.annonce=dealsController.getAnnonce();
        this.dealsController=dealsController;
        this.activeChat=chat;
    }

    @FXML
    void initialize() {
        messages.getItems().setAll(activeChat.getMessages());
        DirectDealing.getInstance().getActiveUser().setNewMessages(false);
        messageTextField.setOnKeyPressed(this::handleEnterKeyPressed);
    }

    private void handleEnterKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    @FXML
    private void sendMessage() {
        String content = messageTextField.getText();
        if (!content.isEmpty()) {
            DirectDealing.getInstance().getActiveUser().sendMessage(this.getOtherUser(),content);
            this.initialize();
            messageTextField.clear();
        } else {
            Alerts.showAlert("Message vide", "Vous n'avez rien à envoyer.");
        }
    }

    private User getOtherUser(){
        if(DirectDealing.getInstance().getActiveUser().equals(proposeur)){
            return demandeur;
        }
        else{
            return proposeur;
        }
    }

    private Chat getOrCreateChat(User otherUser) {
        try {
            User activeUser = DirectDealing.getInstance().getActiveUser();

            if (activeUser.getChats().containsKey(otherUser)) {
                return activeUser.getChats().get(otherUser);
            } else {
                Chat newChat = new Chat(annonce);
                activeUser.getChats().put(otherUser, newChat);
                otherUser.getChats().put(activeUser, newChat);
                return newChat;
            }
        } catch (NotAUserException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void onRetourButtonClicked() {
        //System.out.println("Dans chatController");
        //System.out.println("Bouton retour appuyé.");
        if(DirectDealing.getInstance().getActiveUser().getUsername().equals(this.annonce.getAuthor().getUsername())){
            //System.out.println("Par l'auteur.");
            if(this.dealsController.isChatOpen()){
                //System.out.println("Le chat était ouvert et va donc être fermé. Redirection vers la liste des chats.");
                this.dealsController.closeChat();
                this.dealsController.openListUsersChat();
                //System.out.println("ok !");
            }
            else{
                //System.out.println("Le chat était fermé et donc la list des chats va être refermée.");
                this.dealsController.closeChatList();
                //System.out.println("ok !");
            }
        }
        else{
            //System.out.println("Par l'acheteur.");
            this.dealsController.closeChat();
        }
    }
}
