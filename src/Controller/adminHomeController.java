package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class adminHomeController implements Initializable{
	@FXML
	private Text loggedUser;
	@FXML
	private Button manageOrders;
	@FXML
	private Button manageProducts;
	@FXML
	private Button manageUser;
	

	@FXML
    private Button logoutButton;

	@FXML
    void logoutButtonPressed(ActionEvent event) {
		
	
		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to logout?", ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		{	
			System.exit(0);
		}
		

    }

	// Event Listener on Button[#manageOrders].onAction
	@FXML
	public void manageOrdersButton(ActionEvent event) throws IOException {
		Parent userView = FXMLLoader.load(getClass().getResource("/View/manageOrders.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(userScene);
		window.show();
		userScene.getWindow().centerOnScreen();
		
	}
	// Event Listener on Button[#manageProducts].onAction
	@FXML
	public void manageProductsButton(ActionEvent event) throws IOException {
		Parent userView = FXMLLoader.load(getClass().getResource("/View/manageProducts.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(userScene);
		window.show();
		userScene.getWindow().centerOnScreen();
		
	}
	// Event Listener on Button[#manageUser].onAction
	@FXML
	public void manageUserButton(ActionEvent event) throws IOException {
		Parent userView = FXMLLoader.load(getClass().getResource("/View/manageUsers.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(userScene);
		window.show();
		userScene.getWindow().centerOnScreen();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		loggedUser.setText(LoginController.getLoggedUserName());
		
	}
}
