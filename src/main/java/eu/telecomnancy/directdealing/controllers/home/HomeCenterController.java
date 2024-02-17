package eu.telecomnancy.directdealing.controllers.home;

import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Annonce;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.chat.Chat;
import eu.telecomnancy.directdealing.utils.Alerts;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.telecomnancy.directdealing.controllers.annonce.AnnonceController;
import eu.telecomnancy.directdealing.controllers.preview.PretPreviewController;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.utils.PageManager;

public class HomeCenterController {

    @FXML
    private ListView<VBox> verticalListView;

    @FXML
    private Button annonceButton;

    @FXML
    private Button pretButton;

    @FXML
    private Button serviceButton;

    @FXML
    private TextField searchBar;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    private boolean used = false;

    private boolean used_service = false;

    private ObservableList<Pret> observableList;

    private ObservableList<Annonce> observableListAnnonce;

    private String note_eval;
    public HomeCenterController() {
        // Ajoutez une initialisation ici si nécessaire
        List<Pret> pretList = DAO.getAllPrets();
        observableList = FXCollections.observableList(pretList);
        observableListAnnonce = FXCollections.observableList(new ArrayList<Annonce>());
    }

    public void update_eval(String note) throws IOException {
        this.note_eval = note;
        initialize();
    }

    @FXML
    void initialize() throws IOException {
        this.used = true;
        // Initialiser les éléments de la ChoiceBox
        typeChoiceBox.getItems().addAll("Toutes les annonces");
        typeChoiceBox.setValue("Toutes les annonces");
    
        // Initialiser la liste observable avec les prêts
        List<Annonce> pretList = DAO.getAllAnnonces();
        observableListAnnonce = FXCollections.observableList(pretList);
    
        // Créer une FilteredList liée à l'ObservableList
        FilteredList<Annonce> filteredData = new FilteredList<>(observableListAnnonce, p -> true);
    
        // Charger les éléments initiaux dans la ListView
        loadItemsToView(filteredData);
    
        // Ajouter un écouteur sur la propriété text de la barre de recherche
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pret -> {
                if (newValue == null || newValue.isEmpty()) {
                    // Si le texte de recherche est vide, affiche tout
                    return true;
                }
    
                // Convertir la recherche en minuscules pour une correspondance insensible à la casse
                String lowerCaseFilter = newValue.toLowerCase();
    
                // Vérifier si le prêt contient le texte de recherche dans le titre, la description ou l'adresse
                return pret.getTitre().toLowerCase().contains(lowerCaseFilter) ||
                        pret.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        pret.getAdresse().toLowerCase().contains(lowerCaseFilter);
            });
    
            // Afficher les éléments mis à jour dans la ListView
            loadItemsToView(filteredData);
        });
    }
    
    private void loadItemsToView(FilteredList<Annonce> filteredData) {
        try {
            if (this.note_eval != null) {
                List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
                List<Annonce> prets = new ArrayList<>();
                for (User user : users) {
                    prets.addAll(user.getAnnonces());
                }
                // Effacer les éléments existants dans la ListView
                verticalListView.getItems().clear();

                // Charger les nouveaux éléments
                for (Annonce annonce : prets) {
                    if (annonce instanceof Pret) {
                        Pret currentPret = (Pret) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentPret));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    } else if (annonce instanceof Emprunt) {
                        Emprunt currentEmprunt = (Emprunt) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentEmprunt));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    } else if (annonce instanceof Service) {
                        Service currentService = (Service) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentService));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    }
                }
            } else {
                // Effacer les éléments existants dans la ListView
                verticalListView.getItems().clear();

                // Charger les nouveaux éléments
                for (Annonce annonce : filteredData) {
                    if (annonce instanceof Pret) {
                        Pret currentPret = (Pret) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentPret));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    } else if (annonce instanceof Emprunt) {
                        Emprunt currentEmprunt = (Emprunt) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentEmprunt));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    } else if (annonce instanceof Service) {
                        Service currentService = (Service) annonce;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                        loader.setControllerFactory(iC -> new PretPreviewController(currentService));
                        VBox jourVBox = loader.load();
                        verticalListView.getItems().add(jourVBox);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    

    @FXML
    private void handleAnnonceButtonClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/PretView.fxml"));
        loader.setControllerFactory(iC -> new AnnonceController());
        PageManager.switchPage(loader);
    }

    @FXML
    private void handlePretButtonClick() throws IOException {
        System.out.println("Clicked on button: Prêt");
        typeChoiceBox.getItems().clear();
        verticalListView.getItems().clear();
        typeChoiceBox.getItems().addAll("Toutes les annonces matériels","Demande", "Proposition");
        typeChoiceBox.setValue("Toutes les annonces matériels");
        this.used = false;
        ini_pret();
        init_emprunt();
    }

    @FXML
    private void ini_pret(){
        this.used = true;
        try{
            if(this.note_eval != null) {
                List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
                List<Pret> prets = new ArrayList<>();
                for(User user : users){
                    prets.addAll(user.getPrets());
                }
                for (Pret pret : prets) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(pret));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
            else{
                List<Pret> prets = DAO.getAllPrets();
                for (Pret pret : prets) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(pret));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init_emprunt(){
        this.used = true;
        try{
            if(this.note_eval != null) {
                List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
                List<Emprunt> emprunts = new ArrayList<>();
                for(User user : users){
                    emprunts.addAll(user.getEmprunts());
                }
                for (Emprunt emprunt : emprunts) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(emprunt));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
            else{
                List<Emprunt> emprunts = DAO.getAllEmprunts();
                for (Emprunt emprunt : emprunts) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(emprunt));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    private void handleServiceButtonClick() {
        System.out.println("Clicked on button: Service");
        switchtoservice();
    }

    @FXML
    private void switchtoservice(){
        typeChoiceBox.getItems().clear();
        typeChoiceBox.getItems().addAll("Toutes les annonces services","Avec récurrence", "Sans récurrence");
        typeChoiceBox.setValue("Toutes les annonces services");
        verticalListView.getItems().clear();
        ini_service();
    }

    private void ini_service(){
        this.used_service = true;
        try{
            if(this.note_eval != null) {
                List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
                List<Service> services = new ArrayList<>();
                for(User user : users){
                    services.addAll(user.getServices());
                }
                for (Service service : services) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(service));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
            else{
                List<Service> services = DAO.getAllServices();
                for (Service service : services) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                    loader.setControllerFactory(iC -> new PretPreviewController(service));
                    VBox jourVBox = loader.load();
                    verticalListView.getItems().add(jourVBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleServiceChoice(String selectedOption) {
        if (this.note_eval != null) {
            List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
            List<Service> services = new ArrayList<>();
            for (User user : users) {
                services.addAll(user.getServices());
            }
            if (selectedOption.equals("Avec récurrence")) {
                verticalListView.getItems().clear();
                this.used_service = false;
                try {
                    for (Service service : services) {
                        System.out.println(service);
                        if (service.getFrequence() != null) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Sans récurrence")) {
                verticalListView.getItems().clear();
                this.used_service = false;
                try {
                    for (Service service : services) {
                        if (service.getFrequence() == null) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (!this.used_service) {
                    verticalListView.getItems().clear();
                    try {
                        for (Service service : services) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            List<Service> services = DAO.getAllServices();
            if (selectedOption.equals("Avec récurrence")) {
                verticalListView.getItems().clear();
                this.used_service = false;
                try {
                    for (Service service : services) {
                        System.out.println(service);
                        if (service.getFrequence() != null) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Sans récurrence")) {
                verticalListView.getItems().clear();
                this.used_service = false;
                try {
                    for (Service service : services) {
                        if (service.getFrequence() == null) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (!this.used_service) {
                    verticalListView.getItems().clear();
                    try {
                        for (Service service : services) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(service));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @FXML
    private void handleChoiceBoxAction() {
        String selectedOption = typeChoiceBox.getValue();
        System.out.println("ASelected option in ChoiceBox: " + selectedOption);
        if (this.note_eval != null) {
            List<User> users = DAO.getUserByEval(Integer.parseInt(this.note_eval));
            List<Annonce> prets = new ArrayList<>();
            for (User user : users) {
                prets.addAll(user.getAnnonces());
            }
            if (selectedOption.equals("Demande")) {
                verticalListView.getItems().clear();
                this.used = false;
                try {
                    for (Annonce pret : prets) {
                        if (pret instanceof Emprunt) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController((Emprunt) pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Proposition")) {
                verticalListView.getItems().clear();
                this.used = false;
                try {
                    for (Annonce pret : prets) {
                        if (pret instanceof Pret) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController((Pret) pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Toutes les annonces matériels")) {
                if (!this.used) {
                    verticalListView.getItems().clear();
                    try {
                        List<Pret> prets_2 = DAO.getAllPrets();
                        for (Pret pret : prets_2) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                        List<Emprunt> emprunts = DAO.getAllEmprunts();
                        for (Emprunt emprunt : emprunts) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(emprunt));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                handleServiceChoice(selectedOption);
            }
        } else {
            List<Annonce> prets = DAO.getAllAnnonces();
            if (selectedOption.equals("Demande")) {
                verticalListView.getItems().clear();
                this.used = false;
                try {
                    for (Annonce pret : prets) {
                        if (pret instanceof Emprunt) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController((Emprunt) pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Proposition")) {
                verticalListView.getItems().clear();
                this.used = false;
                try {
                    for (Annonce pret : prets) {
                        if (pret instanceof Pret) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController((Pret) pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (selectedOption.equals("Toutes les annonces matériels")) {
                if (!this.used) {
                    verticalListView.getItems().clear();
                    try {
                        List<Pret> prets_2 = DAO.getAllPrets();
                        for (Pret pret : prets_2) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(pret));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                        List<Emprunt> emprunts = DAO.getAllEmprunts();
                        for (Emprunt emprunt : emprunts) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/directdealing/pretPreviewView.fxml"));
                            loader.setControllerFactory(iC -> new PretPreviewController(emprunt));
                            VBox jourVBox = loader.load();
                            verticalListView.getItems().add(jourVBox);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                handleServiceChoice(selectedOption);
            }
        }
    }
}