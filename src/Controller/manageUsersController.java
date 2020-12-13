package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Encryption;
import models.ModelTableUsers;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

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

import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class manageUsersController implements Initializable {
	
	Connection con = null;
    PreparedStatement st = null, st1=null;
    ResultSet rs = null, rs1=null;    
    PreparedStatement pst = null;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loggedUser.setText(LoginController.getLoggedUserName());
		try {
			con = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false", "fp510", "510");
			SelectUsers();
			clearFields.fire();
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
    private TableView<ModelTableUsers> usersTable;

    @FXML
    private TableColumn<ModelTableUsers, String> col_id;

    @FXML
    private TableColumn<ModelTableUsers, String> col_name;

    @FXML
    private TableColumn<ModelTableUsers, String> col_password;

    @FXML
    private TableColumn<ModelTableUsers, String> col_type;

	@FXML
	private TextField userID;
	@FXML
	private TextField userName;
	@FXML
	private TextField userPassword;
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button clearFields;
	
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
    private RadioButton userRadioButton;

    @FXML
    private RadioButton adminRadioButton;
    
    @FXML
    void setRadioButtonAsAdmin(ActionEvent event) {
    	adminRadioButton.setSelected(true);
    	userRadioButton.setSelected(false);
    }

    @FXML
    void setRadioButtonAsUser(ActionEvent event) {
    	adminRadioButton.setSelected(false);
    	userRadioButton.setSelected(true);
    }

	// Event Listener on Button[#homeButton].onAction
	@FXML
	public void homeButtonPressed(ActionEvent event) throws IOException {
		
		Parent userView = FXMLLoader.load(getClass().getResource("/View/adminHome.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}
	// Event Listener on TableView[#ProductTable].onMouseClicked
	@FXML
	public void setCellValue(MouseEvent event) {
		
		ModelTableUsers data=usersTable.getSelectionModel().getSelectedItem();
		if (data!=null)
		{
			userID.setText(data.getId());
			userName.setText(data.getName());
			String type=data.getType();
			if(type.equals("user"))
			{
				userRadioButton.setSelected(true);
				adminRadioButton.setSelected(false);
			}
			else
			{
				userRadioButton.setSelected(false);
				adminRadioButton.setSelected(true);
			}
		}
	}
	// Event Listener on Button[#addButton].onAction
	@FXML
	public void addButtonPressed(ActionEvent event) throws SQLException {
		
		if(userName.getText().equals("") || userPassword.getText().equals(""))
        {
			Alert alert=new Alert(AlertType.ERROR, "Please Fill the information!");
            alert.show();
        }
		else
		{
				String query = "INSERT INTO G_Kulw_userlogin VALUES (Null,?,?,?)";          
                pst = con.prepareStatement(query);                
                pst.setString(1, userName.getText());
                pst.setString(2, Encryption.MD5(userPassword.getText()));                
                if(userRadioButton.isSelected())
                	pst.setString(3, "user");
                else
                	pst.setString(3, "admin");
                
                pst.execute();             
                usersTable.getItems().clear();
                SelectUsers();
                clearFields.fire();                
                Alert alert=new Alert(AlertType.INFORMATION, "Added successfully!");
                alert.show();     
		}
		
	}
	// Event Listener on Button[#updateButton].onAction
	@FXML
	public void updateButtonPressed(ActionEvent event) throws SQLException {
		if(userName.getText().equals("") || userPassword.getText().equals("") || userID.getText().equals(""))
        {
			Alert alert=new Alert(AlertType.ERROR, "Please select a user to update.");
            alert.show();
            
        }
		else
		{
			String query = "update G_Kulw_userlogin set username=?, password=?, usertype=? where id=?";
			pst = con.prepareStatement(query);            
            pst.setString(1, userName.getText());
            pst.setString(2, Encryption.MD5(userPassword.getText()));            
            if(userRadioButton.isSelected())
            	pst.setString(3, "user");
            else
            	pst.setString(3, "admin");
            
            pst.setInt(4, Integer.parseInt(userID.getText()));
            
            pst.execute();            
            usersTable.getItems().clear();
            SelectUsers();
            clearFields.fire();            
            Alert alert=new Alert(AlertType.INFORMATION, "User Information Updated!");
            alert.show();
		}
	}
	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void deleteButtonPressed(ActionEvent event) throws SQLException {
		
		if(userID.getText().equals(""))
        {
			Alert alert=new Alert(AlertType.ERROR, "Please select a user from table to delete or fill the information!");
            alert.show();
        }
		else
		{
			String query = "delete from G_Kulw_userlogin where id=?";
			pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(userID.getText()));
            pst.execute(); 
            
            usersTable.getItems().clear();
            SelectUsers();
            clearFields.fire();            
            Alert alert=new Alert(AlertType.INFORMATION, " User Deleted!");
            alert.show();            
		}
	}
	// Event Listener on Button[#clearFields].onAction
	@FXML
	public void clearFieldsButtonPressed(ActionEvent event) {
		userID.setText("");
        userName.setText("");
        userPassword.setText("");
        userRadioButton.setSelected(false);
        adminRadioButton.setSelected(false);
	}
	
	ObservableList<ModelTableUsers> oblist= FXCollections.observableArrayList();
    
	public void SelectUsers() throws SQLException
    {
		String query = "SELECT * from G_Kulw_userlogin";
        st = con.prepareStatement(query);
        rs = st.executeQuery(); 
        while(rs.next())
        {
        	oblist.add(new ModelTableUsers(rs.getString("id"), rs.getString("username"), rs.getString("password"), rs.getString("usertype")));
        }	    
	    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
	    col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	    col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
	    col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
	    
	    usersTable.setItems(oblist);      
    }
}
