/*
 */
package PruebasProyectoFinal;

import javax.swing.JOptionPane;

public class Mensajes {
    private JOptionPane ms = new JOptionPane();

    public JOptionPane getMs() {
        return this.ms;
    }

    public void setMs(JOptionPane ms) {
        this.ms = ms;
    }
   
    public String Entrada(String mensaje) {
        String entrada = this.getMs().showInputDialog(mensaje);
        return entrada;
    }
    
    public int EntradaInt(String mensaje) {
        String entrada = this.getMs().showInputDialog(null,mensaje);
        int valor = Integer.parseInt(entrada);
        return valor;
    }
    public double EntradaDouble(String mensaje) {
        String entrada = this.getMs().showInputDialog(null,mensaje);
        double valor = Double.parseDouble(entrada);
        return valor;
    }
    public void Mensaje(String mensaje){
        this.getMs().showMessageDialog(null,mensaje);
    }
    
    public String Mostrar(String mensaje) {
        this.getMs().showMessageDialog(null, mensaje + "\n");
        return mensaje;
    }
    public String MenuPrincipal(){
        String op = Entrada("Menu" + 
                "\n1) Productos" + 
                "\n2) Cliente" + 
                "\n3) Proveedor" + 
                "\n4) Factura " +
                "\n5) Compra " + 
                "\n6) Provincia " +
                "\n7) Ciudad " + 
                "\n8) Cambio de usuario " +
                "\n9) Salir" +
                "\nIntroduzca una opcion: ");
        return op;
    }
     public String SubMenu() {
        String op = Entrada("1) Listado " + 
                "\n2) Agregar " + 
                "\n3) Modificar"  + 
                "\n4) Eliminar" + 
                "\n5) Salir" +
                "\nIngrese una opcion: ");
        return op;
    }
    public String MenuFactura() {
        String op = Entrada("Menu"
                + "\n1) Listar factura"
                + "\n2) Agregar venta"
                + "\n3) Generar factura "
                + "\n4) Eliminar factura"
                + "\n5) Buscar Factura"
                + "\n6) Cancelar"
                + "\nIntroduzca una opcion: ");
        return op;
    }   
    public String MenuFacturaCompra() {
        String op = Entrada("Menu"
                + "\n1) Listar factura"
                + "\n2) Agregar compra"
                + "\n3) Generar factura "
                + "\n4) Eliminar factura"
                + "\n5) Buscar Factura"
                + "\n6) Cancelar"
                + "\nIntroduzca una opcion: ");
        return op;
    }   
    
    
}
