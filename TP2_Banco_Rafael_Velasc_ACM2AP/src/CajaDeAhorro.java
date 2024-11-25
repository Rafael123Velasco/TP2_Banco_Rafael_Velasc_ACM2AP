public class CajaDeAhorro extends CuentaBancaria {

    public CajaDeAhorro(String numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public void depositar(double monto) {
        saldo += monto;
        registrarTransaccion(TipoTransaccion.DEPOSITO, monto, "Depósito en Caja de Ahorro");
    }

    @Override
    public boolean retirar(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            registrarTransaccion(TipoTransaccion.RETIRO, monto, "Retiro de Caja de Ahorro");
            return true;
        }
        return false;
    }
}
