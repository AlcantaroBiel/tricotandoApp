package tricotando;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ChangeProduct extends JFrame {

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
	public ChangeProduct(Product product) {				
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
		
		//Panel Change Data
		JPanel changeDataPanel = new JPanel();
		changeDataPanel.setBorder(new TitledBorder(null, "Inserir Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		changeDataPanel.setBounds((int)(0.065 * width), (int)(0.03 * height), //Position
				  				  (int)(0.40 * width), (int)(0.9 * height)); //Size
		contentPane.add(changeDataPanel);
		changeDataPanel.setLayout(null);
		
		//Name
		//Label
		JLabel nameLabel = new JLabel("Nome");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.05 * changeDataPanel.getHeight()), //Position
						  	(int)(0.80 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(nameLabel);
		//Input
		nameInput = new JTextField();
		nameInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.09 * changeDataPanel.getHeight()), //Position
	  						(int)(0.95 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(nameInput);
		nameInput.setColumns(10);
		nameInput.setText(product.getName());
				
		//Barcode
		//Label
		JLabel barCodeLabel = new JLabel("Código de Barras");
		barCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		barCodeLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.18 * changeDataPanel.getHeight()), //Position
					 		   (int)(0.80 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(barCodeLabel);
		//Input
		barCodeInput = new JTextField();
		barCodeInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		barCodeInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.22 * changeDataPanel.getHeight()), //Position
							   (int)(0.95 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(barCodeInput);
		barCodeInput.setText(product.getBarcode());
		
		//Brand
		//Label
		JLabel brandLabel = new JLabel("Marca");
		brandLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		brandLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.31 * changeDataPanel.getHeight()), //Position
	  						 (int)(0.80 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(brandLabel);
		//Input
		brandInput = new JTextField();
		brandInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		brandInput.setColumns(10);
		brandInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.35 * changeDataPanel.getHeight()), //Position
				   			 (int)(0.95 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(brandInput);
		brandInput.setText(product.getBrand());
		
		//Stock
		//Label
		JLabel stockLabel = new JLabel("Quantidade");
		stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		stockLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.44 * changeDataPanel.getHeight()), //Position
					 		 (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(stockLabel);
		//Input
		stockInput = new JTextField();
		stockInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		stockInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.48 * changeDataPanel.getHeight()), //Position
							 (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(stockInput);
		stockInput.setText(String.valueOf(product.getStock()));
		
		//Safety Stock
		//Label
		JLabel safetyStockLabel = new JLabel("Quantidade Essencial");
		safetyStockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		safetyStockLabel.setBounds((int)(0.526 * changeDataPanel.getWidth()), (int)(0.44 * changeDataPanel.getHeight()), //Position
				     			   (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(safetyStockLabel);
		//Input
		safetyStockInput = new JTextField();
		safetyStockInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		safetyStockInput.setColumns(10);
		safetyStockInput.setBounds((int)(0.525 * changeDataPanel.getWidth()), (int)(0.48 * changeDataPanel.getHeight()), //Position
			 					   (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(safetyStockInput);
		safetyStockInput.setText(String.valueOf(product.getSafetyStock()));
		
		//Price
		//Label
		JLabel priceLabel = new JLabel("Preço");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		priceLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.57 * changeDataPanel.getHeight()), //Position
		 		 			 (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(priceLabel);
		//Input
		priceInput = new JTextField();
		priceInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceInput.setColumns(10);
		priceInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.61 * changeDataPanel.getHeight()), //Position
				 			 (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(priceInput);
		priceInput.setText(String.valueOf(product.getPrice()));
		
		//Purchase Price
		//Label
		JLabel purchasePriceLabel = new JLabel("Preço de Compra");
		purchasePriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		purchasePriceLabel.setBounds((int)(0.526 * changeDataPanel.getWidth()), (int)(0.57 * changeDataPanel.getHeight()), //Position
				   					 (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(purchasePriceLabel);
		//Input
		purchasePriceInput = new JTextField();
		purchasePriceInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		purchasePriceInput.setBounds((int)(0.525 * changeDataPanel.getWidth()), (int)(0.61 * changeDataPanel.getHeight()), //Position
				   					 (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(purchasePriceInput);
		purchasePriceInput.setText(String.valueOf(product.getPurchasePrice()));
		
		// Cor
		// Label
		JLabel colorLabel = new JLabel("Cor");
		colorLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		colorLabel.setBounds((int)(0.031 * changeDataPanel.getWidth()), (int)(0.70 * changeDataPanel.getHeight()), //Position
	 			 			 (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(colorLabel);
		// Input
		colorInput = new JTextField();
		colorInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		colorInput.setColumns(10);
		colorInput.setBounds((int)(0.025 * changeDataPanel.getWidth()), (int)(0.74 * changeDataPanel.getHeight()), //Position
	 			 			 (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(colorInput);
		colorInput.setText(product.getColor());
		
		// Descrição
		// Label
		JLabel descriptionLabel = new JLabel("Descrição");
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		descriptionLabel.setBounds((int)(0.526 * changeDataPanel.getWidth()), (int)(0.70 * changeDataPanel.getHeight()), //Position
					 			   (int)(0.30 * changeDataPanel.getWidth()), (int)(0.03 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(descriptionLabel);
		// Input
		descriptionInput = new JTextField();
		descriptionInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionInput.setColumns(10);
		descriptionInput.setBounds((int)(0.525 * changeDataPanel.getWidth()), (int)(0.74 * changeDataPanel.getHeight()), //Position
					 			   (int)(0.45 * changeDataPanel.getWidth()), (int)(0.06 * changeDataPanel.getHeight())); //Size
		changeDataPanel.add(descriptionInput);
		descriptionInput.setText(product.getDesc());

		//Change Product Button
		JButton changeProductButton = new JButton("Alterar Produto");
		changeProductButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		changeProductButton.addActionListener(new ActionListener() {
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
					//Calls function change product to the product that is being changed passing its parameters
					boolean works = product.changeProduct(name, barcode, brand, stock, safetyStock, price, purchasePrice, color, desc);
					if(works) {
						//Close this window
						dispose();
						//Go back to the insert product window
						new InsertProduct().setVisible(true);
					}
					
				}catch(Exception er) {
			        JOptionPane.showMessageDialog(null, "Error: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);		     
			        er.printStackTrace();
				}

			}
		});
		changeProductButton.setBounds((int)(0.1833 * screenWidth), (int)(0.5491 * screenHeight),
									  (int)(0.125 * screenWidth), (int)(0.0556 * screenHeight));
		changeDataPanel.add(changeProductButton);
		
		// Adiciona KeyListener ao painel
		changeDataPanel.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            changeProductButton.doClick();
		        }
		    }
		});

		// Adiciona KeyListener a cada componente filho
		for (Component component : changeDataPanel.getComponents()) {
		    component.addKeyListener(new KeyAdapter() {
		        @Override
		        public void keyPressed(KeyEvent e) {
		            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		                changeProductButton.doClick();
		            }
		        }
		    });
		}
		
		//Back Button
		JButton backButton = new JButton("Voltar");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close this window
				dispose();
				//Go back to the insert product window
				new InsertProduct().setVisible(true);
			}
		});
		backButton.setBounds((int)(0.6271 * screenWidth), (int)(0.5926 * screenHeight),
							 (int)(0.1146 * screenWidth), (int)(0.0556 * screenHeight));
		contentPane.add(backButton);
		
		//Change Product Title
		JLabel changeTitleLabel = new JLabel("Alterar Produto");
		changeTitleLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 22));
		changeTitleLabel.setBounds((int)(0.638 * screenWidth), (int)(0.0102 * screenHeight),
								   (int)(0.1094 * screenWidth), (int)(0.0333 * screenHeight));
		contentPane.add(changeTitleLabel);
		
		//Logo panel
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds((int)(0.5573 * screenWidth), (int)(0.0861 * screenHeight),
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
	
	public static String convertEmptyToNull(String input) {
	    return (input == null || input.trim().isEmpty()) ? null : input;
	}
}
