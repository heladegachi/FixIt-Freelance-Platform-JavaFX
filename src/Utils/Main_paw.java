/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Entities.Utilisateur;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 *
 */

public class Main_paw extends Application {
      public static Utilisateur session;
    @Override
    public void start(Stage primaryStage) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
      
        primaryStage.initStyle(StageStyle.UNDECORATED);
               primaryStage.setScene(scene);
        //stage.setFullScreen(true);
       primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
