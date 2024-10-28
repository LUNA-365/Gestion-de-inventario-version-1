/*
 */
package PruebasProyectoFinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Producto {

    private Mensajes ms = new Mensajes();
    private ArrayList<Producto> prd = new ArrayList<>();
    private String codigoPrd, nombrePrd, stock, codigoPrv;
    private HashMap<String, String> mp = new HashMap<>();
    private ArrayList<String[]> lista = new ArrayList<>();

    private int  stk = 0;
    private boolean codigoValido = false;
    
    public Producto(String codigoPrd, String nombrePrd, String stock, String codigoPrv) {
        this.codigoPrd = codigoPrd;
        this.nombrePrd = nombrePrd;
        this.stock = stock;
        this.codigoPrv = codigoPrv;
    }

    public Producto() {
    }

    public Producto(String codigoPrd, String stock) {
        this.codigoPrd = codigoPrd;        
        this.stock = stock;
    }

    public boolean setCodigoPrd(String codigoPrd){
        if ((codigoPrd.length() > 0) && !codigoPrd.equals("")) {
            this.codigoPrd= codigoPrd;
            return true;
        }
        return false;
    }
    
    public String getCodigoPrd(String codigoPrd) {
        if (setCodigoPrd(codigoPrd) == true) {
            return this.codigoPrd;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Dato invalido o el codigo ya existe en la base de datos."));
        }
    }
    
    //Metodo adicional del getCodigoPrd para poder obtener su indice en la lista
    public String getCodigoPrd()  {
        return this.codigoPrd;
    }

    public String getNombrePrd() {
        return this.nombrePrd;
    }

    public String getStock() {
        return this.stock;
    }

    public String getCodigoPrv() {
        return this.codigoPrv;
    }
    
    
    public boolean setNombrePrd(String nombrePrd) {
        if (nombrePrd.matches("[a-zA-Z]+") && !nombrePrd.equals(" ")) {
            this.nombrePrd = nombrePrd;
            return true;
        }
        return false;
    }

    public String getNombrePrd(String nombrePrd) {
        if (setNombrePrd(nombrePrd) == true) {
            return this.nombrePrd;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Ingrese solo datos alfabeticos."));
        }
    }

    public boolean setStock(String stock) {
        if (stock.length() > 0 && stock.matches("[0-9]+")) {
            this.stock = stock;
            return true;
        }
        return false;
    }

    public String getStock(String stock) {
        if (setStock(stock) == true) {
            return this.stock;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Ingrese solo digitos numericos."));
        }
    }

    public boolean setCodigoPrv(String codigoPrv) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
        String[] fila;
        while ((fila = cr.readNext()) != null) {
            String codigoPrvFila = fila[0];
            if ((codigoPrvFila.equals(codigoPrv))) {
                this.codigoPrv = codigoPrv;
                return true;
            }else if (!codigoPrvFila.equals(codigoPrv)){
                ms.Mensaje("El codigo no existe en la base de datos.");
            }
        }
        return false;
    }

    public String getCodigoPrv(String codigoPrv) throws IOException, FileNotFoundException, CsvValidationException  {
        if (setCodigoPrv(codigoPrv) == true) {
            return this.codigoPrv;
        } else {
            throw new FileNotFoundException(ms.Mostrar("Datos invalidos o el codigo no existe en la base de datos."));
        }
    }

    public int Posicion(String codigo) {
       int  i = -1;
        for (Producto a : prd) {
            i++;
            //Metodo getCodigoPrd sin argumento para poder almacenar distintos codigo
            if (a.getCodigoPrv().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

     public int PosicionArchivo(String codigo) throws CsvValidationException {
        int indice = -1;
        try {
            CSVReader cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
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
        String op = ms.SubMenu();
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
    public void Opciones() {
        String op = "";
        do {
            try {
                if(dato(op = Menu())==true){
                    switch (op) {
                        case "1":
                            ListarProductos();
                            break;
                        case "2":
                            AgregarProducto();
                            break;
                        case "3":
                            ModificarProducto();
                            break;
                        case "4":
                            EliminarProducto();
                            break;
                        case "5":
                           break;
                        default:
                           ms.Mensaje("La opcion no existe en el menu");
                            break;
                    }
                }
            } catch (CsvValidationException | IOException e) {
            }
        } while (!op.equals("5"));
        
    }
    
     
    public void AgregarProducto() throws CsvValidationException, FileNotFoundException, IOException {
        ArrayList<String[]> al = new ArrayList<>();
        boolean datoValido = false;
        CSVReader cr = null; 
        CSVWriter wr = null;
        do {
            do {
                try {
                    this.getCodigoPrd(this.codigoPrd = ms.Entrada("Codigo: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCodigoPrd())!=-1){
                        ms.Mensaje("El codigo ya existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCodigoPrd())!=-1);
        datoValido = false;
        
         do {
            try {
                this.getNombrePrd(this.nombrePrd = ms.Entrada("Nombre del producto: "));
                    datoValido = true;
                } catch (Exception  e) {}
            } while (!datoValido);
        datoValido = false; 
        
        do {
            try {
                this.getStock(this.stock = ms.Entrada("Stock: "));
                    datoValido = true;
                } catch (Exception  e) {}
            } while (!datoValido);
        datoValido = false;   

         do {
            try {
                this.getCodigoPrv(this.codigoPrv = ms.Entrada("Codigo del proveedor: "));
                    datoValido = true;
                } catch (CsvValidationException | IOException  e) {}
            } while (!datoValido);
        datoValido = false;

        prd.add(new Producto(codigoPrd, nombrePrd, stock, codigoPrv));
    
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));
            al.add(new String[]{this.getCodigoPrd(), this.getNombrePrd(),this.getStock(), this.getCodigoPrv()});
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
    }

    public void EliminarProducto() throws IOException, FileNotFoundException, CsvValidationException {
        ArrayList<String[]> al = new ArrayList<>();
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}        
        
        this.codigoPrd = ms.Entrada("Digite el codigo del producto: ");
        
        if (PosicionArchivo(this.codigoPrd) != -1) { 
            ms.Mensaje("Codigo eliminado exitosamente.");
           try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCodigoPrd())){
                    al.remove(i);
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {} 
      
        }else if (Posicion(this.codigoPrd)== -1){
            ms.Mensaje("El codigo no existe en el sistema.");
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\ProductosAlmacen.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
    }

    public void ModificarProducto() throws CsvValidationException  {
        ArrayList<String[]>al = new ArrayList<>();
        boolean datoValido = false;
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}
        
        do {
            do {
                try {
                    this.getCodigoPrd(this.codigoPrd = ms.Entrada("Codigo: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCodigoPrd())==-1){
                        ms.Mensaje("El codigo no existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCodigoPrd())==-1);
        datoValido = false;
        
        try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCodigoPrd())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {}    
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));            
            
            String [] linea;
            while((linea=cr.readNext())!=null){
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                if(a.equals(this.codigoPrd)){
                    
                    do {
                       try {
                        this.getCodigoPrd(this.codigoPrd = ms.Entrada("Codigo: " + a +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false; 
                    
                    do {
                       try {
                        this.getNombrePrd(this.nombrePrd = ms.Entrada("Nombre del producto: " + b +
                                "\nNuevo producto: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;                   
                    
                   do {
                       try {
                        this.getStock(this.stock = ms.Entrada("Stock: " + c +
                                "\nNuevo stock: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;  
                    
                    do {
                       try {
                        this.getCodigoPrv(this.codigoPrv = ms.Entrada("Codigo del proveedor: " + d +
                                "\nNuevo codigo del proveedor: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false; 
                    
                    al.add(new String []{this.getCodigoPrd(),this.getNombrePrd(), 
                    this.getStock(),this.getCodigoPrv()});
                    wr.writeAll(al);
                    wr.close();
                    cr.close();
                }
            }
            cr.close();
        }catch(CsvValidationException | IOException e) {}
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\ProductosAlmacen.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\ProductosAlmacen.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
    }
    
      public void ListarProductos() {
        CSVReader cr, cv = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\ProductosAlmacen.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                ms.Mensaje("Codigo del producto: " + a +
                         "\nNombre del producto: " + b +
                         "\nStock: " + c + 
                         "\nCodigo del proveedor: " + d);
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }
    @Override
    public String toString() {
        String cadena = "Codigo del producto: " + this.codigoPrd
                + "\nNombre del producto: " + this.nombrePrd
                + "\nStock: " + this.stock
                + "\nCodigo del Proveedor: " + this.codigoPrv;
        return cadena;
    }
}
