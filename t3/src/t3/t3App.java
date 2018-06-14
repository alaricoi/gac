package t3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

class ConexionBean {
	private String driver;
	private String cadena;
	private String usu;
	private String pass;
	private String sufijo;

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

	public ConexionBean(String driver, String cadena, String usu, String pass, String sufijo) {
		super();
		this.driver = driver;
		this.cadena = cadena;
		this.usu = usu;
		this.pass = pass;
		this.sufijo = sufijo;
	}
}

public class t3App {
	private final Map<Integer, String> TRANSLADAS_TIPOS = new HashMap<Integer, String>() {
		{
			put(Types.VARCHAR, "String");
			put(Types.NVARCHAR, "String");
			put(Types.CHAR, "String");
			put(Types.NCHAR, "String");
			put(Types.INTEGER, "Integer");
			put(Types.DATE, "Date");
			put(Types.TIMESTAMP, "Timestamp");
			put(Types.FLOAT, "Float");
			put(Types.DOUBLE, "Double");
		}
	};
	protected Shell shlGacPrctica;
	private Text tCadenaConex;
	private Text tUsu;
	private Text tPass;
	private Combo comboTablas;
	// Elemento de log4j para el control de loger de la aplciación
	private static final Logger logger = LogManager.getLogger(t3App.class);
	private Combo comboTipo;
	private ConexionBean conexionBean;
	private Text tClave;
	private Combo comboLenguaje;
	
	private Text tSalida;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			t3App window = new t3App();
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

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shlGacPrctica.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shlGacPrctica.setLocation(x, y);
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
		shlGacPrctica.setSize(871, 503);
		shlGacPrctica.setText("G.A.C. Pr\u00E1ctica 3");
		shlGacPrctica.setLayout(new FillLayout(SWT.VERTICAL));

		Group grpSeleccionDeTipo = new Group(shlGacPrctica, SWT.NONE);
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
		tCadenaConex.setText("");
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
			@Override
			public void widgetSelected(SelectionEvent e) {

				Cursor waitCursor = new Cursor(shlGacPrctica.getDisplay(), SWT.CURSOR_WAIT);
				try {
					shlGacPrctica.setCursor(waitCursor);
					rellenaComboTablas();
				} finally {
					shlGacPrctica.setCursor(null);
					waitCursor.dispose();
				}
			}

		});
		btnConectar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnConectar.setText("Conectar");
		new Label(composite, SWT.NONE);

		Group grpSeleccionaLaTabla = new Group(shlGacPrctica, SWT.NONE);
		grpSeleccionaLaTabla.setText("Selecciona la tabla");
		grpSeleccionaLaTabla.setLayout(new FormLayout());

		Label lblPlantilla = new Label(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_lblPlantilla = new FormData();
		fd_lblPlantilla.top = new FormAttachment(0, 9);
		fd_lblPlantilla.left = new FormAttachment(0, 5);
		lblPlantilla.setLayoutData(fd_lblPlantilla);
		lblPlantilla.setText("Tipo Plantilla");

		comboLenguaje = new Combo(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_comboLenguaje = new FormData();
		fd_comboLenguaje.right = new FormAttachment(0, 417);
		fd_comboLenguaje.top = new FormAttachment(0, 5);
		fd_comboLenguaje.left = new FormAttachment(0, 84);
		comboLenguaje.setLayoutData(fd_comboLenguaje);
		Label lblTabla = new Label(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_lblTabla = new FormData();
		fd_lblTabla.top = new FormAttachment(0, 37);
		fd_lblTabla.left = new FormAttachment(0, 5);
		lblTabla.setLayoutData(fd_lblTabla);
		lblTabla.setText("Tabla");

		comboTablas = new Combo(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_comboTablas = new FormData();
		fd_comboTablas.right = new FormAttachment(0, 417);
		fd_comboTablas.top = new FormAttachment(0, 33);
		fd_comboTablas.left = new FormAttachment(0, 84);
		comboTablas.setLayoutData(fd_comboTablas);

		Label lblclave = new Label(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_lblclave = new FormData();
		fd_lblclave.top = new FormAttachment(0, 64);
		fd_lblclave.left = new FormAttachment(0, 5);
		lblclave.setLayoutData(fd_lblclave);
		lblclave.setText("clave Primaria");

		tClave = new Text(grpSeleccionaLaTabla, SWT.BORDER);
		FormData fd_tClave = new FormData();
		fd_tClave.right = new FormAttachment(0, 417);
		fd_tClave.top = new FormAttachment(0, 61);
		fd_tClave.left = new FormAttachment(0, 84);
		tClave.setLayoutData(fd_tClave);

		Label lblPathDeSalida = new Label(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_lblPathDeSalida = new FormData();
		fd_lblPathDeSalida.top = new FormAttachment(0, 90);
		fd_lblPathDeSalida.left = new FormAttachment(0, 5);
		lblPathDeSalida.setLayoutData(fd_lblPathDeSalida);
		lblPathDeSalida.setText("Path de Salida");
	

		tSalida = new Text(grpSeleccionaLaTabla, SWT.BORDER);
		FormData fd_tSalida = new FormData();
		fd_tSalida.right = new FormAttachment(0, 417);
		fd_tSalida.top = new FormAttachment(0, 87);
		fd_tSalida.left = new FormAttachment(0, 84);
		tSalida.setLayoutData(fd_tSalida);
	
		Button btnNewButton = new Button(grpSeleccionaLaTabla, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.right = new FormAttachment(0, 316);
		fd_btnNewButton.top = new FormAttachment(0, 113);
		fd_btnNewButton.left = new FormAttachment(0, 184);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trataPlantilla();

			}
		});
		btnNewButton.setText("Generar C\u00F3digo");

		Button btnSeleccionaPath = new Button(grpSeleccionaLaTabla, SWT.NONE);
		btnSeleccionaPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fileName = abreDialogoSalida(SWT.SAVE);
				tSalida.setText(fileName);
			}
		});
		FormData fd_btnSeleccionaPath = new FormData();
		fd_btnSeleccionaPath.top = new FormAttachment(lblPathDeSalida, -5, SWT.TOP);
		fd_btnSeleccionaPath.left = new FormAttachment(tSalida, 6);
		btnSeleccionaPath.setLayoutData(fd_btnSeleccionaPath);
		
		btnSeleccionaPath.setText("Selecciona Path");

		Composite composite_1 = new Composite(shlGacPrctica, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		// cargamos las plantillas
	//	ClassLoader cLoader = this.getClass().getClassLoader();
		try {
			String path = new File(".").getCanonicalPath();
			logger.info(path);
		
		FileInputStream input = new FileInputStream("./conf/template.properties");
	
		//InputStream input = cLoader.getResourceAsStream("conf/template.properties");
		Properties prop = new Properties();
	
			prop.load(input);
			Set<Object> keys = prop.keySet();
			for (Object k : keys) {
				comboLenguaje.add((String) k);
			}
		} catch (IOException e1) {

			logger.error(e1);
		}

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

			Class.forName(con.getDriver());

		} catch (ClassNotFoundException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha podido crear la conexión: " + e1.getMessage());

			logger.error(e1);
		}
		Connection conexion = null;

		try {
			conexion = DriverManager.getConnection("jdbc:" + con.getSufijo() + con.getCadena(), con.getUsu(), con.getPass());
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
			sufijo = "mysql://";
		}
		// Es SQLite
		if (comboTipo.getSelectionIndex() == 0) {
			driver = "org.sqlite.JDBC";
			sufijo = "sqlite:";
		}

		conexionBean = new ConexionBean(driver, tCadenaConex.getText(), tUsu.getText(), tPass.getText(), sufijo);

		Connection conn = dameConexion(conexionBean);
		// Si no obtenemos conexión salimos del método
		if (conn == null) {
			return;
		}
		ResultSet rs = null;

		try {

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
			comboLenguaje.setFocus();

		} catch (SQLException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", "No se ha podido crear la conexión");
			logger.error(e1);
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

	private void muestraDialogoModal(int estilo, String titulo, String texto) {
		MessageBox messageBox = new MessageBox(shlGacPrctica, estilo);
		messageBox.setText(titulo);
		messageBox.setMessage(texto);
		messageBox.open();
	}

	/**
	 * Abre un dialogo de selección de fichero y guarda el path seleccionado en la
	 * caja de texto de origen
	 */
	private String abreDialogoSalida(int estilo) {

		DirectoryDialog dialog = new DirectoryDialog(shlGacPrctica, estilo);

		String fileName = dialog.open();
		if (fileName != null && !fileName.equals("")) {

			return fileName;
		} else
			return "";
	}

	/**
	 * Método que se encarga de capturar los datos introducidos en la interfaz
	 * de usuario y llamar a la clase de generación de código
	 */
	private void trataPlantilla() {
		if (conexionBean == null) {

			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"Para realizar este paso hace falta conectarse previamente");

			return;
		}
		
		if (comboLenguaje.getText() == null) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha seleccionado lenguaje");

			return;			
		}
		
		
		if (comboTablas.getText() == null) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha seleccionado la tabla a exportar");
			return;			
		}
		
		
		if (tClave.getText() == null) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha seleccionado la clave de la tabla");
			return;			
		}
		
		if (tClave.getText() == null) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error",
					"No se ha seleccionado el destino de la generación");
			return;			
		}
		LecturaPlantilla plantilla = new LecturaPlantilla();

		DatosCrud crud = new DatosCrud();
		crud.setTabla(comboTablas.getText());
		
		//quitamos la llamada a jdbc si no es java
		  String sufijo = conexionBean.getSufijo();
		 
	     String conexion = conexionBean.getCadena();
	   //si es sqlite, como estamos en windows hat que escapar el caracter "\"
	     if (conexionBean.getDriver().equalsIgnoreCase("org.sqlite.JDBC")) {
	        	conexion =   conexion.replace("\\", "\\\\");
	      }
	     
	      // si es php se debe tratart la conexión de otra manera a java 
          if (comboLenguaje.getText().toLowerCase().startsWith("php")) { 				  
		
        	    
	        //es mysql y la conexón se realida de forma:
	        //mysql:dbname=employees;host=localhost;port=3306
	        if (!conexionBean.getDriver().equalsIgnoreCase("org.sqlite.JDBC")) {
	        	
	        	//host
	        	String host =  conexionBean.getCadena().substring(0, conexionBean.getCadena().indexOf(":"));
	        	//
	        	String port = conexionBean.getCadena().substring(conexionBean.getCadena().indexOf(":") +1,
	        			                                  conexionBean.getCadena().indexOf("/"));
	        	String dbname = conexionBean.getCadena().substring(conexionBean.getCadena().indexOf("/")+1); 		
	        	conexion = "dbname=" +dbname + ";host=" + host + ";port=" + port;
	        }	
          }		
   
           
          crud.setConexionBd(sufijo + conexion);

		
		crud.setUsuarioBd(conexionBean.getUsu());
		crud.setClaveBd(conexionBean.getPass());
		crud.setNombreClave(tClave.getText());
		crud.setPathSalida(tSalida.getText());
		ResultSet rs = null;
		Connection conn = dameConexion(conexionBean);
		try {

			// Si no obtenemos conexión salimos del método
			if (conn == null) {
				return;
			}

			try {
				DatabaseMetaData metaDatos = conn.getMetaData();
				rs = metaDatos.getColumns(null, null, comboTablas.getText(), null);
				Map<String, String> map = new HashMap<String, String>();
				while (rs.next()) {
					map.putIfAbsent(rs.getString("COLUMN_NAME"), TRANSLADAS_TIPOS.get(rs.getInt("DATA_TYPE")));
				}
				crud.setCampos(map);
          
				plantilla.cargaPlatilla(comboLenguaje.getText(), crud);
				muestraDialogoModal(SWT.ICON_INFORMATION | SWT.OK, "Correcto", "Se ha creado la salida de la plantilla");

			} catch (SQLException e1) {
				logger.error(e1);
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", "No se ha podido crear la conexión");
				return;
			} catch (Exception e) {
				logger.error(e);
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", "No se ha podido crear "
						+ "     la plantilla revise el correcto etiquetado custom");
				return;
			}

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e1) {
				logger.error(e1);
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", "No se ha podido crear la conexión");
				return;
			}
		}
	}
}
