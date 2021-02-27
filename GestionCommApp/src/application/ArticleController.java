package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.mysql.cj.jdbc.Blob;

import javafx.scene.Node;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ArticleController implements Initializable {

	
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfArticle;
	@FXML
	private TextField tfPrix;
	@FXML
	private TextField tfQuantite;
	@FXML
	private TableView<Article> tvGestion;
	@FXML
	private TableColumn<Article, Integer> tvId;
	@FXML
	private TableColumn<Article , String> tvArticle;
	@FXML
	private TableColumn<Article , Float> tvPrix;
	@FXML
	private TableColumn<Article , Integer> tvQuantite;
	@FXML
	private TableColumn<Article , java.sql.Date> tvDate;
	@FXML
	private Button btnAjouter;
	@FXML
	private Button btnModifier;

	@FXML 
	private Button addimg;
	@FXML 
	private ImageView imgview;
	String s;
	ValidationSupport v = new ValidationSupport();
	@FXML
	public void imageselect() throws FileNotFoundException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getPath();
     
            s=path;
            InputStream stream = new FileInputStream(path);
            Image image = new Image(stream);
            imgview.setImage(image);
             }
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No Data");
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		v.registerValidator(tfArticle, Validator.createEmptyValidator("Champ Vide !!"));
        v.registerValidator(tfPrix, Validator.createEmptyValidator("Champ Vide !!"));
        v.registerValidator(tfQuantite, Validator.createEmptyValidator("Champ Vide !!"));
       
		showArticles();
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws SQLException, IOException {
		
		if(event.getSource() == btnAjouter) {
			addArticle();
		}
		else if(event.getSource() == btnModifier) {
			updateArticle();
		}
	}
	@FXML
	private void handleMouseAction() throws SQLException {
		Article article = tvGestion.getSelectionModel().getSelectedItem();
		
		tfArticle.setText(article.getNom_article());
		tfQuantite.setText(""+article.getQuantite());
		tfPrix.setText(""+article.getPrix());
		Connection conn = SqlConnection.getConnection();	    	
		
		Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE id = "+article.getId()+"");
       
        if (rs.next()) {
            InputStream in = rs.getBinaryStream("image_article");
            Image imge = new Image(in);
            imgview.setImage(imge);
        }
	}
	
	
	  public ObservableList<Article> getArticleList(){
	        ObservableList<Article> articleList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	        String query = "SELECT * FROM article";
	        Statement st;
	        ResultSet rs;
	        
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);
	            Article Article;
	            while(rs.next()){
	                Article = new Article(rs.getInt("id"), rs.getString("nom_article"), rs.getFloat("prix"), rs.getInt("quantite"), rs.getDate("created_at"), rs.getString("image_article"));
	                articleList.add(Article);
	            }
	                
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return articleList;
	    }
	public void showArticles() {
		ObservableList<Article> list = getArticleList();
		tvId.setCellValueFactory(new PropertyValueFactory<Article , Integer>("id"));
		tvArticle.setCellValueFactory(new PropertyValueFactory<Article , String>("nom_article"));
		tvPrix.setCellValueFactory(new PropertyValueFactory<Article , Float>("prix"));
		tvQuantite.setCellValueFactory(new PropertyValueFactory<Article , Integer>("quantite"));
		tvDate.setCellValueFactory(new PropertyValueFactory<Article , java.sql.Date>("created_at"));
		
        tvGestion.setItems(list);

	}
	
	private void addArticle() throws SQLException, IOException{
		Connection conn = SqlConnection.getConnection();
		PreparedStatement pre2 = conn.prepareStatement("insert into article (nom_article,prix,quantite,created_at,image_article) values(?,?,?,?,?)");
		PreparedStatement pre = conn.prepareStatement("insert into article (nom_article,prix,quantite,created_at,image_article) values(?,?,?,?,?)");
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
	    Alert d = new Alert(AlertType.ERROR);
		if(s == null) {
			 if(!(tfPrix.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) ) {	          
		           d.setTitle("Champ Prix Error");
		           d.setHeaderText(null);
		           d.setContentText("Champ Prix Invalide !!!"); 
		           d.showAndWait(); 
	     }else if((Float.parseFloat(tfPrix.getText())<=0)) {
	    	 d.setTitle("Champ Prix Error");
	         d.setHeaderText(null);
	         d.setContentText("Prix inférieur a zero !!!"); 
	         d.showAndWait();
	     }
		    else if(!(tfQuantite.getText().matches("[0-9]*"))) {
		    	d.setTitle("Champ Quantité Error");
		           d.setHeaderText(null);
		           d.setContentText("Champ Quantité Invalide !!!");
		           d.showAndWait();
		    } else if (Float.parseFloat(tfQuantite.getText())<5) {
		    	d.setTitle("Champ Quantité Error");
		           d.setHeaderText(null);
		           d.setContentText("Quantite inférieur a 5 !!!");
		           d.showAndWait();
		    	
		    }
		    else {
	     	pre2.setString(1, tfArticle.getText());
	       	pre2.setFloat(2, Float.parseFloat(tfPrix.getText()));
	    	pre2.setInt(3, Integer.parseInt(tfQuantite.getText()));
	    	pre2.setDate(4, sqlDate);
	    	pre2.setBinaryStream(5,(InputStream)null,(int)0);
	    	pre2.executeUpdate();
	        pre2.close();
	        Alert ddd = new Alert(AlertType.INFORMATION);
    		ddd.setTitle("Produit");	          
    		ddd.setHeaderText(null);
    		ddd.setContentText("Produit Enregistré !!!");
    		ddd.showAndWait();
    		tfArticle.clear();
    		tfPrix.clear();
    		tfQuantite.clear();
	    }
		}
		
	    
		else if(!(tfPrix.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) ) {	          
	           d.setTitle("Champ Prix Error");
	           d.setHeaderText(null);
	           d.setContentText("Champ Prix Invalide !!!"); 
	           d.showAndWait(); 
     }else if((Float.parseFloat(tfPrix.getText())<=0)) {
    	 d.setTitle("Champ Prix Error");
         d.setHeaderText(null);
         d.setContentText("Prix inférieur a zero !!!"); 
         d.showAndWait();
     }
	    else if(!(tfQuantite.getText().matches("[0-9]*"))) {
	    	d.setTitle("Champ Quantité Error");
	           d.setHeaderText(null);
	           d.setContentText("Champ Quantité Invalide !!!");
	           d.showAndWait();
	    } else if (Float.parseFloat(tfQuantite.getText())<5) {
	    	d.setTitle("Champ Quantité Error");
	           d.setHeaderText(null);
	           d.setContentText("Quantite inférieur a 5 !!!");
	           d.showAndWait();
	    	
	    }
	    
	    
	    else {
	    	File monImage = new File(s);
		    FileInputStream istreamImage = new FileInputStream(monImage);
	      	pre.setString(1, tfArticle.getText());
	       	pre.setFloat(2, Float.parseFloat(tfPrix.getText()));
	    	pre.setInt(3, Integer.parseInt(tfQuantite.getText()));
	    	pre.setDate(4, sqlDate);
	    	pre.setBinaryStream(5,(InputStream)istreamImage,(int)monImage.length());
	    	pre.executeUpdate();
	        pre.close();
	        conn.close(); 
	        Alert dd = new Alert(AlertType.INFORMATION);
    		dd.setTitle("Produit");	          
    		dd.setHeaderText(null);
    		dd.setContentText("Produit Enregistré !!!");
    		dd.showAndWait();
	    }
     
        
        showArticles();
       
    }
    private void updateArticle() throws SQLException{
		Alert d = new Alert(AlertType.WARNING);

    	if(tfPrix.getText().isEmpty() || tfQuantite.getText().isEmpty() || tfArticle.getText().isEmpty()) {
    		d.setTitle("Champs Error");	          
    		d.setHeaderText(null);
    		d.setContentText("Champ Vide !!!");
    		d.showAndWait();
    	}
    	else if(!(tfPrix.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) ) {	          
	           d.setTitle("Champ Prix Error");
	           d.setHeaderText(null);
	           d.setContentText("Champ Prix Invalide"); 
	           d.showAndWait(); 
  }
    	else if((Float.parseFloat(tfPrix.getText())<=0)) {
 	 d.setTitle("Champ Prix Error");
      d.setHeaderText(null);
      d.setContentText("Prix inférieur a zero"); 
      d.showAndWait();
  }
	    else if(!(tfQuantite.getText().matches("[0-9]*"))) {
	    	d.setTitle("Champ Quantité Error");
	           d.setHeaderText(null);
	           d.setContentText("Champ Quantité Invalide");
	           d.showAndWait();
	    }
	    else if (Float.parseFloat(tfQuantite.getText())<5) {
	    	d.setTitle("Champ Quantité Error");
	           d.setHeaderText(null);
	           d.setContentText("Quantite inférieur a 5");
	           d.showAndWait();
	    	
	    }
    	else {
    		Article article = tvGestion.getSelectionModel().getSelectedItem();
    	    
            String query = "UPDATE  article SET nom_article  = '" + tfArticle.getText() + "', prix = '" + tfPrix.getText() + "', quantite = " +
                    tfQuantite.getText() + " WHERE id = " + article.getId() + "";
            SqlConnection.executeQuery(query);
            if(Integer.parseInt(tfQuantite.getText())>=5) {
                SqlConnection.executeQuery("DELETE FROM message WHERE alert LIKE '%"+tfArticle.getText()+"%'");
                Alert ddd = new Alert(AlertType.INFORMATION);
        		ddd.setTitle("Produit");	          
        		ddd.setHeaderText(null);
        		ddd.setContentText("Produit Modifié !!!");
        		ddd.showAndWait();
            }
    	}
    	
        showArticles();
    }
//    private void deleteArticle(){  	
//    	Article article = tvGestion.getSelectionModel().getSelectedItem();
//    	String query = "DELETE FROM article WHERE id = " + article.getId() + "";
//    	SqlConnection.executeQuery(query);
//    	showArticles();
//    }
    
    
}
