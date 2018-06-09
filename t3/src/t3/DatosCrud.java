package t3;

import java.io.Serializable;
import java.util.Map;

public class DatosCrud implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -666924284406532401L;

    /**
     * tabla de base de datos
     */
    private String tabla;

    /**
     * Nombre del campo clave
     */
    private String nombreClave;

    /**
     * Objeto que guarda los campos y el tipo de datos
     */
    private Map <String, String> campos;


    /**
     * cadena de conexión de la base de datos
     */
    private String conexionBd;

    /**
     *
     * Uuasrio de base de datos
     */
    private String usuarioBd;

    /**
     *
     */
    private String claveBd;


    private String pathSalida;

    public String getTabla() {
        return tabla;
    }


    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Map<String, String> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }


    public String getConexionBd() {
        return conexionBd;
    }


    public void setConexionBd(String conexionBd) {
        this.conexionBd = conexionBd;
    }


    public String getUsuarioBd() {
        return usuarioBd;
    }


    public void setUsuarioBd(String usuarioBd) {
        this.usuarioBd = usuarioBd;
    }


    public String getClaveBd() {
        return claveBd;
    }


    public void setClaveBd(String claveBd) {
        this.claveBd = claveBd;
    }


    public String getNombreClave() {
        return nombreClave;
    }


    public void setNombreClave(String nombreClave) {
        this.nombreClave = nombreClave;
    }


	public String getPathSalida() {
		return pathSalida;
	}


	public void setPathSalida(String pathSalida) {
		this.pathSalida = pathSalida;
	}


}
