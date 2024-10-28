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
import java.util.Iterator;
import java.util.Map;

public class Provincia {

    protected Mensajes ms = new Mensajes();
    protected String codigo, nombre;
    protected int i = 0;
    private ArrayList<Provincia> pro = new ArrayList<>();

    public Provincia(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Provincia() {
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

    public String getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }


    public int PosicionArchivo(String codigo) throws CsvValidationException {
        int indice = -1;
        try {
            CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
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
                            ListarProvincia();
                            break;
                        case "2":
                            AgregarProvincia();
                            break;
                        case "3":
                            ModificarProvincia();
                            break;
                        case "4":
                            EliminarProvincia();
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
    
    
    public void AgregarProvincia() throws CsvValidationException {
        boolean datoValido = false;
        do {
            do {
                try {
                    this.getCodigo(this.codigo = ms.Entrada("Codigo: "));
                    datoValido = true;
                    if (PosicionArchivo(this.getCodigo()) != -1) {
                        ms.Mensaje("El codigo ya existe en la base de datos.");
                    }
                } catch (CsvValidationException e) {
                }
            } while (PosicionArchivo(this.codigo) != -1);
            if (PosicionArchivo(this.codigo) != -1) {
                ms.Mensaje("El codigo ya existe en el sistema" + "\nDigite un nuevo codigo.");
            }
        } while (!datoValido);
        datoValido = false;

        do {
            try {
                this.getNombre(this.nombre = ms.Entrada("Nombre: "));
                datoValido = true;
            } catch (Exception e) {
            }
        } while (!datoValido);
        datoValido = false;

        try {
            CSVWriter cw = new CSVWriter(new FileWriter("Archivos\\CodigoProvincia.csv", true));
            String[] cdgPrv = {this.getCodigo(), this.getNombre()};
            cw.writeNext(cdgPrv);
            cw.close();
        } catch (IOException e) {
        }

        pro.add(new Provincia(codigo, nombre));
    }
    
    public  HashMap<String, String> mp = new HashMap<>();

    public void EliminarProvincia() throws CsvValidationException {
        ArrayList<String[]> al = new ArrayList<>();
        CSVReader cr, cv = null;
        CSVWriter wr = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                al.add(linea);
            }
        } catch (CsvValidationException | IOException e) {}        
        
        this.codigo = ms.Entrada("Codigo de la provincia: ");
        String a=" ",b = " ", c = " ", d = " ";
        if (PosicionArchivo(this.getCodigo()) != -1) {   
            boolean existe = false;
           try {
            int i = -1;
            String [] linea;
            cr = new CSVReader(new FileReader("Archivos\\CodigoCiudad.csv"));
            while((linea=cr.readNext())!=null){
                    a = linea[2];
                    if(this.getCodigo().equals(a)){
                        ms.Mensaje("El codigo no se puede eliminar porque esta enlazado a una ciudad");
                        existe = true;
                    }
            }
            cr.close();
            } catch (CsvValidationException | IOException e) {}
           
            if(!existe){
                try {
                    int j = -1;
                   cv = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
                   String [] line;
                   while((line=cv.readNext())!=null){
                       String aux = line[0];
                       String aux2 = line[1];
                       j++;
                       if(this.getCodigo().equals(aux)){
                           ms.Mensaje("Codigo de la ciudad: " + aux + 
                                   "\nNombre de la provincia: " + aux2 + 
                                   "\nEliminados exitosamente.");
                           al.remove(j);
                       }
                   }
                   cv.close();
               } catch (CsvValidationException | IOException e) {
               }
            
            }   
            
        }else if (PosicionArchivo(this.getCodigo())== -1){
            ms.Mensaje("El codigo no existe en la base de datos.");
        }
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\CodigoProvincia.csv"));
            bw.write("");
            bw.close();
            bw.flush();
        } catch (IOException e) {}
        
        try {
            wr = new CSVWriter(new FileWriter("Archivos\\CodigoProvincia.csv",true));
            wr.writeAll(al);
            wr.close();
        } catch (IOException e) {}
        
    }

    public  void ModificarProvincia() throws CsvValidationException {
        try {
            CSVWriter cw = new CSVWriter(new FileWriter("Archivos\\CodigoProvincia.csv",true));
            CSVReader cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
            this.codigo = ms.Entrada("Codigo: ");
            
            String [] linea;
            while ((linea = cr.readNext()) != null) {
                String cod = linea[0];
                String nom = linea[1];
                mp.put(cod, nom);
                if(cod.equals(this.codigo)){
                    String b = ms.Entrada("Codigo: " + cod + 
                            "\nNuevo codigo: ");
                    String c = ms.Entrada("Nombre: " + nom + 
                            "\nNuevo nombre: ");
                    String []d = {b,c};
                    cw.writeNext(d);
                }
            }
            
            try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\CodigoProvincia.csv"));
            bw.write(" ");
            } catch (IOException e) {}
            
            mp.remove(this.codigo);
            
            for (Map.Entry<String, String> entry : mp.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String [] nuevos = {key,value};
                cw.writeNext(nuevos);
            }
        cw.close();
        } catch (IOException e) {}
        
    }
    public void ListarProvincia() {
        CSVReader cr, cv = null;
        try {
            cr = new CSVReader(new FileReader("Archivos\\CodigoProvincia.csv"));
            String[] linea;
            while ((linea = cr.readNext()) != null) {
                String a = linea[0];
                String b = linea[1];
                ms.Mensaje("Codigo del la provincia: " + a +
                         "\nNombre de la provincia: " + b );
            }
            cr.close();
        } catch (CsvValidationException | IOException e) {
        }
    }   
    @Override
    public String toString() {
        String cadena = "Provincia"
                + "\nCodigo de la provincia: " + codigo
                + "\nNombre de la provincia: " + nombre;
        return cadena;
    }

}
