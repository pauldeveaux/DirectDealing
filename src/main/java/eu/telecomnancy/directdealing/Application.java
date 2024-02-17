package eu.telecomnancy.directdealing;

import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.exceptions.UserAlreadyExistsException;
import eu.telecomnancy.directdealing.exceptions.WrongPasswordException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.models.User;
import eu.telecomnancy.directdealing.models.annonces.Emprunt;
import eu.telecomnancy.directdealing.models.annonces.Pret;
import eu.telecomnancy.directdealing.models.annonces.Service;
import eu.telecomnancy.directdealing.models.evaluation.Evaluation;
import eu.telecomnancy.directdealing.utils.Hash;
import eu.telecomnancy.directdealing.utils.JsonUtils;
import eu.telecomnancy.directdealing.utils.PageManager;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        DirectDealing dd = DirectDealing.getInstance();
        dd.setUsers(DAO.getAllUsersByUsername());

        BorderPane root = new BorderPane();
        PageManager.root = root;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ConnexionView.fxml"));
        root.setCenter(fxmlLoader.load());
        fxmlLoader = new FXMLLoader(Application.class.getResource("TopHeaderView.fxml"));
        root.setTop(fxmlLoader.load());

        String cssFile = Objects.requireNonNull(getClass().getResource("/eu/telecomnancy/directdealing/style.css")).toExternalForm();
        root.getStylesheets().add(cssFile);

        Scene scene = new Scene(root);
        
        stage.setTitle("Kuiling ");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.onCloseRequestProperty().setValue(e -> {
            if(dd.getActiveUser() != null)
                JsonUtils.toJson(dd.getActiveUser());
        });

        stage.show();

        //connectTestUser();
    }

    private void connectTestUser(){
        DirectDealing dd = DirectDealing.getInstance();
        try {

            // creation du user
            User user = dd.inscrireUser("user", "mail", "adresse", "09129231", Hash.hash("password"));
            User yasuo = dd.inscrireUser("yasuo", "mail", "adresse", "09129231", Hash.hash("password"));
            User ahti = dd.inscrireUser("ahti", "mail", "adresse", "09129231", Hash.hash("password"));


            Pret pret1 = new Pret("Titre 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ullamcorper justo sit amet leo imperdiet hendrerit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam a rutrum dolor. Sed quis mauris orci. Morbi at bibendum ex. Ut hendrerit vulputate est, et tincidunt risus congue pulvinar. Aliquam commodo molestie nibh, sed posuere enim ornare in. Donec vel tellus magna. Mauris interdum, justo sed vulputate semper, ex diam suscipit nisl, ut pretium metus lorem sit amet nisi. Praesent maximus eleifend mi. Pellentesque posuere, justo vel auctor tristique, tortor dolor tempor ipsum, sed egestas eros sapien eu arcu. Duis rhoncus, odio et ornare aliquet, quam lectus posuere neque, sit amet vehicula lacus orci sit amet magna.", 10, "Adresse", ahti, LocalDate.of(2024,1,10),LocalDate.of(2024,1,15), "lien");
            Pret pret2 = new Pret("Titre 2", "Nutella sit amet metus ac elit pulvinar euismod sit amet in neque. Vestibulum ac venenatis dolor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eleifend ligula id aliquet vulputate. Nam viverra lacus porttitor, rutrum massa ut, sollicitudin nunc. Fusce eget tellus eu enim vestibulum congue sit amet et dolor. Nunc a lorem risus. Nunc sollicitudin elit ac nisl placerat, eu efficitur diam scelerisque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse id suscipit lorem, ac dictum quam. Maecenas tristique tincidunt dolor at porttitor. Nunc a felis ex.", 10, "Adresse", ahti, LocalDate.of(2024,1,10), LocalDate.of(2024,1,15), "lien");
            Pret pret3 = new Pret("Titre 3", "Description", 10, "Adresse", user, LocalDate.of(2024,1,10), LocalDate.of(2024,1,15), "lien");
            Pret pret4 = new Pret("Titre 4", "Description", 60, "Adresse", yasuo, LocalDate.of(2024,1,10), LocalDate.of(2024,1,15), "lien");

            ahti.addAnnonce(pret1);
            ahti.addAnnonce(pret2);
            user.addAnnonce(pret3);
            yasuo.addAnnonce(pret4);

            Emprunt emprunt1 = new Emprunt("Recherche une ventouse", "Cherche une ventouse pour déboucher mon évier", 25, "Adresse", ahti, LocalDate.of(2024,1,10), LocalDate.of(2024,1,15));
            Emprunt emprunt2 = new Emprunt("Recherche sabre", "Cherche un sabre pour tuer mes potes", 47, "Adresse", yasuo, LocalDate.of(2024,1,10), LocalDate.of(2024,1,15));

            ahti.addAnnonce(emprunt1);
            yasuo.addAnnonce(emprunt2);

            Service service1 = new Service("Service 1", "Description", 40,"Adresse", yasuo,"Recherche de service", LocalDate.of(2024,1,10));
            Service service2 = new Service("Service 2", "Description", 40,"Adresse", ahti,"Mise à dispoision de service", LocalDate.of(2024,1,10));
            Service service3 = new Service("Service 3", "Description", 40,"Adresse", user,"Recherche de service", LocalDate.of(2024,1,10), "7 jours");
            Service service4 = new Service("Service 4", "Description", 40,"Adresse", yasuo,"Mise à dispoision de service", LocalDate.of(2024,1,10), "14 jours");

            yasuo.addAnnonce(service1);
            ahti.addAnnonce(service2);
            user.addAnnonce(service3);
            yasuo.addAnnonce(service4);

            Evaluation eval1 = new Evaluation(5,user);
            Evaluation eval2 = new Evaluation(4,user);
            Evaluation eval3 = new Evaluation(3,user);


            user.addEvaluation(eval1);
            user.addEvaluation(eval2);
            user.addEvaluation(eval3);

            Evaluation eval4 = new Evaluation(5,yasuo);
            Evaluation eval5 = new Evaluation(4,yasuo);

            yasuo.addEvaluation(eval4);
            yasuo.addEvaluation(eval5);


            System.out.println("User evaluation");
            System.out.println(user.getEvaluations());
            System.out.println("\nDao all evaluations");
            System.out.println("\nDao user evaluations");
            System.out.println(DAO.getUser(user.getUsername()).getEvaluations());

            // ajout de pret exemple
        } catch (UserAlreadyExistsException e) {

        }
        finally {
            try {
                dd.connectUser("user", Hash.hash("password"));
            } catch (NotAUserException e) {
                throw new RuntimeException(e);
            } catch (WrongPasswordException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
