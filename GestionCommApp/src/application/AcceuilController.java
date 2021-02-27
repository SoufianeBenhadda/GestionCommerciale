package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

public class AcceuilController extends VenteController {
	@FXML
	private AnchorPane allpanel;
	@FXML
	private Button btn_acceuil;
	@FXML
	private Button btn_achat;
	@FXML
    private AnchorPane facturePane;	
	@FXML
	public ImageView alertID;
	@FXML
    private AnchorPane mainPane;


	@FXML
    private void initialize() throws SQLException {		
    	alert();
    	
    	}
	 
	@FXML
	public void handleButtonAction() throws IOException {			
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("Achat.fxml"));
		allpanel.getChildren().setAll(pane);				
	}
	@FXML
	public void acceuilAction() throws IOException {			
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
		mainPane.getChildren().setAll(pane);			
	}
	@FXML
    private void salesAction() throws IOException {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("vente.fxml"));
		allpanel.getChildren().setAll(pane);
    }
	@FXML
	public void handleFacturationAction() throws IOException {			
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("facturation.fxml"));
		allpanel.getChildren().setAll(pane);			
	}
	@FXML
	public void handleMessageAction() throws IOException, SQLException {	
	AnchorPane pane = FXMLLoader.load(getClass().getResource("message.fxml"));
	allpanel.getChildren().setAll(pane);
	}
	
	@FXML
    private void voirProduit() throws IOException {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("VoirProduit.fxml"));
		allpanel.getChildren().setAll(pane);
    }
	
	@FXML
    private void dashBoard() throws IOException {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
		allpanel.getChildren().setAll(pane);
    }
	 
	 public void alert() throws SQLException {
		 Connection conn = SqlConnection.getConnection();
		 String query = "select * from message";
		 Statement st = conn.createStatement();
	    	ResultSet rs;
	        rs = st.executeQuery(query);
	        if(rs.next() == false) {
	        	alertID.setVisible(false);   	
	        }
	        else {
	        	alertID.setVisible(true);   	

	        }
	
	 }


	
	

}
