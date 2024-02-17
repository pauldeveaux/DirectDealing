module eu.telecomnancy.directdealing {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires java.sql;

    opens eu.telecomnancy.directdealing to javafx.fxml;
    exports eu.telecomnancy.directdealing;
    exports eu.telecomnancy.directdealing.models.annonces;
    opens eu.telecomnancy.directdealing.models.annonces;
    exports eu.telecomnancy.directdealing.controllers;
    exports eu.telecomnancy.directdealing.controllers.preview;
    opens eu.telecomnancy.directdealing.controllers to javafx.fxml;
    opens eu.telecomnancy.directdealing.controllers.preview to javafx.fxml;
    opens eu.telecomnancy.directdealing.controllers.home to javafx.fxml;
    opens eu.telecomnancy.directdealing.controllers.annonce to javafx.fxml, org.hibernate.orm.core;
    exports eu.telecomnancy.directdealing.controllers.annonce;
    opens eu.telecomnancy.directdealing.models to javafx.fxml, org.hibernate.orm.core;
    exports eu.telecomnancy.directdealing.models;
    exports eu.telecomnancy.directdealing.models.chat;
    opens eu.telecomnancy.directdealing.models.deals to javafx.fxml, org.hibernate.orm.core;

    opens eu.telecomnancy.directdealing.models.chat to javafx.fxml, org.hibernate.orm.core;
    opens eu.telecomnancy.directdealing.controllers.deals to javafx.fxml;

    opens eu.telecomnancy.directdealing.models.evaluation to javafx.fxml, org.hibernate.orm.core;




    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jakarta.persistence;

}