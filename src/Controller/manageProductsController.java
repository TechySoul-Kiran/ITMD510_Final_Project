package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ModelTable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class manageProductsController implements Initializable{
	
	Connection con = null;
    PreparedStatement st = null, st1=null;
    ResultSet rs = null, rs1=null;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loggedUser.setText(LoginController.getLoggedUserName());
	    try {
	    	con = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false", "fp510", "510");
	    	SelectProd();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	@FXML
	private Text loggedUser;
	
	@FXML
    private Button homeButton;

	@FXML
    private TableView<ModelTable> ProductTable;

    @FXML
    private TableColumn<ModelTable, String> col_id;

    @FXML
    private TableColumn<ModelTable, String> col_name;

    @FXML
    private TableColumn<ModelTable, String> col_qty;
	@FXML
	private TextField ProductID;
	@FXML
	private TextField ProductName;
	@FXML
	private TextField ProductQuantity;
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
    private Button logoutButton;

	@FXML
    void logoutButtonPressed(ActionEvent event) throws SQLException {
			
		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to logout?", ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{	
			con.close();
			System.exit(0);
		}
    }
	
	@FXML
	public void homeButtonPressed(ActionEvent event) throws IOException {
		// TODO Autogenerated
		Parent userView = FXMLLoader.load(getClass().getResource("/View/adminHome.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(userScene);
		window.show();
		
		
	}
	// Event Listener on TableView.onMouseClicked
	@FXML
	public void setCellValue(MouseEvent event) {
		ModelTable data=ProductTable.getSelectionModel().getSelectedItem();
		if (data!=null)
		{
			ProductID.setText(data.getId());
			ProductName.setText(data.getName());
			ProductQuantity.setText(data.getQuantity());
		}
	}
	
    
    PreparedStatement pst = null;
	@FXML
    void addButtonPressed(ActionEvent event) throws SQLException {
		
		if(ProductName.getText().equals("") || ProductQuantity.getText().equals(""))
        {
            Alert alert=new Alert(AlertType.ERROR, "Please Fill the information!");
            alert.show();
        }
		else
		{
			String query = "INSERT INTO G_Kulw_product VALUES (Null,?,?)";
                pst = con.prepareStatement(query);
                pst.setString(1, ProductName.getText());
                pst.setInt(2, Integer.parseInt(ProductQuantity.getText()));
                pst.execute();            
                ProductTable.getItems().clear();
                SelectProd();
                clearFields();            
		}
    }

    @FXML
    void deleteButtonPressed(ActionEvent event) throws SQLException {
    	
    	if(ProductID.getText().equals(""))
        {
    		Alert alert=new Alert(AlertType.ERROR, "Please select a product from table to delete!");
            alert.show();    		
        }
    	else
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to Delete this?", ButtonType.YES, ButtonType.NO);
    		alert.setHeaderText("Are you sure?");
    		alert.showAndWait();
    		alert.showAndWait();
    		if (alert.getResult() == ButtonType.YES)
    		{
    		String query = "delete from G_Kulw_product where id=?";
           
                pst = con.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(ProductID.getText()));
                pst.execute(); 
                
                ProductTable.getItems().clear();
                SelectProd();
                clearFields();
    		}
    	}
    }
    
    @FXML
    void updateButtonPressed(ActionEvent event) throws SQLException {
    	
    	if(ProductName.getText().equals("") || ProductQuantity.getText().equals("") || ProductID.getText().equals(""))
        {
    		Alert alert=new Alert(AlertType.ERROR, "Nothing to update! Please fill the details.");
            alert.show();    		
        }
    	else
    	{
    		String query = "update G_Kulw_product set name=?, quantity=? where id=?";
            
                pst = con.prepareStatement(query);
                pst.setString(1, ProductName.getText());
                pst.setInt(2, Integer.parseInt(ProductQuantity.getText()));
                pst.setInt(3, Integer.parseInt(ProductID.getText()));
                pst.execute(); 
                
                ProductTable.getItems().clear();
                SelectProd();
                clearFields();
                
                Alert alert=new Alert(AlertType.INFORMATION, "Updated successfully!.");
                alert.show(); 
    	}
    }
	
    ObservableList<ModelTable> oblist= FXCollections.observableArrayList();
    
	public void SelectProd() throws SQLException
    {
		String query = "SELECT * from G_Kulw_product";
        st = con.prepareStatement(query);
        rs = st.executeQuery(); 
        while(rs.next())
        {
        	oblist.add(new ModelTable(rs.getString("id"), rs.getString("name"), rs.getString("quantity")));
        }	    
	    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
	    col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	    col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	    
	    ProductTable.setItems(oblist);      
    }
	
	private void clearFields()
    {
        ProductID.setText("");
        ProductName.setText("");
        ProductQuantity.setText("");
        
    }
}
