package tricotando;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SaleOrder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField insertCodInput;
	private JTable itemsTable;
	private DefaultTableModel table;
	private JLabel finalPriceLabel;
	


	/**
	 * Create the frame.
	 */
	public SaleOrder(Sale sale) {
		//Title
		setTitle("Tricotando");
		//Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(InsertProduct.class.getResource("/tricotando/img/tricotandoLogo.png")));
		//Remove bordas
		setUndecorated(true);
		//Closes aplication
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //Set percentage of screen to be used by the window
        int width = (int) (screenSize.width * 0.9); // 90% of screen width
        int height = (int) (screenSize.height * 0.678); // 68.8% of screen height
        this.setSize(width, height);
        // Alinhar a janela
        setLocation((int) Math.ceil(screenWidth * 0.05), (int) Math.ceil(screenHeight * 0.252));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		finalPriceLabel = new JLabel("R$ 0,00");
		finalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		finalPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		finalPriceLabel.setBounds((int)(0.6667 * screenWidth), (int)(0.5694 * screenHeight),
								  (int)(0.1302 * screenWidth), (int)(0.0556 * screenHeight));
		contentPane.add(finalPriceLabel);
		
		//Panel product list
		JPanel productListPanel = new JPanel();
		productListPanel.setBackground(new Color(255, 255, 255));
		productListPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		productListPanel.setBounds((int)(0.4818 * screenWidth), (int)(0.0463 * screenHeight),
								   (int)(0.3417 * screenWidth), (int)(0.5093 * screenHeight));
		contentPane.add(productListPanel);
		productListPanel.setLayout(null);

		//ScrollPane product list
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, (int)(0.3417 * screenWidth), (int)(0.5093 * screenHeight));
		productListPanel.add(scrollPane);
		
		//Table product list
		String[] nomeColunas = {"Nome", "Marca", "Quantidade", "Preço"};
		DefaultTableModel table = new DefaultTableModel(nomeColunas, 0);
		itemsTable = new JTable(table);
		itemsTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(itemsTable);
		itemsTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		itemsTable.setRowHeight(20);
		
		//Vefify any changes in table and call update functions
		table.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            	//Get where the changes was made
            	int row = e.getFirstRow();
                int column = e.getColumn();
                
                //Verify if is a valid point
                if (column >= 0 && row >=0) {
                	//Get changed values
                    Object changedValue = itemsTable.getValueAt(row, column);
                    //Get product where the changes was made
                    Product product = sale.getProductList().get(row);
                    
                    //If changes was made in stock
					if (column == 2) {
						//Update product sold amount in productList
						float sold = Float.parseFloat(changedValue.toString());
						product.setSoldAmount(sold);
					}//If changes was made in price 
					else if(column == 3){
						//Update product price in productList
	                    float price = Float.parseFloat(changedValue.toString());
	                    product.setPrice(price);
					}
					
                }
                //Updates final price by the products in table
                updateFinalPrice();
            }
        });
		
		//Panel Insert Barcode
		JPanel insertCodPanel = new JPanel();
		insertCodPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		insertCodPanel.setBounds((int)(0.0417 * screenWidth), (int)(0.0370 * screenHeight),
								 (int)(0.3385 * screenWidth), (int)(0.1898 * screenHeight));
		contentPane.add(insertCodPanel);
		insertCodPanel.setLayout(null);
		
		//Label Insert Barcode
		JLabel insertCodLabel = new JLabel("Insira o Código do Produto");
		insertCodLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		insertCodLabel.setBounds((int)(0.0052 * screenWidth), (int)(0.0102 * screenHeight),
								 (int)(0.25 * screenWidth), (int)(0.0370 * screenHeight));
		insertCodPanel.add(insertCodLabel);
		
		//Input Insert Barcode
		insertCodInput = new JTextField();
		insertCodInput.setBounds((int)(0.0052 * screenWidth), (int)(0.0574 * screenHeight),
								 (int)(0.3125 * screenWidth), (int)(0.0463 * screenHeight));
		insertCodPanel.add(insertCodInput);
		insertCodInput.setColumns(10);
		
		//Button Add Product
		JButton addProductButton = new JButton("Adcionar (Enter)");
		addProductButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addProductButton.setOpaque(false);
		addProductButton.setBackground(new Color(0, 128, 0));
		addProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get and stores barcode in variable
				String productBarcode = insertCodInput.getText();
				if (productBarcode.isEmpty()){
					//Do nothing
				}else {
					//Create a new product
					Product product = new Product();
					//Calls search item function
					product.searchItem(productBarcode);
					//Add product to sale product list
					sale.addProduct(product);
					//Set table rows 0
					table.setRowCount(0);
					//Set product rows in table for each product in sale product list
					for (Product prod : sale.getProductList()) {
						Object[] linhaProduto = {prod.getName(), prod.getBrand(), prod.getSoldAmount(), prod.getPrice()};
						table.addRow(linhaProduto);
					}
					//Reset input
					insertCodInput.setText("");
				}
			}
		});
		addProductButton.setBounds((int)(0.0052 * screenWidth), (int)(0.1213 * screenHeight),
								   (int)(0.125 * screenWidth), (int)(0.0556 * screenHeight));
		insertCodPanel.add(addProductButton);
		
		//Key listener enter in barcode input
		insertCodInput.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	addProductButton.doClick();
		        }
		    }
		});
		
		//Back Button
		JButton backButton = new JButton("Voltar");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backButton.setBounds((int)(0.0739 * screenWidth), (int)(0.5694 * screenHeight), 
							 (int)(0.1146 * screenWidth), (int)(0.0556 * screenHeight));
		contentPane.add(backButton);
		
		//Button End Sale
		JButton endSaleButton = new JButton("Encerrar Compra");
		endSaleButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		endSaleButton.setBounds((int)(0.2313 * screenWidth), (int)(0.5694 * screenHeight),
								(int)(0.1146 * screenWidth), (int)(0.0556 * screenHeight));
		endSaleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getRowCount() == 0) {
					//Do Nothing
				}else {
					sale.insertSale(table);
					table.setRowCount(0);
				}
			}
		});
		contentPane.add(endSaleButton);
		
		//Logo panel
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds((int)(0.1339 * screenWidth), (int)(0.2574 * screenHeight),
							(int)(0.1547 * screenWidth), (int)(0.2778 * screenHeight));
		contentPane.add(logoPanel);
		logoPanel.setLayout(null);
		
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
	}
	
	public void updateFinalPrice() {
		float finalPrice = 0.00f;
		int rowCount = itemsTable.getRowCount();
		for(int i = 0; i < rowCount; i++) {
			//Set table insert price as parameters
			String priceTable = itemsTable.getValueAt(i,3).toString();
			float price = Float.parseFloat(priceTable);
			String amountTable = itemsTable.getValueAt(i, 2).toString();
			float amount = Float.parseFloat(amountTable);
			price = price * amount;
			finalPrice += price;
		}
		
		finalPriceLabel.setText(String.format("R$ %.2f", finalPrice));
	}

}
