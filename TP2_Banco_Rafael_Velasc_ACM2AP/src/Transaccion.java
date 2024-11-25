public class Transaccion {
    private TipoTransaccion tipo;
    private double monto;
    private String descripcion;

    public Transaccion(TipoTransaccion tipo, double monto, String descripcion) {
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipo + ", Monto: " + monto + ", Descripción: " + descripcion;
    }
}
