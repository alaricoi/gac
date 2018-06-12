class <<#nombreClase#>>Model
{
    private $pdo;

    public function __CONSTRUCT()
    {
        try
        {
            $this->pdo = new PDO('<<#conexionBd#>>', '<<#userDb#>>', '<<#passDb#>>');
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

            $stm = $this->pdo->prepare("SELECT * FROM <<#tablename#>>");
            $stm->execute();

            foreach($stm->fetchAll(PDO::FETCH_OBJ) as $r)
            {

                $<<#nombreObjeto#>> = new <<#nombreClase#>>();

                <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $r-><<#nombre_campo#>>);
               <</#seccion_loop_campos#>>
                $result[] = $<<#nombreObjeto#>>;
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
                      ->prepare("SELECT * FROM <<#tablename#>> WHERE <<#nombre_clave_tabla#>> = ?");


            $stm->execute(array($id));
            $r = $stm->fetch(PDO::FETCH_OBJ);

           $<<#nombreObjeto#>> = new <<#nombreClase#>>();

              <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $r-><<#nombre_campo#>>);
               <</#seccion_loop_campos#>>

            return $<<#nombreObjeto#>>;
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
                      ->prepare("DELETE FROM <<#tablename#>> WHERE <<#nombre_clave_tabla#>> = ?");

            $stm->execute(array($id));
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Actualizar(Alumno $data)
    {
        try
        {
            $sql = "UPDATE alumnos SET
                       <<#todos_campo_valor#>>
                    WHERE <<#nombre_clave_tabla#>> = ?";

            $array_param = [];
            <<#seccion_loop_campos#>>
              array_push( $array_param,  $data->__GET('<<#nombre_campo#>>');
            <</#seccion_loop_campos#>>
             array_push( $array_param,  $data->__GET('<<#nombre_clave#>>');
            $this->pdo->prepare($sql)->execute($array_param);
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }

    public function Registrar(Alumno $data)
    {
        try
        {
        $sql = "INSERT INTO <<#tablename#>> "
       + "(<<#todos_campos_tabla#>>)"
        + "VALUES (<<#todos_campos_tabla_parametro#>>)";

            $array_param = [];
            <<#seccion_loop_campos#>>
              array_push( $array_param,  $data->__GET('<<#nombre_campo#>>');
            <</#seccion_loop_campos#>>
             $this->pdo->prepare($sql)->execute($array_param);
        } catch (Exception $e)
        {
            die($e->getMessage());
        }
    }
}