
public class Main {

    public static void main(String[] args) {

        Tienda tienda = new Tienda();

        Thread robot1 = new Thread(new Robot1(tienda));
        Thread robot2 = new Thread(new Robot2(tienda));
        robot1.start();
        robot2.start();

    }
}