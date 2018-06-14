
<?php
/*
  Desde el comentario  <<#custom_code#>> hasta el
  comentario con <</#custom_code#>> se reserva para el codigo
  que se desee mantener despues de volver a generar las clases generación
  todo codigo que este entre estas etiquetas se mantendrá 
*/
/*<<#custom_code_head#>>*/

/*<</#custom_code_head#>>*/
/*
fin de las etiquetas de custom code, todo lo que quede fuera
de este codigo será reemplazo en una nueva generación
*/

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
  que se desee mantener despues de volver a generar las clases generación
  todo codigo que este entre estas etiquetas se mantendrá 
*/
/*<<#custom_code_body#>>*/

/*<</#custom_code_body#>>*/
/*
fin de las etiquetas de custom code, todo lo que quede fuera
de este codigo será reemplazo en una nueva generación
*/
?>