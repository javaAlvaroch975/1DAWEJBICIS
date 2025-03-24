package a;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

class ConnectionSingleTon {
	private static Connection con;

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3307/EJ_BIcis";
		String user = "alumno";
		String password = "alumno";

		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, user, password);
		}
		return con;
	}
}

public class Tema8EjBicis {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tema8EjBicis window = new Tema8EjBicis();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tema8EjBicis() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 884, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnAadirUsuario = new JButton("Añadir Usuario");
		btnAadirUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAadirUsuario.setBounds(172, 60, 154, 25);
		frame.getContentPane().add(btnAadirUsuario);

		JButton btnAñadirBici = new JButton("Añadir Bici");
		btnAñadirBici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAñadirBici.setBounds(578, 60, 155, 25);
		frame.getContentPane().add(btnAñadirBici);

		JButton btnNewAlquilar = new JButton("Alquilar");
		btnNewAlquilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewAlquilar.setBounds(371, 27, 154, 25);
		frame.getContentPane().add(btnNewAlquilar);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID_usuario");
		model.addColumn("Nombre");
		model.addColumn("Edad");
		model.addColumn("Cuenta_Bancaria");

		try {
			Connection con = ConnectionSingleTon.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuarios");
			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getString("ID_usuario");
				row[1] = rs.getString("Nombre");
				row[2] = rs.getInt("Edad");
				row[3] = rs.getString("Cuenta_Bancaria");

				model.addRow(row);
			}
		}

		catch (SQLException ex) {
		}

		JTable table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBounds(71, 56, 146, 74);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(76, 112, 367, 188);
		frame.getContentPane().add(scrollPane);
		
		//Tabla2
		DefaultTableModel model2 = new DefaultTableModel();
		model2.addColumn("ID_Bici");
		model2.addColumn("Usuario_Asignado");

		try {
			Connection con1 = ConnectionSingleTon.getConnection();
			Statement stmt1 = con1.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Bicis");
			while (rs1.next()) {
				Object[] row1 = new Object[2];
				row1[0] = rs1.getInt("ID_Bici");
				row1[1] = rs1.getInt("Usuario_Asignado");


				model2.addRow(row1);
			}
		}

		catch (SQLException ex) {
		}

		JTable table1 = new JTable(model2);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table1.setBounds(71, 56, 146, 74);
		JScrollPane scrollPane1 = new JScrollPane(table1);
		scrollPane1.setBounds(467, 112, 367, 188);
		frame.getContentPane().add(scrollPane1);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(76, 334, 70, 15);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(76, 366, 70, 15);
		frame.getContentPane().add(lblEdad);
		
		JLabel lblCuentaBancaria = new JLabel("Cuenta Bancaria:");
		lblCuentaBancaria.setBounds(76, 393, 125, 15);
		frame.getContentPane().add(lblCuentaBancaria);
		
		textField = new JTextField();
		textField.setBounds(164, 332, 114, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(164, 364, 114, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(212, 391, 114, 19);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
	}

}
