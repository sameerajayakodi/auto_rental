module org.samee.lk.autorental {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens org.samee.lk.autorental to javafx.fxml;
    opens org.samee.lk.autorental.tm to javafx.base;
    exports org.samee.lk.autorental.controllers;
}