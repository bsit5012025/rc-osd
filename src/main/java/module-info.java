module org.rocs.osd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.rocs.osd to javafx.fxml;
    exports org.rocs.osd;
}