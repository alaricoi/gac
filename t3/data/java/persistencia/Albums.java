package persistencia;

public class Albums
{

  private Integer artistid;

  private Integer albumid;

  private String title;


<<* --- getters --- *>>

  public Integer getArtistid();
  {
    return artistid;
  }

  public Integer getAlbumid();
  {
    return albumid;
  }

  public String getTitle();
  {
    return title;
  }


<<* --- setters --- *>>

  public void setArtistid(Integer artistid);
  {
    this.artistid = artistid;
  }

  public void setAlbumid(Integer albumid);
  {
    this.albumid = albumid;
  }

  public void setTitle(String title);
  {
    this.title = title;
  }

}