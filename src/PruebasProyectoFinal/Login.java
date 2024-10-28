/*
 */
package PruebasProyectoFinal;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Login{
    Mensajes ms = new Mensajes();
    private BufferedReader br=null;
    private BufferedWriter brw, bw= null;
    
    String usuario, clave;

    public Login(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public Login() {
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }
    
    public boolean login() throws IOException, CsvValidationException{
        CSVReader cr = null;
        CSVWriter wr = null;
        String a="",b="";
        boolean datoValido = false;
        HashMap<String,String> mp = new HashMap<>();
        HashMap<String,String> mh = new HashMap<>();        
        ArrayList<String[]> al = new ArrayList<>();        
        try {
            cr = new CSVReader(new FileReader("Archivos\\Login.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                a = linea[0];
                b = linea[1];
                mp.put(a, b);
            }
        } catch (CsvValidationException | IOException e) {
            
        }
         try {
            cr = new CSVReader(new FileReader("Archivos\\Bloqueados.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                String c = linea[0];
                String d = linea[1];
                mh.put(c, d);
            }
        } catch (CsvValidationException | IOException e) {
        }
      
        
        int i = 0;
        
        for (i = 0; i < 3; i++) {
            this.usuario = ms.Entrada("Usuario: ");
            this.clave = ms.Entrada("Clave: ");            
            if(mp.containsKey(this.getUsuario())){
                
                if(mh.containsKey(this.getUsuario())){
                    if(mh.get(this.getUsuario()).equals(this.getClave())){
                    ms.Mensaje("Alerta: Este usuario esta Bloqueado");
                    }
                }
                
                else{
                    if((mp.get(this.getUsuario()).equals(this.getClave()))){
                    datoValido = true;
                    break;
                    }
                }
                
            }else if(i==2){
                ms.Mostrar("Alerta: Usuario bloqueado " ); 
                try {
                    wr = new CSVWriter(new FileWriter("Archivos\\Bloqueados.csv",true));
                    al.add(new String []{a,b});
                    wr.writeAll(al);
                    wr.close();
                } catch (IOException e) {
                }
            }
        }
        return datoValido;
    }
    
    public boolean login2(){
        CSVReader cr = null;
        CSVWriter wr = null;
        boolean datoValido = false;
        HashMap<String,String> mp = new HashMap<>();
        try {
            cr = new CSVReader(new FileReader("Archivos\\Login2.csv"));
            String [] linea;
            while((linea=cr.readNext())!=null){
                String a = linea[0];
                String b = linea[1];
                mp.put(a, b);
            }
        } catch (CsvValidationException | IOException e) {
        }
        
        int i = 0;
        
        for (i = 0; i < 3; i++) {
            this.usuario = ms.Entrada("Usuario: ");
            this.clave = ms.Entrada("Clave: ");
            if(mp.containsKey(this.getUsuario())){
                if(mp.get(this.getUsuario()).equals(this.getClave())){
                    datoValido = true;
                    break;
                }else{
                    ms.Mostrar("Login incorrecto.");
                    
                }
            }else if(i==2){
                ms.Mostrar("Alerta: Base de datos eliminada");
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("Archivos\\Login2.csv"));
                    bw.write("");
                    bw.close();
                    bw.flush();
                } catch (IOException e) {}
            }
        }
        return datoValido;
    }
    
}
