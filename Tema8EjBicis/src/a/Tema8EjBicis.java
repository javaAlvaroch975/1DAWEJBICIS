package tema8Alquiler;

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
import javax.swing.table.TableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ConnectionSingleton {
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
	private JTextField textFieldUsuario;
	private JTextField textFieldBici;

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

		DefaultTableModel modelUsuario = new DefaultTableModel();
		modelUsuario.addColumn("ID_usuario");
		modelUsuario.addColumn("Nombre");
		modelUsuario.addColumn("Edad");
		modelUsuario.addColumn("Cuenta_Bancaria");

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Usuarios");
			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getString("ID_usuario");
				row[1] = rs.getString("Nombre");
				row[2] = rs.getInt("Edad");
				row[3] = rs.getString("Cuenta_Bancaria");

				modelUsuario.addRow(row);
			}
		} catch (SQLException ex) {
		}

		JTable table_Usuarios = new JTable(modelUsuario);
		table_Usuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice =table_Usuarios.getSelectedRow();
				TableModel modelo = table_Usuarios.getModel();
				
				textFieldUsuario.setText(String.valueOf(modelo.getValueAt(indice, 0))); //Coger un campo que queremos

			}

		});
		table_Usuarios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_Usuarios.setBounds(71, 56, 146, 74);
		JScrollPane scrollPane = new JScrollPane(table_Usuarios);
		scrollPane.setBounds(76, 112, 367, 188);
		frame.getContentPane().add(scrollPane);

		// Tabla2
		DefaultTableModel modelBicis = new DefaultTableModel();
		modelBicis.addColumn("ID_Bici");
		modelBicis.addColumn("Usuario_Asignado");

		try {
			Connection con1 = ConnectionSingleton.getConnection();
			Statement stmt1 = con1.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * FROM Bicis  ORDER BY ID_Bici");
			while (rs1.next()) { //Recorrer los campos y rellenar sus valores
				Object[] row1 = new Object[2];
				row1[0] = rs1.getInt("ID_Bici");
				row1[1] = rs1.getInt("Usuario_Asignado");

				modelBicis.addRow(row1);
			}
		}

		catch (SQLException ex) {
		}

		JTable table_Bicis = new JTable(modelBicis);
		table_Bicis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice =table_Bicis.getSelectedRow();
				TableModel modelo = table_Bicis.getModel();
				
				textFieldBici.setText(String.valueOf(modelo.getValueAt(indice, 0))); //Coger con el cick un campo que queremos
				
			}
		});
		table_Bicis.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_Bicis.setBounds(71, 56, 146, 74);
		JScrollPane scrollPane1 = new JScrollPane(table_Bicis);
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
						Connection con = ConnectionSingleton.getConnection();
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
					Connection con = ConnectionSingleton.getConnection();
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
				
				int codp=table_Bicis.getSelectedRow();
				TableModel model= table_Bicis.getModel();
				
				if ( (int) model.getValueAt(codp, 1) != 0) {
					JOptionPane.showMessageDialog(frame, "No puedes alquilar una bici que ya está alquilada","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else {
					String usuario=textFieldUsuario.getText();
					String bici=textFieldBici.getText();
					
			
					try {
						Connection con =ConnectionSingleton.getConnection();
						PreparedStatement comprobante = con.prepareStatement("SELECT COUNT(Usuario_Asignado) AS cuenta FROM Bicis WHERE Usuario_Asignado=?");	//Nota mental, asegurate de que el código es el del USUARIO
						comprobante.setInt(1, Integer.parseInt(usuario));
						ResultSet comprobar = comprobante.executeQuery();
				
						comprobar.next();

						if (comprobar.getInt("cuenta")!=0) {	//Tienes que asegurarte de hacer el next de antemano. Y los alias tienen que hacerse bien
							JOptionPane.showMessageDialog(frame, "No puedes alquilar más de una bicicleta","Advertencia",JOptionPane.ERROR_MESSAGE);
						}else {
						
						PreparedStatement alquiler = con.prepareStatement("UPDATE Bicis SET Usuario_Asignado=? WHERE ID_Bici=?");  //Recorrer los campos y rellenar sus valores
						alquiler.setString(1, usuario);
						alquiler.setString(2, bici);
						alquiler.executeUpdate();
						alquiler.close();
						JOptionPane.showMessageDialog(frame, "Se ha alquilado la bicicleta");
						Statement muestrabici = con.createStatement();
						ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM Bicis ORDER BY ID_Bici");
						modelBicis.setRowCount(0);
						while(visualizarbici.next()) {
							Object[] row = new Object [2];
							row[0] = visualizarbici.getInt("ID_Bici");
							row[1] = visualizarbici.getInt("Usuario_Asignado");
							modelBicis.addRow(row);	
						}
						textFieldUsuario.setText("");
						textFieldBici.setText("");
						con.close();
						}
						
						comprobante.close();
						comprobar.close();
					}catch (SQLException ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
					}
			}
			}
		
		});
		btnNewAlquilar.setBounds(164, 24, 154, 25);
		frame.getContentPane().add(btnNewAlquilar);
		btnAñadirBici.setBounds(578, 60, 155, 25);
		frame.getContentPane().add(btnAñadirBici);
		btnAadirUsuario.setBounds(161, 60, 165, 25);
		frame.getContentPane().add(btnAadirUsuario);
		
		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codp=table_Bicis.getSelectedRow(); //Seleccionar elemento de la tabla
				TableModel model= table_Bicis.getModel();
				
				if ( (int) modelBicis.getValueAt(codp, 1) == 0) {
					JOptionPane.showMessageDialog(frame, "No puedes devolver una bici que no está alquilada","Advertencia",JOptionPane.ERROR_MESSAGE);
				}else {
					String bici=textFieldBici.getText();
					
					try {
						Connection con =ConnectionSingleton.getConnection();
						PreparedStatement devolver = con.prepareStatement("UPDATE Bicis  SET Usuario_Asignado=0 WHERE ID_Bici=?");
						devolver.setString(1, bici);
						devolver.executeUpdate();
						devolver.close();
						JOptionPane.showMessageDialog(frame, "Se ha devuelto la bicicleta");
						Statement muestrabici = con.createStatement();
						ResultSet visualizarbici=muestrabici.executeQuery("SELECT * FROM Bicis ORDER BY ID_Bici");
						modelBicis.setRowCount(0);
						while(visualizarbici.next()) {
							Object[] row = new Object [2];
							row[0] = visualizarbici.getInt("ID_Bici");
							row[1] = visualizarbici.getInt("Usuario_Asignado");
							modelBicis.addRow(row);	
						}
						textFieldBici.setText("");
						con.close();
					}catch (SQLException ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnDevolver.setBounds(578, 25, 155, 23);
		frame.getContentPane().add(btnDevolver);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(467, 331, 86, 20);
		frame.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		textFieldBici = new JTextField();
		textFieldBici.setBounds(588, 331, 86, 20);
		frame.getContentPane().add(textFieldBici);
		textFieldBici.setColumns(10);

		lblNombre.setVisible(false);
		lblEdad.setVisible(false);
		lblCuentaBancaria.setVisible(false);
		textField_Name.setVisible(false);
		textField_Edad.setVisible(false);
		textField_CB.setVisible(false);
		btnComprobarYFinalizar.setVisible(false);

		
	
	}
}
