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
import java.util.Map;

public class Ciudad {
    private Mensajes ms = new Mensajes();
    private ArrayList<Ciudad> cd = new ArrayList<>();
    private String codigo, nombre, cdp;
    
    public Ciudad(String codigo, String nombre, String cdp) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cdp = cdp;
    }

    public Ciudad() {
    }

    public boolean setCodigo(String codigo) {
        if (codigo.length() == 3 && codigo.matches("[0-9]+")) {
            this.codigo = codigo;
            return true;
        }
        return false;
    }

    public String getCodigo(String codigo) {
        if (setCodigo(codigo) == true) {
            return this.codigo;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Digite tres caractres numericos"));
        }
    }

    public boolean setNombre(String nombre) {
        if (nombre.matches("[a-zA-Z]+")) {
            this.nombre = nombre;
            return true;
        }
        return false;
    }

    public String getNombre(String nombre) {
        if (setNombre(nombre) == true) {
            return this.nombre;
        } else {
            throw new IllegalArgumentException(ms.Mostrar("Digite solo caracteres alfabeticos"));
        }
    }
    public boolean setProvincia(String provincia) throws FileNotFoundException, IOException, CsvValidationException {
        CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
        String[] fila;
        while ((fila = cr.readNext()) != null) {
            if (fila[0].equals(provincia)) {
                this.cdp = provincia;
                return true;
            }
        }
        return false;
    }

    public String getProvincia(String provincia) throws IOException, FileNotFoundException, CsvValidationException {
        if (setProvincia(provincia) == true) {
            return this.cdp;
        } else {
            throw new FileNotFoundException(ms.Mostrar("El codigo no existe en la base de datos."));
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCdp() {
        return cdp;
    }
    
    public int PosicionArchivo(String codigo) throws CsvValidationException {
        int indice = -1;
        try {
            CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
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
                            ListarCiudad();
                            break;
                        case "2":
                            AgregarCiudad();
                            break;
                        case "3":
                            ModificarCiudad();
                            break;
                        case "4":
                            EliminarCiudad();
                            break;
                        case "5":
                           break;
                        default:
                           ms.Mensaje("La opcion no existe en el menu");
                            break;
                    }
                }
            } catch (CsvValidationException e) {
            }
        } while (!op.equals("5"));
        
    }
    public void AgregarCiudad() throws CsvValidationException {
        boolean datoValido = false;
        do {
            do {
                try {
                    this.getCodigo(this.codigo = ms.Entrada("Codigo de la ciudad: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCodigo())!=-1){
                        ms.Mensaje("El codigo ya existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCodigo())!=-1);
        datoValido = false;

        do {
            try {
                this.getNombre(this.nombre = ms.Entrada("Nombre: "));
                datoValido = true;
            } catch (Exception e) {
            }
        } while (!datoValido);
        datoValido = false;
        
        do {
            do {
                try {
                    this.getProvincia(this.cdp = ms.Entrada("Codigo de la provincia: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCodigo())!=-1){
                        ms.Mensaje("El codigo ya existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCodigo())!=-1);
        datoValido = false;    

        try {
            CSVWriter cw = new CSVWriter(new FileWriter("Archivos\\CodigoCiudad.csv", true));
            String[] cdc = {this.getCodigo(), this.getNombre(), this.getCdp()};
            cw.writeNext(cdc);
            cw.close();
        } catch (IOException e) {
        }
        
    
        cd.add(new Ciudad(this.getCodigo(),this.getNombre(), this.getCdp()));
    }
    
    public void EliminarCiudad() throws CsvValidationException {
        ArrayList<String[]> al = new ArrayList<>();
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}        
        
        this.codigo = ms.Entrada("Codigo de la provincia: ");
        
        if (PosicionArchivo(this.getCodigo()) != -1) {        
           try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCodigo())){
                    al.remove(i);
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {} 
      
        }else if (PosicionArchivo(this.getCodigo())== -1){
            ms.Mensaje("El codigo no existe en la base de datos.");
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\CodigoCiudad.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\CodigoCiudad.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
        
    }
    
    public static HashMap<String, String> mp = new HashMap<>();
    
    public void ModificarCiudad() throws CsvValidationException {
        ArrayList<String[]> al = new ArrayList<>();
        boolean datoValido = false;
        CSVReader cr = null;
        CSVWriter wr = null;
        int i = -1;
         try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}
        
         do {
            do {
                try {
                    this.getCodigo(this.codigo = ms.Entrada("Codigo de la ciudad: "));
                    datoValido = true;
                    if(PosicionArchivo(this.getCodigo())==-1){
                        ms.Mensaje("El codigo no existe en la base de datos");
                    }
                } catch (Exception  e) {}
            } while (!datoValido);
        } while (PosicionArchivo(this.getCodigo())==-1);
        datoValido = false;
        
        try {
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\CodigoCiudad.csv",true));                        
            while((linea=cr.readNext())!=null){
                i++;
                if(linea[0].equals(this.getCodigo())){
                    al.remove(i);
                    wr.writeAll(al);
                    wr.close();
                }
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {}
        
        try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            wr = new CSVWriter(new FileWriter("Archivos\\CodigoCiudad.csv",true));            
            
            String [] linea;
            while((linea=cr.readNext())!=null){
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                if(a.equals(this.codigo)){
                    
                    do {
                       try {
                        this.getCodigo(this.codigo = ms.Entrada("Codigo de la ciudad: " + a +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false; 
                    
                    do {
                       try {
                        this.getNombre(this.nombre = ms.Entrada("Nombre de la ciudad: " + b +
                                "\nNuevo nombre: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;                   
                    
                    do {
                       try {
                        this.getProvincia(this.cdp = ms.Entrada("Codigo de la provincia: " + c +
                                "\nNuevo codigo: "));
                        datoValido = true;
                    } catch (Exception  ex) {}
                    } while (!datoValido);
                    datoValido = false;
                    
                    
                    al.add(new String []{this.getCodigo(),this.getNombre(),this.getCdp()});
                    wr.writeAll(al);
                    wr.close();
                    cr.close();
                }
            }
            cr.close();
        }catch(CsvValidationException | IOException exx) {}
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\CodigoCiudad.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\CodigoCiudad.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
         
    }
    public void ListarCiudad() {
        CSVReader cr, cv = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                String c = linea[2];
                ms.Mensaje("Codigo del la ciudad: " + a +
                         "\nNombre de la ciudad: " + b  + 
                        "\nCodigo de la provincia: " + c);
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }   
    @Override
    public String toString() {
        String cadena = "Cuidad" + 
                "\nCodigo de la cuidad: " + codigo +
                "\nNombre de la ciudad: " + nombre + 
                "\nCodigo de la provincia: " + cdp;
        return cadena;
    }
}
