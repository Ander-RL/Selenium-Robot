import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Robot2 implements Runnable {

    private static final String EMAIL = "Selenium3080@gmail.com";
    private static final String PASSWORD = "rtx30802020";
    private static final String NOMBRE = "Ander Rodriguez";
    private static final String CARD = "**************";
    private static final String CV = "***";

    private Tienda tienda;

    public Robot2(Tienda tienda){
        this.tienda=tienda;
    }

    public void run(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Enlace web
        //driver.get("https://www.pccomponentes.com/zotac-gaming-geforce-rtx-3080-trinity-10gb-gddr6x");
        driver.get("https://www.pccomponentes.com/evga-geforce-rtx-3080-xc3-black-gaming-10gb-gddr6x");

        // Espera para aceptar mensaje de Cookies
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"ficha-producto\"]/div[5]/div/div/div[2]")).click();

        // Recogemos el estado del boton mediante el texto del mismo
        String estado = driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).getText().toUpperCase();
        System.out.println(estado);

        // Comprobamos que el estado sea = COMPRAR si no lo es, refresca en bucle hasta que lo sea
        while (!estado.equals("COMPRAR") && tienda.getParar() == false) {
            driver.navigate().refresh();
            estado = driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).getText().toUpperCase();
            System.out.println(estado);
        }

        if(tienda.getParar()==false){

            tienda.pararRobots();

            // Espera para dar a boton comprar.
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"btnsWishAddBuy\"]/button[3]")).click();
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
            WebElement tarjetaNombre = driver.findElement(By.xpath("//*[@id=\"cardform\"]/input[1]"));
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
            driver.findElement(By.xpath("//*[@id=\"cardform\"]/input[3]")).click();

            // Aceptar Condiciones de envio y facturacion
            driver.findElement(By.xpath("//*[@id=\"ticket-pago\"]/p/label/span")).click();
            // Realizar compra
            driver.findElement(By.xpath("//*[@id=\"GTM-carrito-finalizarCompra\"]")).click();

        }else{
            driver.quit();
        }
    }
}
