package t1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class t1App {

	protected Shell shlPrcticaGac;
	private Text tOrigen;
	private Text tSalida;
	private Button bCrearXML;
	private Text tAreaOrigen;
	private Text tAreaDestino;
	private Button bFileOrigen;
	private Button btnSeleccionaXmlSalida;
	
	// Elemento de log4j para el control de loger de la apliciación
	private static final Logger logger = LogManager.getLogger(t1App.class);
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			loadSwtJar();
			t1App window = new t1App();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static  void loadSwtJar() {
	    String osName = System.getProperty("os.name").toLowerCase();
	    String osArch = System.getProperty("os.arch").toLowerCase();
	    String swtFileNameOsPart = 
	        osName.contains("win") ? "win32" :
	        osName.contains("mac") ? "macosx" :
	        osName.contains("linux") || osName.contains("nix") ? "linux_gtk" :
	        ""; // throw new RuntimeException("Unknown OS name: "+osName)

	    String swtFileNameArchPart = osArch.contains("64") ? "x64" : "x86";
	    String swtFileName = "swt_"+swtFileNameOsPart+"_"+swtFileNameArchPart+".jar";
	    File f = new File("./lib/" + swtFileName);
	    try {
			URL myJarFile = new URL("jar","","file:"+f.getAbsolutePath()+"!/");
			URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			Class<URLClassLoader> sysClass = URLClassLoader.class;
			Method sysMethod = sysClass.getDeclaredMethod("addURL",new Class[] {URL.class});
			sysMethod.setAccessible(true);
			sysMethod.invoke(sysLoader, new Object[]{myJarFile});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPrcticaGac.open();
		shlPrcticaGac.layout();
		while (!shlPrcticaGac.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlPrcticaGac = new Shell();
		shlPrcticaGac.setImage(SWTResourceManager.getImage("img/th.jpg"));
		shlPrcticaGac.setSize(652, 441);
		shlPrcticaGac.setText("Pr\u00E1ctica 1 G.A.C.");
		shlPrcticaGac.setLayout(new FormLayout());
		
		tOrigen = new Text(shlPrcticaGac, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_tOrigen = new FormData();
		fd_tOrigen.bottom = new FormAttachment(0, 52);
		fd_tOrigen.top = new FormAttachment(0, 31);
		fd_tOrigen.left = new FormAttachment(0, 25);
		fd_tOrigen.right = new FormAttachment(100, -168);
		tOrigen.setLayoutData(fd_tOrigen);
		
		tSalida = new Text(shlPrcticaGac, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_tSalida = new FormData();
		fd_tSalida.left = new FormAttachment(0, 25);
		fd_tSalida.top = new FormAttachment(tOrigen, 6);
		tSalida.setLayoutData(fd_tSalida);
		
		bFileOrigen = new Button(shlPrcticaGac, SWT.NONE);
		bFileOrigen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fileName = abreDialogoJson(SWT.OPEN);
				tOrigen.setText(fileName);
			}
		});
		FormData fd_bFileOrigen = new FormData();
		fd_bFileOrigen.left = new FormAttachment(100, -162);
		fd_bFileOrigen.right = new FormAttachment(100, -24);
		bFileOrigen.setLayoutData(fd_bFileOrigen);
		bFileOrigen.setText("Selecciona JSON Origen");
		
		btnSeleccionaXmlSalida = new Button(shlPrcticaGac, SWT.NONE);
		fd_bFileOrigen.bottom = new FormAttachment(btnSeleccionaXmlSalida, -2);
		fd_tSalida.right = new FormAttachment(btnSeleccionaXmlSalida, -6);
		btnSeleccionaXmlSalida.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fileName = abreDialogoJson(SWT.SAVE);
				tSalida.setText(fileName);

			}
		});
		FormData fd_btnSeleccionaXmlSalida = new FormData();
		fd_btnSeleccionaXmlSalida.left = new FormAttachment(100, -162);
		fd_btnSeleccionaXmlSalida.right = new FormAttachment(100, -24);
		fd_btnSeleccionaXmlSalida.top = new FormAttachment(0, 56);
		btnSeleccionaXmlSalida.setLayoutData(fd_btnSeleccionaXmlSalida);
		btnSeleccionaXmlSalida.setText("Selecciona XML Destino");
		
		bCrearXML = new Button(shlPrcticaGac, SWT.NONE);
		bCrearXML.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				crearXML();
			}
		});
		FormData fd_bCrearXML = new FormData();
		fd_bCrearXML.top = new FormAttachment(tSalida, 18);
		fd_bCrearXML.left = new FormAttachment(0, 25);
		bCrearXML.setLayoutData(fd_bCrearXML);
		bCrearXML.setText("Crear XML");
		
		Composite composite = new Composite(shlPrcticaGac, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(100, -64);
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.top = new FormAttachment(bCrearXML, 22);
		fd_composite.bottom = new FormAttachment(100, -29);
		composite.setLayoutData(fd_composite);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText("JSON Origen");
		
		Label lblXmlDestino = new Label(composite, SWT.NONE);
		lblXmlDestino.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblXmlDestino.setText("XML Destino");
		
		tAreaOrigen = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		tAreaOrigen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tAreaOrigen.setEditable(false);
		
		tAreaDestino = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		tAreaDestino.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tAreaDestino.setEditable(false);

	}
	
	
	
	/**
	 * Abre un dialogo de selección de fichero y guarda el path seleccionado en
	 * la caja de texto de origen
	 */
	private String abreDialogoJson(int estilo) {
		
		FileDialog dlg = new FileDialog(shlPrcticaGac, estilo);
		if (estilo == SWT.SAVE) {
			dlg.setOverwrite(true);
		}
		String fileName = dlg.open();
		if (fileName != null && !fileName.equals("")) {
		
			return fileName;
		}
		else return "";
	}

	

	/**
	 * Metodo que crea el fichero XML a partir del JSON seleccioando, el fichero
	 * creado se gurada en la ruta seleccionada en la caja de texto tSalida
	 * 
	 */
	private void crearXML() {
		Cursor waitCursor = new Cursor(shlPrcticaGac.getDisplay(), SWT.CURSOR_WAIT);
		shlPrcticaGac.setCursor(waitCursor);
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
				
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error de Lectura I/O",
						"No es posible leer el fichero seleccionado de origen JSON");
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
				
				muestraDialogoModal(SWT.ICON_WORKING | SWT.OK, "Correcto", 
						"Fichero xml creado correctamente.");
				

			} catch (JSONException e1) {
				
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "JSON error", 
						"El fichero de entrada no cumple con formato JSON");
				logger.error(e1);
			} catch (IOException e1) {
				// Excepción porque ha habido un problema en la excritura del
				// fichero
				
				muestraDialogoModal(SWT.ICON_ERROR | SWT.OK, "Error de escritura I/O", 
						"No es posible escribir el fichero seleccionado de salida XML");
				logger.error(e1);
			}

		} finally {
			shlPrcticaGac.setCursor(null);
		}
	}
	private void muestraDialogoModal(int estilo, String titulo, String texto) {
		MessageBox messageBox = new MessageBox(shlPrcticaGac, estilo);
		messageBox.setText(titulo);
		messageBox.setMessage(texto);
		messageBox.open();
	}
	
}
