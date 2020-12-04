public class Tienda {

    private static boolean parar = false;

    public synchronized void pararRobots(){

        if(!parar){
            parar=true;
        }
    }

    public synchronized boolean getParar(){
        return parar;
    }
}
