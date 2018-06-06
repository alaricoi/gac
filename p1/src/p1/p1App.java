package p1;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class p1App {

	private JFrame frmPrcticaGac;
	private JTextField tOrigen;
	private JTextField tSalida;
	private JTextArea tAreaOrigen;
	private JTextArea tAreaDestino;
	private JButton btnSeleccionaXmlSalida;
	private JButton bCrearXML;
	private JButton bFileOrigen;
	// Elemento de log4j para el control de loger de la aplciación
	private static final Logger logger = LogManager.getLogger(p1App.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					p1App window = new p1App();
					window.frmPrcticaGac.setVisible(true);
				} catch (Exception e) {
					// e.printStackTrace();
					logger.error(e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public p1App() {
		logger.info("Entrada a la aplicación");
		initialize();
	}

	/**
	 * Al crear la clase se llama a este método que nos va a servir para crear
	 * los objetos visuales, colocarlos en la pantalla y asignarles eventos
	 */
	private void initialize() {
		/* Creación del frame principal y asignación de propiedades */
		frmPrcticaGac = new JFrame();
		frmPrcticaGac.setResizable(false);

		frmPrcticaGac.setTitle("Práctica 1 G.A.C.");
		frmPrcticaGac.setBounds(100, 100, 627, 472);
		frmPrcticaGac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Creación del botón de crear xml a partir de un JSON */
		bCrearXML = new JButton("Crear XML");
		/*
		 * Inicialmente esta desabilitado, solo se activa si las casillas de
		 * origen i destino esta rellenas
		 */
		bCrearXML.setEnabled(false);

		/* Creación del boton de selección de fichero JSON */
		bFileOrigen = new JButton("Selecciona json Origen");

		/* Asignamos la acción de seleccionar fichero de origen Json */
		bFileOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abreDialogoJson();
			}

		});
		/* Creación de la casilla de texto para el fichero JSON de Origen */
		tOrigen = new JTextField();
		tOrigen.setHorizontalAlignment(SwingConstants.LEFT);
		tOrigen.setEditable(false);
		tOrigen.setColumns(30);
		/* Asignamos la acción que nos permite activar el boton de generar XML */
		tOrigen.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				activaCrearXML();
			}
		});

		/* Creación del botón de selección de fichero de Salida XML */
		btnSeleccionaXmlSalida = new JButton("Selecciona xml Salida");
		btnSeleccionaXmlSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionXMLDestino();
			}

		});
		/* Creación de la casilla de texto para el fichero XML de Salida */
		tSalida = new JTextField();
		tSalida.setHorizontalAlignment(SwingConstants.LEFT);
		tSalida.setEditable(false);
		tSalida.setColumns(30);

		/* Asignamos la acción que nos permite activar el boton de generar XML */
		tSalida.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				activaCrearXML();
			}
		});
		bCrearXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearXML();

			}
		});

		/* Cuadro de texto donde se muestra el contenido de origen JSON */
		JScrollPane scrollPane = new JScrollPane();
		tAreaOrigen = new JTextArea();
		scrollPane.setViewportView(tAreaOrigen);
		tAreaOrigen.setWrapStyleWord(true);
		tAreaOrigen.setLineWrap(true);
		tAreaOrigen.setEditable(false);

		/* Cuadro de texto donde se muestra el contenido de salida XML */
		JScrollPane scrollPane_1 = new JScrollPane();
		tAreaDestino = new JTextArea();
		scrollPane_1.setViewportView(tAreaDestino);

		tAreaDestino.setWrapStyleWord(true);
		tAreaDestino.setLineWrap(true);
		tAreaDestino.setEditable(false);

		/* Creación del layaut */

		JLabel lblJsonOrigen = new JLabel("JSON Origen");

		JLabel lblXmlDestino = new JLabel("XML Destino");
		GroupLayout groupLayout = new GroupLayout(
				frmPrcticaGac.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(tOrigen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(bFileOrigen, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(bCrearXML))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(7)
								.addComponent(tSalida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(30)
								.addComponent(btnSeleccionaXmlSalida, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(28)
										.addComponent(lblJsonOrigen)
										.addGap(50))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(18)
										.addComponent(scrollPane)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblXmlDestino)
									.addComponent(scrollPane_1)))))
					.addGap(30))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(tOrigen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(bFileOrigen))
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(tSalida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSeleccionaXmlSalida))
					.addGap(4)
					.addComponent(bCrearXML)
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblJsonOrigen)
						.addComponent(lblXmlDestino))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
					.addGap(33))
		);
		/*
		 * asignamos al frame el layaut generado con los componentes visuales ya
		 * colacados
		 */
		frmPrcticaGac.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Habilita o deshabilita el botón de creación de XML dependiendo si estan
	 * seleccionados tanto la entrada como la salida
	 */
	private void activaCrearXML() {

		if (!tOrigen.getText().equals("") && !tSalida.getText().equals(""))
			bCrearXML.setEnabled(true);
		else
			bCrearXML.setEnabled(false);
	}

	/**
	 * Abre un dialogo de selección de fichero y guarda el path seleccionado en
	 * la caja de texto de origen
	 */
	private void abreDialogoJson() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser
				.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int result = fileChooser.showOpenDialog(frmPrcticaGac);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			tOrigen.setText(selectedFile.getAbsolutePath());
		}
	}

	/**
	 * Abre un dialogo de selección de fichero a guardar y guarda el path
	 * seleccionado en la caja de texto de destino
	 */
	private void seleccionXMLDestino() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser
				.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int result = fileChooser.showSaveDialog(frmPrcticaGac);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			tSalida.setText(selectedFile.getAbsolutePath());
		}
	}

	/**
	 * Metodo que crea el fichero XML a partir del JSON seleccioando, el fichero
	 * creado se gurada en la ruta seleccionada en la caja de texto tSalida
	 * 
	 */
	private void crearXML() {
		frmPrcticaGac.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String contents = "";
		try {
			try {
				/* Leemos el fichero de origen y lo guardamos en un String */
				contents = new String(Files.readAllBytes(Paths.get(tOrigen
						.getText())));
				/* Mostramos en pantalla el contenido del fichero seleccionado */
				tAreaOrigen.setText(contents);
			} catch (IOException e1) {
				// Excepción porque ha habido un problema en la lectura del
				// fichero
				JOptionPane
						.showMessageDialog(
								frmPrcticaGac,
								"No es posible leer el fichero seleccionado de origen JSON",
								"Error de Lectura I/O",
								JOptionPane.ERROR_MESSAGE);

				logger.error(e1);
			}

			try {
				/*
				 * Cargamos el contenido del fichero JSON en un OBJETO
				 * JSONObject
				 */
				JSONObject o = new JSONObject(contents);

				/* Parseamos el objeto JSONObject en un String con formato XML */
				String xml = XML.toString(o);

				/* Se muestra el XML generado */
				tAreaDestino.setText(xml);

				/* Se guarda el fichero generado */
				Files.write(Paths.get(tSalida.getText()), xml.getBytes());
				JOptionPane.showMessageDialog(frmPrcticaGac,
						"Fichero xml creado correctamente.");

			} catch (JSONException e1) {
				JOptionPane.showMessageDialog(frmPrcticaGac,
						"El fichero de entrada no cumple con formato JSON",
						"JSON error", JOptionPane.ERROR_MESSAGE);

				logger.error(e1);
			} catch (IOException e1) {
				// Excepción porque ha habido un problema en la excritura del
				// fichero
				JOptionPane
						.showMessageDialog(
								frmPrcticaGac,
								"No es posible escribir el fichero seleccionado de salida XML",
								"Error de escritura I/O",
								JOptionPane.ERROR_MESSAGE);

				logger.error(e1);
			}

		} finally {
			frmPrcticaGac.setCursor(Cursor.getDefaultCursor());
		}
	}
}
