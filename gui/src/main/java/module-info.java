module com.sdb.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.sdb.gui to javafx.fxml;
    exports com.sdb.gui;
}