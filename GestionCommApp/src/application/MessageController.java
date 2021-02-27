package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MessageController {
	@FXML
    private TableView<Message> tvMessage;

    @FXML
    private TableColumn<Message, Integer> IDD;

    @FXML
    private TableColumn<Message, String> Alertt;
    
    @FXML
    private void initialize() throws SQLException {
    	showMessages();	    	
    	
    	}
    
    public ObservableList<Message> getMessageList(){
        ObservableList<Message> MessageList = FXCollections.observableArrayList();
        Connection conn = SqlConnection.getConnection();
        String query = "SELECT * FROM Message";
        Statement st;
        ResultSet rs;
        
        
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Message Message;
            while(rs.next()){
            	Message = new Message(rs.getInt("id"), rs.getString("alert"));
                MessageList.add(Message);
                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return MessageList;
    }
    
    public void showMessages() {
		ObservableList<Message> list = getMessageList();
		
		IDD.setCellValueFactory(new PropertyValueFactory<Message , Integer>("id"));
		Alertt.setCellValueFactory(new PropertyValueFactory<Message , String>("alert"));
		
		tvMessage.setItems(list);

	}
	
	 
	    public static void message() throws SQLException, IOException {
		 	
	    	Connection conn = SqlConnection.getConnection();
	    	String query = "select * from article";
	    	
	    	Statement st = conn.createStatement();
	    	ResultSet rs;
	        rs = st.executeQuery(query);
	        while(rs.next()) {
	        	int quantite = rs.getInt("quantite"); 
	        	if(quantite < 5) {  
	        		
	        	    PreparedStatement pre = conn.prepareStatement("insert into message (alert) values(?)");
	        	    String article = rs.getString("nom_article");
	        	    String alert = "La quantité du produit "+article+" est "+quantite+", stock presque épuisé";
	               	pre.setString(1, alert);
	               	pre.executeUpdate();
	                
	        	}
	        	
	        }
	   
	    	
	    }
}
