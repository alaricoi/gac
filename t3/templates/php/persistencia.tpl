<?php
class <<#nombreClase#>>
{
    <<#seccion_loop_campos#>>
    private $<<#nombre_campo#>>;
    <</#seccion_loop_campos#>>

    public function __GET($k){ return $this->$k; }
    public function __SET($k, $v){ return $this->$k = $v; }
}

/*
  Desde el comentario  <<#custom_code#>> hasta el
  comentario con <</#custom_code#>> se reserva para el codigo
  que se desee mantener despues de volver a generar las clases generaci�n
  todo codigo que este entre estas etiquetas se mantendr� 
*/
/*<<#custom_code#>>*/

/*<</#custom_code#>>*/
/*
fin de las etiquetas de custom code, todo lo que quede fuera
de este codigo ser� reemplazo en una nueva generaci�n
*/
?>