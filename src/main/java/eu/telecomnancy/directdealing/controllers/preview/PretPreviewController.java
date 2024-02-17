package eu.telecomnancy.directdealing.controllers.preview;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.controllers.deals.DealsController;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.deals.Deal;
import eu.telecomnancy.directdealing.utils.Alerts;
import eu.telecomnancy.directdealing.utils.PageManager;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class PretPreviewController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label dateLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Button viewButton;

    @FXML
    private Button finirDeal;

    @FXML
    private Label labelAttente;

    @FXML
    private ImageView authorImageView;

    private Pret pret;

    private Emprunt emprunt;

    @FXML
    private Label end;

    @FXML
    private Label frequency;

    @FXML
    private Label type;

    @FXML
    private Button cancel;
    @FXML
    private VBox pane;

    private Service service;

    private boolean used = false;

    private boolean usedAttente = false;

    private boolean usedFinirDeal = false;

    private boolean booluser = false;

    private Deal currentDeal;

    public PretPreviewController(Pret pret) {
        this.pret = pret;
    }

    public PretPreviewController(Emprunt emprunt) {
        this.emprunt = emprunt;
    }

    public PretPreviewController(Service service){
        this.service = service;
    }

    public PretPreviewController(Pret pret,String ahah) {
        this.pret = pret;
        this.used = true;
    }

    public PretPreviewController(Emprunt emprunt, String aha) {
        this.emprunt = emprunt;
        this.used = true;
    }

    public void setCurrentDeal(Deal deal){
        this.currentDeal = deal;
    }

    public PretPreviewController(Service service,String ahah){
        this.service = service;
        this.used = true;
    }

    public PretPreviewController(Annonce annonce, Boolean used){
        this.usedFinirDeal = used;
        if(annonce instanceof Pret){
            this.pret = (Pret) annonce;
        }
        else if(annonce instanceof Emprunt){
            this.emprunt = (Emprunt) annonce;
        }
        else{
            this.service = (Service) annonce;
        }
    }

    public PretPreviewController(Annonce annonce, Boolean used, Boolean attente){
        this.usedFinirDeal = used;
        usedAttente = attente;
        if(annonce instanceof Pret){
            this.pret = (Pret) annonce;
        }
        else if(annonce instanceof Emprunt){
            this.emprunt = (Emprunt) annonce;
        }
        else{
            this.service = (Service) annonce;
        }


        if(this.currentDeal != null){
            if(this.currentDeal.getState() == 4){
                labelAttente.setVisible(true);
                labelAttente.setText("Deal terminé");
            }
        }
    }

    public PretPreviewController(Annonce annonce, Boolean used, Boolean attente, boolean booluser){
        this.usedFinirDeal = used;
        usedAttente = attente;
        if(annonce instanceof Pret){
            this.pret = (Pret) annonce;
        }
        else if(annonce instanceof Emprunt){
            this.emprunt = (Emprunt) annonce;
        }
        else{
            this.service = (Service) annonce;
        }
        this.booluser = booluser;
    }



    @FXML
    public void initialize() {
        if (pret != null) {
            setPretData(pret.getTitre(), pret.getPrix(), pret.getDescription(), pret.getImgUrl(), pret.getCurrencyImageUrl(),
                    pret.dateToString(pret.getDate_debut()), pret.dateToString(pret.getDate_fin()), pret.getAdresse(), pret.getAuthor().getUsername());
        }
        else if(emprunt != null){
            setPretData(emprunt.getTitre(), emprunt.getPrix(), emprunt.getDescription(), emprunt.getImgUrl(), emprunt.getCurrencyImageUrl(),
                    emprunt.dateToString(emprunt.getDate_debut()), emprunt.dateToString(emprunt.getDate_fin()), emprunt.getAdresse(), emprunt.getAuthor().getUsername());
        }
        else{
            setServiceDate(service.getTitre(), service.getPrix(), service.getDescription(), service.getImgUrl(), service.getCurrencyImageUrl(),
                    service.dateToString(service.getDate_debut()), service.getAdresse(), service.getAuthor().getUsername(), service.getType(), service.getFrequence());
        }
        if(used){
            cancel.setVisible(true);
        }
        if(usedFinirDeal){
            finirDeal.setVisible(true);
        }
        if(usedAttente){
            labelAttente.setVisible(true);
        }

        if(this.currentDeal != null){
            if(this.currentDeal.getState() == 4){
                labelAttente.setVisible(true);
                labelAttente.setText("Deal terminé");
            }
        }
    }


    // Méthode pour définir les données du prêt
    public void setPretData(String title, int i, String description, String imageUrl, String currencyImageUrl,
            String date_debut,String date_end, String location, String author) {
        titleLabel.setText(title);
        priceLabel.setText(String.valueOf(i));
        descriptionLabel.setText(description);
        // imageView.setImage(new Image(imageUrl));
        dateLabel.setText("Date début: " + date_debut);
        end.setText("Date fin: " + date_end);
        locationLabel.setText(location);
        authorLabel.setText(author);

    }

    public void setServiceDate(String title, int i, String description, String imageUrl, String currentimage,
                               String date_debut, String adresse, String author, String type,String frequence) {
        titleLabel.setText(title);
        priceLabel.setText(String.valueOf(i));
        descriptionLabel.setText(description);
        dateLabel.setText("Date début: " + date_debut);
        locationLabel.setText(adresse);
        authorLabel.setText(author);
        if (frequence != null){
            System.out.println("Tous les  " + frequence);
            frequency.setText("Tous les " + frequence);
            frequency.setVisible(true);
        }
        end.setVisible(false);
        this.type.setText(type);
        this.type.setVisible(true);
    }

    // Méthode pour gérer le clic sur le bouton "Voir l'annonce"
    @FXML
    private void handleViewButton() {
        System.out.println("Voir l'annonce");
        if(pret != null)
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("DealsView.fxml")),c -> {return new DealsController(pret);});
        else if(emprunt != null)
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("DealsView.fxml")),c -> {return new DealsController(emprunt);});
        else
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("DealsView.fxml")),c -> {return new DealsController(service);});
    }


    @FXML
    private void cancelbutton() {
        if (pret != null) {
            DAO.deleteAnnonce(pret);
            DirectDealing.getInstance().getActiveUser().getAnnonces().remove(pret);
            Alerts.showValidation("Annonce supprimée", "Votre annonce a bien été supprimée");
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("ActivitesView.fxml")));
        } else if (emprunt != null) {
            DAO.deleteAnnonce(emprunt);
            DirectDealing.getInstance().getActiveUser().getAnnonces().remove(emprunt);
            Alerts.showValidation("Annonce supprimée", "Votre annonce a bien été supprimée");
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("ActivitesView.fxml")));
        } else {
            DAO.deleteAnnonce(service);
            DirectDealing.getInstance().getActiveUser().getAnnonces().remove(service);
            Alerts.showValidation("Annonce supprimée", "Votre annonce a bien été supprimée");
            PageManager.switchPage(new FXMLLoader(Application.class.getResource("ActivitesView.fxml")));

        }
    }

    public  String getDescription(){
        return descriptionLabel.getText();
    }


    
    private void updtAnnonce(){
        this.finirDeal.setVisible(false);
        labelAttente.setVisible(true);
        if(this.currentDeal != null){
            if(this.currentDeal.getState() == 4){
                labelAttente.setVisible(true);
                labelAttente.setText("Deal terminé");
            } 
        }
    }


    // Ajoutez d'autres méthodes et gestionnaires d'événements selon vos besoins

    @FXML
    void handleFinirdealButton(){
            System.out.println("FINIR");
            updtAnnonce();
            if(currentDeal.getState() == 0){
                currentDeal.setState(1);
            } else
            if(currentDeal.getState() == 1){
                currentDeal.setState(2);
            } else if(currentDeal.getState() == 2){
                currentDeal.setState(4);
            }
            DAO.updateDeal(this.currentDeal);
    }
}