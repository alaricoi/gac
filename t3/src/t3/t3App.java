package t3;

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
import java.util.HashMap;
import java.util.Map;

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

	protected Shell shlGacPrctica;
	private Text tCadenaConex;
	private Text tUsu;
	private Text tPass;
	private Combo comboTablas;
	// Elemento de log4j para el control de loger de la aplciación
	private static final Logger logger = LogManager.getLogger(t3App.class);
	private Text tWhere;
	private Combo comboTipo;
	private ConexionBean conexionBean;
	private Label lblSalida;

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
		shlGacPrctica.setImage(SWTResourceManager.getImage("/img/th.jpg"));
		shlGacPrctica.setSize(616, 503);
		shlGacPrctica.setText("G.A.C. Pr\u00E1ctica 3");
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
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblSalida.setText("");
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
		new Label(grpSeleccionaLaTabla, SWT.NONE);
		new Label(grpSeleccionaLaTabla, SWT.NONE);
		
		Button btnNewButton = new Button(grpSeleccionaLaTabla, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				LecturaPlantilla  plantilla = new LecturaPlantilla();
			  
			      DatosCrud crud = new DatosCrud();
			    crud.setTabla("cAMPANA");
			    crud.setConexionBd("mysql.asdada");
			    crud.setUsuarioBd("root");
			    crud.setClaveBd("");
			    crud.setNombreClave("ID_CAMPO");
			    Map<String, String> map = new HashMap<String, String>();
			    map.putIfAbsent("ID_CAMPO", "Integer");
			    map.putIfAbsent("DS_CAMPO", "String");
			    crud.setCampos(map);

			    plantilla.cargaPlatilla("java", crud);
			    System.out.println("---------------------------");
			    plantilla.cargaPlatilla("php", crud);


			  

			}
		});
		btnNewButton.setText("New Button");
		new Label(grpSeleccionaLaTabla, SWT.NONE);

		Group grpSalida = new Group(shlGacPrctica, SWT.NONE);
		grpSalida.setText("Salida");
		grpSalida.setLayout(new FormLayout());

		lblSalida = new Label(grpSalida, SWT.NONE);
		lblSalida.setText("\t\t\t\t\t\t");
		lblSalida.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		FormData fd_lblSalida = new FormData();
		fd_lblSalida.left = new FormAttachment(0, 10);
		fd_lblSalida.top = new FormAttachment(0, 5);
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

			Class.forName(con.getDriver());

		} catch (ClassNotFoundException e1) {
			muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error", 
					"No se ha podido crear la conexión: " + e1.getMessage());
		
			logger.error(e1);
		}
		Connection conexion = null;

		try {
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
			comboTablas.setFocus();
			
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
}
