<?php
require_once '<<#nombreClase#>>.entidad.php';
require_once '<<#nombreClase#>>.model.php';

$<<#nombreObjeto#>> = new <<#nombreClase#>>();
$model = new <<#nombreClase#>>Model();

if(isset($_REQUEST['action']))
{
    switch($_REQUEST['action'])
    {
        case 'actualizar':

                <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $r-><<#nombre_campo#>>);
               <</#seccion_loop_campos#>>

            $model->Actualizar($<<#nombreObjeto#>>);
            header('Location: index.php');
            break;

        case 'registrar':
              <<#seccion_loop_campos#>>
                   $<<#nombreObjeto#>>->__SET('<<#nombre_campo#>>', $r-><<#nombre_campo#>>);
               <</#seccion_loop_campos#>>

            $model->Registrar($<<#nombreObjeto#>>);
            header('Location: index.php');
            break;

        case 'eliminar':
            $model->Eliminar($_REQUEST['id']);
            header('Location: index.php');
            break;

        case 'editar':
            $<<#nombreObjeto#>> = $model->Obtener($_REQUEST['id']);
            break;
    }
}

?>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Anexsoft</title>
        <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
    </head>
    <body style="padding:15px;">

        <div class="pure-g">
            <div class="pure-u-1-12">

                <form action="?action=<?php echo $<<#nombreObjeto#>>->id > 0 ? 'actualizar' : 'registrar'; ?>" method="post" class="pure-form pure-form-stacked" style="margin-bottom:30px;">
                    <input type="hidden" name="id" value="<?php echo $<<#nombreObjeto#>>->__GET('id'); ?>" />

                    <table style="width:500px;">
                      <<#seccion_loop_campos#>>
                           <tr>
                            <th style="text-align:left;"><<#nombre_campo#>></th>
                            <td><input type="text" name="<<#nombre_campo#>>" value="<?php echo $<<#nombreObjeto#>>->__GET('<<#nombre_campo#>>'); ?>" style="width:100%;" /></td>
                        </tr>
                       <</#seccion_loop_campos#>>

                    </table>
                </form>

                <table class="pure-table pure-table-horizontal">
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

                                <a href="?action=editar&id=<?php echo $r-><<#nombre_clave#>>; ?>">Editar</a>
                            </td>
                            <td>
                                <a href="?action=eliminar&id=<?php echo $r-><<#nombre_clave#>>; ?>">Eliminar</a>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                </table>

            </div>
        </div>

    </body>
</html>