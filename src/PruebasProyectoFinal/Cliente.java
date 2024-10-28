
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

public class Cliente {
    private String cedula, nombre, direccion, provincia, ciudad;
    private Mensajes ms = new Mensajes();
    private HashMap<String,String> mp = new HashMap<>();
    private ArrayList<Cliente> clt = new ArrayList<>();
    int i = 0;
    boolean datoValido = false;
    public Cliente (String cedula, String nombre, String direccion, String provincia, String ciudad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.provincia = provincia;
        this.ciudad = ciudad;
    }

    public Cliente() {
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
            throw new IllegalArgumentException(ms.Mostrar("Debe tener 2 palabras alfabeticas"));
        }
    }
    
    public boolean setDireccion(String direccion){
        String [] palabras = direccion.trim().split(" +");
        if(palabras.length == 3 ){
            this.direccion = direccion;
            return true;};
        return false;
    }
    
    public String getDireccion(String direccion){
        if(setDireccion(direccion)==true){return this.direccion;}
        else{
        throw new IllegalArgumentException(ms.Mostrar("La direccion debe tener 3 palabras alfabeticas"));    
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
 
    public String getCedula(){
        return this.cedula;
    }

    public String getNombre() {
        return nombre;
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
                            ListarClientes();
                            break;
                        case "2":
                            AgregarCliente();
                            break;
                        case "3":
                            ModificarCliente();
                            break;
                        case "4":
                            EliminarCliente();
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
    
    public void AgregarCliente() throws CsvValidationException{
        CSVReader cr = null;
        CSVWriter wr = null;
        ArrayList<String[]> al = new ArrayList<>();
        boolean datoValido = false;
        
       do {
            do {
                try {
                    this.getCedula(this.cedula = ms.Entrada("Cedula: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCedula())!=-1){
                        ms.Mensaje("La cedula ya existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCedula())!=-1);
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
        
        clt.add(new Cliente(cedula, nombre, direccion, provincia, ciudad));
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));
            al.add(new String[]{this.getCedula(), this.getNombre(), this.getDireccion(),this.getProvincia(), 
                this.getCiudad()});
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {} 
    }
    
    public void EliminarCliente() throws CsvValidationException {
        ArrayList<String[]> al = new ArrayList<>();
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;        
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}  
        
        this.cedula=ms.Entrada("Ingrese la cedula: ");
        
        if (PosicionArchivo(this.cedula) != -1) {
            
           try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCedula())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {} 
            ms.Mensaje("Cliente eliminado exitosamente");
        }else if (PosicionArchivo(this.cedula)==-1){
            ms.Mensaje("La cedula no existe en la base de datos.");
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Cliente.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
    }
    
    public void ModificarCliente() throws CsvValidationException, FileNotFoundException, IOException {
        ArrayList<String[]>al = new ArrayList<>();
        boolean datoValido = false;
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}
        
        do {
            do {
                try {
                    this.getCedula(this.cedula = ms.Entrada("Codigo: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCedula())==-1){
                        ms.Mensaje("El codigo no existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCedula())==-1);
        datoValido = false;
        
        try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCedula())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {}    
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));            
            
            String [] linea;
            while((linea=cr.readNext())!=null){
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                String e = linea[4];
                if(a.equals(this.cedula)){
                    
                    do {
                       try {
                        this.getCedula(this.cedula = ms.Entrada("Cedula: " + a +
                                "\nNuevo cedula: "));
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
                    
                   do {
                       try {
                        this.getDireccion(this.direccion = ms.Entrada("Direccion: " + c +
                                "\nNuevo direccion: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;  
                    
                    do {
                       try {
                        this.getProvincia(this.provincia = ms.Entrada("Codigo de la provincia: " + d +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    do {
                       try {
                        this.getCiudad(this.ciudad = ms.Entrada("Codigo de la cuidad: " + d +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    al.add(new String []{this.getCedula(),this.getNombre(), 
                    this.getDireccion(),this.getProvincia()});
                    wr.writeAll(al);
                    wr.close();
                    cr.close();
                }
            }
            cr.close();
        }catch(CsvValidationException | IOException exx) {}
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Cliente.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\Cliente.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
    }
    public void ListarClientes() {
        CSVReader cr, cv = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\Cliente.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                String d = linea[3];
                String e = linea[4];
                ms.Mensaje("Cedula del cliente: " + a +
                         "\nNombres y apellidos: " + b +
                         "\nDireccion: " + c + 
                         "\nProvincia: " + d + 
                        "\nCiudad: " + e);
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }
    @Override
    public String toString() {
        String cadena = "Clientes" + 
                "\nCedula: " + this.cedula + 
                "\nNombre y apellido: " + this.nombre + 
                "\nDireccion: " + this.direccion + 
                "\nProvincia: " + this.provincia + 
                "\nCiudad: " + this.ciudad;
        return cadena;
    }
}
