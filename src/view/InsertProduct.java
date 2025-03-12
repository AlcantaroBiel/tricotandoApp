package tricotando;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Toolkit;

public class InsertProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameInput;
	private JTextField brandInput;
	private JTextField barCodeInput;
	private JTextField stockInput;
	private JTextField safetyStockInput;
	private JTextField priceInput;
	private JTextField purchasePriceInput;
	private JTextField colorInput;
	private JTextField descriptionInput;


	/**
	 * Create the frame.
	 */
	public InsertProduct() {
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
		
		//Panel Insert Data
		JPanel insertDataPanel = new JPanel();
		insertDataPanel.setBorder(new TitledBorder(null, "Inserir Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		insertDataPanel.setBounds((int)(0.065 * width), (int)(0.03 * height), //Position
								  (int)(0.40 * width), (int)(0.9 * height)); //Size
		contentPane.add(insertDataPanel);
		insertDataPanel.setLayout(null);
		
		//Name
		//Label
		JLabel nameLabel = new JLabel("Nome");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.05 * insertDataPanel.getHeight()), //Position
				  			(int)(0.80 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(nameLabel);
		//Input
		nameInput = new JTextField();
		nameInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.09 * insertDataPanel.getHeight()), //Position
	  						(int)(0.95 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(nameInput);
		nameInput.setColumns(10);
		
		//Barcode
		//Label
		JLabel barCodeLabel = new JLabel("Código de Barras");
		barCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		barCodeLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.18 * insertDataPanel.getHeight()), //Position
					 		   (int)(0.80 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(barCodeLabel);
		//Input
		barCodeInput = new JTextField();
		barCodeInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		barCodeInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.22 * insertDataPanel.getHeight()), //Position
							   (int)(0.95 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(barCodeInput);
		
		//Brand
		//Label
		JLabel brandLabel = new JLabel("Marca");
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		brandLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.31 * insertDataPanel.getHeight()), //Position
	  						 (int)(0.80 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(brandLabel);
		//Input
		brandInput = new JTextField();
		brandInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandInput.setColumns(10);
		brandInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.35 * insertDataPanel.getHeight()), //Position
				   			 (int)(0.95 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(brandInput);
		
		//Stock
		//Label
		JLabel stockLabel = new JLabel("Quantidade");
		stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stockLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.44 * insertDataPanel.getHeight()), //Position
					 		 (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(stockLabel);
		//Input
		stockInput = new JTextField();
		stockInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		stockInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.48 * insertDataPanel.getHeight()), //Position
							 (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(stockInput);
		
		//Safety Stock
		//Label
		JLabel safetyStockLabel = new JLabel("Quantidade Essencial");
		safetyStockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		safetyStockLabel.setBounds((int)(0.526 * insertDataPanel.getWidth()), (int)(0.44 * insertDataPanel.getHeight()), //Position
				     			   (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(safetyStockLabel);
		//Input
		safetyStockInput = new JTextField();
		safetyStockInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		safetyStockInput.setColumns(10);
		safetyStockInput.setBounds((int)(0.525 * insertDataPanel.getWidth()), (int)(0.48 * insertDataPanel.getHeight()), //Position
			 					   (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(safetyStockInput);

		//Price
		//Label
		JLabel priceLabel = new JLabel("Preço");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		priceLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.57 * insertDataPanel.getHeight()), //Position
		 		 			 (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(priceLabel);
		//Input
		priceInput = new JTextField();
		priceInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceInput.setColumns(10);
		priceInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.61 * insertDataPanel.getHeight()), //Position
				 			 (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(priceInput);
		
		//Purchase Price
		//Label
		JLabel purchasePriceLabel = new JLabel("Preço de Compra");
		purchasePriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		purchasePriceLabel.setBounds((int)(0.526 * insertDataPanel.getWidth()), (int)(0.57 * insertDataPanel.getHeight()), //Position
				   					 (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(purchasePriceLabel);
		//Input
		purchasePriceInput = new JTextField();
		purchasePriceInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		purchasePriceInput.setBounds((int)(0.525 * insertDataPanel.getWidth()), (int)(0.61 * insertDataPanel.getHeight()), //Position
				   					 (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(purchasePriceInput);

		// Cor
		// Label
		JLabel colorLabel = new JLabel("Cor");
		colorLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		colorLabel.setBounds((int)(0.031 * insertDataPanel.getWidth()), (int)(0.70 * insertDataPanel.getHeight()), //Position
	 			 			 (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(colorLabel);
		// Input
		colorInput = new JTextField();
		colorInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		colorInput.setColumns(10);
		colorInput.setBounds((int)(0.025 * insertDataPanel.getWidth()), (int)(0.74 * insertDataPanel.getHeight()), //Position
	 			 			 (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(colorInput);

		// Descrição
		// Label
		JLabel descriptionLabel = new JLabel("Descrição");
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		descriptionLabel.setBounds((int)(0.526 * insertDataPanel.getWidth()), (int)(0.70 * insertDataPanel.getHeight()), //Position
					 			   (int)(0.30 * insertDataPanel.getWidth()), (int)(0.03 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(descriptionLabel);
		// Input
		descriptionInput = new JTextField();
		descriptionInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionInput.setColumns(10);
		descriptionInput.setBounds((int)(0.525 * insertDataPanel.getWidth()), (int)(0.74 * insertDataPanel.getHeight()), //Position
					 			   (int)(0.45 * insertDataPanel.getWidth()), (int)(0.06 * insertDataPanel.getHeight())); //Size
		insertDataPanel.add(descriptionInput);
		
		//Insert Button
		JButton insertButton = new JButton("Inserir Produto");
		insertButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Get text inputs and stores in variables
					String name = convertEmptyToNull(nameInput.getText());
					String brand = convertEmptyToNull(brandInput.getText());
					String barcode = convertEmptyToNull(barCodeInput.getText());
					float stock = Float.parseFloat(stockInput.getText()); 
					float safetyStock = Float.parseFloat(safetyStockInput.getText());
					float price = Float.parseFloat(priceInput.getText());
					float purchasePrice = Float.parseFloat(purchasePriceInput.getText());
					String color = convertEmptyToNull(colorInput.getText());
					String desc = convertEmptyToNull(descriptionInput.getText());
					//Create a product and pass its parameters
					Product product = new Product(name, barcode, brand, price, purchasePrice, stock, safetyStock, color, desc);		
					//Calls insert product function. Product Method
					product.insertProduct();		
					//Calls reset input function
					resetInput();
				}catch(Exception er) {
			        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
			        er.printStackTrace();
				}
			}
		});
		insertButton.setBounds((int)(0.60 * insertDataPanel.getWidth()), (int)(0.53 * screenHeight),
							   (int)(0.30 * insertDataPanel.getWidth()), (int)(0.10 * insertDataPanel.getHeight()));
		insertDataPanel.add(insertButton);
		
		// Adiciona KeyListener ao painel
		insertDataPanel.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            insertButton.doClick();
		        }
		    }
		});

		// Adiciona KeyListener a cada componente filho
		for (Component component : insertDataPanel.getComponents()) {
		    component.addKeyListener(new KeyAdapter() {
		        @Override
		        public void keyPressed(KeyEvent e) {
		            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		                insertButton.doClick();
		            }
		        }
		    });
		}
			
		//Title Label
		JLabel insertTitleLabel = new JLabel("Inserir Produto");
		insertTitleLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 22));
		insertTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		insertTitleLabel.setBounds((int)(0.638 * screenWidth), (int)(0.0102 * screenHeight),
								   (int)(0.101 * screenWidth), (int)(0.0333 * screenHeight));
		contentPane.add(insertTitleLabel);
		
		//Back Button
		JButton backButton = new JButton("Voltar");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close insert product window
				dispose();
			}
		});
		backButton.setBounds((int)(0.6932 * screenWidth), (int)(0.5926 * screenHeight),
						 	 (int)(0.1146 * screenWidth), (int)(0.0556 * screenHeight));
		contentPane.add(backButton);
		
		//Button Change Product
		JButton changeProductButton = new JButton("Alterar Produto");
		changeProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(true) {
					//Opens input window to get product's barcode that wants to be changed
					String barCodeChange = JOptionPane.showInputDialog(null, "Insira o código do produto:");
					//If barcod input is empty or closes the window stop search
		            if (barCodeChange == null || barCodeChange.trim().isEmpty()) {
		                break;
		            }
					//Create a new product and calls search item function with barcode as parameters. Product Method
					Product product = new Product();
					boolean found = product.searchItem(barCodeChange);
					if(found) {
						//After product was found creates a change product window with the found product as paremeter
						ChangeProduct changeProduct = new ChangeProduct(product);
						changeProduct.setVisible(true);
						//Closes this window
						dispose();
						break;
					}else {
						JOptionPane.showMessageDialog(null, "Produto não encontrado. Tente novamente.");
					}
				}
			}
		});
		changeProductButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		changeProductButton.setBounds((int)(0.5604 * screenWidth), (int)(0.5926 * screenHeight),
									  (int)(0.1146 * screenWidth), (int)(0.0556 * screenHeight));
		contentPane.add(changeProductButton);
		
		//Logo panel
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds((int)(0.5557 * screenWidth), (int)(0.087 * screenHeight),
							(int)(0.2573 * screenWidth), (int)(0.462 * screenHeight));
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
	
	//Reset insert inputs
	public void resetInput() {
		//nameInput.setText("");
		//brandInput.setText("");
		barCodeInput.setText("");
		stockInput.setText("");
		//safetyStockInput.setText("");
		//priceInput.setText("");
		//purchasePriceInput.setText("");
		colorInput.setText("");
		descriptionInput.setText("");
		
	}
	
	public static String convertEmptyToNull(String input) {
	    return (input == null || input.trim().isEmpty()) ? null : input;
	}
}
