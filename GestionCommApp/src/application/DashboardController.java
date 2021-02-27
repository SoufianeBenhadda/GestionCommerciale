package application;

import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.validation.Validator;

	public class DashboardController implements Initializable {
		@FXML
		private PieChart piechart;
		@FXML
		private PieChart piechart1;
		@FXML
	    private DatePicker tffventejour;
		@FXML
	    private DatePicker tffventejour1;
		
		private ObservableList<PieChart.Data> data;
		private ObservableList<PieChart.Data> data1;

	//	int sum=0;
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	       ok2();
	       chartAchat2();
		}
		

	    public  void ok(){
		        Connection conn = SqlConnection.getConnection();
		    	LocalDate localDate = tffventejour.getValue();
		       String query = "SELECT * FROM vente WHERE dateVente = '"+ localDate + "'";
		        Statement st;
		        ResultSet rs;
		        data = FXCollections.observableArrayList();
		        try{
		            st = conn.createStatement();
		            rs = st.executeQuery(query);
	            	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	            	
	    	        piechart.setTitle("");
	    	        piechart.setData(pieChartData);
		            while(rs.next()){ 		      

		        if(rs.getDouble(8)!=0)
		    	        data.add(new PieChart.Data(rs.getString(2) +"  " +(rs.getDouble(8)	),rs.getDouble(8)));
		    	        piechart.setTitle("Vente du "+ localDate);
		            } 
		            
		            piechart.getData().addAll(data);

		        }catch(Exception ex){
		            ex.printStackTrace();
		        }
  } 
	 
	    
	    public  void chartAchat(){
	        Connection conn1 = SqlConnection.getConnection();
	    	LocalDate localDate1 = tffventejour1.getValue();
	       String query1 = "SELECT * FROM article WHERE created_at = '"+ localDate1 + "'";
	//    	String query =    " SELECT * , sum(benifice) as `items_total` FROM vente  WHERE dateVente = '"+ localDate + "'";
	        Statement stt;
	        ResultSet rss;
	        data1 = FXCollections.observableArrayList();
	        try{
	            stt = conn1.createStatement();
	            rss = stt.executeQuery(query1);
            	ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();
            	
    	        piechart1.setTitle("");
    	        piechart1.setData(pieChartData1);
	            while(rss.next()){ 		      
	           /*  int qt = rs.getInt(8);  
	            	       sum=sum+qt;           	 
	            	 System.out.println(sum);*/
	        
	    	        data1.add(new PieChart.Data(rss.getString(2) +"  " +(rss.getDouble(3)*rss.getInt(4)	),(rss.getDouble(3)*rss.getInt(4))));
	    	        piechart1.setTitle("Achat du "+ localDate1);
	            } 
	            
	            piechart1.getData().addAll(data1);
            	

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }	

    } 
	 
	    public  void chartAchat2(){
	        Connection conn1 = SqlConnection.getConnection();
	    	
	       String query1 = "SELECT * FROM article";
	//    	String query =    " SELECT * , sum(benifice) as `items_total` FROM vente  WHERE dateVente = '"+ localDate + "'";
	        Statement stt;
	        ResultSet rss;
	        data1 = FXCollections.observableArrayList();
	        try{
	            stt = conn1.createStatement();
	            rss = stt.executeQuery(query1);
            	ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();
            	
    	        piechart1.setTitle("");
    	        piechart1.setData(pieChartData1);
	            while(rss.next()){ 		      
	           /*  int qt = rs.getInt(8);  
	            	       sum=sum+qt;           	 
	            	 System.out.println(sum);*/
	        
	    	        data1.add(new PieChart.Data(rss.getString(2) +"  " +(rss.getDouble(3)*rss.getInt(4)	),(rss.getDouble(3)*rss.getInt(4))));
	            } 
	            
	            piechart1.getData().addAll(data1);
            	

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }	

    } 
	    
	    public  void ok2(){
	        Connection conn = SqlConnection.getConnection();
	    	
	       String query = "SELECT * FROM vente";
	        Statement st;
	        ResultSet rs;
	        data = FXCollections.observableArrayList();
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);
            	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            	
    	        piechart.setTitle("");
    	        piechart.setData(pieChartData);
	            while(rs.next()){ 		      

	        if(rs.getDouble(8)!=0)
	    	        data.add(new PieChart.Data(rs.getString(2) +"  " +(rs.getDouble(8)	),rs.getDouble(8)));
	    	        
	            } 
	            
	            piechart.getData().addAll(data);

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
} 
	 
	 
	
}
