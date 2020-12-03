/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.reclamation;
import Utils.ConnexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ReclamationService {
    Connection cnx = ConnexionBD.getInstance().getCnx();
       private PreparedStatement pst ;
   //ajout reclamation
    public Boolean insererReclamation (reclamation p)
    {
        String req="INSERT INTO Reclamations (id_utilisateur,objet,etat,text,type,date) VALUES(?,?,'Non traitée','test','test',now())" ; 
        try { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1,p.getUtilisateur()) ; 
            ste.setString(2,p.getObjet()) ;
       //     ste.setString(3,p.getText()) ; 
      //      ste.setString(4,p.getType()) ;           
            ste.executeUpdate() ; 
            return true ;
            
        } catch (SQLException ex) {
            System.out.println(ex);
            return false ;
        }
    }
    //affichage reclamation
    public ObservableList<reclamation> getAll(){
        String req="SELECT * FROM reclamation" ;
        ObservableList<reclamation> list = FXCollections.observableArrayList();
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                int id = rs.getInt("id");
                String objet = rs.getString("objet");
                String text= rs.getString("text");
                String type = rs.getString("type");
                Timestamp date = rs.getTimestamp("date");
                int utilisateur= rs.getInt("id_utilisateur");
                String etat = rs.getString("etat");
                              
                list.add(new reclamation(id,utilisateur, objet, text, type, date,etat));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
     
    public void updateReclamation (reclamation p, int id )
    {
    String req="UPDATE reclamation SET objet=?, text=?, type=? WHERE id =?" ; 
        try { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
             
            ste.setString(1,p.getObjet()) ; 
            ste.setString(2, p.getText());
            ste.setString(3,p.getType()) ;  
            ste.setInt(4, id);
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
     public void DeleteReclamation (int id )
    {
        String req="DELETE  from reclamation where  id =?" ; 
        try { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1,id) ;
            ste.executeUpdate() ;    
        } 
        catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
//les reclamations mte3i 
    public ArrayList<reclamation> getReclamationUtilisateur(int i) {
        String req="SELECT * FROM reclamation WHERE id_utilisateur=?" ;
        ArrayList<reclamation> list = new ArrayList();
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1, i);
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                int id = rs.getInt("id");
                String objet = rs.getString("objet");
                String text= rs.getString("text");
                String type = rs.getString("type");
                String etat = rs.getString("etat");
                java.sql.Timestamp date = rs.getTimestamp("date");
                              
                list.add(new reclamation(id,i, objet, text, type, date,etat));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
//rep reclamation ou nn 
    public void traiterReclamation(int i) 
    {
    String req="UPDATE reclamation SET etat=? WHERE id=?" ; 
        try { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            
            ste.setString(1, "Traitée");
            ste.setInt(2, i);
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
// ijib remerciment
    public double getRemerciment() {
        String req="SELECT count(*) as a FROM reclamation WHERE type=?" ;
        double x=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setString(1, "Remerciment");
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                x= rs.getInt("a");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return x;
    }
    
//affichage reclamation selon type
    public double getReclamation() {
        String req="SELECT count(*) as a FROM reclamation WHERE type=?" ;
        double x=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setString(1, "reclamation");
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                x= rs.getInt("a");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return x;    
    }
//afficher les reclamation traiter 
    public double getTraitee() {
        String req="SELECT count(*) as a FROM reclamation WHERE etat=?" ;
        double x=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setString(1, "Traitée");
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                x= rs.getInt("a");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return x;    
    }
//afficher les reclamation non traiter
    public double getNonTraitee() {
        String req="SELECT count(*) as a FROM reclamation WHERE etat=?" ;
        double x=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setString(1, "Non traitée");
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                x= rs.getInt("a");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return x;        }
    
     public int nombre() {
        int y = 0;
        String sql = "SELECT count(*) as nbr FROM `reclamation` WHERE etat='Non traitée'";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                y = results.getInt("nbr");
            }
        } catch (SQLException ex) {
            System.out.println("erreur affichage nombre");
        }
        return y;
    }
//les reclamation selon les objet
    public ResultSet getNbrReclamationType()
    {
        String req="SELECT count(*) as nbr, objet FROM `reclamation` group by `objet`" ;
        double x=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ResultSet rs = ste.executeQuery(); 
            return rs;
        } catch (SQLException ex) {
            System.out.println("mochekla");
            System.out.println(ex);
            return null;
        }   
    }
//
    public Object nombreMesReclamations(int i) {
        String req="SELECT * FROM reclamation WHERE id_utilisateur=?" ;
        int y=0;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1, i);
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                y++;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return y;
    }

}
