module com.example.emates {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.emates to javafx.fxml;
    exports com.example.emates;
}