package dao;

import java.sql.*;
import java.util.*;
import domain.*;



public class ArtistsDao
{


  private Artists getFromResultSet (ResultSet r)
  throws SQLException
  {
    Artists artists = new Artists();

    artists.setArtistid( r.getInteger("ArtistId") );

    artists.setName( r.getString("Name") );

    return artists;
  }


  public Artists selectByKey (Connection conn, int id)
  throws SQLException
  {
    Commands currentRow = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try
    {

      String query = " SELECT * FROM artists WHERE artistId = " + id;
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
   * Insert a new Artists into the artists database table.
   */
  public void insertArtists(Artists artists, Connection connection)
  {
    try
    {

      String query = "INSERT INTO artists "
       + "(ArtistId,Name)"
        + "VALUES (?, ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      
      preparedStatement.setInteger(0, artists.getArtistid();
      
      preparedStatement.setString(1, artists.getName();
      

      preparedStatement.execute();
      connection.close();
    }
    catch (Exception e)
    {

    }
  }

  /**
   * Perform a SQL DELETE on the given id for the artists table.
   */
  public boolean deleteByKey (Connection connection, int id)
  throws SQLException
  {
    try
    {
      String query = "DELETE FROM artists where artistId = ?";
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
   * Update the given Artists object into the artists table.
   * Assumes the key for the object is the 'id' field.
   */
  public void updateArtists(Artists artists, Connection connection)
  {
    try
    {
      // TODO/ERROR - need to fix this query, as it will have an extra comma at the end
      String query = "UPDATE artists SET "
                   + "ArtistId = ?, Name = ?"
                   + " WHERE artistId =?";

      // smarty template note: index_next lets the index start at 1 instead of 0
      // do all the 'set' statements for the fields
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      
      preparedStatement.setInteger(0, artists.getArtistid();
      
      preparedStatement.setString(1, artists.getName();
      
      // set the key

<<* using the smarty math function to get the right number here *>>
      preparedStatement.setInt(2, artists.getArtistid());

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