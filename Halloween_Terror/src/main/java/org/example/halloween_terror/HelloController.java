package org.example.halloween_terror;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    protected void abrirRuleta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/halloween_terror/Ruleta.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ruleta del Terror");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Para depuración
        }
    }
}
