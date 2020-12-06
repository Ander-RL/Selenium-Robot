
public class Main {

    public static void main(String[] args) {

        Tienda tienda = new Tienda();
        LectorArchivo la = new LectorArchivo();
        la.leerArchivo();

        Thread robot1 = new Thread(new Robot(tienda, la,"https://www.pccomponentes.com/amd-ryzen-7-5800x-38ghz"));
        Thread robot2 = new Thread(new Robot(tienda, la,"https://www.pccomponentes.com/amd-ryzen-5-5600x-37ghz"));
        robot1.start();
        robot2.start();

    }
}
