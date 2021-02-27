package application;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
//import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class FactureController {
	
	@FXML
    private TextField tffacture;
	@FXML
	private TextField tfbalance;
	
	ValidationSupport v = new ValidationSupport();
	
	@FXML
    private void initialize() throws SQLException, IOException {	    	
        v.registerValidator(tfbalance, Validator.createEmptyValidator("Champ Vide !!"));
        v.registerValidator(tffacture, Validator.createEmptyValidator("Champ Vide !!"));	   

    	}
	
    @FXML
    private void searchClientFacture() throws SQLException, JRException {
    	Connection conn = SqlConnection.getConnection();	    	
    	String select = "SELECT * FROM vente WHERE client = "+tffacture.getText()+"";
    	
       	Statement st = conn.createStatement();
    	ResultSet rs;
        rs = st.executeQuery(select);
    	if(tfbalance.getText().isEmpty() || tffacture.getText().isEmpty()) {
    		Alert d = new Alert(AlertType.WARNING);
    		d.setTitle("Champs Error");
	        d.setHeaderText(null);
	        d.setContentText("Champ Vide !!!");
	        d.showAndWait();
    	}
    	else if(!(tffacture.getText().matches("[0-9]*"))|| !(tfbalance.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) ) {
    		Alert d = new Alert(AlertType.WARNING);
    		d.setTitle("Champs Error");
	        d.setHeaderText(null);
	        d.setContentText("Champ Invalide !!!");
	        d.showAndWait();
    	}
    	else if(rs.next() == false) {
        	Alert d = new Alert(AlertType.ERROR);
    		d.setTitle("Champs Error");
	           d.setHeaderText(null);
	           d.setContentText("ID NOT FOUND !!!");
	           d.showAndWait();
        
        }
    	else {
    		Map<String, Object> parameters = new HashMap<>();
        	parameters.put("balance", Double.parseDouble(tfbalance.getText()));       	
           	Connection connn = SqlConnection.getConnection();    
           	String query = "select * from vente where client = '"+ Integer.parseInt(tffacture.getText())+"'";
        	JRDesignQuery update = new JRDesignQuery();
        	update.setText(query);	    	
        	JasperDesign jDesign = JRXmlLoader.load("src\\application\\Invoice.jrxml");
        	jDesign.setQuery(update);
        	JasperReport jReport = JasperCompileManager.compileReport(jDesign);
        	JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters,connn);
        	//JasperViewer.viewReport(jPrint, false);
        	JasperViewer j = new JasperViewer(jPrint, false);
        	j.setTitle("MARA Shop Facture");
        	j.setVisible(true);
        	ImageIcon img = new ImageIcon("src\\icons\\MARA.png");
        	j.setIconImage(img.getImage());
    	}
        	
    	}

  
    

    

    
    
}
