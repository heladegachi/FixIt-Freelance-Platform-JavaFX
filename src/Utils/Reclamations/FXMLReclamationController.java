/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.Reclamations;

import Entities.reclamation;
import Entities.RepRec;
import Services.ReclamationService;
import Services.RepRecServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.Notifications;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
//import static paw.Paw.session;

/**
 * FXML Controller class
 *
 * 
 */
public class FXMLReclamationController implements Initializable {

    @FXML
    private AnchorPane reclamations;
    @FXML
    private Label nom1;
    @FXML
    private Label nom11;
    @FXML
    private JFXTextArea msg;
    @FXML
    private JFXComboBox<String> titre;
    @FXML
    private JFXToggleButton type;
    @FXML
    private Pagination paginator;

    ArrayList<reclamation> mesReclamations ;
    @FXML
    private AnchorPane box1;
    @FXML
    private Label titre1;
    @FXML
    private Label text1;
    @FXML
    private Label date1;
    @FXML
    private Label titre2;
    @FXML
    private Label text2;
    @FXML
    private Label date2;
    @FXML
    private Label titre3;
    @FXML
    private Label text3;
    @FXML
    private Label date3;
    @FXML
    private AnchorPane box2;
    @FXML
    private AnchorPane box3;
    @FXML
    private StackPane vide;
    @FXML
    private AnchorPane rep;
    @FXML
    private Label reptext;
    @FXML
    private Separator separator;
    @FXML
    private Label repdate;
    @FXML
    private JFXButton annulation;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rep.setVisible(false);
        ReclamationService service= new ReclamationService();
        ObservableList<String> types = FXCollections.observableArrayList(
            "Service",
            "Produit",
            "Other"
   
        );
        titre.setItems(types);
        mesReclamations = service.getReclamationUtilisateur(1);
        if (mesReclamations.isEmpty()) {
            box1.setVisible(false);
            box2.setVisible(false);
            box3.setVisible(false);
            vide.setVisible(true);
            paginator.setVisible(false);
        } else {
            paginator.setVisible(true);
            vide.setVisible(false);
            setNbPages();
            initReclamationPage(0);
        }  
        RequiredFieldValidator rf = new RequiredFieldValidator();
        rf.setMessage("Veuillez mentionner L'objet de votre réclamation");
        rf.setMessage("Veuillez nous expliquer le sujet de votre réclamation");
        msg.getValidators().add(rf);
        msg.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    msg.validate();
                }
            }
        });
    }    
//static code api sous le bouton de envoyer reclamation 
    @FXML
    private void envoyerReclamation(ActionEvent event) throws MessagingException {
        try {
            ReclamationService service= new ReclamationService();
            String t = "Reclamation";
            if (type.isSelected()){
                t="Remerciment";
            }
            
            
            
            if((titre.getValue() == null))
            {
                Notifications.create()
                        .title("Informations manquantes")
                        .text("Veuillez choisir l'objet de votre réclamation")
                        .showWarning();
            }
            
      
         
            else{
                if(msg.validate())
                {
                    if(service.insererReclamation(new reclamation(1, titre.getValue(), msg.getText(), t)))
                    {
                        Notifications.create()
                                .title("Reclamation envoyée")
                                .text("Merci pour votre collaboration !")
                                .showInformation();
                        type.setSelected(false);
                        titre.setValue("");
                        msg.setText("");
                        mesReclamations = service.getReclamationUtilisateur(1);
                        
                        setNbPages();
                        initReclamationPage(paginator.getCurrentPageIndex());
                    }
                    else
                    {
                        Notifications.create()
                                .title("Erreur")
                                .text("Veuillez vérifier votre connexion")
                                .showError();
                    }
                }
                else
                {
                    Notifications.create()
                            .title("Informations manquantes")
                            .text("Veuillez expliquer encore votre reclamation")
                            .showWarning();
                }
            }
            
            
            
            
            /*  api sms   */
            
           
              
//              try {
//              // Construct data
//              String apiKey = "apikey=d6Ol6c7ZBEE-8axR9YDjQeouqqbdVQviSL5jsTc5TF";
//              String message = "&message=Bienvenue votre reclamatons a été deposée avec succés [old age home center]" ;
//              String sender = "&sender=old age home center" ;
//              String numbers = "&numbers=+21622502773" ;
//              
//              // Send data
//              HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
//              String data = apiKey + numbers + message + sender;
//              conn.setDoOutput(true);
//              conn.setRequestMethod("POST");
//              conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//              conn.getOutputStream().write(data.getBytes("UTF-8"));
//              final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//              final StringBuffer stringBuffer = new StringBuffer();
//              String line;
//              while ((line = rd.readLine()) != null) {
//              //stringBuffer.append(line);
//              JOptionPane.showMessageDialog(null, "message"+line);
//              }
//              rd.close();
//              
//              } catch (Exception e) {
//              JOptionPane.showMessageDialog(null, e);
//              
//              }
             
            
            
            
            
            
            
            //send Mail
            
            String host = "smtp.gmail.com";
            String utilisateur = "";
            String pass = "";
            String SendTo = "";
            String from = "";
            String Subject = "Fixit";
            boolean sessionDebug=false;
            String textMessage = "bonjour M./Mm votre reclamation sera traité le plus tot possible";
            boolean seesionDebug = false;
            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
             Session session = Session.getInstance(props,
             new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(utilisateur, pass);
           }
             });
            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(SendTo)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(Subject);
            msg.setSentDate(new Date());
            msg.setText(textMessage);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, utilisateur, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");   
        } catch (AddressException ex) {
            Logger.getLogger(FXMLReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setNbPages() {
       if (mesReclamations.size() % 3 != 0) {
            paginator.setPageCount((mesReclamations.size() / 3) + 1);
        } else {
            paginator.setPageCount(mesReclamations.size() / 3);
        }
        
        paginator.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            initReclamationPage(newIndex.intValue());
        });
    }
    
    public List<reclamation> getReclamationsPage(int index) {
        int start = 3 * index;
        int fin = start + 3;
        if (mesReclamations.size() > start) {
            if (mesReclamations.size() > fin) {
                return mesReclamations.subList(start, fin);
            } else {
                return mesReclamations.subList(start, mesReclamations.size());
            }
        }
        return mesReclamations.subList(0, 2);
    } 

    private void initReclamationPage(int index) {
        paginator.setCurrentPageIndex(index);
        List<reclamation> TroisReclamations = getReclamationsPage(index);  
        ReclamationService serv = new ReclamationService();
        if (TroisReclamations.size() >= 1) {
            vide.setVisible(false);
            box1.setVisible(true);
            if("Remerciment".equals(TroisReclamations.get(0).getType()))
            {
                box1.setStyle("-fx-background-color: #00A65A; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #008448; -fx-border-width: 0 0 0 4;");
            }
            else
            {
                box1.setStyle("-fx-background-color: #DD4B39; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #B03C2D; -fx-border-width: 0 0 0 4;");
            }
            titre1.setText(TroisReclamations.get(0).getObjet());
            text1.setText(TroisReclamations.get(0).getText());
            date1.setText(String.valueOf(TroisReclamations.get(0).getDate()).substring(0, 16));
            
            if (TroisReclamations.get(0).getEtat().equals("Traitée"))
            {
                box1.setOnMouseClicked((MouseEvent e) -> {
                    initialiserDetails(TroisReclamations.get(0));
                    rep.setVisible(true);
                });
            }
            else
            {
                box1.setOnMouseClicked((MouseEvent e) -> {
                });
            }
            

        } 
        else { 
            box1.setVisible(false);
        }

        ///////////////////////////////////////////////////////
        if (TroisReclamations.size() >= 2){
            box2.setVisible(true);  
            if("Remerciment".equals(TroisReclamations.get(1).getType()))
            {
                box2.setStyle("-fx-background-color: #00A65A; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #008448; -fx-border-width: 0 0 0 4;");
            }
            else
            {
                box2.setStyle("-fx-background-color: #DD4B39; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #B03C2D; -fx-border-width: 0 0 0 4;");
            }
            titre2.setText(TroisReclamations.get(1).getObjet());
            text2.setText(TroisReclamations.get(1).getText());
            date2.setText(String.valueOf(TroisReclamations.get(1).getDate()).substring(0, 16));
            if (TroisReclamations.get(1).getEtat().equals("Traitée"))
            {    
                box2.setOnMouseClicked((MouseEvent e) -> {
                    initialiserDetails(TroisReclamations.get(1));
                    rep.setVisible(true);
                });
            }
            else
            {
                box2.setOnMouseClicked((MouseEvent e) -> {
                });
            }
        } 
        else{
            box2.setVisible(false);
        }
        ///////////////////////////////////////////////////////////
        
        if (TroisReclamations.size() >= 3){
            box3.setVisible(true);         
            if("Remerciment".equals(TroisReclamations.get(2).getType()))
            {
                box3.setStyle("-fx-background-color: #00A65A; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #008448; -fx-border-width: 0 0 0 4;");
            }
            else
            {
                box3.setStyle("-fx-background-color: #DD4B39; -fx-border-radius: 4; -fx-background-radius: 4; -fx-border-color: #B03C2D; -fx-border-width: 0 0 0 4;");
            }
            titre3.setText(TroisReclamations.get(2).getObjet());
            text3.setText(TroisReclamations.get(2).getText());
            date3.setText(String.valueOf(TroisReclamations.get(2).getDate()).substring(0, 16));
            if (TroisReclamations.get(2).getEtat().equals("Traitée"))
            {  
                box3.setOnMouseClicked((MouseEvent e) -> {
                    initialiserDetails(TroisReclamations.get(2));
                    rep.setVisible(true);
                });
            }
            else
            {
                box3.setOnMouseClicked((MouseEvent e) -> {
                });
            }
        } 
        else{
            box3.setVisible(false); 
        }

    }

//    @FXML
//    private void annulation(ActionEvent event) {
//        rep.setVisible(false);
//    }

   private void initialiserDetails(reclamation get) {
        RepRecServices sdetails = new RepRecServices();
        RepRec lareponse = sdetails.getReponse(get.getId());
        repdate.setText("Réponse rédigée le : "+String.valueOf(lareponse.getDate()).substring(0, 16));
        reptext.setText(lareponse.getText());
    }
    
}
