/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class FXMLreclamation_main extends Application {
    
    //@Override
    public void start(Stage primaryStage) throws Exception {
        try{
      
        Parent root = FXMLLoader.load(getClass().getResource("FXMLreclamation2.fxml"));
         Scene scene  = new  Scene (root);
        primaryStage.setScene(scene);
        primaryStage.show();
        }catch(Exception e) { System .out.println(e); }
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        /*StackPane root = new StackPane();
        root.getChildren().add(btn);*/
     /*try{
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLreclamrion.fxml")));
    
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
         }catch(Exception e){System.out.println(e); }*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
