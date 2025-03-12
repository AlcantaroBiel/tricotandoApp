package tricotando;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Sale {
	//att
	private int idSale;
	private String date;
	private ArrayList<Product> productList;
	
	//met
	//Initialize productList
	public Sale() {
		this.productList= new ArrayList<Product>();
	}
	
	//Set & get idSale
	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
	
	public int getIdSale() {
		return idSale;
	}
	
	//Set & get date
	public void setDate(String date) {
		this.date = date;
	}	
	
	public String getDate() {
		return date;
	}
	
	//Add product in productList
	public void addProduct(Product product) {
		//Calls function to verify if product already inserted in product list
		boolean exists = verifyExists(product);
		if(exists == true) {
			String msg = "Produto já inserido";
			JOptionPane optionPane = new JOptionPane();
	        optionPane.setMessage(msg);
	        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
	        JDialog dialog = optionPane.createDialog(null, "Tricotando");
	        dialog.setVisible(true);
		}else {
			productList.add(product);
		}
	}
	
	//Remove product in productList
	public void removeProduct(Product product) {
		productList.remove(product);
	}
	
	//Get product list
	public ArrayList<Product> getProductList(){
		return productList;
	}
	
	//Function that verifies if product is already in product list
	public boolean verifyExists(Product product) {
		for(Product productInList : productList) {
			if(productInList.getId() == product.getId()) {
				return true;
			}
		}return false;
	}
	
	//Insert a new sale order in db
	public void insertSale(DefaultTableModel table) {
		Menu.getConnection();
		//Prepare and execute insert into Sales getting its id
		String sql = "INSERT INTO Sales () VALUES ();";
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			preparedStatement.executeUpdate();
			//Stores new sale id
			ResultSet rs = preparedStatement.getGeneratedKeys();			
			int id = -1;
			if (rs.next()) {
				id = rs.getInt(1);
			}
			//Initialize an index for the table rows
			int rowIndex = 0;
			//Prepare and execute a insert into item_sales in db for each item in the product list
			for (Product produto : this.getProductList()) {
				//Prepare sql command
				String sql2 = "INSERT INTO Items_Sales (Sales_idSale, Items_idItem, soldAmount, soldPrice) VALUES (?, ?, ?, ?)";
				try(PreparedStatement preparedStatement2 = Menu.connection.prepareStatement(sql2)){
					//Set sale id parameter
					preparedStatement2.setInt(1, id);
					//Set product id parameter
					preparedStatement2.setInt(2, produto.getId());
					//Set table insert amount as parameter
					String amount = table.getValueAt(rowIndex,2).toString();
					produto.setStock(Float.parseFloat(amount));
					preparedStatement2.setFloat(3, produto.getStock());
					//Set table insert price as parameters
					String price = table.getValueAt(rowIndex,3).toString();
					produto.setPrice(Float.parseFloat(price));
					preparedStatement2.setFloat(4, produto.getPrice());
					//Execute insert
					int rowsAffected = preparedStatement2.executeUpdate();
				}catch(SQLException er) {
			        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
			        er.printStackTrace();
				}
				rowIndex++;
			}
			this.getProductList().clear();
			successMsg();
		}catch(SQLException er){
	        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        er.printStackTrace();
		}
	}
	
	//Success insert message
	public void successMsg() {
		String msg = "Venda inserido com sucesso";
		JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Tricotando");
        dialog.setVisible(true);
	}

}
