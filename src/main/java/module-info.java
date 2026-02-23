module org.rocs.osd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens org.rocs.osd.controller.login to javafx.fxml;
    opens org.rocs.osd.controller.dashboard to javafx.fxml;
    opens org.rocs.osd.model.login to org.hibernate.orm.core;

    exports org.rocs.osd;
}

