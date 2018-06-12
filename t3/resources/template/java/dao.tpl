package <<#paquete_daos#>>;

import java.sql.*;
import java.util.*;
import <<#paquete_modelos#>>.*;



public class <<#nombreClase#>>Dao
{


  private <<#nombreClase#>> getFromResultSet (ResultSet r)
  throws SQLException
  {
    <<#nombreClase#>> <<#nombreObjeto#>> = new <<#nombreClase#>>();
<<#seccion_loop_campos#>>
    <<#nombreObjeto#>>.set<<#Nombre_campo#>>( r.get<<#tipo_campo#>>("<<#nombre_campo_tabla#>>") );
<</#seccion_loop_campos#>>
    return <<#nombreObjeto#>>;
  }


  public <<#nombreClase#>> selectByKey (Connection conn, int id)
  throws SQLException
  {
    Commands currentRow = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try
    {

      String query = " SELECT * FROM <<#tablename#>> WHERE <<#nombre_clave_tabla#>> = " + id;
      statement = conn.createStatement();
      resultSet = statement.executeQuery(query);
      if ( resultSet.next() )
      {
        currentRow = getFromResultSet(resultSet);
      }
    }
    catch (SQLException se)
    {
      // log exception if desired
      throw se;
    }
    finally
    {
      if ( statement != null )
      {
        statement.close();
      }
    }
  }

  /**
   * Insert a new <<#nombreClase#>> into the <<#tablename#>> database table.
   */
  public void insert<<#nombreClase#>>(<<#nombreClase#>> <<#nombreObjeto#>>, Connection connection)
  {
    try
    {

      String query = "INSERT INTO <<#tablename#>> "
       + "(<<#todos_campos_tabla#>>)"
        + "VALUES (<<#todos_campos_tabla_parametro#>>)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      <<#seccion_loop_campos#>>
      preparedStatement.set<<#tipo_campo#>>(<<#posicion_campo#>>, <<#nombreObjeto#>>.get<<#Nombre_campo#>>();
      <</#seccion_loop_campos#>>

      preparedStatement.execute();
      connection.close();
    }
    catch (Exception e)
    {

    }
  }

  /**
   * Perform a SQL DELETE on the given id for the <<#tablename#>> table.
   */
  public boolean deleteByKey (Connection connection, int id)
  throws SQLException
  {
    try
    {
      String query = "DELETE FROM <<#tablename#>> where <<#nombre_clave_tabla#>> = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
      connection.close();
    }
    catch (SQLException se)
    {

      throw se;
    }
    finally
    {
      if ( preparedStatement != null )
      {
        preparedStatement.close();
      }
    }
  }


  /**
   * Update the given <<#nombreClase#>> object into the <<#tablename#>> table.
   * Assumes the key for the object is the 'id' field.
   */
  public void update<<#nombreClase#>>(<<#nombreClase#>> <<#nombreObjeto#>>, Connection connection)
  {
    try
    {
      // TODO/ERROR - need to fix this query, as it will have an extra comma at the end
      String query = "UPDATE <<#tablename#>> SET "
                   + "<<#todos_campo_valor#>>"
                   + " WHERE <<#nombre_clave_tabla#>> =?";

      // smarty template note: index_next lets the index start at 1 instead of 0
      // do all the 'set' statements for the fields
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      <<#seccion_loop_campos#>>
      preparedStatement.set<<#tipo_campo#>>(<<#posicion_campo#>>, <<#nombreObjeto#>>.get<<#Nombre_campo#>>();
      <</#seccion_loop_campos#>>
      // set the key

<<* using the smarty math function to get the right number here *>>
      preparedStatement.setInt(<<#numero_campos#>>, <<#nombreObjeto#>>.get<<#Nombre_clave#>>());

      // execute the preparedstatement
      preparedStatement.execute();
      connection.close();
    }
    catch (Exception e)
    {
      // TODO log the exception however you normally do
    }
  }
}