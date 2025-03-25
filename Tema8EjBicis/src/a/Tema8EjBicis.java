package a;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

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
	private JTextField textField_Name;
	private JTextField textField_Edad;
	private JTextField textField_CB;

	boolean comprobarExpReg(String cadena, String patron) {
		Pattern pat = Pattern.compile(patron);
		Matcher mat = pat.matcher(cadena);

		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

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
		} catch (SQLException ex) {
		}

		JTable table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBounds(71, 56, 146, 74);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(76, 112, 367, 188);
		frame.getContentPane().add(scrollPane);

		// Tabla2
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

		textField_Name = new JTextField();
		textField_Name.setBounds(164, 332, 114, 19);
		frame.getContentPane().add(textField_Name);
		textField_Name.setColumns(10);

		textField_Edad = new JTextField();
		textField_Edad.setBounds(164, 364, 114, 19);
		frame.getContentPane().add(textField_Edad);
		textField_Edad.setColumns(10);

		textField_CB = new JTextField();
		textField_CB.setBounds(212, 391, 114, 19);
		frame.getContentPane().add(textField_CB);
		textField_CB.setColumns(10);

		JButton btnComprobarYFinalizar = new JButton("Comprobar y finalizar");
		btnComprobarYFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_Name.getText().length() == 0) {
					JOptionPane.showMessageDialog(frame, "El nombre está vacío", "Advertencia",
							JOptionPane.ERROR_MESSAGE);
				} else if (textField_Edad.getText().length() == 0) {
					JOptionPane.showMessageDialog(frame, "La edad está vacía", "Advertencia",
							JOptionPane.ERROR_MESSAGE);
				} else if (textField_CB.getText().length() == 0) {
					JOptionPane.showMessageDialog(frame, "El número de cuenta está vacío", "Advertencia",
							JOptionPane.ERROR_MESSAGE);
				}
				if (!comprobarExpReg(textField_Name.getText(), "^[a-zA-z]+$")) {
					JOptionPane.showMessageDialog(frame, "El nombre debe contener solo letras", "Advertencia",
							JOptionPane.ERROR_MESSAGE);
				} else if (!comprobarExpReg(textField_Edad.getText(), "^\\d{1,3}$")) {
					JOptionPane.showMessageDialog(frame, "La edad debe ser un número entero de hasta 3 dígitos",
							"Advertencia", JOptionPane.ERROR_MESSAGE);
				} else if (!comprobarExpReg(textField_CB.getText(), "^\\d{1,12}$")) {
					JOptionPane.showMessageDialog(frame,
							"La cuenta bancaria debe ser un número entero de hasta 12 dígitos", "Advertencia",
							JOptionPane.ERROR_MESSAGE);
				} else {
					lblNombre.setVisible(false);
					lblEdad.setVisible(false);
					lblCuentaBancaria.setVisible(false);
					textField_Name.setVisible(false);
					textField_Edad.setVisible(false);
					textField_CB.setVisible(false);
					btnComprobarYFinalizar.setVisible(false);

					try {
						// Añadir datos usuario
						Connection con = ConnectionSingleTon.getConnection();
						PreparedStatement insUs = con
								.prepareStatement("INSERT INTO Usuarios (Nombre,Edad,Cuenta_Bancaria) VALUES (?,?,?)");
						String nombre = textField_Name.getText();
						String edad = textField_Edad.getText();
						String cb = textField_CB.getText();
						insUs.setString(1, nombre);
						insUs.setString(2, edad);
						insUs.setString(3, cb);
						insUs.executeUpdate();
						JOptionPane.showMessageDialog(frame, "Se ha añadido el producto");
						con.close();
						insUs.close();
					} catch (SQLException erra) {
						erra.printStackTrace();
						erra.getMessage();
						erra.getSQLState();
						JOptionPane.showMessageDialog(frame, erra.getMessage(), "Advertencia",
								JOptionPane.ERROR_MESSAGE);
					}	
				}
				}
			});
		btnComprobarYFinalizar.setBounds(76, 427, 194, 25);
		frame.getContentPane().add(btnComprobarYFinalizar);

		JButton btnAadirUsuario = new JButton("Añadir Usuario");
		btnAadirUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNombre.setVisible(true);
				lblEdad.setVisible(true);
				lblCuentaBancaria.setVisible(true);
				textField_Name.setVisible(true);
				textField_Edad.setVisible(true);
				textField_CB.setVisible(true);
				btnComprobarYFinalizar.setVisible(true);
			}
		});

		JButton btnAñadirBici = new JButton("Añadir Bici");
		btnAñadirBici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionSingleTon.getConnection();
					PreparedStatement insPstmt = con.prepareStatement("INSERT INTO Bicis VALUES (null, 0)");
					insPstmt.executeUpdate();
					insPstmt.close();
					JOptionPane.showMessageDialog(frame, "Se ha añadido el producto");
					con.close();
					insPstmt.close();
				} catch (SQLException erra) {
					erra.printStackTrace();
					erra.getMessage();
					erra.getSQLState();
					JOptionPane.showMessageDialog(frame, erra.getMessage(), "Advertencia", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnNewAlquilar = new JButton("Alquilar");
		btnNewAlquilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewAlquilar.setBounds(371, 27, 154, 25);
		frame.getContentPane().add(btnNewAlquilar);
		btnAñadirBici.setBounds(578, 60, 155, 25);
		frame.getContentPane().add(btnAñadirBici);
		btnAadirUsuario.setBounds(161, 60, 165, 25);
		frame.getContentPane().add(btnAadirUsuario);

		lblNombre.setVisible(false);
		lblEdad.setVisible(false);
		lblCuentaBancaria.setVisible(false);
		textField_Name.setVisible(false);
		textField_Edad.setVisible(false);
		textField_CB.setVisible(false);
		btnComprobarYFinalizar.setVisible(false);

		
	}

}
