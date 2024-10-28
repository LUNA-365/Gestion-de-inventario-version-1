/*
 */
package PruebasProyectoFinal;
import java.io.IOException;
public class PruebasProyecto {
    public static void main(String[] args) throws IOException, Exception {
        if(login.login()==true){
            Opciones();
        }

    }
    public static Mensajes ms = new Mensajes();
    public static Login login = new Login();
    public static Producto producto = new Producto();
    public static Cliente cliente = new Cliente();
    public static Provincia provincia = new Provincia();
    public static Ciudad ciudad = new Ciudad();
    public static Proveedor proveedor = new Proveedor();
    public static Factura factura = new Factura();
    public static Compra compra = new Compra();
    
    public static boolean dato(String a){
        if(!a.equals(" ") && a.matches("[0-9]+")){
            return true;
        }else{
            ms.Mensaje("Dato invalido");
        }
        return false;
    }
    
    public static void Opciones() {
        boolean datoValido = false;
        String op = "";
       
        do {
            try {
                if(dato(op = ms.MenuPrincipal())==true){
                    switch (op) {
                    case "1":
                        producto.Opciones();
                        break;
                    case "2":
                        cliente.Opciones();
                        break;
                    case "3":
                        proveedor.Opciones();
                        break;
                    case "4":
                        factura.Opciones();
                        break;
                    case "5":
                        compra.Opciones();
                        break;
                    case "6":
                        provincia.Opciones();
                        break;
                    case "7":
                        ciudad.Opciones();
                        break;
                    case "8":
                        login.login2();
                    case "9":
                        break;
                    default:
                    ms.Mensaje("La opcion no existe en el menu");
                    break;
                    }
                }
            } catch (Exception e) {
            }
        } while (!op.equals("9"));
    }
   
}