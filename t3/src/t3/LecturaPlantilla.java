package t3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LecturaPlantilla {
	private static final Logger logger = LogManager.getLogger(t3App.class);

	public void cargaPlatilla(String plantilla, DatosCrud crud) {
		try {
			//ClassLoader cLoader = this.getClass().getClassLoader();
			FileInputStream input = new FileInputStream("./conf/template.properties");
			// InputStream input = cLoader.getResourceAsStream("conf/template.properties");
			Properties prop = new Properties();
			// load a properties file

			prop.load(input);
			String[] plantillas = prop.getProperty(plantilla).split(",");
			for (String pl : plantillas) {
				String e = cargaFichero("./templates/" + plantilla + "/" + pl);
				String s = trataPlantilla(e, crud);

				logger.info(s);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String cargaFichero(String plantilla) throws IOException {

	//	ClassLoader cLoader = this.getClass().getClassLoader();

		try {
			logger.info("buscando " + plantilla);
			//File file = new File(cLoader.getResource(plantilla).getFile());
			File file = new File(plantilla);
			logger.info("File Found : " + file.exists());
			// Read File Content
			String content = new String(Files.readAllBytes(file.toPath()));
			return content;
		} catch (IOException e) {
			logger.info("File no found " + plantilla);
			throw e;
		}
		// File is found

	}

	public String trataPlantilla(String plantilla, DatosCrud crud) {
		String plantillaTratada = plantilla;

		// gestión de paquetes
		plantillaTratada = plantillaTratada.replaceAll("<<#paquete_modelos#>>", "domain");

		plantillaTratada = plantillaTratada.replaceAll("<<#paquete_daos#>>", "dao");

		// datos de conexión de base de datos
		plantillaTratada = plantillaTratada.replaceAll("<<#conexionBd#>>", crud.getConexionBd());
		plantillaTratada = plantillaTratada.replaceAll("<<#userDb#>>", crud.getUsuarioBd());
		plantillaTratada = plantillaTratada.replaceAll("<<#passDb#>>", crud.getClaveBd());

		// se asigan el campo que será clave
		plantillaTratada = plantillaTratada.replaceAll("<<#nombre_clave_tabla#>>", crud.getNombreClave());

		plantillaTratada = plantillaTratada.replaceAll("<<#nombre_clave#>>", camelCase(crud.getNombreClave(), false));

		plantillaTratada = plantillaTratada.replaceAll("<<#Nombre_clave#>>", camelCase(crud.getNombreClave(), true));

		// llamamos a la clase con el nombre de la tabla con la primera
		// letra el mayusculas
		String nombreClase = camelCase(crud.getTabla(), true);
		plantillaTratada = plantillaTratada.replaceAll("<<#nombreClase#>>", nombreClase);
		String nombreObjeto = camelCase(crud.getTabla(), false);
		plantillaTratada = plantillaTratada.replaceAll("<<#nombreObjeto#>>", nombreObjeto);

		// Nombre de la tabla
		plantillaTratada = plantillaTratada.replaceAll("<<#tablename#>>", crud.getTabla());

		// lista de todos los campos separados por coma

		String campos = StringUtils.join(crud.getCampos().keySet(), ',');
		plantillaTratada = plantillaTratada.replaceAll("<<#todos_campos_tabla#>>", campos);
		String todos_param = "";
		String update_param = "";
		int index = 1;
		for (Map.Entry<String, String> entry : crud.getCampos().entrySet()) {
			todos_param += "?";
			update_param += entry.getKey() + " = ?";
			if (crud.getCampos().size() > index) {
				todos_param += ", ";
				update_param += ", ";
			}
			index++;
		}
		plantillaTratada = plantillaTratada.replaceAll("<<#todos_campos_tabla_parametro#>>", todos_param);
		plantillaTratada = plantillaTratada.replaceAll("<<#todos_campo_valor#>>", update_param);
		plantillaTratada = plantillaTratada.replaceAll("<<#numero_campos#>>",
				Integer.toString(crud.getCampos().size()));

		if (crud.getCampos() != null)
			plantillaTratada = trataCampos(crud, plantillaTratada);

		return plantillaTratada;

	}

	/**
	 * @param crud
	 * @param plantillaTratada
	 */
	private String trataCampos(DatosCrud crud, String plantillaTratada) {

		// buscamos el reemplazo de campos
		int ocurrenciaCampos = plantillaTratada.indexOf("<<#seccion_loop_campos#>>");
		int finOcurrenciaCampos = plantillaTratada.indexOf("<</#seccion_loop_campos#>>");
		while (ocurrenciaCampos > -1) {
			String trataCampos = plantillaTratada.substring(ocurrenciaCampos, finOcurrenciaCampos);

			// Quitamos las marcas de bucle de campos
			trataCampos = trataCampos.replaceFirst("<<#seccion_loop_campos#>>", "");
			trataCampos = trataCampos.replaceFirst("<</#seccion_loop_campos#>>", "");
			String salidaCampos = "";

			Integer index = 0;
			for (Map.Entry<String, String> entry : crud.getCampos().entrySet()) {
				String bucle = trataCampos;
				bucle = bucle.replaceAll("<<#nombre_campo#>>", camelCase(entry.getKey(), false));
				bucle = bucle.replaceAll("<<#Nombre_campo#>>", camelCase(entry.getKey(), true));
				bucle = bucle.replaceAll("<<#nombre_campo_tabla#>>", entry.getKey());

				bucle = bucle.replaceAll("<<#posicion_campo#>>", (index++).toString());

				bucle = bucle.replaceAll("<<#tipo_campo#>>", entry.getValue());

				salidaCampos += bucle;
			}
			String resultado = plantillaTratada.substring(0, ocurrenciaCampos);
			resultado += salidaCampos;
			resultado += plantillaTratada.substring(finOcurrenciaCampos + "<</#seccion_loop_campos#>>".length());
			plantillaTratada = resultado;
			// preparamos la siguiente iteración
			ocurrenciaCampos = plantillaTratada.indexOf("<<#seccion_loop_campos#>>");
			finOcurrenciaCampos = plantillaTratada.indexOf("<</#seccion_loop_campos#>>");
		}
		return plantillaTratada;
	}

	private String primeraMayuscula(String cadena) {

		return WordUtils.capitalize(cadena.toLowerCase());
	}

	private String camelCase(String cadena, boolean primera) {
		String[] t = cadena.split("_");
		String salida = "";
		for (int i = 0; i < t.length; i++) {
			if (!primera && i == 0) {
				salida = t[i].toLowerCase();
			}

			else {
				salida += primeraMayuscula(t[i]);
			}
		}
		return salida;
	}

}
