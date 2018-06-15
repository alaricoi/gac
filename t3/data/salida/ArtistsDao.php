<?php
class ArtistsModel
{
    private $pdo;

    public function __CONSTRUCT()
    {
        try
        {
            $this->pdo = new PDO('sqlite:D:\workspaceMio\gac\t3\data\test.db', '', '');
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        }
        catch(Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Listar()
    {
        try
        {
            $result = array();

            $stm = $this->pdo->prepare("SELECT * FROM artists");
            $stm->execute();

            foreach($stm->fetchAll(PDO::FETCH_OBJ) as $r)
            {

                $artists = new Artists();

                
                   $artists->__SET('artistid', $r->ArtistId);
               
                   $artists->__SET('name', $r->Name);
               
                $result[] = $artists;
            }

            return $result;
        }
        catch(Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Obtener($id)
    {
        try
        {
            $stm = $this->pdo
                      ->prepare("SELECT * FROM artists WHERE artistId = ?");


            $stm->execute(array($id));
            $r = $stm->fetch(PDO::FETCH_OBJ);

           $artists = new Artists();

              
                   $artists->__SET('artistid', $r->ArtistId);
               
                   $artists->__SET('name', $r->Name);
               

            return $artists;
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Eliminar($id)
    {
        try
        {
            $stm = $this->pdo
                      ->prepare("DELETE FROM artists WHERE artistId = ?");

            $stm->execute(array($id));
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Actualizar(Artists $data)
    {
        try
        {
            $sql = "UPDATE artists SET
                       ArtistId = ?, Name = ?
                    WHERE artistId = ?";

            $array_param = [];
            
              array_push( $array_param,  $data->__GET('artistid'));
            
              array_push( $array_param,  $data->__GET('name'));
            
             array_push( $array_param,  $data->__GET('artistid'));
            $this->pdo->prepare($sql)->execute($array_param);
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Registrar(Artists $data)
    {
        try
        {
        $sql = "INSERT INTO artists "
        . " (ArtistId,Name) "
        . " VALUES (?, ?)";

            $array_param = [];
            
              array_push( $array_param,  $data->__GET('artistid'));
            
              array_push( $array_param,  $data->__GET('name'));
            
             $this->pdo->prepare($sql)->execute($array_param);
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }
}
?>