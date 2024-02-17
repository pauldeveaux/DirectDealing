package eu.telecomnancy.directdealing.utils;

import eu.telecomnancy.directdealing.exceptions.NotAUserException;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.utils.db.DAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;

/**
 * Classe pour gérer le changement de pages
 */
public class PageManager {

    /**
     * Pane root de la scene
     */
    public static BorderPane root;

    /**
     * Switch de page : la méthode doit être appelée de cette façon :
     * PageManager.switchPage(new FXMLLoader(Application.class.getResource("XYZView.fxml")));
     * @param loader
     */
    public static void switchPage(FXMLLoader loader){
        System.out.println(DirectDealing.getInstance().getActiveUser());
        System.out.println(DAO.getAllDeal());
        try {
            root.setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Switch de page avec un controllerFactory : la méthode doit être appelée de cette façon :
     * PageManager.switchPage(new FXMLLoader(Application.class.getResource("CreationCompteView.fxml")), c -> {
     *             return new Controller(params);
     *         });
     * @param loader
     */
    public static void switchPage(FXMLLoader loader, Callback<Class<?>, Object> controllerFactory){
        loader.setControllerFactory(controllerFactory);
        switchPage(loader);
    }

    public static void resetPage(){
        root.setCenter(null);
        root.setTop(null);
        root.setLeft(null);
        root.setRight(null);
        root.setBottom(null);
    }

    public static void switchPage(BorderPane pane){
        root.setCenter(pane.getCenter());
        root.setTop(pane.getTop());
        root.setLeft(pane.getLeft());
    }

    public static void resetLeft(){
        root.setLeft(null);
    }


}
