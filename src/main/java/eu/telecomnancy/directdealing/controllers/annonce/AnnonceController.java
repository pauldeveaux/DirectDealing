package eu.telecomnancy.directdealing.controllers.annonce;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.*;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.PageManager;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnnonceController {
    @FXML
    private Button materiel;

    @FXML
    private Button service;

    @FXML
    private TextField montant;

    @FXML
    private DatePicker begin;

    @FXML
    private DatePicker end;

    @FXML
    private RadioButton pret;

    @FXML
    private RadioButton emprunt;

    @FXML
    private RadioButton en_recherche;

    @FXML
    private RadioButton mise_dispo;

    @FXML
    private RadioButton oui;

    @FXML
    private RadioButton non;

    @FXML
    private TextField frequence;

    @FXML
    private Text text_frequence;

    private NewAnnonceController control;

    private int numero = 0;



    public void next(Event event){
        if(event.getSource().equals(materiel)){
            this.numero = 1;
        }
        else if(event.getSource().equals(service)){
            this.numero = 2;
        }
        int finalNumero = numero;
        System.out.println(this.numero);
        this.control = new NewAnnonceController(finalNumero);
        control_sin.getInstance().setControl(this);
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("NouvelleAnnonceView.fxml")), c -> {return this.control;});
    }

    public void poster(){
        System.out.println(control_sin.getInstance().getControl().getNumero());
        User user = DirectDealing.getInstance().getActiveUser();
        Annonce annonce = null;
        if(control_sin.getInstance().getControl().getNumero() == 1){
            System.out.println("1");
            if(pret.isSelected()){
                int vbucks = Integer.parseInt(montant.getText());
                String title = control_sin.getInstance().getControl().getControl().getTitle();
                String description = control_sin.getInstance().getControl().getControl().getDescription();
                System.out.println(begin.getValue());
                System.out.println(end.getValue());
                annonce = new Pret(title,description,vbucks,DirectDealing.getInstance().getActiveUser().getAdresse(), DirectDealing.getInstance().getActiveUser(), begin.getValue(),end.getValue(),"");

                System.out.println(DirectDealing.getInstance().getActiveUser());
            }
            else{
                int vbucks = Integer.parseInt(montant.getText());
                String title = control_sin.getInstance().getControl().getControl().getTitle();
                String description = control_sin.getInstance().getControl().getControl().getDescription();
                annonce = new Emprunt(title,description,vbucks,DirectDealing.getInstance().getActiveUser().getAdresse(), DirectDealing.getInstance().getActiveUser(), begin.getValue(),end.getValue());

                System.out.println(DirectDealing.getInstance().getActiveUser());
            }
        }
        else if(control_sin.getInstance().getControl().getNumero() == 2){
            System.out.println("2");
            if(en_recherche.isSelected()){
                System.out.println("en recherche");
                int vbucks = Integer.parseInt(montant.getText());
                String title = control_sin.getInstance().getControl().getControl().getTitle();
                String description = control_sin.getInstance().getControl().getControl().getDescription();
                String adresse = DirectDealing.getInstance().getActiveUser().getAdresse();

                if(oui.isSelected()){
                    String frequence = this.frequence.getText();
                    annonce = new Service(title,description,vbucks,adresse,user,en_recherche.getText(),begin.getValue(),frequence);

                    System.out.println(DirectDealing.getInstance().getActiveUser());
                }
                else if(non.isSelected()){
                    annonce = new Service(title,description,vbucks,adresse,user,en_recherche.getText(),begin.getValue());

                    System.out.println(DirectDealing.getInstance().getActiveUser());

                }
            }
            else if(mise_dispo.isSelected()){
                System.out.println("mise dispo");
                int vbucks = Integer.parseInt(montant.getText());
                String title = control_sin.getInstance().getControl().getControl().getTitle();
                String description = control_sin.getInstance().getControl().getControl().getDescription();
                String adresse = DirectDealing.getInstance().getActiveUser().getAdresse();

                if(oui.isSelected()){
                    String frequence = this.frequence.getText();
                    annonce = new Service(title,description,vbucks,adresse,user,mise_dispo.getText(),begin.getValue(),frequence);

                    System.out.println(DirectDealing.getInstance().getActiveUser());
                }
                else if(non.isSelected()){
                    System.out.println("non");
                    annonce = new Service(title,description,vbucks,adresse,user, mise_dispo.getText(),begin.getValue());

                    System.out.println(DirectDealing.getInstance().getActiveUser());
                }
            }

        }

        if(annonce == null)
            return;

        user.addAnnonce(annonce);

        smallWindow("Votre annonce a bien été postée");
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ProfilView.fxml")));
    }

    public static void smallWindow(String text_aff){
        Stage smallwindow = new Stage();
        smallwindow.initModality(Modality.APPLICATION_MODAL);
        smallwindow.initStyle(StageStyle.UTILITY);

        Text text = new Text(text_aff);
        StackPane pane = new StackPane();
        pane.getChildren().add(text);

        Scene scene = new Scene(pane, 250, 100);

        smallwindow.setScene(scene);
        smallwindow.showAndWait();
    }

    public void dsilay_frequence(){
        if(oui.isSelected()){
            non.setDisable(true);
            text_frequence.setVisible(true);
            frequence.setVisible(true);
        } else if (!oui.isSelected()) {
            non.setDisable(false);
            text_frequence.setVisible(false);
            frequence.setVisible(false);
        }
    }

    public void display_non(){
        if(non.isSelected()){
            oui.setDisable(true);
        } else if (!non.isSelected()) {
            oui.setDisable(false);
        }
    }

    public Button getMateriel() {
        return materiel;
    }

    public Button getService() {
        return service;
    }

    public TextField getMontant() {
        return montant;
    }

    public DatePicker getBegin() {
        return begin;
    }

    public DatePicker getEnd() {
        return end;
    }

    public RadioButton getPret() {
        return pret;
    }

    public RadioButton getEmprunt() {
        return emprunt;
    }

    public int getNumero() {
        return numero;
    }

    public NewAnnonceController getControl() {
        return control;
    }
}
