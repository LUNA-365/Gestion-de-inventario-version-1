/*
 */
package PruebasProyectoFinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Factura {

    private double precio,subtotal, total;
    private int stock;
    private String codigoPrd, codigoCln;
    private Mensajes ms = new Mensajes();
    private BufferedReader br = null;
    
    private String nf, cedula;
    ArrayList<Factura> factura = new ArrayList<>();
//    Producto codigo = new Producto(codigoPrd, stock);

    public Factura(double precio, double subtotal, double total, int stock, String codigoPrd, String codigoCln, String nf, String cedula) {
        this.precio = precio;
        this.subtotal = subtotal;
        this.total = total;
        this.stock = stock;
        this.codigoPrd = codigoPrd;
        this.codigoCln = codigoCln;
        this.nf = nf;
        this.cedula = cedula;
    }

    public Factura() {
    }
    
    public boolean setCedula(String cedula){
        if((cedula.length()==10) && (cedula.matches("[0-9]+"))){ 
            this.cedula = cedula;
            return true;}
        return false;
    }
    
    public String getCedula(String cedula){
        if(setCedula(cedula)==true){
        return this.cedula;
        }else{
         throw new IllegalArgumentException(ms.Mostrar("La cedula debe tener 10 dígitos numéricos."));            
        }   
    }
    public boolean setCodigoCln(String codigo) {
        if (codigo != null && codigo.matches("[0-9]+")) {
            this.codigoCln = codigo;
            return true;
        }
        return false;
    }

    public boolean setCodigoPrd(String codigoPrd) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
        String[] fila;
        while ((fila = cr.readNext()) != null) {
            String codigoPrdFila = fila[0];
            if (codigoPrdFila.equals(codigoPrd)) {
                this.codigoPrd = codigoPrd;
                return true;
            }
        }
        return false;
    }

    public String getCodigoPrd(String codigoPrd) throws IOException, FileNotFoundException, CsvValidationException {
        if (setCodigoPrd(codigoPrd) == true) {
            return this.codigoPrd;
        } else {
            throw new FileNotFoundException(ms.Mostrar("El codigo no existe en la base de datos."));
        }
    }

    public String getCodigoCln(String codigo) {
        if (setCodigoCln(codigo) == true) {
            return this.codigoCln;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Ingrese solo digitos numericos."));
        }
    }

    public boolean setPrecio(double precio) {
        if (precio > 0) {
            this.precio = precio;
            return true;
        }
        return false;
    }

    public double getPrecio(double precio) {
        if (setPrecio(precio) == true) {
            return this.precio;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Ingrese solo digitos numericos enteros."));
        }

    }

    public boolean setStock(int cantidad) {
        if (cantidad > 0) {
            this.stock = cantidad;
            return true;
        }
        return false;
    }

    public int getStock(int cantidad) {
        if (setStock(cantidad) == true) {
            return this.stock;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Ingrese solo digitos numericos enteros."));
        }
    }
    
    public boolean setNf(String numeroFactura){
        if ((numeroFactura.length() > 0) && !numeroFactura.equals("")) {
            this.nf= numeroFactura;
            return true;
        }
        return false;
    }
    
    public String getNf(String numeroFactura) {
        if (setNf(numeroFactura) == true) {
            return this.nf;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Dato invalido."));
        }
    }
    
    public double getPrecio() {
        return precio;
    }


    public int getStock() {
        return this.stock;
    }

    public String getCedula() {
        return this.cedula;
    }

    public String getCodigoPrd() {
        return this.codigoPrd;
    }

    public String getNf() {
        return this.nf;
    }


    public int PosicionArchivo(String codigo) throws CsvValidationException {
        int indice = -1;
        try {
            CSVReader cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                for (int j = 0; j < linea.length; j++) {
                    if (linea[j].equals(codigo)) {
                        indice = j;
                        return indice;
                    }
                }
            }
        } catch (IOException e) {
        }
        return -1;
    }
    
    
    public String Menu() {
        String op = ms.MenuFactura();
        return op;
    }
    
    public boolean dato(String a){
        if(!a.equals(" ") && a.matches("[0-9]+")){
            return true;
        }else{
            this.ms.Mensaje("Dato invalido");
        }
        return false;
    }
    public void Opciones() throws IOException {
        String op = "";
        do {
            try {
                if(dato(op = Menu())==true){
                    switch (op) {
                        case "1":
                            ListarFacturas();
                            break;
                        case "2":
                            AgregarVenta();
                            break;
                        case "3":
                            GenerarFactura();
                            break;
                        case "4":
                            EliminarFactura();
                            break;
                        case "5":
                            BuscarFactura();
                           break;
                        case "6":
                            break;
                        default:
                           ms.Mensaje("La opcion no existe en el menu");
                            break;
                    }
                }
            } catch (CsvValidationException e) {
            }
        } while (!op.equals("6"));
    }
    
    private  ArrayList<String> producto = new ArrayList<>();
    private  ArrayList<Integer> cantidad = new ArrayList<>();
    private  ArrayList<Double> pco = new ArrayList<>();
    private String nombre;
    private int num = 0;
    private HashMap<String, Double> mp = new HashMap<>();

    public  void AgregarVenta() throws IOException, CsvValidationException {
        BufferedWriter br = null;
        CSVWriter wr, wc = null;
        CSVReader cr, cv = null;
        int i = 0;
        int id = -1;
        boolean datoValido = false;
        ArrayList<String[]> al = new ArrayList<>();        
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {
        }

            do {
                try {
                    this.getCedula(this.cedula = ms.Entrada("Cedula: "));
                    datoValido = true;
                } catch (Exception  e) {}
            } while (!datoValido);
        datoValido = false;
        
        
        if (PosicionArchivo(this.getCedula()) == -1) {
            ms.Mensaje("La cedula no existe en la base de datos");
        } else if (PosicionArchivo(this.getCedula()) != -1) {
            
            try{
                cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
                String[] line;
                while ((line = cr.readNext()) != null) {
                    String ced = line[0];
                    nombre = line[1];
                    if (ced.equals(this.getCedula())) {
                        ms.Mensaje("Nombre del cliente: " + nombre);
                        break;
                    }
                }
                cr.close();
            }catch(IOException e){}
            
            do {
                try {
                    this.getCodigoPrd(this.codigoPrd = ms.Entrada("Codigo del producto: "));
                    datoValido = true;
                    } catch (IOException e) {}
                } while (!datoValido);
            datoValido = false;
                        
            try {
                String[] linea;
                cv = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
                while ((linea = cv.readNext()) != null) {
                    id++;
                    if (linea[0].equals(this.getCodigoPrd())) {
                        al.remove(id);
                    }
                }
            cv.close();
            } catch (CsvValidationException | IOException e) {}
        
        try{    
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv", true));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];

                String stockDisponible = c;
                int stkDisponible = Integer.parseInt(stockDisponible);

                if (a.equals(this.getCodigoPrd())) {
                    
                    do {
                        try {
                            this.getStock(this.stock = ms.EntradaInt("Codigo seleccionado: " + a
                            + "\nNombre del producto: " + b
                            + "\nStock: " + c
                            + "\nCantidad a vender: "));
                            datoValido = true;
                        } catch (Exception e) {
                        }
                    } while (!datoValido);
                    datoValido = false;
                    

                    if ((stkDisponible - this.stock) >= 0) {

                        int entradaStock = (stkDisponible - this.stock);
                        stockDisponible = Integer.toString(entradaStock);
                        ms.Mensaje("Stock actual: " + entradaStock);
                        
                        do {
                            try {
                                this.getPrecio(this.precio = ms.EntradaDouble("Precio de venta: "));
                                datoValido = true;
                            } catch (Exception e) {
                            }
                        } while (!datoValido);
                        datoValido = false;
                        
                        mp.put(this.getCodigoPrd(), this.getPrecio());
                        
                    } else {
                        ms.Mensaje("Stock agotado");
                    }

                    al.add(new String[]{this.codigoPrd, b, stockDisponible, d});
                    wr.writeAll(al);
                    wr.close();
                    cr.close();
                    
                }
                    
            }
            if (this.producto.contains(this.getCodigoPrd())) {
                i = this.producto.indexOf(this.getCodigoPrd());
                this.cantidad.set(i, this.cantidad.get(i) + this.stock);
            } else {
                this.producto.add(this.getCodigoPrd());
                this.cantidad.add(this.getStock());
                this.pco.add(this.getPrecio());
            }
        }catch(IOException e){}

            try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\ProductosAlmacen.csv"));
            bw.write("");
            bw.close();
            bw.flush();
            } catch (IOException e) {
            }

            try {
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv", true));
            wr.writeAll(al);
            wr.close();
            } catch (IOException e) {
            }
        }
        
    }
    
    
    public  void GenerarFactura() {
        CSVReader cr = null;
        CSVWriter wr, wc = null;
        num++;
        System.out.println("Numero de factura: " + this.num);
        System.out.printf("%-8s %-8s %-8s %-8s", "Producto", "Precio", "Cantidad ", "SubTotal\n");
        System.out.println("-------------------------------------");

        double total = 0, des = 0, st = 0;

        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                for (int j = 0; j < this.producto.size(); j++) {
                    String prd = this.producto.get(j);
                    if (linea[0].equals(prd)) {
                        double pre = mp.get(prd);
                        int canti = this.cantidad.get(j);
                        st = pre * canti;
                        total += st;
                        System.out.printf("%-8s %-8s %-8s %8.2f\n", prd, pre, canti, st);
                    }
                }

            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }

        System.out.println("--------------------------------------");
        System.out.printf("Total: %8.2f\n", total);
        System.out.println("");


        try {
            wr = new CSVWriter(new FileWriter("Archivos\\ListadoFacturas.csv", true));
            for (int j = 0; j < this.producto.size(); j++) {
                String[] lf = {String.valueOf(this.num),this.nombre, String.valueOf(total)};
                wr.writeNext(lf);
            }
            wr.close();
        } catch (IOException e) {
        }

        try {
            wc = new CSVWriter(new FileWriter("Archivos\\Factura.csv", true));
            for (int j = 0; j < this.producto.size(); j++) {
                String[] factura = {String.valueOf(this.num), this.producto.get(j), String.valueOf(cantidad.get(j)),
                     String.valueOf(this.pco.get(j)), String.valueOf(total)};
                wc.writeNext(factura);
            }
            wc.close();
        } catch (IOException e) {
        }
        
        for (int i = 0; i < this.producto.size(); i++) {
            this.producto.clear();
            this.cantidad.clear();
            this.pco.clear();
        }
    }
    
    public void EliminarFactura() throws CsvValidationException {
        ArrayList<String[]> alc = new ArrayList<>();
        ArrayList<String[]> alcp = new ArrayList<>();
        CSVReader cr,cv, cs= null;
        CSVWriter wr, wc = null;
        String codf;
        boolean datoValido = false;
        try {
            cr = new CSVReader(new FileReader("Archivos\\Factura.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                alc.add(linea);
            }
        } catch (CsvValidationException | IOException e) {
        }
        
        String cdg = "";
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                cdg = linea[0];
                alcp.add(linea);
            }
        } catch (CsvValidationException | IOException e) {
        }
        
        do {
            try {
                this.getNf(this.nf = ms.Entrada("Numero de factura: "));
                datoValido = true;
            } catch (Exception e) {
            }
        } while (!datoValido);
        datoValido = false;
        
        try {
            int i = -1;
            String[] linea;
            cr = new CSVReader(new FileReader("Archivos\\Factura.csv"));
            ArrayList<Integer> indicesEliminar = new ArrayList<>();

            while ((linea = cr.readNext()) != null) {
                String x = linea[0];
                i++;

                if (x.equals(this.getNf())) {
                    indicesEliminar.add(i);
                }
            }

                // Eliminar los elementos de la lista en orden inverso
                /*Evitar el desbordamiento de indice*/
                for (int j = indicesEliminar.size() - 1; j >= 0; j--) {
                    int index = indicesEliminar.get(j);
                    if (index < alc.size()) {
                        alc.remove(index);
                    }
                }
            } catch (CsvValidationException | IOException ex) {}

        String e = " ", d = " ",z = " ", aux="";
        int tsk = 0;
        try {
            cr = new CSVReader(new FileReader("Archivos\\Factura.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                if(a.equals(this.getNf())){
                    try {
                        int j = -1;
                        cv = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
                        wc = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));
                        String[] line;
                        while((line=cv.readNext())!=null){
                             d = line[0];
                             z = line[1];
                             e = line[2];
                             String f = line[3];
                             j++;
                             if(b.equals(d)){
                                   
                                 alcp.remove(j);
                                 
                                ms.Mensaje("Codigo del producto: " + b + 
                                         "\nCantidad vendida: " + c + 
                                         "\nStock almacen productos: " + e);
                                String stf = c;
                                int nst = Integer.parseInt(stf);
                                String stp = e;
                                int est = Integer.parseInt(e);
                                
                                tsk = (nst + est);
                                aux = String.valueOf(tsk);
                                ms.Mensaje("Stock recuperado: " + tsk);
                               
                             
                                alcp.add(new String[]{b, z, String.valueOf(tsk), f});
                                wc.writeAll(alcp);
                                wc.close();
                                cv.close();
      
                                try {
                                    BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\ProductosAlmacen.csv"));
                                    bw.write("");
                                    bw.close();
                                    bw.flush();
                                } catch (IOException ex) {}
        
                                try {
                                    wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));
                                    wr.writeAll(alcp);
                                    wr.close();
                                } catch (IOException ex) {}
                             }
                        }
                        cv.close();
                    } catch (CsvValidationException | IOException ex) {
                    }
                }
            }
        } catch (CsvValidationException | IOException ex) {}
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Factura.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException ex) {
        }

        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Factura.csv", true));
            wr.writeAll(alc);
            wr.close();
        } catch (IOException ex) {
        }

    }
    
    public  void BuscarFactura() throws IOException, CsvValidationException {
        CSVReader cr = null;
        boolean datoValido = false;
        do {
            try {
                this.getNf(this.nf = ms.Entrada("Numero de factura: "));
                datoValido = true;
            } catch (Exception e) {
            }
        } while (!datoValido);
        datoValido = false;
        
        int i = -1;
        try {
            cr = new CSVReader(new FileReader("Archivos\\Facturas.csv"));
            String[] linea, t;
            while (((linea = cr.readNext()) != null)) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                String e = linea[4];
                if (a.contains(this.getNf())) {
                    ms.Mensaje(
                            "\nCodigo del producto: " + b
                            + "\nCantidad: " + c
                            + "\nPrecio: " + d
                            + "\nTotal: " + e);
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }
    
    public void ListarFacturas() {
        CSVReader cr, cv = null;
        System.out.printf("%-8s %-8s %-8s", "Numero de factura", "Cliente", "Total vendido\n");
        System.out.println("-------------------------------------");
        try {
            cr = new CSVReader(new FileReader("Archivos\\ListadoFacturas.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                ms.Mensaje("Numero de factura: " + a
                        + "\nCliente: " + b
                        + "\nTotal vendido: " + c);
                System.out.printf("%-8s %-10s %-20s\n", a, b, c);
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }
    @Override
    public String toString() {
        String cadena = "Factura: " + this.num
                + "\nCodigo del producto:" + this.codigoPrd
                + "\nPrecio: " + this.precio
                + "\nCantidad: " + this.stock
                + "\nTotal: " + this.total;
        return cadena;
    }
    
    
}
