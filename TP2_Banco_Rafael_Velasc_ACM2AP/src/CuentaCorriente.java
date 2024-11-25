public class CuentaCorriente extends CuentaBancaria {
    private double limiteDescubierto;

    public CuentaCorriente(String numeroCuenta, double limiteDescubierto) {
        super(numeroCuenta);
        this.limiteDescubierto = limiteDescubierto;
    }

    @Override
    public void depositar(double monto) {
        saldo += monto;
        registrarTransaccion(TipoTransaccion.DEPOSITO, monto, "Depósito en Cuenta Corriente");
    }

    @Override
    public boolean retirar(double monto) {
        if (saldo + limiteDescubierto >= monto) {
            saldo -= monto;
            registrarTransaccion(TipoTransaccion.RETIRO, monto, "Retiro de Cuenta Corriente");
            return true;
        }
        return false;
    }
}
