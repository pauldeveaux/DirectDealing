package eu.telecomnancy.directdealing.controllers.annonce;

import eu.telecomnancy.directdealing.models.annonces.Annonce;

public class control_sin {
    private AnnonceController control;

    private static final control_sin INSTANCE = new control_sin();

    private control_sin() {
    }

    public static control_sin getInstance() {
        return INSTANCE;
    }

    public void setControl(AnnonceController control) {
        this.control = control;
    }

    public AnnonceController getControl() {
        return control;
    }


}
