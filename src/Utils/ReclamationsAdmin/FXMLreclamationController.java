/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.ReclamationsAdmin;

import Entities.reclamation;
import Entities.RepRec;
import Entities.Utilisateur;
import Services.ReclamationService;
import Services.RepRecServices;
//import Services.UtilisateurServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;
//import paw.mainUI.FXMLCnxController;

/**
 * FXML Controller class
 *
 * 
 */
public class FXMLreclamationController  implements Initializable {
    List<reclamation> liste;
        @FXML
    private JFXTreeTableView<reclamation> ReclamationTable;
    @FXML
    private TreeTableColumn<reclamation, String> objet;
    @FXML
    private TreeTableColumn<reclamation, String> text;
    @FXML
    private TreeTableColumn<reclamation, String> type;
    @FXML
    private TreeTableColumn<reclamation, String> date;
    @FXML
    private TreeTableColumn<reclamation,JFXButton> repondre;
    @FXML
    private TreeTableColumn<reclamation,JFXButton> supprimer;
    @FXML
    
    
//    private TreeTableColumn<Reclamations,JFXButton> modifier1;
//    @FXML
    
    
    private Label titrer;
    @FXML
    private Label textr;
    @FXML
    private Label dater;
    @FXML
    private Label utilisateurr;
    @FXML
    private JFXTextArea reponser;
    @FXML
    private AnchorPane rep;
    @FXML
    private Separator separator;
    @FXML
    private Label reptext;
    @FXML
    private JFXButton btnEnregistrer;
    @FXML
    private Label lab;
    @FXML
    private Label datereponse;
    @FXML
    private JFXButton btnAnnuler;
    @FXML
    private JFXTextField filtre;
    @FXML
    private AnchorPane window;
    @FXML
    private JFXTextArea supprimmer;
    @FXML
//    private JFXTextArea modifier;
//    @FXML
    private Label utilisateurr1;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReclamationService serviceRec = new ReclamationService();
        
        RequiredFieldValidator rf = new RequiredFieldValidator();
        rf.setMessage("Vous devez répondre à la réclamation.");
        reponser.getValidators().add(rf);
        reponser.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                   reponser.validate();
                    System.out.println("test1");
                }
            }
        });
       
        
        rep.setVisible(false);
        
        initReclamation();
        System.out.println("test2");
    }    

    private void initReclamation() {
        ReclamationService serviceRec = new ReclamationService();
        liste= serviceRec.getAll();
      //  UtilisateurServices serviceUtil = new UtilisateurServices();

        text.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            Utilisateur u = null;
           // u = serviceUtil.rechercher(r.getUtilisateur());
            property.set(r.getText());
            return property;
        });
        objet.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            Utilisateur u = null;
          //  u = serviceUtil.rechercher(r.getUtilisateur());
            property.set(r.getObjet());
            return property;
        });
        type.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            Utilisateur u = null;
          //  u = serviceUtil.rechercher(r.getUtilisateur());
            property.set(r.getType());
            return property;
        });
        date.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            Utilisateur u = null;
        //    u = serviceUtil.rechercher(r.getUtilisateur());
            property.set(String.valueOf(r.getDate()).substring(0, 16));
            return property;
        });
        
        repondre.setCellValueFactory(param -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            if (r.getEtat().equals("Non traitée"))
            {
                JFXButton rep = new JFXButton("Répondre");
                rep.setStyle("-fx-background-color:#00BFFF;");
                rep.setOnAction((ActionEvent e) -> {
                    reponseReclamation(r);
                });
                property.set(rep);
                return property;
            }
            else
            {
                JFXButton rep = new JFXButton("traitée :D ");
                rep.setStyle("-fx-background-color:#3ff500;");

               // rep.setOnAction((ActionEvent e) -> {
                  //  voirReponseReclamation(r);
                //});

                property.set(rep);
                return property;
            }
            
        });
        
        
          supprimer.setCellValueFactory(param -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            reclamation r = (reclamation) param.getValue().getValue();
            ReclamationService rs = new ReclamationService();
            
                
                JFXButton rep = new JFXButton("suprimer");
                rep.setStyle("-fx-background-color:#d62e2e;");
                rep.setOnAction((ActionEvent e) -> {
                    if (r.getEtat().equals("Traitée")){
                        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Boîte de dialogue de confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer la commande : ");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                    rs.DeleteReclamation(r.getId());}}
                    else { 
                        
                        Notifications.create()
                                .title("Suppression Impossible")
                                .text("Impossible de supprimer avant de répondre !")
                                .showInformation(); }
                    
                    Parent root;
                             try {
                root=FXMLLoader.load(getClass().getResource("FXMLreclamationAdmin.fxml"));
                rep.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(FXMLreclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
                });
                property.set(rep);
                return property;
              
            
        });
          
//          modifier1.setCellValueFactory(param -> {
//            SimpleObjectProperty property = new SimpleObjectProperty();
//            Reclamations r = (Reclamations) param.getValue().getValue();
//            ReclamationsService rs = new ReclamationsService();
//                JFXButton rep = new JFXButton("modifier");
//                rep.setStyle("-fx-background-color:#3ff500;");
//                rep.setOnAction((ActionEvent e) -> {
//                    rs.DeleteReclamation(r.getId());
//                    
//                    Parent root;
//                             try {
//                root=FXMLLoader.load(getClass().getResource("FXMLreclamationAdmin.fxml"));
//                rep.getScene().setRoot(root);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLreclamationController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                });
//                property.set(rep);
//                return property;
//              
//            
//        });
 

          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          

        
                   
        
        
        ObservableList<reclamation> reclamations = FXCollections.observableArrayList(liste);
        TreeItem<reclamation> root = new RecursiveTreeItem<>(reclamations, RecursiveTreeObject::getChildren);
        ReclamationTable.setRoot(root);
        ReclamationTable.setShowRoot(false);
        
        filtre.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                ReclamationTable.setPredicate(new Predicate<TreeItem<reclamation>>() {
//                    @Override
//                    public boolean test(TreeItem<Reclamations> t) {
//                        Boolean flag =  t.getValue().getText().contains(newValue)
//                                ||t.getValue().getType().contains(newValue)
//                                ||t.getValue().getDate().toString().contains(newValue)
//                                ||t.getValue().getObjet().contains(newValue);
//                        return flag;
//                    }
//                });
            }
        
        });
    }

    private void reponseReclamation(reclamation r) {
            Utilisateur u = null;
      //      UtilisateurServices serviceUtil = new UtilisateurServices();
          //  u = serviceUtil.rechercher(r.getUtilisateur());
            
            dater.setText(String.valueOf(r.getDate()).substring(0,16));
            titrer.setText(r.getType()+" : "+r.getObjet());
            textr.setText(r.getText());
//            utilisateurr.setText(u.getEsm());
            reponser.setText("");
            lab.setText(String.valueOf(r.getId()));
            reponser.setVisible(true);
            btnAnnuler.setLayoutX(150);
            reptext.setVisible(false);
            datereponse.setVisible(false);
            btnEnregistrer.setVisible(true);
            separator.setVisible(false);
            rep.setVisible(true);
    }

    @FXML
    private void enregistrer(ActionEvent event) {
        if (reponser.validate())
        {
            RepRecServices s=new RepRecServices();
            s.insererRepReclamation(new RepRec (Integer.parseInt(lab.getText()),reponser.getText()));
            ReclamationService r= new ReclamationService();
            r.traiterReclamation(Integer.parseInt(lab.getText()));
            initReclamation();
            rep.setVisible(false);
        }
    }

    @FXML
    private void annuler(ActionEvent event) {
        rep.setVisible(false);
    }

    private void voirReponseReclamation(reclamation r) {
            Utilisateur u = null;
          //  UtilisateurServices serviceUtil = new UtilisateurServices();
            RepRecServices reponseService = new RepRecServices();
           // u = serviceUtil.rechercher(r.getUtilisateur());
            
            dater.setText(String.valueOf(r.getDate()).substring(0,16));
            titrer.setText(r.getType()+" : "+r.getObjet());
            textr.setText(r.getText());
            RepRec x=reponseService.getReponse(r.getId());
            datereponse.setText("Réponse ecrite le : "+String.valueOf(x.getDate()).substring(0, 16));
            utilisateurr.setText(u.getEsm());
            reponser.setVisible(false);
            btnAnnuler.setLayoutX(325);
            btnEnregistrer.setVisible(false);
            datereponse.setVisible(true);
            separator.setVisible(true);
            reptext.setVisible(true);
            reptext.setText(x.getText());
            rep.setVisible(true);
    }
/*
    private void gotoAjout(ActionEvent event) {
        try {
            loadSplashScreen("/paw/annonceadoption/FXMLajouter.fxml");
        } catch (Exception ex) {
            Logger.getLogger(FXMLreclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    */
    

}

