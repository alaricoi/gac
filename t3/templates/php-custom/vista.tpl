<?php
require_once '<<#nombreClase#>>.php';
require_once '<<#nombreClase#>>Dao.php';

$<<#nombreObjeto#>> = new <<#nombreClase#>>();
$model = new <<#nombreClase#>>Model();

if(isset($_REQUEST['action']))
{
    switch($_REQUEST['action'])
    {
        case 'actualizar':

                <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $_REQUEST['<<#nombre_campo#>>']);
               <</#seccion_loop_campos#>>

            $model->Actualizar($<<#nombreObjeto#>>);
            header('Location: <<#nombreClase#>>Vista.php');
            break;

        case 'registrar':
              <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $_REQUEST['<<#nombre_campo#>>']);
               <</#seccion_loop_campos#>>

            $model->Registrar($<<#nombreObjeto#>>);
            header('Location: <<#nombreClase#>>Vista.php');
            break;

        case 'eliminar':
            $model->Eliminar($_REQUEST['<<#nombre_clave#>>']);
            header('Location: <<#nombreClase#>>Vista.php');
            break;

        case 'editar':
            $<<#nombreObjeto#>> = $model->Obtener($_REQUEST['<<#nombre_clave#>>']);
            break;
    }
}

?>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title><<#nombreClase#>>.</title>
       
    </head>
    <body style="padding:15px;">

        <div >
            <div ">

                <form action="?action=<?php echo $<<#nombreObjeto#>>-><<#nombre_clave#>> > 0 ? 'actualizar' : 'registrar'; ?>" method="post" >
                    <input type="hidden" name="<<#nombre_clave#>>" value="<?php echo $<<#nombreObjeto#>>->__GET('<<#nombre_clave#>>'); ?>" />

                    <table style="width:500px;">
                      <<#seccion_loop_campos#>>
                           <tr>
                            <th style="text-align:left;"><<#nombre_campo#>></th>
                            <td><input type="text" name="<<#nombre_campo#>>" value="<?php echo $<<#nombreObjeto#>>->__GET('<<#nombre_campo#>>'); ?>" style="width:100%;" /></td>
                        </tr>
                       <</#seccion_loop_campos#>>

                    </table>
                    	
					 <input type="submit" value="aceptar">
                </form>

                <table >
                    <thead>
                        <tr>
                         <<#seccion_loop_campos#>>
                            <th style="text-align:left;"><<#nombre_campo#>></th>
                        <</#seccion_loop_campos#>>
                            <th></th>
                            <th></th>

                        </tr>
                    </thead>
                    <?php foreach($model->Listar() as $r): ?>
                        <tr>
                        <<#seccion_loop_campos#>>
                            <td><?php echo $r->__GET('<<#nombre_campo#>>'); ?></td>

                             <</#seccion_loop_campos#>>
                            <td>

                                <a href="?action=editar&<<#nombre_clave#>>=<?php echo $r-><<#nombre_clave#>>; ?>">Editar</a>
                            </td>
                            <td>
                                <a href="?action=eliminar&<<#nombre_clave#>>=<?php echo $r-><<#nombre_clave#>>; ?>">Eliminar</a>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                </table>

            </div>
        </div>

    </body>
</html>