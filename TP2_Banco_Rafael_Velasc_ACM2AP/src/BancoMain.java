import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BancoMain {
    private static List<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        String[] opciones = {"Registrarse", "Iniciar Sesión", "Salir"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción",
                    "Sistema Bancario",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {
                case 0 -> registrarCliente();
                case 1 -> iniciarSesion();
            }
        } while (opcion != 2);

        JOptionPane.showMessageDialog(null, "Gracias por usar el sistema bancario.");
    }

    private static void registrarCliente() {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        String dni = JOptionPane.showInputDialog("Ingrese su DNI:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        if (buscarClientePorDNI(dni) != null) {
            JOptionPane.showMessageDialog(null, "El DNI ya está registrado.");
            return;
        }

        Cliente cliente = new Cliente(nombre, dni, contrasena);
        clientes.add(cliente);
        JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
    }

    private static void iniciarSesion() {
        String dni = JOptionPane.showInputDialog("Ingrese su DNI:");
        Cliente cliente = buscarClientePorDNI(dni);

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "DNI no encontrado.");
            return;
        }

        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");
        if (cliente.verificarContrasena(contrasena)) {
            JOptionPane.showMessageDialog(null, "Bienvenido, " + cliente.getNombre() + ".");
            gestionarCuentas(cliente);
        } else {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
        }
    }

    private static Cliente buscarClientePorDNI(String dni) {
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        return null;
    }

    private static void gestionarCuentas(Cliente cliente) {
        String[] opciones = {"Crear Cuenta", "Ver Cuentas", "Depositar", "Retirar", "Transferir", "Salir"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una operación",
                    "Gestión de Cuentas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {
                case 0 -> crearCuenta(cliente);
                case 1 -> verCuentas(cliente);
                case 2 -> depositar(cliente);
                case 3 -> retirar(cliente);
                case 4 -> transferir(cliente);
            }
        } while (opcion != 5);
    }

    private static void crearCuenta(Cliente cliente) {
        String[] tipos = {"Cuenta Corriente", "Caja de Ahorro"};
        int tipo = JOptionPane.showOptionDialog(
                null,
                "Seleccione el tipo de cuenta",
                "Crear Cuenta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        String numeroCuenta = JOptionPane.showInputDialog("Ingrese el número de la nueva cuenta:");
        if (tipo == 0) {
            double limite = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el límite de descubierto:"));
            cliente.agregarCuenta(new CuentaCorriente(numeroCuenta, limite));
        } else if (tipo == 1) {
            cliente.agregarCuenta(new CajaDeAhorro(numeroCuenta));
        }

        JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente.");
    }

    private static void verCuentas(Cliente cliente) {
        String cuentasInfo = "Cuentas del cliente:\n";
        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            cuentasInfo += "Número: " + cuenta.getNumeroCuenta() + ", Saldo: " + cuenta.getSaldo() + "\n";
        }
        JOptionPane.showMessageDialog(null, cuentasInfo);
    }

    private static void depositar(Cliente cliente) {
        String numeroCuenta = JOptionPane.showInputDialog("Ingrese el número de cuenta:");
        CuentaBancaria cuenta = buscarCuenta(cliente, numeroCuenta);

        if (cuenta != null) {
            double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a depositar:"));
            cuenta.depositar(monto);
            JOptionPane.showMessageDialog(null, "Depósito exitoso. Nuevo saldo: " + cuenta.getSaldo());
        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada.");
        }
    }

    private static void retirar(Cliente cliente) {
        String numeroCuenta = JOptionPane.showInputDialog("Ingrese el número de cuenta:");
        CuentaBancaria cuenta = buscarCuenta(cliente, numeroCuenta);

        if (cuenta != null) {
            double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a retirar:"));
            if (cuenta.retirar(monto)) {
                JOptionPane.showMessageDialog(null, "Retiro exitoso. Nuevo saldo: " + cuenta.getSaldo());
            } else {
                JOptionPane.showMessageDialog(null, "Fondos insuficientes.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada.");
        }
    }

    private static void transferir(Cliente cliente) {
        String numeroCuentaOrigen = JOptionPane.showInputDialog("Ingrese el número de cuenta origen:");
        CuentaBancaria cuentaOrigen = buscarCuenta(cliente, numeroCuentaOrigen);

        if (cuentaOrigen != null) {
            String numeroCuentaDestino = JOptionPane.showInputDialog("Ingrese el número de cuenta destino:");
            CuentaBancaria cuentaDestino = buscarCuentaEnClientes(numeroCuentaDestino);

            if (cuentaDestino != null) {
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a transferir:"));
                if (cuentaOrigen.retirar(monto)) {
                    cuentaDestino.depositar(monto);
                    JOptionPane.showMessageDialog(null, "Transferencia exitosa.");
                } else {
                    JOptionPane.showMessageDialog(null, "Fondos insuficientes en la cuenta origen.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cuenta destino no encontrada.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cuenta origen no encontrada.");
        }
    }

    private static CuentaBancaria buscarCuenta(Cliente cliente, String numeroCuenta) {
        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }

    private static CuentaBancaria buscarCuentaEnClientes(String numeroCuenta) {
        for (Cliente cliente : clientes) {
            CuentaBancaria cuenta = buscarCuenta(cliente, numeroCuenta);
            if (cuenta != null) {
                return cuenta;
            }
        }
        return null;
    }
}
