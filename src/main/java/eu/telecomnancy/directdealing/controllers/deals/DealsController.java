package eu.telecomnancy.directdealing.controllers.deals;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.controllers.ChatController;
import eu.telecomnancy.directdealing.controllers.DealPopupController;
import eu.telecomnancy.directdealing.controllers.ListChatsController;
import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.*;
import eu.telecomnancy.directdealing.models.annonces.*;
import eu.telecomnancy.directdealing.models.deals.*;
import eu.telecomnancy.directdealing.utils.PageManager;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import java.time.LocalDate;

import java.io.IOException;

public class DealsController {

    @FXML
    private Button chat;

    @FXML
    private Button deals;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane new_chat;

    @FXML
    private Text auteur;

    @FXML
    private Text description;

    @FXML
    private Text date;

    @FXML
    private TextField prix;

    @FXML
    private Text adresse;

    @FXML
    private ImageView annonceView;

    private Annonce annonce;
    @FXML
    private Text end;

    @FXML
    private Text frequency;

    @FXML
    private Text type;

    private Parent chatview;

    private Parent chatlistview;

    private boolean isChatOpen;

    private boolean isChatListOpen;

    public DealsController(Annonce annonce){
        this.annonce = annonce;
        this.isChatOpen=false;
        this.isChatListOpen=false;
    }



    @FXML
    public void initialize(){
        if(annonce instanceof Pret) {
            auteur.setText("Auteur : " + annonce.getAuthor().getUsername());
            description.setText("Description : " + annonce.getDescription());
            date.setText("Date de debut : " + annonce.dateToString(annonce.getDate_debut()));
            if(annonce.getDate_fin() != null)
                end.setText("Date de fin : " + annonce.dateToString(annonce.getDate_fin()));
            prix.setText(String.valueOf(annonce.getPrix()));
            adresse.setText("Adresse : " + annonce.getAdresse());
        }
        else if(annonce instanceof Emprunt){
            auteur.setText("Auteur : " + annonce.getAuthor().getUsername());
            description.setText("Description : " +  annonce.getDescription());
            date.setText("Date de debut : " +  annonce.dateToString( annonce.getDate_debut()));
            end.setText("Date de fin : " +  annonce.dateToString( annonce.getDate_fin()));
            prix.setText(String.valueOf( annonce.getPrix()));
            adresse.setText("Adresse : " +  annonce.getAdresse());
        }
        else if(annonce instanceof Service){
            end.setVisible(false);
            auteur.setText("Auteur : " +  annonce.getAuthor().getUsername());
            description.setText("Description : " +  annonce.getDescription());
            date.setText("Date de debut : " +  annonce.dateToString( annonce.getDate_debut()));
            prix.setText(String.valueOf( annonce.getPrix()));
            adresse.setText("Adresse : " +  annonce.getAdresse());
            type.setText("Type : " +  annonce.getType());
            type.setVisible(true);
            if( annonce.getFrequence() != null){
                frequency.setText("Frequence : " + annonce.getFrequence());
                frequency.setVisible(true);
            }
        }
    }

    public void openChat() throws IOException, NotAUserException {
        if (DirectDealing.getInstance().getActiveUser().getUsername().equals(annonce.getAuthor().getUsername())) {
            if (!this.isChatListOpen) {
                this.openListUsersChat();
                this.isChatListOpen = true;
            }
        } else {
            if (!this.isChatOpen) {
                this.openRealChat();
                this.isChatOpen = true;
            }
        }
    }

    public void openListUsersChat() {
        if(!this.isChatListOpen){
            try {
                FXMLLoader loader = new FXMLLoader(Application.class.getResource("ListChatsView.fxml"));
                loader.setControllerFactory(c -> {
                    try {
                        return new ListChatsController(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });

                Parent chatview = loader.load();
                Window parent_panel = pane.getScene().getWindow();
                double x = parent_panel.getX() + 10;
                double y = parent_panel.getY() + parent_panel.getHeight() - chatview.prefHeight(-1) - 100;
                chatview.setTranslateX(x);
                chatview.setTranslateY(y);
                pane.getChildren().add(chatview);
                this.chatlistview = chatview;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openRealChat() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("ChatView.fxml"));
            loader.setControllerFactory(c -> {
                try {
                    return new ChatController(DirectDealing.getInstance().getActiveUser(), annonce,this);
                } catch (NotAUserException e) {
                    e.printStackTrace();
                }
                return null;
            });
            Parent chatview = loader.load();
            Window parent_panel = pane.getScene().getWindow();
            double x = parent_panel.getX() + 10;
            double y = parent_panel.getY() + parent_panel.getHeight() - chatview.prefHeight(-1) - 100;
            chatview.setTranslateX(x);
            chatview.setTranslateY(y);
            pane.getChildren().add(chatview);
            this.chatview = chatview;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openRealChat(ChatController chatC){
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("ChatView.fxml"));
        loader.setControllerFactory(c ->chatC);
        Parent chatview = null;
        try {
            chatview = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Window parent_panel = pane.getScene().getWindow();
        double x = parent_panel.getX() + 10;
        double y = parent_panel.getY() + parent_panel.getHeight() - chatview.prefHeight(-1) - 100;
        chatview.setTranslateX(x);
        chatview.setTranslateY(y);
        pane.getChildren().add(chatview);
        this.chatview = chatview;
    }

    public void dealHandler(){
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("DealPopupView.fxml"));
        loader.setControllerFactory(c -> new DealPopupController(annonce));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage popup = new Stage();
        popup.setScene(new Scene(root));

        popup.showAndWait();
        System.out.println("BBBBBBBBBBBBBBBbb");
        System.out.println("");
        System.out.println(DirectDealing.getInstance().getActiveUser());
        System.out.println(annonce.getAuthor().getDealsOffrant());
        System.out.println(DirectDealing.getInstance().getActiveUser().getDealsDemandeur());
        System.out.println(DAO.getAllDeal());
    }




    public void home(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("Home.fxml")));
    }

    public void setChatview(Parent chatview) {
        this.chatview = chatview;
    }


    public Annonce getAnnonce(){
        return this.annonce;
    }

    public Pane getPane(){
        return this.pane;
    }

    public void closeChat() {
        if(this.isChatOpen){
            //System.out.println("on va remove chatview du pane.");
            pane.getChildren().remove(chatview);
            //System.out.println("C'est bon.");
            this.isChatOpen=false;
        }
    }

    public void closeChatList() {
        //System.out.println("Dans le close chat list (and) : "+this.isChatListOpen+!this.isChatOpen);
        if(this.isChatListOpen&&!this.isChatOpen){
            //System.out.println("on va remove chatlistview du pane.");
            pane.getChildren().remove(chatlistview);
            //System.out.println("C'est bon");
            this.isChatListOpen=false;
        }
    }

    public void setChatListOpen(boolean value){
        this.isChatListOpen=value;
    }

    public boolean isChatOpen() {
        return isChatOpen;
    }

    public boolean isChatListOpen() {
        return isChatListOpen;
    }

    public void setChatOpen(boolean chatOpen) {
        isChatOpen = chatOpen;
    }
}
