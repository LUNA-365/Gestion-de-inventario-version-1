
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

public class Proveedor {
    private Mensajes ms = new Mensajes();
    private HashMap<String, String> mp = new HashMap<>();
    private String ruc,nombre, telefono, direccion, provincia, ciudad;
    private boolean datoValido = false;
    private ArrayList<Proveedor> prv = new ArrayList<>();
    int i = 0;
    public Proveedor(String ruc,String nombre, String telefono, String direccion, String provincia, String ciudad) {
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.provincia = provincia;
        this.ciudad = ciudad;
    }

    public Proveedor() {
    }
    
    public boolean setRuc(String ruc){
        if((ruc.length()==13) && (ruc.matches("[0-9]+"))){ 
            this.ruc = ruc;
            return true;}
        return false;
    }
    
    public String getRuc(String ruc){
        if(setRuc(ruc)==true){
        return this.ruc;
        }else{
        throw new IllegalArgumentException(ms.Mostrar("El RUC debe tener 13 dígitos numéricos."));            
        }   
    }
    public boolean setNombre(String nombre){
        String [] palabras = nombre.trim().split(" +");
        if((palabras.length >= 2) && (palabras.length <= 4)){
            this.nombre = nombre;
            return true;};
        return false;
    }
    
    public String getNombre(String nombre){
        if(setNombre(nombre)==true ){ return this.nombre;}
        else{
            throw new IllegalArgumentException(ms.Mostrar("Debe tener almenos 2 palabras alfabeticas"));
        }
    }    
    
    public boolean setTelefono(String telefono){
        if((telefono.length()<=9) && (telefono.matches("[0-9]+"))){
            this.telefono = telefono;
            return true;}
        return false;
    }
    
    public String getTelefono(String telefono){
        if(setTelefono(telefono)==true){
        return this.telefono;
        }else{
         throw new IllegalArgumentException(ms.Mostrar("El telefono debe tener dígitos numéricos."));            
        }   
    }    
    
    public boolean setDireccion(String direccion){
        String [] palabras = direccion.trim().split(" +");
        if(palabras.length == 2 ){
            this.direccion = direccion;
            return true;};
        return false;
    }
    
    public String getDireccion(String direccion){
        if(setDireccion(direccion)==true){return this.direccion;}
        else{
        throw new IllegalArgumentException(ms.Mostrar("La direccion debe tener 2 palabras alfabeticas"));    
        }
    }
    
    public boolean setProvincia(String provincia) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
        String[] fila;
        while ((fila = cr.readNext()) != null) {
            if (fila[0].equals(provincia)) {
                this.provincia = provincia;
                return true;
            }
        }
        return false;
    }

    public String getProvincia(String provincia) throws IOException, FileNotFoundException, CsvValidationException {
        if (setProvincia(provincia) == true) {
            return this.provincia;
        } else {
            throw new FileNotFoundException(ms.Mostrar("El codigo no existe en la base de datos."));
        }
    }
    public boolean setCiudad (String ciudad) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
        String[] fila;
        while ((fila = cr.readNext()) != null) {
            if (fila[0].equals(ciudad)) {
                this.ciudad = ciudad;
                return true;
            }
        }
        return false;
    }

    public String getCiudad (String ciudad) throws IOException, FileNotFoundException, CsvValidationException {
        if (setCiudad(ciudad) == true) {
            return this.ciudad;
        } else {
            throw new FileNotFoundException(ms.Mostrar("El codigo no existe en la base de datos."));
        }
    }

    public String getRuc() {
        return ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCiudad() {
        return ciudad;
    }
    
    public int Posicion(String ruc) {
        i = -1;
        for(Proveedor a : prv){
            i++;
            if(a.getRuc().equals(ruc)){
                return i;
            }
        }
        return -1;
    }
    public int PosicionArchivo(String codigo) throws CsvValidationException {
        int indice = -1;
        try {
            CSVReader cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
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
                            ListarProveedor();
                            break;
                        case "2":
                            AgregarProveedor();
                            break;
                        case "3":
                            ModificarProveedor();
                            break;
                        case "4":
                            EliminarProveedor();
                            break;
                        case "5":
                           break;
                        default:
                            throw new AssertionError();
                    }
                }
            } catch (CsvValidationException e) {
            }
        } while (!op.equals("5"));
        
    }
    
    public void AgregarProveedor() throws CsvValidationException{
       CSVReader cr = null;
        CSVWriter wr = null;
        ArrayList<String[]> al = new ArrayList<>();
        boolean datoValido = false;
        
       do {
            do {
                try {
                    this.getRuc(this.ruc = ms.Entrada("RUC: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getRuc())!=-1){
                        ms.Mensaje("El RUC ya existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getRuc())!=-1);
        datoValido = false;
        
        do{
            try {
                this.getNombre(this.nombre = ms.Entrada("Nombre y apellido"));
                datoValido = true;
                } catch (Exception e) {}
        }while(!datoValido);
        datoValido =false;
        
        do{    
            try {
              this.getTelefono(this.telefono = ms.Entrada("Telefono: "));
              datoValido = true;  
            } catch (Exception e) {}
        } while (!datoValido);
        datoValido =false;
        
        do{    
            try {
              this.getDireccion(this.direccion = ms.Entrada("Direccion: "));
              datoValido = true;  
            } catch (Exception e) {}
        } while (!datoValido);
        datoValido =false;

        do {            
         try {
             this.getProvincia(this.provincia = ms.Entrada("Provincia: "));
              datoValido=true;
            }catch (Exception e) {}
        } while (!datoValido);
        datoValido =false;
        
        do {            
         try {
             this.getCiudad(this.ciudad = ms.Entrada("Cuidad: "));
              datoValido=true;
            }catch (Exception e) {}
        } while (!datoValido);
        datoValido =false;
        
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));
            al.add(new String[]{this.getRuc(), this.getNombre(), this.getDireccion(),
                this.getTelefono(),this.getProvincia(), 
                this.getCiudad()});
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}         
        
        prv.add(new Proveedor(ruc, nombre, telefono, direccion, provincia, ciudad));
    }
    
    public void EliminarProveedor() throws CsvValidationException  {
        ArrayList<String[]> al = new ArrayList<>();
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;        
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}  
        
        this.getRuc(this.ruc = ms.Entrada("RUC: "));
        
        if (PosicionArchivo(this.ruc) != -1) {
            
           try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getRuc())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {} 
            ms.Mensaje("Proveedor eliminado exitosamente");
        }else if (Posicion(this.ruc)==-1){
            ms.Mensaje("El RUC no existe en el sistema.");
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Proveedor.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
    }
    
    public void ModificarProveedor() throws CsvValidationException {
        ArrayList<String[]>al = new ArrayList<>();
        boolean datoValido = false;
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}
        
        do {
            do {
                try {
                    this.getRuc(this.ruc = ms.Entrada("Ingrese el RUC: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getRuc())==-1){
                        ms.Mensaje("El RUC no existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getRuc())==-1);
        datoValido = false;
        
        try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getRuc())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {}    
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));            
            
            String [] linea;
            while((linea=cr.readNext())!=null){
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                String e = linea[4];
                String f = linea[5];
                if(a.equals(this.ruc)){
                    
                    do {
                       try {
                        this.getRuc(this.ruc = ms.Entrada("RUC: " + a +
                                "\nNuevo ruc: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false; 
                    
                    do {
                       try {
                        this.getNombre(this.nombre = ms.Entrada("Nombre: " + b +
                                "\nNuevo nombre: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    do{    
                        try {
                        this.getTelefono(this.telefono = ms.Entrada("Telefono: " + c +
                        "\nNuevo telefono: "));
                        datoValido = true;  
                     } catch (Exception ex) {}
                     } while (!datoValido);
                    datoValido =false;
                    
                   do {
                       try {
                        this.getDireccion(this.direccion = ms.Entrada("Direccion: " + d +
                                "\nNuevo direccion: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;  
                    
                    do {
                       try {
                        this.getProvincia(this.provincia = ms.Entrada("Codigo de la provincia: " + e +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    do {
                       try {
                        this.getCiudad(this.ciudad = ms.Entrada("Codigo de la cuidad: " + f +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    al.add(new String []{this.getRuc(),this.getNombre(), this.getTelefono(),
                    this.getDireccion(),this.getProvincia()});
                    wr.writeAll(al);
                    wr.close();
                    cr.close();
                }
            }
            cr.close();
        }catch(CsvValidationException | IOException exx) {}
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Proveedor.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Proveedor.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
    }
    public void ListarProveedor() {
        CSVReader cr, cv = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\Proveedor.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                String e = linea[4];
                String f = linea[5];
                ms.Mensaje("RUC : " + a +
                        "\nNombre del proveedor: " + b +
                        "\nTelefono: " + c + 
                        "\nDireccion: " + d + 
                        "\nCodigo de la Provincia: " + e + 
                        "\nCodigo de la Ciudad: " + f);
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }
    @Override
    public String toString() {
        String cadena = "Proveedor" + 
                "\nRUC: " + this.ruc + 
                "\nNombre: " + this.nombre + 
                "\nTelefono: " + this.telefono + 
                "\nDireccion: " + this.direccion + 
                "\nProvincia: " + this.provincia + 
                "\nCiudad: " + this.ciudad;
        return cadena;
    }
    
}
