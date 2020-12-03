/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.reclamation;
import Entities.RepRec;
import Utils.ConnexionBD;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Checksum;




public class RepRecServices {
    Connection cnx = ConnexionBD.getInstance().getCnx();
       private PreparedStatement pst ;
    
    public void insererRepReclamation (RepRec a)
    {
        String req="INSERT INTO reponsereclamation (id_reclamation,text,date) VALUES(?,?,now())" ; 
        try { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1,a.getId_reclamation()) ; 
            ste.setString(2,a.getText()) ; 

            ste.executeUpdate() ; //executer requete sql
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    public RepRec getReponse(int i) {
        String req="SELECT * FROM reponsereclamation WHERE id_reclamation=?" ;
        RepRec x= null;
        try 
        { 
            PreparedStatement ste = cnx.prepareStatement(req) ;
            ste.setInt(1, i);
            ResultSet rs = ste.executeQuery(); 
            while (rs.next())
            {
                int id = rs.getInt("id");
                String text= rs.getString("text");
                java.sql.Timestamp date = rs.getTimestamp("date");
                              
                x=new RepRec(id,i, text,date);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return x;
    }
    
        
    
}
