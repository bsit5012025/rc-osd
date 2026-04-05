module org.rocs.osd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires com.oracle.database.jdbc;
    requires org.slf4j;

    opens org.rocs.osd.controller.login to javafx.fxml;
    opens org.rocs.osd.controller.dashboard to javafx.fxml;
    opens org.rocs.osd.controller.offense to javafx.fxml;
    opens org.rocs.osd.controller.appeal to javafx.fxml;
    opens org.rocs.osd.controller.student to javafx.fxml;
    opens org.rocs.osd.controller.request to javafx.fxml;
    opens org.rocs.osd.controller.dialog to javafx.fxml;

    exports org.rocs.osd;
}
