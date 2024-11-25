import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private String dni;
    private String contrasena;
    private List<CuentaBancaria> cuentas;

    public Cliente(String nombre, String dni, String contrasena) {
        this.nombre = nombre;
        this.dni = dni;
        this.contrasena = contrasena;
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public boolean verificarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public void modificarInformacion(String nuevoNombre, String nuevaContrasena) {
        this.nombre = nuevoNombre;
        this.contrasena = nuevaContrasena;
    }
}
