package tricotando;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class Report extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel table;
	private JTable itemsTable;
	private JTextField searchProductInput;
	private String search = null;
	private Map<String, String> columnMap;
	public int state = 0;
	

	/**
	 * Create the frame.
	 */
	public Report() {
		//Title
		setTitle("Tricotando");
		//Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(InsertProduct.class.getResource("/tricotando/img/tricotandoLogo.png")));
		//Closes aplication
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Maximize window
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //Set percentage of screen to be used by the window
        int width = (int) (screenWidth * 1.0); // 100% of screen widht
        int height = (int) (screenHeight * 0.963); // 96.3% of screen height
        this.setSize(width, height);
        //Center window
        this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		//Table panel
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds((int)(0.4375 * screenWidth), (int)(0.0370 * screenHeight),
							 (int)(0.5078 * screenWidth), (int)(0.8028 * screenHeight));
		getContentPane().add(tablePanel);
		tablePanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, (int)(0.5078 * screenWidth), (int)(0.8028 * screenHeight));
		tablePanel.add(scrollPane);
		
		//Table product list
		String[] nomeColunas = {"Id", "Nome", "Cor", "Descrição", "Marca", "Quantidade", "Quantidade Essencial", "Preço", "Preço de Compra"};
		table = new DefaultTableModel(nomeColunas, 0);
		itemsTable = new JTable(table);
		itemsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(itemsTable);
		itemsTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		itemsTable.setRowHeight(20);

		
		// Acessando o TableColumnModel da JTable
		TableColumnModel columnModel = itemsTable.getColumnModel();

		// Definindo a largura preferida para cada coluna
        columnModel.getColumn(0).setPreferredWidth(60);    // "Id" - aumentado levemente
        columnModel.getColumn(1).setPreferredWidth(180);   // "Nome" - diminuído levemente
        columnModel.getColumn(2).setPreferredWidth(120);   // "Cor"
        columnModel.getColumn(3).setPreferredWidth(180);   // "Descrição" - diminuído levemente
        columnModel.getColumn(4).setPreferredWidth(120);   // "Marca"
        columnModel.getColumn(5).setPreferredWidth(100);   // "Quantidade"
        columnModel.getColumn(6).setPreferredWidth(100);   // "Quantidade Essencial"
        columnModel.getColumn(7).setPreferredWidth(120);   // "Preço"
        columnModel.getColumn(8).setPreferredWidth(120);   // "Preço de Compra" - aumentado levemente
		
		//Report options panel
		JPanel reportsPanel = new JPanel();
		reportsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		reportsPanel.setBounds((int)(0.0521 * screenWidth), (int)(0.0537 * screenHeight), 
							   (int)(0.2771 * screenWidth), (int)(0.2944 * screenHeight));
		getContentPane().add(reportsPanel);
		reportsPanel.setLayout(null);
		
		startMapColumn();
		
		//Vefify any changes in table and call update functions
		table.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            	if (e.getType() == TableModelEvent.UPDATE) {
	            	//Get where the changes was made
	            	int row = e.getFirstRow();
	                int column = e.getColumn();
	                
	                Object id = itemsTable.getValueAt(row, 0);
	                
	                Object changes = itemsTable.getValueAt(row, column);
	                
	                String columnChanged = columnMap.get(itemsTable.getColumnName(column));
	                
	                if(columnChanged != null) {
	                	String sql = "UPDATE Items SET "+columnChanged+" = ? WHERE idItem = ?";
	                	try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
	                		preparedStatement.setObject(1, changes);
	                		preparedStatement.setObject(2, id);
	                		preparedStatement.executeUpdate();
	                	}catch(SQLException er){
	            	        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	            	        er.printStackTrace();
	            		}finally {
	            			id = null;
	            			changes = null;
	            			columnChanged = null;
	            		}
	                } 
            	}
            }
        });
		
		//Report items bellow safety stock
		JButton reportBellowStockButton = new JButton("Mostrar itens abaixo do estoque");
		reportBellowStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(1);
				returnStockBelowReport();
			}
		});
		reportBellowStockButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reportBellowStockButton.setBounds((int)(0.0130 * screenWidth), (int)(0.0231 * screenHeight), 
										  (int)(0.2448 * screenWidth), (int)(0.0556 * screenHeight));
		reportsPanel.add(reportBellowStockButton);
		
		//Report all itens in stocks
		JButton reportStockButton = new JButton("Mostrar Estoque");
		reportStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(2);
				returnStockReport();
			}
		});
		reportStockButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reportStockButton.setBounds((int)(0.0130 * screenWidth), (int)(0.1111 * screenHeight),
									(int)(0.2448 * screenWidth), (int)(0.0556 * screenHeight));
		reportsPanel.add(reportStockButton);
		
		//Back Button
		JButton backButton = new JButton("Voltar");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backButton.setBounds((int)(0.0130 * screenWidth), (int)(0.1991 * screenHeight),
							 (int)(0.1224 * screenWidth), (int)(0.0556 * screenHeight));
		reportsPanel.add(backButton);
		
		//Logo Panel
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds((int)(0.0802 * screenWidth), (int)(0.4574 * screenHeight),
							(int)(0.2214 * screenWidth), (int)(0.3981 * screenHeight));
		getContentPane().add(logoPanel);
		
		//Label logo
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(0, 0, logoPanel.getWidth(), logoPanel.getHeight());
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(InsertProduct.class.getResource("/tricotando/img/tricotandoLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image scaledImage = originalImage.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
		logoLabel.setIcon(new ImageIcon(scaledImage));
		logoPanel.add(logoLabel);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new Color(240, 240, 240));
		searchPanel.setBounds((int)(0.4375 * screenWidth), (int)(0.8485 * screenHeight),
							  (int)(0.5078 * screenWidth), (int)(0.05 * screenHeight));
		getContentPane().add(searchPanel);
		searchPanel.setLayout(null);
		
		searchProductInput = new JTextField();
		searchProductInput.setBounds(0, 0,
				 					 350, (int)(0.05 * screenHeight));
		searchPanel.add(searchProductInput);
		searchProductInput.setColumns(10);
		
		JButton searchButton = new JButton("Procurar");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchInput = searchProductInput.getText();
				setSearch(searchInput);
			}
		});
		searchButton.setBounds(370, 0, 100, (int)(0.05 * screenHeight));
		searchPanel.add(searchButton);
		
		//Key listener enter in search product input
		searchProductInput.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	searchButton.doClick();
		        }
		    }
		});
	}
	
	//Return function to get all items where stock is bellow safety stock
	public void returnStockReport() {
		Menu.getConnection();
		resetTable();
		String search = (getSearch());
		String sql;
		if(search == null) {
			//Prepare and insert query
			sql = "SELECT * FROM Items";
		}else{
			sql = "SELECT * FROM Items WHERE ";
			String[] arr = search.split(" ");
			for(int i = 0; i < arr.length; i++) {
				String s = arr[i];
				if(i > 0) {
					sql += "AND";
				}
				sql += "(itemName LIKE '%"+s+"%' OR itemColor LIKE '%"+s+"%' OR itemDesc LIKE '%"+s+"%' OR itemBrand LIKE '%"+s+"%')";
			}		
		}		
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
			//Get results and stores in the product object
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("idItem"));
				product.setName(resultSet.getString("itemName"));
				product.setBrand(resultSet.getString("itemBrand"));
				product.setBarcode(resultSet.getString("itemBarcode"));
				product.setStock(resultSet.getFloat("itemStock"));
				product.setSafetyStock(resultSet.getFloat("itemSafetyStock"));
				product.setPrice(resultSet.getFloat("itemPrice"));
				product.setPurchasePrice(resultSet.getFloat("itemPurchasePrice"));
				product.setColor(resultSet.getString("itemColor"));
				product.setDesc(resultSet.getString("itemDesc"));
				//Add every product in the result set in the table
				Object[] linhaProduto = {product.getId(), product.getName(), product.getColor(), product.getDesc(), product.getBrand(), product.getStock(), product.getSafetyStock(), product.getPrice(), product.getPurchasePrice()};
				table.addRow(linhaProduto);
				}
			}catch(SQLException er) {
		        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
		        er.printStackTrace();
			}
		}catch(SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        e.printStackTrace();
		}
	}
	
	//Return function to get every item registered in stock
	public void returnStockBelowReport() {
		Menu.getConnection();
		resetTable();
		String sql;
		if(getSearch() == null) {
			//Prepare and insert query
			sql = "SELECT * FROM Items WHERE itemStock <= itemSafetyStock";
		}else{
			sql = "SELECT * FROM Items WHERE (itemStock <= itemSafetyStock) AND";
			String[] arr = search.split(" ");
			for(int i = 0; i < arr.length; i++) {
				String s = arr[i];
				if(i > 0) {
					sql += "AND";
				}
				sql += "(itemName LIKE '%"+s+"%' OR itemColor LIKE '%"+s+"%' OR itemDesc LIKE '%"+s+"%' OR itemBrand LIKE '%"+s+"%')";	
			}
		}
		try(PreparedStatement preparedStatement = Menu.connection.prepareStatement(sql)){
			//Get results and stores in the product object
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("idItem"));
				product.setName(resultSet.getString("itemName"));
				product.setBrand(resultSet.getString("itemBrand"));
				product.setBarcode(resultSet.getString("itemBarcode"));
				product.setStock(resultSet.getFloat("itemStock"));
				product.setSafetyStock(resultSet.getFloat("itemSafetyStock"));
				product.setPrice(resultSet.getFloat("itemPrice"));
				product.setPurchasePrice(resultSet.getFloat("itemPurchasePrice"));
				product.setColor(resultSet.getString("itemColor"));
				product.setDesc(resultSet.getString("itemDesc"));
				//Add every product in the result set in the table
				Object[] linhaProduto = {product.getId(), product.getName(), product.getColor(), product.getDesc(), product.getBrand(), product.getStock(), product.getSafetyStock(), product.getPrice(), product.getPurchasePrice()};
				table.addRow(linhaProduto);
				}
			}catch(SQLException er) {
		        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
		        er.printStackTrace();
			}
		}catch(SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
	        e.printStackTrace();
		}
	}
	
	public void setSearch(String search) {
		convertEmptyToNull(search);
		this.search = search;
		if(getState() == 0) {
			//do nothing;
		}else if(getState() == 1) {
			returnStockBelowReport();
		}else {
			returnStockReport();
		}
	}
	
	public String getSearch() {
		return search;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public static String convertEmptyToNull(String input) {
	    return (input == null || input.trim().isEmpty()) ? null : input;
	}
	

	public void resetTable() {
		table.setRowCount(0);
	}
	
	public void startMapColumn() {
		columnMap = new HashMap<>();
		//columnMap.put("Id","idItem");
		columnMap.put("Nome","itemName");
		columnMap.put("Cor", "itemColor");
		columnMap.put("Descrição","itemDesc");
		columnMap.put("Marca","itemBrand");
		columnMap.put("Quantidade","itemStock");
		columnMap.put("Quantidade Essencial","itemSafetyStock");
		columnMap.put("Preço", "itemPrice");
		columnMap.put("Preço de Compra","itemPurchasePrice");
	}
}
