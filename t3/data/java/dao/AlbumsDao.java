package dao;

import java.sql.*;
import java.util.*;
import persistencia.*;



public class AlbumsDao
{


  private Albums getFromResultSet (ResultSet r)
  throws SQLException
  {
    Albums albums = new Albums();

    albums.setArtistid( r.getInteger("ArtistId") );

    albums.setAlbumid( r.getInteger("AlbumId") );

    albums.setTitle( r.getString("Title") );

    return albums;
  }


  public Albums selectByKey (Connection conn, int id)
  throws SQLException
  {
    Commands currentRow = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try
    {

      String query = " SELECT * FROM albums WHERE AlbumId = " + id;
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
   * Insert a new Albums into the albums database table.
   */
  public void insertAlbums(Albums albums, Connection connection)
  {
    try
    {

      String query = "INSERT INTO albums "
       + "(ArtistId,AlbumId,Title)"
        + "VALUES (?, ?, ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      
      preparedStatement.setInteger(0, albums.getArtistid();
      
      preparedStatement.setInteger(1, albums.getAlbumid();
      
      preparedStatement.setString(2, albums.getTitle();
      

      preparedStatement.execute();
      connection.close();
    }
    catch (Exception e)
    {

    }
  }

  /**
   * Perform a SQL DELETE on the given id for the albums table.
   */
  public boolean deleteByKey (Connection connection, int id)
  throws SQLException
  {
    try
    {
      String query = "DELETE FROM albums where AlbumId = ?";
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
   * Update the given Albums object into the albums table.
   * Assumes the key for the object is the 'id' field.
   */
  public void updateAlbums(Albums albums, Connection connection)
  {
    try
    {
      // TODO/ERROR - need to fix this query, as it will have an extra comma at the end
      String query = "UPDATE albums SET "
                   + "ArtistId = ?, AlbumId = ?, Title = ?"
                   + " WHERE AlbumId =?";

      // smarty template note: index_next lets the index start at 1 instead of 0
      // do all the 'set' statements for the fields
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      
      preparedStatement.setInteger(0, albums.getArtistid();
      
      preparedStatement.setInteger(1, albums.getAlbumid();
      
      preparedStatement.setString(2, albums.getTitle();
      
      // set the key

<<* using the smarty math function to get the right number here *>>
      preparedStatement.setInt(3, albums.getAlbumid());

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