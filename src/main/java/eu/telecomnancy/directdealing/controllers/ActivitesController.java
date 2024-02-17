package eu.telecomnancy.directdealing.controllers;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.controllers.preview.PretPreviewController;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.PageManager;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivitesController {

    private static final int ROTATE_ARROW_CLOSE = -90, ROTATE_ARROW_OPEN = 0;

    @FXML
    private Text moula;

    @FXML
    private ListView<VBox> list_pret;

    @FXML
    private ListView<VBox> list_emprunt;

    @FXML
    private ListView<VBox> list_annonce;

    @FXML
    private ImageView arrowPrets;
    @FXML
    private ImageView arrowEmprunts;
    @FXML
    private ImageView arrowAnnonces;


    private boolean bigPrets = false;
    private boolean bigEmprunts = false;
    private boolean bigAnnonces = false;

    @FXML
    private Text evaluation;

    @FXML
    private ImageView img_note;

@FXML
    public void initialize() throws IOException {
        list_annonce.setMinHeight(250);
        list_emprunt.setMinHeight(250);
        list_pret.setMinHeight(250);
        User user = DAO.getUser(DirectDealing.getInstance().getActiveUser().getUsername());
        moula.setText(String.valueOf(user.getSolde()));
        System.out.println(user.getEvaluationString());
        evaluation.setText("Votre note d'évaluation : " + String.valueOf(user.getEvaluationString()) + "/5");
        if(user.getEvaluationString().equals("Pas encore d'évaluation")){
            img_note.setVisible(false);
        }
        List<Annonce> annonces = user.getAnnonces();

        //
        List<Deal> deals = DAO.getAllDeal();
        List<Annonce> annoncesFromDeal = new ArrayList<Annonce>();
        for (Deal d : deals){
            Annonce currentAnnonce = d.getAnnonce();
            if(currentAnnonce instanceof Pret){
                Pret current = (Pret) currentAnnonce;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController(current));
                if(currentAnnonce.getAuthor().equals(user)){
                    if(d.getState() == 1){
                        PretPreviewController controller  = new PretPreviewController(current,false, false);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    } else if( d.getState() == 2){
                            PretPreviewController controller  = new PretPreviewController(current, true, false);
                            loader.setControllerFactory(iC -> controller);
                            controller.setCurrentDeal(d);
                    } else if( d.getState() == 4){
                        PretPreviewController controller  = new PretPreviewController(current, false, true);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    }
                    else{
                        PretPreviewController controller  = new PretPreviewController(current, true, false);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    }
                } else {
                    if(d.getState() == 1){
                        PretPreviewController controller  = new PretPreviewController(current,true, false);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    } else if( d.getState() == 2){
                        PretPreviewController controller  = new PretPreviewController(current, false, true);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    } else if( d.getState() == 4){
                        PretPreviewController controller  = new PretPreviewController(current,false, true);
                        loader.setControllerFactory(iC -> controller);
                        controller.setCurrentDeal(d);
                    }
                }
                VBox annonce = loader.load();
                list_pret.getItems().add(annonce);
            }
            if(currentAnnonce instanceof Emprunt){
                Emprunt current = (Emprunt) currentAnnonce;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController(current, true));
                VBox annonce = loader.load();
                list_emprunt.getItems().add(annonce);
            }
            if(currentAnnonce instanceof Service){
                Service current = (Service) currentAnnonce;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController(current));
                VBox annonce = loader.load();
                list_annonce.getItems().add(annonce);
            }
        }



        for (Annonce a : annonces){
            if(a instanceof Pret){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController((Pret) a,"cancel"));
                VBox annonce = loader.load();
                list_annonce.getItems().add(annonce);
            }
            else if(a instanceof Emprunt){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController((Emprunt) a,"cancel"));
                VBox annonce = loader.load();
                list_annonce.getItems().add(annonce);
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                loader.setControllerFactory(iC -> new PretPreviewController((Service) a,"cancel"));
                VBox annonce = loader.load();
                list_annonce.getItems().add(annonce);
            }

        }

    }

    @FXML
    public void switchToAnnonces(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("PretView.fxml")));
    }

    @FXML
    public void switchToEditProfile(){
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ProfilView.fxml")));
    }


    @FXML
    public void togglePrets(){
        if(bigPrets){
            this.arrowPrets.setRotate(ROTATE_ARROW_CLOSE);
            list_pret.setMinHeight(50);
            list_pret.setMaxHeight(100);
            list_pret.setPrefHeight(100);
            bigPrets = false;
        }
        else{
            this.arrowPrets.setRotate(ROTATE_ARROW_OPEN);
            list_pret.setMaxHeight(400);
            list_pret.setPrefHeight(400);
            bigPrets = true;
        }
    }

    @FXML
    public void toggleEmprunts(){
        if(bigEmprunts){
            this.arrowEmprunts.setRotate(ROTATE_ARROW_CLOSE);
            list_emprunt.setMinHeight(50);
            list_emprunt.setMaxHeight(100);
            list_emprunt.setPrefHeight(100);
            bigEmprunts = false;
        }
        else{
            this.arrowEmprunts.setRotate(ROTATE_ARROW_OPEN);
            list_emprunt.setMaxHeight(400);
            list_emprunt.setPrefHeight(400);
            bigEmprunts = true;
        }
    }

    @FXML
    public void toggleAnnonces(){
        if(bigAnnonces){
            this.arrowAnnonces.setRotate(ROTATE_ARROW_CLOSE);
            list_annonce.setMinHeight(50);
            list_annonce.setMaxHeight(100);
            list_annonce.setPrefHeight(100);
            bigAnnonces = false;
        }
        else{
            this.arrowAnnonces.setRotate(ROTATE_ARROW_OPEN);
            list_annonce.setMinHeight(400);
            list_annonce.setPrefHeight(400);
            bigAnnonces = true;
        }
    }
}
