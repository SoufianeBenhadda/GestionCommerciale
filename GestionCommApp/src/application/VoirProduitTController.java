package application;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class VoirProduitTController implements Initializable {
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
    private TextField tfSearch;
	@FXML
	private Button btnSupprimer;
	@FXML
	private ImageView imgview;

	ObservableList<Article> listM;
    ObservableList<Article> dataList;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showArticles();
		searchArticle();
		
      
	}
	
	public void searchArticle() {
		// showArticles();
	        
	        dataList = getArticleList();
	        tvGestion.setItems(dataList);
	        FilteredList<Article> filteredData = new FilteredList<>(dataList, b -> true);  
	 tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
	 filteredData.setPredicate(article -> {
	    if (newValue == null || newValue.isEmpty()) {
	     return true;
	    }    
	    String lowerCaseFilter = newValue.toLowerCase();
	    
	    if (article.getNom_article().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	     return true; 
	    } 
	    else if (String.valueOf(article.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
		     return true;
	    }
	    else if (String.valueOf(article.getQuantite()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
	     return true; 
	    }else if (String.valueOf(article.getCreated_at()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
		     return true;
	    }
	    else if (String.valueOf(article.getPrix()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
		     return true; 
	    }
	                                
	         else  
	          return false; 
	   });
	  });  
	  SortedList<Article> sortedData = new SortedList<>(filteredData);  
	  sortedData.comparatorProperty().bind(tvGestion.comparatorProperty());  
	  tvGestion.setItems(sortedData);      
	  
	}
	
	// Event Listener on TableView[#tvGestion].onMouseClicked
	@FXML
	public void handleMouseAction(MouseEvent event) throws SQLException {
		Article article = tvGestion.getSelectionModel().getSelectedItem();
		//tfSearch.setText(article.getNom_article());
		
		Connection conn = SqlConnection.getConnection();	    	
		
		Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM article WHERE id = "+article.getId()+"");
       
        if (rs.next()) {
            InputStream in = rs.getBinaryStream("image_article");
            Image imge = new Image(in);
            imgview.setImage(imge);
        }
	}
	// Event Listener on Button[#btnSupprimer].onAction
	@FXML
	public void handleButtonAction(ActionEvent event)  {
		
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
	  @FXML
	private void deleteArticle(){  
		  Article article = tvGestion.getSelectionModel().getSelectedItem();
	    	String query = "DELETE FROM article WHERE id = " + article.getId() + "";
		  Alert d11 = new Alert(AlertType.CONFIRMATION);
  			d11.setTitle("Champs Delete");
	           d11.setHeaderText(null);
	           d11.setContentText("Voulez vous supprimer le produit ?");
	           Optional<ButtonType> answer = d11.showAndWait();
	           if (answer.get() == ButtonType.OK) {
	        	   SqlConnection.executeQuery(query);
	           }
	           else {
	           System.out.println("User chose Cancel or closed the dialog-box");
	           }
    	
    	
    	showArticles();
    }
	
}
