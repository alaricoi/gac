package <<#paquete_modelos#>>;
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
public class <<#nombreClase#>>
{
<<#seccion_loop_campos#>>
  private <<#tipo_campo#>> <<#nombre_campo#>>;
<</#seccion_loop_campos#>>

<<* --- getters --- *>>
<<#seccion_loop_campos#>>
  public <<#tipo_campo#>> get<<#Nombre_campo#>>();
  {
    return <<#nombre_campo#>>;
  }
<</#seccion_loop_campos#>>

<<* --- setters --- *>>
<<#seccion_loop_campos#>>
  public void set<<#Nombre_campo#>>(<<#tipo_campo#>> <<#nombre_campo#>>);
  {
    this.<<#nombre_campo#>> = <<#nombre_campo#>>;
  }
<</#seccion_loop_campos#>>


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
}