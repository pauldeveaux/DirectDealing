package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.controllers.deals.DealsController;
import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.chat.Chat;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ListChatsController {

    @FXML
    ListView<String> listmessages;

    private DealsController dealsController;

    public ListChatsController(DealsController dealsController){
        this.dealsController=dealsController;
    }

    @FXML
    public void initialize(){
        listmessages.getItems().setAll(DirectDealing.getInstance().getActiveUser().getChatsUserListedByUsername());
    }

    @FXML
    public void onRetourButtonClicked() {
        //System.out.println("Dans chatListController");
        //System.out.println("Bouton retour appuyé.");
        //System.out.println(DirectDealing.getInstance().getActiveUser().getUsername()+" "+this.dealsController.getAnnonce().getAuthor().getUsername());
        if(DirectDealing.getInstance().getActiveUser().getUsername().equals(this.dealsController.getAnnonce().getAuthor().getUsername())){
            //System.out.println("Par l'auteur.");
            if(this.dealsController.isChatOpen()){
                //System.out.println("Le chat était ouvert et va donc être fermé. Redirection vers la liste des chats.");
                this.dealsController.closeChat();
                this.dealsController.openListUsersChat();
                //System.out.println("ok !");
            }
            else{
                //System.out.println("Le chat était fermé et donc la list des chats va être refermée.");
                //System.out.println("bool chatlist "+dealsController.isChatListOpen());
                //System.out.println("bool chat "+dealsController.isChatOpen());
                this.dealsController.closeChatList();
                //System.out.println("ok !");
            }
        }
        else{
            //System.out.println("Par l'acheteur.");
            this.dealsController.closeChat();
        }
    }

    @FXML
    public void onChatClicked() throws IOException {
        String selectedUser = listmessages.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                this.dealsController.setChatOpen(true);
                this.dealsController.openRealChat(new ChatController(getOrCreateChatWithUser(DirectDealing.getInstance().getUser(selectedUser)),this.dealsController));
            } catch (NotAUserException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Chat getOrCreateChatWithUser(User otherUser) {
        User activeUser = DirectDealing.getInstance().getActiveUser();

        if (activeUser.getChats().containsKey(otherUser)) {
            return activeUser.getChats().get(otherUser);
        } else {
            Chat newChat = null;
            try {
                newChat = new Chat(this.dealsController.getAnnonce());
            } catch (NotAUserException e) {
                throw new RuntimeException(e);
            }
            activeUser.getChats().put(otherUser, newChat);
            otherUser.getChats().put(activeUser, newChat);
            return newChat;
        }
    }
}
