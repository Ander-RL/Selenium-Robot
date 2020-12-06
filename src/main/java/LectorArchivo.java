
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LectorArchivo {

    private String EMAIL;
    private String PASSWORD;
    private String NOMBRE;
    private String CARD;
    private String CV;
    private String datos;
    private ArrayList<String> data = new ArrayList<String>();

    public void leerArchivo() {

        FileReader archivo = null;

        try {
            archivo = new FileReader("C:\\Users\\ander\\Desktop\\DATOS.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(archivo);

        try {
            while ((datos = bufferedReader.readLine()) != null) {
                data.add(datos);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        EMAIL = data.get(0);
        PASSWORD = data.get(1);
        CARD = data.get(2);
        CV = data.get(3);
        NOMBRE = data.get(4);
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public String getCARD() {
        return CARD;
    }

    public String getCV() {
        return CV;
    }
}
