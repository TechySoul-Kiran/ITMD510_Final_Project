package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Encryption;

public class LoginController implements Initializable{
	
	Connection con = null;	
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	
		try 
		{
			con = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false", "fp510", "510");
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @FXML
    private TextField username;

    @FXML
    private ComboBox<?> usertype;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;
    
    @FXML
    void login(ActionEvent event) throws SQLException, IOException
    {
    		String query = "SELECT * from G_Kulw_userlogin WHERE username=? and password=? and usertype=?";
    		
            pst = con.prepareStatement(query);
            pst.setString(1, username.getText());
            pst.setString(2, Encryption.MD5(password.getText()));
            pst.setString(3, (String) usertype.getValue());
            rs=pst.executeQuery();
            if(rs.next())
            {
            	setLoggedUserID(rs.getInt(1));
				setLoggedUserName(username.getText()); 
				
				if(usertype.getValue().equals("user"))
				{
					
            		Parent userView = FXMLLoader.load(getClass().getResource("/View/user.fxml"));
            		Scene userScene = new Scene(userView);
            		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            		window.setScene(userScene);
            		userScene.getWindow().centerOnScreen();
            		window.show();
            	}
            	else
            	{
            		Parent userView = FXMLLoader.load(getClass().getResource("/View/adminHome.fxml"));
            		Scene userScene = new Scene(userView);
            		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            		
            		window.setScene(userScene);
            		window.show();
            		userScene.getWindow().centerOnScreen();            		
            	}            
            }
            else
            {
            	Alert alert = new Alert(AlertType.ERROR, "Wrong Username/Password!");
            	alert.show();
            }            
    }
    
    private static String loggedUserName=null;
    private static int loggedUserID;

	/**
	 * @return the loggedUserName
	 */
	public static String getLoggedUserName() {
		return loggedUserName;
	}
	/**
	 * @param loggedUserName the loggedUserName to set
	 */
	public void setLoggedUserName(String loggedUserName) {
		LoginController.loggedUserName = loggedUserName;
	}
	/**
	 * @return the loggedUserID
	 */
	public static int getLoggedUserID() {
		return loggedUserID;
	}
	/**
	 * @param loggedUserID the loggedUserID to set
	 */
	public void setLoggedUserID(int loggedUserID) {
		LoginController.loggedUserID = loggedUserID;
	}
	

}
