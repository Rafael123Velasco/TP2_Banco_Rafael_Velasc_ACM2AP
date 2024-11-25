import java.util.ArrayList;
import java.util.List;

public abstract class CuentaBancaria {
    protected String numeroCuenta;
    protected double saldo;
    protected List<Transaccion> historial;

    public CuentaBancaria(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0;
        this.historial = new ArrayList<>();
    }

    public abstract void depositar(double monto);
    public abstract boolean retirar(double monto);

    public void registrarTransaccion(TipoTransaccion tipo, double monto, String descripcion) {
        historial.add(new Transaccion(tipo, monto, descripcion));
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transaccion> getHistorial() {
        return historial;
    }
}
