package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class VenteController {
	 @FXML
	    private AnchorPane venteAnchor;
	 @FXML
	    private TableView<Vente> tvvente;

	 @FXML
	    private TableColumn<Vente, Integer> idventecol;

	    @FXML
	    private TableColumn<Vente, String> produitcol;

	    @FXML
	    private TableColumn<Vente, String> clientcol;

	    @FXML
	    private TableColumn<Vente, Integer> quantitecol;

	    @FXML
	    private TableColumn<Vente, Float> prixdeventecol;

	    @FXML
	    private TableColumn<Vente, Date> datecol;

	    @FXML
	    private TableColumn<Vente, Double> totalcol;

	    @FXML
	    private TableColumn<Vente, Float> benificecol;

	    @FXML
	    private TextField tfidproduit;
	    @FXML
	    private TextField tfprixachat;

	    @FXML
	    private TextField tfproduit;

	    @FXML
	    private TextField tfquantite;

	    @FXML
	    private TextField tfprixdevente;

	    @FXML
	    private TextField tfclient;
	    @FXML
	    private TextField tfquantiteproduit;
	    @FXML
	    private DatePicker tfventejour;
	    ValidationSupport v = new ValidationSupport();
	    @FXML
	    private void initialize() throws SQLException, IOException {	    	
	    	showVentes();	 
	        v.registerValidator(tfclient, Validator.createEmptyValidator("Champ Vide !!"));
	        v.registerValidator(tfprixdevente, Validator.createEmptyValidator("Champ Vide !!"));	   
	        v.registerValidator(tfquantiteproduit, Validator.createEmptyValidator("Champ Vide !!"));	
	        tfprixachat.setEditable(false);
	        tfproduit.setEditable(false);

	    	}
	    @FXML
	    void handleAddButton() throws SQLException, IOException {
	    	addVente();
	    }
	    
	    @FXML
	    void handleModifierButton() {
	    	updateVente();
	    }
//	    @FXML
//	    void handleSupprimerButton() {
//	    	deleteVente();
//	    }
	    @FXML
	    void handleSearchButton() throws SQLException {
	    	venteJour();
	    }
	    @FXML
		private void handleMouseAction() {
			Vente vente = tvvente.getSelectionModel().getSelectedItem();
			
			tfproduit.setText(vente.getProduit());
			tfclient.setText(""+vente.getClient());
			tfprixdevente.setText(""+vente.getPrixVente());
			tfquantiteproduit.setText(""+vente.getQuantite());
			
			tfidproduit.setEditable(false);
			tfproduit.setEditable(false);
			tfprixachat.setEditable(false);


		//	tfprixachat.setText(""+vente.get());

		}
	    
	    @FXML
	    void handleTextField(javafx.scene.input.KeyEvent event) throws SQLException {
	    	if (event.getCode().equals(KeyCode.ENTER))
            {
	    		Connection conn = SqlConnection.getConnection();	    	
		    	String select = "SELECT * FROM article WHERE id = "+tfidproduit.getText()+"";
		    	System.out.println(tfidproduit.getText());
		       	Statement st = conn.createStatement();
		    	ResultSet rs;
		        rs = st.executeQuery(select);
		        if(rs.next() == false) {
		        	Alert d = new Alert(AlertType.ERROR);
		    		d.setTitle("Champs Error");
			           d.setHeaderText(null);
			           d.setContentText("ID NOT FOUND !!!");
			           d.showAndWait();
		        
		        }
		        else {
		        	String product = rs.getString("nom_article");   	       
		        	String prixachat =""+rs.getFloat("prix");   
		        	

		        	tfproduit.setText(product);
		        	tfprixachat.setText(prixachat);
		        	tfproduit.setEditable(false);
		        	tfprixachat.setEditable(false);
		        	
		        }
            }
	    }
	    

	    
	    public ObservableList<Vente> getVenteList(){
	        ObservableList<Vente> venteList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	        String query = "SELECT * FROM vente";
	        Statement st;
	        ResultSet rs;
	        
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);
	            Vente vente;
	            while(rs.next()){
	            	vente = new Vente(rs.getInt("id"), rs.getString("article"), rs.getInt("client"), rs.getFloat("prixVente"),rs.getInt("quantite"), rs.getDate("dateVente"),rs.getDouble("total"),rs.getFloat("benifice"));
	                venteList.add(vente);
	                
	            }
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return venteList;
	    }
	    
	    public void showVentes() {
			ObservableList<Vente> list = getVenteList();
			
			idventecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("id"));
			produitcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("produit"));
			clientcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("client"));
			quantitecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("quantite"));
			prixdeventecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("prixVente"));
			datecol.setCellValueFactory(new PropertyValueFactory<Vente , java.sql.Date>("dateVente"));
			totalcol.setCellValueFactory(new PropertyValueFactory<Vente , Double>("total"));
			benificecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("benifice"));

	        tvvente.setItems(list);

		}
	    
	    private void addVente() throws SQLException, IOException {
	        	Alert d = new Alert(AlertType.WARNING);

	    	Connection conn = SqlConnection.getConnection();   	
	    	String select = "SELECT * FROM article WHERE id = "+ tfidproduit.getText() +"";
	       	Statement st = conn.createStatement();
	    	ResultSet rs;
	        rs = st.executeQuery(select);
	       	            
	        while(rs.next()) {
	       	       
	        	float prixachat =rs.getFloat("prix");   
	        	int quantiteProduit =rs.getInt("quantite");  

	        	
	        	float prixVente = Float.parseFloat(tfprixdevente.getText());		        
	 	    	int quantiteVente = Integer.parseInt(tfquantiteproduit.getText());  
	 	    	
	 	        double total = prixVente * quantiteVente;
	 	        float benifice = (float)(total - (quantiteVente * prixachat)) ;
	 	        
	 	       
	 	        if(quantiteVente >= quantiteProduit) {
	 	    		d.setTitle("Champs Error");
	 		           d.setHeaderText(null);
	 		           d.setContentText("Stock indisponible !!!");
	 		           d.showAndWait();
	 	        	continue;
	 	        } 
	 	        
	 	       else if ((Float.parseFloat(tfprixdevente.getText())<=0)) {
	 	        	d.setTitle("Champs Error");
	 		           d.setHeaderText(null);
	 		           d.setContentText("Champ Prix de vente Invalide !!!");
	 		           d.showAndWait();
	 	        }
	 	      
	 	       else if (Float.parseFloat(tfquantiteproduit.getText())<=0) {
	 	        	d.setTitle("Champs Error");
	 		           d.setHeaderText(null);
	 		           d.setContentText("Quantite inférieur a 0 !!!");
	 		           d.showAndWait();
	 	        }
	 	      else if (Float.parseFloat(tfclient.getText())<=0) {
	 	        	d.setTitle("Champs Error");
	 		           d.setHeaderText(null);
	 		           d.setContentText("Numéro de vente Invalide !!!");
	 		           d.showAndWait();
	 	        }
	 	        else {
	 	        	quantiteProduit -= quantiteVente;	   
	 	        	String query3 = "UPDATE  article SET quantite  ="+quantiteProduit+" WHERE id = "+tfidproduit.getText()+"";
	 	        	SqlConnection.executeQuery(query3); 
	 	        	
	 	        	java.util.Date date=new java.util.Date();
	 	 	       java.sql.Date sqlDate=new java.sql.Date(date.getTime());	
	 	 	       String query1 = "INSERT INTO vente (article,client,prixVente,quantite,dateVente,total,benifice) VALUES ('" + tfproduit.getText() + "'," + tfclient.getText() + ","
	 		               + tfprixdevente.getText() + "," + tfquantiteproduit.getText() + ",'" + sqlDate + "'," + total + "," + benifice + ")";
	 	 	       
	 		        SqlConnection.executeQuery(query1);	 
	 		       Alert dd = new Alert(AlertType.INFORMATION);
	 	    		dd.setTitle("Vente");	          
	 	    		dd.setHeaderText(null);
	 	    		dd.setContentText("Vente Enregistrée !!!");
	 	    		dd.showAndWait();
	 	    		MessageController.message();
	 	    		tfclient.clear();
	 	    		tfidproduit.clear();
	 	    		tfprixachat.clear();
	 	    		tfprixdevente.clear();
	 	    		tfproduit.clear();
	 	    		tfquantite.clear();
	 	    		tfquantiteproduit.clear();
	 	        }
	 	        
	 	          
		        
	        }        		       
	        showVentes();
	        
	    }
	    
	    private void updateVente() {
	    	if(tfclient.getText().isEmpty() || tfquantiteproduit.getText().isEmpty() || tfprixdevente.getText().isEmpty()) {
	    		Alert d = new Alert(AlertType.WARNING);
	    		d.setTitle("Champs Error");  
		        d.setHeaderText(null);
		        d.setContentText("Champ Vide !!!");
		        d.showAndWait();
	    	} 
	    	else { 
	    		Vente vente = tvvente.getSelectionModel().getSelectedItem();
	            String query = "UPDATE  vente SET client  = '" + tfclient.getText() + "', prixVente = '" + tfprixdevente.getText() + "', quantite = " +
	                    tfquantiteproduit.getText() + " WHERE id = " + vente.getId() + "";
	            SqlConnection.executeQuery(query);
	    	}
	    	
	            showVentes();
	    }
	    @FXML
	    private void deleteVente(){  	
	    	Vente vente = tvvente.getSelectionModel().getSelectedItem();
   	    	String query = "DELETE FROM vente WHERE id = " + vente.getId() + "";
	    	Alert d = new Alert(AlertType.CONFIRMATION);
  		    	d.setTitle("Champs Delete");
	           d.setHeaderText(null);
	           d.setContentText("Voulez vous supprimer le produit ?");
	           Optional<ButtonType> answer = d.showAndWait();
	           if (answer.get() == ButtonType.OK) {
	        	   
	   	    	SqlConnection.executeQuery(query);
	           }
	           else {
	           System.out.println("User chose Cancel or closed the dialog-box");
	           }
	    	showVentes();
	    }
	    
	    public ObservableList<Vente> getVenteJourList() throws SQLException{
	        ObservableList<Vente> venteJourList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	    	LocalDate localDate = tfventejour.getValue();
	        String query = "SELECT * FROM vente WHERE dateVente = '"+ localDate + "'";
	        Statement st;
	        ResultSet rs;
	        
	      
	            st = conn.createStatement();
	            rs = st.executeQuery(query);	          
	            Vente vente;
	            while(rs.next()){
	            	vente = new Vente(rs.getInt("id"), rs.getString("article"), rs.getInt("client"), rs.getFloat("prixVente"),rs.getInt("quantite"), rs.getDate("dateVente"),rs.getDouble("total"),rs.getFloat("benifice"));
	            	venteJourList.add(vente);
	            }
	       
	        return venteJourList;
	    }
	    @FXML
	    private void venteJour() throws SQLException {
	    
	    	ObservableList<Vente> list = getVenteJourList();
	    	
			idventecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("id"));
			produitcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("produit"));
			clientcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("client"));
			quantitecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("quantite"));
			prixdeventecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("prixVente"));
			datecol.setCellValueFactory(new PropertyValueFactory<Vente , java.sql.Date>("dateVente"));
			totalcol.setCellValueFactory(new PropertyValueFactory<Vente , Double>("total"));
			benificecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("benifice"));

	        tvvente.setItems(list);
	        
	    	
	    }
	    

	    
	    
	    
}
