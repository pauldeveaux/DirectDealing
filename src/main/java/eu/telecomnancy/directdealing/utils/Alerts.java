package eu.telecomnancy.directdealing.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    // Pour une erreur
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error (t'es vraiment trop nul)");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Pour une question
    public static boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("T'es sûr sûr là ?");
        alert.setHeaderText(title);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    public static void showValidation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bien joué BG !");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

