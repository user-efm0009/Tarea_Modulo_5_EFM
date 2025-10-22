package org.example.halloween_terror;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Carga la interfaz principal desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/halloween_terror/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Configura la ventana principal
        stage.setTitle("Halloween");
        stage.setScene(scene);
        stage.show();

        // Aplica la hoja de estilos CSS
        scene.getStylesheets().add(
                getClass().getResource("/org/example/halloween_terror/Ventana_Principal.css").toExternalForm()
        );
    }

    // Punto de entrada de la aplicación
    public static void main(String[] args) {
        launch();
    }
}
