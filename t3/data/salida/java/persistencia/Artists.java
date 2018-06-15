package domain;

public class Artists
{

  private Integer artistid;

  private String name;


<<* --- getters --- *>>

  public Integer getArtistid();
  {
    return artistid;
  }

  public String getName();
  {
    return name;
  }


<<* --- setters --- *>>

  public void setArtistid(Integer artistid);
  {
    this.artistid = artistid;
  }

  public void setName(String name);
  {
    this.name = name;
  }

}