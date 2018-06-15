<?php
require_once 'Artists.php';
require_once 'ArtistsDao.php';

$artists = new Artists();
$model = new ArtistsModel();

if(isset($_REQUEST['action']))
{
    switch($_REQUEST['action'])
    {
        case 'actualizar':

                
                   $artists->__SET('artistid', $_REQUEST['artistid']);
               
                   $artists->__SET('name', $_REQUEST['name']);
               

            $model->Actualizar($artists);
            header('Location: ArtistsVista.php');
            break;

        case 'registrar':
              
                   $artists->__SET('artistid', $_REQUEST['artistid']);
               
                   $artists->__SET('name', $_REQUEST['name']);
               

            $model->Registrar($artists);
            header('Location: ArtistsVista.php');
            break;

        case 'eliminar':
            $model->Eliminar($_REQUEST['artistid']);
            header('Location: ArtistsVista.php');
            break;

        case 'editar':
            $artists = $model->Obtener($_REQUEST['artistid']);
            break;
    }
}

?>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Artists.</title>
       
    </head>
    <body style="padding:15px;">

        <div >
            <div ">

                <form action="?action=<?php echo $artists->artistid > 0 ? 'actualizar' : 'registrar'; ?>" method="post" >
                    <input type="hidden" name="artistid" value="<?php echo $artists->__GET('artistid'); ?>" />

                    <table style="width:500px;">
                      
                           <tr>
                            <th style="text-align:left;">artistid</th>
                            <td><input type="text" name="artistid" value="<?php echo $artists->__GET('artistid'); ?>" style="width:100%;" /></td>
                        </tr>
                       
                           <tr>
                            <th style="text-align:left;">name</th>
                            <td><input type="text" name="name" value="<?php echo $artists->__GET('name'); ?>" style="width:100%;" /></td>
                        </tr>
                       

                    </table>
                    	
					 <input type="submit" value="aceptar">
                </form>

                <table >
                    <thead>
                        <tr>
                         
                            <th style="text-align:left;">artistid</th>
                        
                            <th style="text-align:left;">name</th>
                        
                            <th></th>
                            <th></th>

                        </tr>
                    </thead>
                    <?php foreach($model->Listar() as $r): ?>
                        <tr>
                        
                            <td><?php echo $r->__GET('artistid'); ?></td>

                             
                            <td><?php echo $r->__GET('name'); ?></td>

                             
                            <td>

                                <a href="?action=editar&artistid=<?php echo $r->artistid; ?>">Editar</a>
                            </td>
                            <td>
                                <a href="?action=eliminar&artistid=<?php echo $r->artistid; ?>">Eliminar</a>
                            </td>
                        </tr>
                    <?php endforeach; ?>
                </table>

            </div>
        </div>

    </body>
</html>