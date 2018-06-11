package t2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
/**
 * Clase que utilizamos para la mantener los datos de conexión 
 * capturados del interfaz de usuarios
 * @author Isma
 *
 */
class ConexionBean {
	/**
	 * la clase del driver o connector que podemos emplear
	 */
	private String driver;
	/**
	 * cadena de conexión al SGDB
	 */
	private String cadena;
	/**
	 * usurio de conexion
	 */
	private String usu;
	/**
	 * contraseña de conexión
	 */
	private String pass;
	/**
	 * dependiendo del SGDB se debe componer la cadena de conexion
	 * con este valo abstraemos al usuario de saber que poner
	 */
	private String sufijo;

	// Metodos getter y setter de la clase
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = usu;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSufijo() {
		return sufijo;
	}

	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

	/**
	 * constructor con todos los atributos del objeto
	 * @param driver
	 * @param cadena
	 * @param usu
	 * @param pass
	 * @param sufijo
	 */
	public ConexionBean(String driver, String cadena, String usu, String pass, String sufijo) {
		super();
		this.driver = driver;
		this.cadena = cadena;
		this.usu = usu;
		this.pass = pass;
		this.sufijo = sufijo;
	}
}
/**
 * Clase principal que carga la pantalla y tiene los métodos y acciones
 * necesarias del trabajo
 * @author Isma
 *
 */
public class t2App {

	protected Shell shlGacPrctica;
	private Text tCadenaConex;
	private Text tUsu;
	private Text tPass;
	private Text tSalida;
	private Combo comboTablas;
	// Elemento de log4j para el control de loger de la aplciación
	private static final Logger logger = LogManager.getLogger(t2App.class);
	private Text tWhere;
	private Combo comboTipo;
	private ConexionBean conexionBean;
	private Combo comboFormatSalida;
	private Label lblSalida;

	/**
	 * Arranque de la aplicación
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			t2App window = new t2App();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shlGacPrctica.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shlGacPrctica.setLocation (x, y);
		shlGacPrctica.open();
		shlGacPrctica.layout();
		while (!shlGacPrctica.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {

		shlGacPrctica = new Shell();
		shlGacPrctica.setImage(SWTResourceManager.getImage("img/th.jpg"));
		shlGacPrctica.setSize(616, 503);
		shlGacPrctica.setText("G.A.C. Pr\u00E1ctica 2");
		shlGacPrctica.setLayout(new FillLayout(SWT.VERTICAL));

		Composite composite_1 = new Composite(shlGacPrctica, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpSeleccionDeTipo = new Group(composite_1, SWT.NONE);
		grpSeleccionDeTipo.setText("Selecci\u00F3n base de datos");
		grpSeleccionDeTipo.setLayout(new FillLayout(SWT.VERTICAL));

		Composite composite = new Composite(grpSeleccionDeTipo, SWT.NONE);
		GridLayout gl_composite = new GridLayout(2, false);
		composite.setLayout(gl_composite);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Tipo ");

		comboTipo = new Combo(composite, SWT.NONE);
		comboTipo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboTipo.setItems(new String[] { "SQLite", "MySQL" });
		comboTipo.select(0);

		Label lblCadenaDeConesn = new Label(composite, SWT.NONE);
		lblCadenaDeConesn.setText("Cadena de Conexi\u00F3n");

		tCadenaConex = new Text(composite, SWT.BORDER);
		tCadenaConex.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblUsuario = new Label(composite, SWT.NONE);
		lblUsuario.setText("Usuario");

		tUsu = new Text(composite, SWT.BORDER);
		tUsu.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblContrasea = new Label(composite, SWT.NONE);
		lblContrasea.setText("Contrase\u00F1a");
	
		tPass = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		tPass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnConectar = new Button(composite, SWT.NONE);
		btnConectar.addSelectionListener(new SelectionAdapter() {
			/**
			 * Boton de conexxión de datos.
			 * Se llama al metodo rellenaComboTablas que crea la conexion
			 * carga el combo con las tablas 
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblSalida.setText("");
				//cambiamos el cursor de pantalla al de espera
				Cursor waitCursor = new Cursor(shlGacPrctica.getDisplay(), SWT.CURSOR_WAIT);
				try {
					shlGacPrctica.setCursor(waitCursor);
					rellenaComboTablas();
				} finally {
					// volvemos a poner el cursor por defecto
					shlGacPrctica.setCursor(null);
					waitCursor.dispose();
				}
			}

		});
		btnConectar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnConectar.setText("Conectar");
		new Label(composite, SWT.NONE);

		Group grpSeleccionaLaTabla = new Group(composite_1, SWT.NONE);
		grpSeleccionaLaTabla.setText("Selecciona la tabla");
		grpSeleccionaLaTabla.setLayout(new GridLayout(2, false));

		Label lblTabla = new Label(grpSeleccionaLaTabla, SWT.NONE);
		lblTabla.setText("Tabla");

		comboTablas = new Combo(grpSeleccionaLaTabla, SWT.NONE);
		comboTablas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblWhere = new Label(grpSeleccionaLaTabla, SWT.NONE);
		lblWhere.setText("where");

		tWhere = new Text(grpSeleccionaLaTabla,
				SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_tWhere = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_tWhere.heightHint = 49;
		tWhere.setLayoutData(gd_tWhere);

		Label lblFormatoDeSalida = new Label(grpSeleccionaLaTabla, SWT.WRAP);
		GridData gd_lblFormatoDeSalida = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblFormatoDeSalida.widthHint = 54;
		lblFormatoDeSalida.setLayoutData(gd_lblFormatoDeSalida);
		lblFormatoDeSalida.setText("Formato de salida");

		comboFormatSalida = new Combo(grpSeleccionaLaTabla, SWT.NONE);
		comboFormatSalida.setItems(new String[] { "CSV", "HTML", "XML", "JSON" });
		comboFormatSalida.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboFormatSalida.select(0);

		Button btnGenerarSalida = new Button(grpSeleccionaLaTabla, SWT.NONE);
		btnGenerarSalida.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnGenerarSalida.addSelectionListener(new SelectionAdapter() {
			/**
			 * evento del boton de generar la salida
			 * llama a ejecutarConsulta con los datos de la tabla y el
			 * texto del where
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Cursor waitCursor = new Cursor(shlGacPrctica.getDisplay(), SWT.CURSOR_WAIT);
				try {
					shlGacPrctica.setCursor(waitCursor);
					if (ejecutaConsulta(comboTablas.getText(), tWhere.getText())){
						muestraDialogoModal(SWT.ICON_INFORMATION | SWT.OK,
								"Información",
								"Creada la salida de " + comboTablas.getText());
					
				}
				} finally {
					shlGacPrctica.setCursor(null);
					waitCursor.dispose();
				}

			}
		});
		btnGenerarSalida.setText("Generar");
		new Label(grpSeleccionaLaTabla, SWT.NONE);
		new Label(grpSeleccionaLaTabla, SWT.NONE);
		new Label(grpSeleccionaLaTabla, SWT.NONE);

		Group grpSalida = new Group(shlGacPrctica, SWT.NONE);
		grpSalida.setText("Salida");
		grpSalida.setLayout(new FormLayout());

		Button btnGuardar = new Button(grpSalida, SWT.NONE);
		btnGuardar.addSelectionListener(new SelectionAdapter() {
			/**
			 * Guarda la salida en un fichero
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				String tabla = lblSalida.getText().trim();
				// control de que haya generado la salida
				if (tabla == null || tabla.equals("")) {
					muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha creado todavia la salida");
					
					return;
				}
				// dialogo para seleccionar la salida de fichero
				FileDialog dlg = new FileDialog(shlGacPrctica, SWT.SAVE);
				dlg.setFileName(lblSalida.getText());
				dlg.setOverwrite(true);
				String fileName = dlg.open();
				// guardamos el fichero si se selecciono el destino
				if (fileName != null && !fileName.equals("")) {
					try {
						Files.write(Paths.get(fileName), tSalida.getText().getBytes());
					} catch (IOException e1) {
						muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
								"No se ha podido crearel fichero: " + e1.getMessage());
						
						logger.error(e1);
					}
				}
			}
		});
		FormData fd_btnGuardar = new FormData();
		fd_btnGuardar.top = new FormAttachment(0);
		fd_btnGuardar.left = new FormAttachment(100, -143);
		btnGuardar.setLayoutData(fd_btnGuardar);
		btnGuardar.setText("Guardar fichero");

		tSalida = new Text(grpSalida, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		fd_btnGuardar.right = new FormAttachment(tSalida, 0, SWT.RIGHT);
		tSalida.setEditable(false);
		tSalida.setEnabled(true);
		FormData fd_tSalida = new FormData();
		fd_tSalida.top = new FormAttachment(0, 29);
		fd_tSalida.bottom = new FormAttachment(100, -59);
		fd_tSalida.left = new FormAttachment(0, 10);
		fd_tSalida.right = new FormAttachment(100, -16);
		tSalida.setLayoutData(fd_tSalida);

		lblSalida = new Label(grpSalida, SWT.NONE);
		lblSalida.setText("\t\t\t\t\t\t");
		lblSalida.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		FormData fd_lblSalida = new FormData();
		fd_lblSalida.top = new FormAttachment(btnGuardar, 5, SWT.TOP);
		fd_lblSalida.left = new FormAttachment(tSalida, 0, SWT.LEFT);
		lblSalida.setLayoutData(fd_lblSalida);

	}

	/**
	 * Crea una conexxión jdbc a partir de los parametros pasados por parametro
	 * 
	 * @param tipo
	 *            valores: 0--> MySql 1--> SQLite
	 * @param conexion:
	 *            cadena de conexión, en caso de SQLlite será la ruta del fichero db
	 *            en caso de que sea Mysql debemos indicar el host:puerto/esquema
	 * @param usu:
	 *            usuario de la base de datos
	 * @param pass:
	 *            contraseña de acceso
	 * @return
	 */

	private Connection dameConexion(ConexionBean con) {

		try {
            // carga del driver o conector
			Class.forName(con.getDriver());

		} catch (ClassNotFoundException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", 
					"No se ha podido crear la conexión: " + e1.getMessage());
		
			logger.error(e1);
		}
		Connection conexion = null;

		try {
			// creamos la conexion a base de datos
			conexion = DriverManager.getConnection(con.getSufijo() + con.getCadena(), con.getUsu(), con.getPass());
		} catch (SQLException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha podido crear la conexión: " + e1.getMessage());
			
			logger.error(e1);
		}
		return conexion;
	}

	/**
	 * Rellena el combo de tablas a partir de la base de datos seleccionada
	 */
	private void rellenaComboTablas() {
		// Es MySQL
		String driver = null;
		String sufijo = null;
		if (comboTipo.getSelectionIndex() == 1) {
			driver = "com.mysql.jdbc.Driver";
			sufijo = "jdbc:mysql://";
		}
		// Es SQLite
		if (comboTipo.getSelectionIndex() == 0) {
			driver = "org.sqlite.JDBC";
			sufijo = "jdbc:sqlite:";
		}
        
		// creamos el objeto conexión con los datos seleccionados
		conexionBean = new ConexionBean(driver, tCadenaConex.getText(), tUsu.getText(), tPass.getText(), sufijo);

		// conexion a bese de datos
		Connection conn = dameConexion(conexionBean);
		// Si no obtenemos conexión salimos del método
		if (conn == null) {
			return;
		}
		ResultSet rs = null;

		try {
            // extraccion del catalogo de datos del SGDB
			DatabaseMetaData metaDatos = conn.getMetaData();
			rs = metaDatos.getTables(null, null, "%", null);
			comboTablas.removeAll();
			
			while (rs.next()) {
				// El contenido de cada columna del ResultSet se puede ver
				// en la API, en el metodo getTables() de DataBaseMetaData.
				// La columna 1 es TABLE_CAT
				// y la 3 es TABLE_NAME
				// String catalogo = rs.getString(1);
				String tabla = rs.getString(3);

				comboTablas.add(tabla);

			}
			muestraDialogoModal(SWT.ICON_INFORMATION | SWT.OK, "Información", "Creada la conexión");
			comboTablas.setFocus();
			
		} catch (SQLException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", "No se ha podido crear la conexión");
			logger.error(e1);
			// liberamos los objetos de de conexion
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e1) {
				logger.error(e1);
			}
		}
	}

	/**
	 * A partir de la tabla seleccionada y la base de datos conectada se ejecuta
	 */
	private boolean ejecutaConsulta(String tabla, String where) {
		
		if (conexionBean == null) {

			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"Para realizar este paso hace falta conectarse previamente");
			
			logger.error("No hay conexión creada");
			return false;
		}
		// Obtenemos la conexion
		Connection conn = dameConexion(conexionBean);
		ResultSet rs = null;
		Statement st = null;
		try {
			// Creamos el Statement para poder hacer consultas
			st = conn.createStatement();
			String sql = "Select * from " + tabla;
			// En caso que hayamos incorporado una clausa where
			if (!where.equals(""))
				sql += " where " + where;
			rs = st.executeQuery(sql);

			switch (comboFormatSalida.getSelectionIndex()) {
            // segun el combo de salida se genera el parseado
			case 0:
				// CSV;
				tSalida.setText(rellenaCSV(rs));
				lblSalida.setText(tabla + ".csv");
				break;

			case 1:
				// HTML;
				tSalida.setText(rellenaHTML(tabla, rs));
				lblSalida.setText(tabla + ".html");
				break;
			case 2:
				// XML;
				tSalida.setText(rellenaXML(tabla, rs));
				lblSalida.setText(tabla + ".xml");
				break;
			case 3:
				// JSON
				tSalida.setText(rellenaJSON(tabla, rs));
				lblSalida.setText(tabla + ".json");
			default:

				break;

			}

		} catch (SQLException e) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"Error al ejecutar la consulta: " + e.getMessage());
			
			logger.error(e);
			return false;
			
		} finally {
			//se liberan los objetos de conexión
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e1) {
				logger.error(e1);
				return false;
			}
		}
		return true;
	}

	/**
	 * Recorre el resultset pasado como parametro y rellena un string con formato
	 * JSON, ponemos una clave raiz con valos la lista de resultados
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private String rellenaJSON(String tabla, ResultSet rs) throws SQLException {

		String resultado = "{\""+tabla + "\":[\n";
		ResultSetMetaData metadata = rs.getMetaData();
		String dato;
		while (rs.next()) {
			resultado += "{";
			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				if (rs.getString(i) != null)
					dato = rs.getString(i);
				else
					dato = "";
				resultado += "\"" + metadata.getColumnName(i) + "\":" + "\"" + dato + "\"";
				// no es el ultimo, se añade coma
				if (i < metadata.getColumnCount()) {
					resultado += ",\n";
				}

			}
			resultado += "},\n";

		}
		// hay que quitar la ultima coma
		resultado = resultado.substring(0, resultado.length() - 2);
		resultado += "\n]}";
		return resultado;
	}
/**
 * Crea la salida en xml, anidamos la marca principal con el nombre de 
 * la tabla. Cada registro ira entre la marca <fila>..</fila>
 * @param tabla
 * @param rs
 * @return
 * @throws SQLException
 */
	private String rellenaXML(String tabla, ResultSet rs) throws SQLException {

		ResultSetMetaData metadata = rs.getMetaData();
		String resultado = "<" + tabla + ">\n";
		while (rs.next()) {
			resultado += "<fila>\n";
			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				resultado += "<" + metadata.getColumnName(i) + ">";
				if (rs.getString(i) != null)
					resultado += rs.getString(i);
				resultado += "</" + metadata.getColumnName(i) + ">\n";

			}
			resultado += "</fila>\n";
		}

		resultado += "</" + tabla + ">";

		return resultado;
	}
/**
 * Genera el html a partir del resultset de la consulta realizada
 * @param tabla
 * @param rs
 * @return
 * @throws SQLException
 */
	private String rellenaHTML(String tabla, ResultSet rs) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		//caberera del fichero
		String resultado ="<html><body>";
		resultado += "<h1>" + tabla + "</h1>\n";
		//creación del objeto table
		resultado += "<table>\n";
		resultado += "<tr>\n";
       // ponemos una celda de cabecera por cada campo
		for (int i = 1; i <= metadata.getColumnCount(); i++) {
			resultado += "<th>" + metadata.getColumnName(i) + "</th>\n";
		}
		resultado += "</tr>\n";
        // por cada registro ponemos un tr y cada campo va enmarcado en celda (td)
		while (rs.next()) {
			resultado += "<tr>\n";
			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				resultado += "<td>";
				if (rs.getString(i) != null)
					resultado += rs.getString(i);
				resultado += "</td>\n";
			}
			resultado += "</tr>\n";

		}
        // cerramos la tabla y el tag de fichero
		resultado += "</table>\n";
		 resultado +="</body></html>";
		return resultado;
	}
/**
 * Metodo que genera la salida csv, el separador de campo es el ;
 * @param rs
 * @return
 * @throws SQLException
 */
	private String rellenaCSV(ResultSet rs) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		String resultado = "";
		for (int i = 1; i <= metadata.getColumnCount(); i++) {
			resultado += metadata.getColumnName(i);
			if (i < metadata.getColumnCount())
				resultado += ";";
		}
		resultado += "\n";
		while (rs.next()) {

			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				if (rs.getString(i) != null)
					resultado += rs.getString(i);
				if (i < metadata.getColumnCount())
					resultado += ";";
			}
			resultado += "\n";

		}

		return resultado;
	}
	/**
	 * metodo generico de crear un mesaje de pantalla.
	 * @param estilo
	 * @param titulo
	 * @param texto
	 */
	private void muestraDialogoModal(int estilo, String titulo, String texto) {
		MessageBox messageBox = new MessageBox(shlGacPrctica, estilo);
		messageBox.setText(titulo);
		messageBox.setMessage(texto);
		messageBox.open();
	}
}
