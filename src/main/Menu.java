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

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	static Connection connection;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//if can not connect to database dont initialize the application
		if(!initializeDatabase()) {
			JOptionPane.showMessageDialog(null, "Não foi possível se conectar ao banco de dados");
			System.exit(1);
		};
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		//Title
		setTitle("Tricotando");
		//Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(InsertProduct.class.getResource("/tricotando/img/tricotandoLogo.png")));
		//Closes aplication
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //Set percentage of screen to be used by the window
        int width = (int) (screenWidth * 1.0); // 100% of screen widht
        int height = (int) (screenHeight * 0.963); // 96.3% of screen height
        this.setSize(width, height);
        //Center window
        setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Logo label
		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds((int) (screenWidth * 0.4349), (int) (screenHeight * 0.0083), 
    						(int) (screenWidth * 0.1188), (int) (screenHeight * 0.2130));
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(Menu.class.getResource("/tricotando/img/tricotandoLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image scaledImage = originalImage.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH);
		logoLabel.setIcon(new ImageIcon(scaledImage));
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(logoLabel);
		
		//Panel options
		JPanel optionPanel = new JPanel();
		optionPanel.setBounds((int) (screenWidth * 0.0625), (int) (screenHeight * 0.2315), 
                			  (int) (screenWidth * 0.8750), (int) (screenHeight * 0.6204));
		contentPane.add(optionPanel);
		
		//Label options
		JLabel optionsLabel = new JLabel("Escolha sua opção:");
		optionsLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionsLabel.setBounds((int) (screenWidth * 0.3438), (int) (screenHeight * 0.1917), 
                			   (int) (screenWidth * 0.1901), (int) (screenHeight * 0.0926));
		optionPanel.add(optionsLabel);
		
		//Insert product button
		JButton insertButton = new JButton("Inserir Produto");
		insertButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		insertButton.setBounds((int) (screenWidth * 0.1604), (int) (screenHeight * 0.3694), 
                			   (int) (screenWidth * 0.1667), (int) (screenHeight * 0.1204));
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertProduct insert = new InsertProduct();
				insert.setVisible(true);
			}
		});
		optionPanel.setLayout(null);
		optionPanel.add(insertButton);
		
		//Sale order button
		JButton saleButton = new JButton("Saída Produto");
		saleButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saleButton.setBounds((int) (screenWidth * 0.5469), (int) (screenHeight * 0.3694), 
                			 (int) (screenWidth * 0.1667), (int) (screenHeight * 0.1204));
		saleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sale sale = new Sale();
				SaleOrder saleOrder= new SaleOrder(sale);
				saleOrder.setVisible(true);
			}
		});
		optionPanel.add(saleButton);
		
		//Return reports button
		JButton reportButton = new JButton("Gerar Relatório");
		reportButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reportButton.setBounds((int) (screenWidth * 0.3547), (int) (screenHeight * 0.3694), 
                			   (int) (screenWidth * 0.1667), (int) (screenHeight * 0.1204));
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Report report = new Report();
				report.setVisible(true);
			}
		});
		optionPanel.add(reportButton);
		
		//Credits label
		JLabel creditsLabel = new JLabel("Developed by github.com/AlcantaroBiel");
		creditsLabel.setForeground(new Color(128, 128, 128));
		creditsLabel.setBounds(1570, 928, 
                			   260, 14);
		contentPane.add(creditsLabel);
		

	}
	//Initialize db function
	private static boolean initializeDatabase() {
		try {
			connection = databaseManager.connect();
			System.out.println("Conexão com o banco bem sucedida");
			return true;
		}catch(SQLException e){
			System.out.println("ERRO: " + e.getMessage());
			e.printStackTrace();
			
			String msg = "ERRO: " + e.getMessage();
			JOptionPane optionPane = new JOptionPane();
	        optionPane.setMessage(msg);
	        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
	        JDialog dialog = optionPane.createDialog(null, "Tricotando");
	        dialog.setVisible(true);
			return false;
		}
	}
	
	public static Connection getConnection() { 
		try{
			if(connection == null || connection.isClosed()) {
				connection = databaseManager.connect();
				}
			} catch (SQLException e) {
		        System.out.println("Erro ao verificar a conexão: " + e.getMessage());
			}
		   return connection;
	}
}
