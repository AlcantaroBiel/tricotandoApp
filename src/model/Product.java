package tricotando;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Product {
	//att
	private int idItem;
	private String name;
	private String barcode;
	private String brand;
	private float price;
	private float purchasePrice;
	private float stock;
	private float safetyStock;
	private float soldAmount;
	private String color;
	private String desc;
	
	//met
	//constructor default
	public Product() {
		setName("");
		setBarcode("");
		setBrand("");
		setPrice(0.0f);
		setPurchasePrice(0.0f);
		setStock(0.0f);
		setSafetyStock(0.0f);
		setSoldAmount(1);
		setColor("");
		setDesc("");
	}
	
	//constructor parameters
	public Product(String name, String barcode, String brand, float price, float purchasePrice, float stock, float safetyStock, String color, String desc) {
		setName(name);
		setBarcode(barcode);
		setBrand(brand);
		setPrice(price);
		setPurchasePrice(purchasePrice);
		setStock(stock);
		setSafetyStock(safetyStock);
		setColor(color);
		setDesc(desc);
	}
	
	//set & get id
	public void setId(int idItem){
		this.idItem = idItem;
	}
	
	public int getId() {
		return idItem;
	}
	
	//set & get name
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	//set & get barcode;
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	//set & get brand
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getBrand() {
		return brand;
	}
	
	//set & get price
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getPrice() {
		return price;
	}
	
	//set & get purchase price
	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public float getPurchasePrice() {
		return purchasePrice;
	}
	
	//set & get stock
	public void setStock(float stock) {
		this.stock = stock;
	}
	
	public float getStock() {
		return stock;
	}
	
	//set & get safety stock
	public void setSafetyStock(float safetyStock) {
		this.safetyStock = safetyStock;
	}
	
	public float getSafetyStock() {
		return safetyStock;
	}
	
	//Set & get sold amount
	public void setSoldAmount(float soldAmount) {
		this.soldAmount = soldAmount;
	}
	
	public float getSoldAmount() {
		return soldAmount;
	}
	
	//Set & get color
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	//Set & get Desc
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	//insert new product in the db
	public void insertProduct() {
		Menu.getConnection();
		//Prepare and insert the sql command 
		//Comando SQL
		String sql = "INSERT INTO Items(itemBarcode, itemName, itemBrand, itemPrice, itemPurchasePrice, itemStock, itemSafetyStock, itemColor, itemDesc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
			//Define parameters to insert statement
			preparedStatement.setString(1, getBarcode());
			preparedStatement.setString(2, getName());
			preparedStatement.setString(3, getBrand());
			preparedStatement.setFloat(4, getPrice());
			preparedStatement.setFloat(5, getPurchasePrice());
			preparedStatement.setFloat(6, getStock());
			preparedStatement.setFloat(7, getSafetyStock());
			preparedStatement.setString(8, getColor());
			preparedStatement.setString(9, getDesc());
			//Execute Insert
			int rowsAffected = preparedStatement.executeUpdate();
			//Calls insert return functions
			successInsertMsg();
		}catch(SQLException e){			
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        e.printStackTrace();
		}
	}
	
	//Returns a insert success message
	public void successInsertMsg() {
		String msg = "Produto inserido com sucesso";
		JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Tricotando");
        dialog.setVisible(true);
	}
	
	
	//Search for an item in the db by barcode as parameter
	public boolean searchItem(String codBarras) {
		Menu.getConnection();
		//Prepare sql query and search for the item
		String sql = "SELECT * FROM Items WHERE itemBarcode = ?;";
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
			preparedStatement.setString(1, codBarras);
			//Get results and stores in the product object
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.next()) {
				setId(resultSet.getInt("idItem"));
				setName(resultSet.getString("itemName"));
				setBrand(resultSet.getString("itemBrand"));
				setBarcode(resultSet.getString("itemBarcode"));
				setStock(resultSet.getFloat("itemStock"));
				setSafetyStock(resultSet.getFloat("itemSafetyStock"));
				setPrice(resultSet.getFloat("itemPrice"));
				setPurchasePrice(resultSet.getFloat("itemPurchasePrice"));
				setColor(resultSet.getString("itemColor"));
				setDesc(resultSet.getString("itemDesc"));
				return true;
				}else {
					return false;
				}
			}catch(SQLException er) {
		        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
		        er.printStackTrace();
				return false;
			}
		}catch(SQLException er) {
			errorSearchMsg();
	        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        er.printStackTrace();
			return false;
		}
	}
	
	//Returns a search error message
	public void errorSearchMsg() {
		String msg = "Falha de Pesquisa";
		JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Tricotando");
        dialog.setVisible(true);
	} 
	
	//Change product in data base using input parameters
	public boolean changeProduct(String name, String barcode, String brand, float stock, float safetyStock, float price, float purchasePrice, String color, String desc) {
		Menu.getConnection();
		//Set parameters as attributes
		setName(name);
		setBarcode(barcode);
		setBrand(brand);
		setStock(stock);
		setSafetyStock(safetyStock);
		setPrice(price);
		setPurchasePrice(purchasePrice);
		setColor(color);
		setDesc(desc);
		//Prepare and insert sql update command
		String sql = "UPDATE Items SET itemBarcode = ?, itemName = ?, itemBrand = ?, itemPrice = ?, itemPurchasePrice = ?, itemStock = ?, itemSafetyStock = ?, itemColor = ?, itemDesc = ? WHERE idItem = ?";
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
			//Define parameters in the update
			preparedStatement.setString(1, getBarcode());
			preparedStatement.setString(2, getName());
			preparedStatement.setString(3, getBrand());
			preparedStatement.setFloat(4, getPrice());
			preparedStatement.setFloat(5, getPurchasePrice());
			preparedStatement.setFloat(6, getStock());
			preparedStatement.setFloat(7, getSafetyStock());
			preparedStatement.setString(8, getColor());
			preparedStatement.setString(9, getDesc());
			preparedStatement.setInt(10, getId());
			//Execute update
			int rowsAffected = preparedStatement.executeUpdate();
			//Calls update return functions
			successUpdateMsg();
			return true;
		}catch(SQLException er){
			errorUpdateMsg();
	        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        er.printStackTrace();
	        return false;
		}
	}
	
	//Returns a change success message
	public void successUpdateMsg() {
		String msg = "Produto alterado com sucesso";
		JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Tricotando");
        dialog.setVisible(true);
	}
	
	//Returns a change error message
	public void errorUpdateMsg() {
		String msg = "Falha de alteração";
		JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Tricotando");
        dialog.setVisible(true);
	}
	
}
