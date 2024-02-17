package eu.telecomnancy.directdealing.controllers.home;

import java.io.IOException;

import eu.telecomnancy.directdealing.Application;
import eu.telecomnancy.directdealing.models.DirectDealing;
import eu.telecomnancy.directdealing.utils.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;

public class HomeTopController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu homeMenu;

    @FXML
    private Menu userMenu;

    @FXML
    private MenuItem activityMenuItem;

    @FXML
    private MenuItem homeItem;

    @FXML
    private MenuItem settingsMenuItem;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Menu helpMenu;

    private static final int SIZE = 17;

    // You can add more methods or event handlers as needed

    // For example, handling a menu item click

    @FXML
    void initialize() {
        setFontSize(homeMenu, SIZE);
        setFontSize(userMenu, SIZE);
        setFontSize(helpMenu, SIZE);
        setFontSize(activityMenuItem, SIZE);
        setFontSize(homeItem, SIZE);
        setFontSize(settingsMenuItem, SIZE);
        setFontSize(logoutMenuItem, SIZE);
    }


    @FXML
    private void handleActivityMenuItem() {
        PageManager.resetLeft();
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("ActivitesView.fxml")));
    }

    @FXML
    private void handleHomeMenuItem() throws IOException {
        PageManager.switchPage(new FXMLLoader(Application.class.getResource("Home.fxml")));
        System.out.println("Clicked on Home");
    }

    @FXML
    private void handleSettingsMenuItem() {
        System.out.println("Clicked on Settings");
        // Add logic for handling settings menu item
    }

    @FXML
    private void handleLogoutMenuItem() throws IOException {
        (DirectDealing.getInstance()).deconnexion();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ConnexionView.fxml"));
        PageManager.resetPage();
        PageManager.switchPage(fxmlLoader);
    }

    private void setFontSize(Menu menu, double size) {
        menu.setStyle("-fx-font-size: " + size + "px;");
    }

    private void setFontSize(MenuItem menuItem, double size) {
        menuItem.setStyle("-fx-font-size: " + size + "px;");
    }

}
