package org.n0thing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.n0thing.services.DatabaseService;

public class Main extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception {
        // Inicializar base de datos
        DatabaseService.initialize();

        // Cargar la vista de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();

        // Crear escena
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());

        //Configurar ventana
        primaryStage.setTitle("N0THING in order");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED); //Sin barra de titulo de windows
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
