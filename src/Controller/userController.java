package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ModelTable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class userController implements Initializable {
	
	Connection con = null;
    PreparedStatement st = null, st1=null;
    ResultSet rs = null, rs1=null;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loggedUser.setText(LoginController.getLoggedUserName());
	    try {
	    		con = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false", "fp510", "510");
	    	SelectProd();
			cart_update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
    private TableView<ModelTable> ProductTable;

    @FXML
    private TableColumn<ModelTable, String> col_id;

    @FXML
    private TableColumn<ModelTable, String> col_name;

    @FXML
    private TableColumn<ModelTable, String> col_qty;
    
    @FXML
    private Button logoutButton;
    
	@FXML
	private Text loggedUser;
	@FXML
	private TextField ProductID;
	@FXML
	private TextField ProductName;
	@FXML
	private TextField ProductQuantity;
	@FXML
	private TextField ProductRequiredQuantity;
	@FXML
	private Button add_to_cart;
	
	
	@FXML
    private TableView<ModelTable> cartTable;

    @FXML
    private TableColumn<ModelTable, String> col1_id;

    @FXML
    private TableColumn<ModelTable, String> col1_name;

    @FXML
    private TableColumn<ModelTable, String> col1_qty;
	@FXML
	private Button userCheckout;
	@FXML
    private Button resetCart;
	
	@FXML
    private Button myOrders;
	
	@FXML
    void myOrdersButtonPressed(ActionEvent event) throws IOException {
		Parent userView = FXMLLoader.load(getClass().getResource("/View/myOrders.fxml"));
		Scene userScene = new Scene(userView);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(userScene);
		window.show();
		userScene.getWindow().centerOnScreen();
    }
	
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
	
	 Vector<Integer> item_remain=new Vector<>();
	 Vector<Vector<Object>> cart_data = new Vector<Vector<Object>>();
	 
	// Event Listener on Button[#add_to_cart].onAction
	@SuppressWarnings("unchecked")
	@FXML
	public void add_to_cartActionPerformed(ActionEvent event) throws SQLException {
		
		if(ProductID.getText().equals("") || ProductRequiredQuantity.getText().equals("") || (Integer.parseInt(ProductRequiredQuantity.getText()) > Integer.parseInt(ProductQuantity.getText())))
        {
           Alert alert=new Alert(AlertType.ERROR,"Please select a product/check quantity");
           alert.show();
        }
		else
		{
			int flag=0;
            int size=cart_data.size();
            for(int i=0;i<size;i++)
            {
                Vector<String> vecClone = new Vector<String>(3);
                vecClone = (Vector)cart_data.get(i).clone();
                int cloneID=Integer.parseInt(vecClone.get(0));
                if(cloneID == Integer.parseInt(ProductID.getText())) 
                {
                    flag=1;
                    break;
                }
            }
            if(flag==0)
            {
                item_remain.add(Integer.parseInt(ProductQuantity.getText()) - Integer.parseInt(ProductRequiredQuantity.getText()));
                Vector<Object> row = new Vector<Object>(3);
                row.addElement(ProductID.getText());
                row.addElement(ProductName.getText());
                row.addElement(ProductRequiredQuantity.getText());
                cart_data.addElement(row);
                cart.add(new ModelTable(ProductID.getText(), ProductName.getText(), ProductRequiredQuantity.getText()));
    			cart_update();
    			clearFields();
    			
            }
            else
            {
            	Alert alert=new Alert(AlertType.ERROR,"Product already present in cart!");
                alert.show();
            }
		}
	}
	// Event Listener on Button[#userCheckout].onAction
	@SuppressWarnings("unchecked")
	@FXML
	public void userCheckoutActionPerformed(ActionEvent event) throws SQLException 
	{
		int size=cart_data.size();        
        if(size>0)
        {
        	Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to donate these items? ", ButtonType.YES, ButtonType.NO);
    		alert.setHeaderText("Are you Sure?");
    		alert.showAndWait();

    		if (alert.getResult() == ButtonType.YES)
    		{
    			String query = "update G_Kulw_product set quantity=? where id=?";
    	        String addquery = "INSERT INTO G_Kulw_productordered VALUES (Null,?,?,?,?,?)";
    	        st = con.prepareStatement(query);
    	        st1 = con.prepareStatement(addquery);
    	        for(int i=0;i<size;i++)
    	        {
    	        	Vector<String> vecClone = new Vector<String>(3);
    	        	vecClone = (Vector)cart_data.get(i).clone();
    	        	st.setInt(1, item_remain.get(i));
    	        	st.setInt(2, Integer.parseInt(vecClone.get(0)));
    	        	st.execute();
    	        	
    	        	st1.setInt(1, Integer.parseInt(vecClone.get(0)));
    	        	st1.setString(2,vecClone.get(1));
    	        	st1.setInt(3, Integer.parseInt(vecClone.get(2)));
    	        	st1.setInt(4, LoginController.getLoggedUserID());
    	        	st1.setString(5, LoginController.getLoggedUserName());
    	        	st1.execute();
    	        }    	        
    	        ProductTable.getItems().clear();
    	        SelectProd();
    	        cart_data.clear();
    	        cart.clear();
    	        cart_update();
    	        alert=new Alert(AlertType.INFORMATION,"Check out Completed!");
    	        alert.show();    			
    		}  
        }
        else
        {
        	Alert alert=new Alert(AlertType.ERROR,"Error! Cart Empty!");
            alert.show();            
        }	
	}
	
	@FXML
    void setCellValue(MouseEvent event) {
		
		ModelTable data=ProductTable.getSelectionModel().getSelectedItem();
		if (data!=null)
		{
			ProductID.setText(data.getId());
			ProductName.setText(data.getName());
			ProductQuantity.setText(data.getQuantity());
		}
    }
	
    ObservableList<ModelTable> oblist= FXCollections.observableArrayList();
    ObservableList<ModelTable> cart= FXCollections.observableArrayList();
    
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
	
	public void cart_update()
	{
		col1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
	    col1_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	    col1_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	    
	    cartTable.setItems(cart); 		
	}
	
	public void clearFields()
	{
		ProductID.setText("");
		ProductName.setText("");
		ProductQuantity.setText("");
		ProductRequiredQuantity.setText("");
	}
	@FXML
    void resetCartButtonPressed(ActionEvent event) {
		cart_data.clear();
        cart.clear();        
        cart_update();
    }
}
