import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Robot implements Runnable {

    private String EMAIL;
    private String PASSWORD;
    private String NOMBRE;
    private String CARD;
    private String CV ;
    private String URL;
    private long contador;

    private Tienda tienda;

    public Robot(Tienda tienda, LectorArchivo lector, String URL){
        this.tienda=tienda;
        this.URL = URL;

        EMAIL = lector.getEMAIL();
        PASSWORD = lector.getPASSWORD();
        CARD = lector.getCARD();
        CV = lector.getCV();
        NOMBRE = lector.getNOMBRE();
    }

    public void run(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Enlace web
        driver.get(URL);

        // Espera para aceptar mensaje de Cookies
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //driver.findElement(By.xpath("//*[@id=\"ficha-producto\"]/div[5]/div/div/div[2]")).click();

        // Recogemos el estado del boton mediante el texto del mismo
        String estado = driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).getText().toUpperCase();
        System.out.println(estado);

        // Recogemos el precio del producto
        String precioCaracter = driver.findElement(By.xpath("//*[@id=\"precio-main\"]")).getText();
        precioCaracter = precioCaracter.replace("€","");
        precioCaracter = precioCaracter.replace(",",".");
        double precio = Double.parseDouble(precioCaracter);

        // Comprobamos que el estado sea = COMPRAR si no lo es, refresca en bucle hasta que lo sea
        while ((!estado.equals("COMPRAR") && tienda.getParar() == false) || precio > 850) {
            driver.navigate().refresh();
            contador++;
            System.out.println(contador);
            estado = driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).getText().toUpperCase();
            System.out.println(estado);
        }

        if(tienda.getParar()==false){

            tienda.pararRobots();

            // Espera para dar a boton comprar.
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).click();

            System.out.println(precio);

            // Sonido del sistema avisando
            for(int i = 0; i < 2; i++){
                Toolkit.getDefaultToolkit().beep();
            }

            // Espera hasta que boton realizar/proceder con pedido este cargado
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"GTM-carrito-realizarPedidoPaso1\"]")).click();

            // Espera hasta cargar pantalla de introduccion de credenciales
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement input = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[4]/div/input"));
            input.sendKeys(EMAIL);
            WebElement input2 = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div[5]/div/div/input"));
            input2.sendKeys(PASSWORD);

            // Iniciar sesion
            driver.findElement(By.xpath("//*[@id=\"login-form\"]/button[2]")).click();

            // Rellenar datos tarjeta
            // EN CASO DE QUE NO HAYA TARJETA YA DEFINIDA

            /*WebElement tarjetaNombre = driver.findElement(By.xpath("//*[@id=\"cardform\"]/input[1]"));
            tarjetaNombre.sendKeys(NOMBRE);
            WebElement tarjetaNum = driver.findElement(By.xpath("//*[@id=\"cardform\"]/fieldset[1]/input"));
            tarjetaNum.sendKeys(CARD);

            // Seleccionar fecha caducidad
            // Mes
            driver.findElement(By.xpath("//*[@id=\"cardform\"]/fieldset[2]/div[1]/select/option[13]")).click();
            // Año
            driver.findElement(By.xpath("//*[@id=\"cardform\"]/fieldset[2]/div[2]/select/option[3]")).click();
            // Codigo
            driver.findElement(By.xpath("//*[@id=\"cardform\"]/fieldset[3]/div[1]/input")).sendKeys(CV);
            // Aceptar
            driver.findElement(By.xpath("//*[@id=\"cardform\"]/input[3]")).click();*/

            // Aceptar Condiciones de envio y facturacion
            driver.findElement(By.xpath("//*[@id=\"ticket-pago\"]/p/label/span")).click();

            // Espera a que se reinicie el boton de compra
            try {
                Thread.sleep(4000);
                driver.findElement(By.xpath("//*[@id=\"ticket-pago\"]/p/label/span")).click();
            } catch (InterruptedException e) {
                System.out.println("Sleep en zona de compra");
                e.printStackTrace();
            }
            // Realizar compra
            //driver.findElement(By.xpath("//*[@id=\"GTM-carrito-finalizarCompra\"]")).click();

        }else{
            driver.quit();
        }
    }
}
