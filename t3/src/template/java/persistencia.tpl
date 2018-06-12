package <<#paquete_modelos#>>;

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
}