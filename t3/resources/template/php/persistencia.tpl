class <<#nombreClase#>>
{
    <<#seccion_loop_campos#>>
    private <<#nombre_campo#>>;
    <</#seccion_loop_campos#>>

    public function __GET($k){ return $this->$k; }
    public function __SET($k, $v){ return $this->$k = $v; }
}