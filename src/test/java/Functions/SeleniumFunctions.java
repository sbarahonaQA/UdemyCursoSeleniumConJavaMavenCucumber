package Functions;

import StepDefinitions.Hooks;
import cucumber.api.Scenario;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SeleniumFunctions {
    static WebDriver driver;
    public static Properties prop = new Properties();
    public static InputStream in = SeleniumFunctions.class.getResourceAsStream("../test.properties");
    public static Map<String, String> ScenaryData = new HashMap<>();

    public SeleniumFunctions() {
        driver = Hooks.driver;
    }

    public String ElementText = "";

    public static final int EXPLICIT_TIMEOUT = 5;

    /******** Scenario Attributes ********/
    Scenario scenario = null;
    public void scenario (Scenario scenario) {
        this.scenario = scenario;
    }

    public String readProperties(String property) throws IOException {
        prop.load(in);
        return prop.getProperty(property);
    }

    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(SeleniumFunctions.class);
    public static String FileName = "";
    public static String PagesFilePath = "src/test/resources/Pages/";

    public static String GetFieldBy = "";
    public static String ValueToFind = "";

    public static Object readJson() throws Exception {
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try {

            if (reader != null) {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            log.error("ReadEntity: No existe el archivo " + FileName);
            return null;
        } catch (NullPointerException e) {

            log.error("ReadEntity: No existe el archivo " + FileName);
            throw new IllegalStateException("ReadEntity: No existe el archivo " + FileName, e);

        }

    }

    public static JSONObject ReadEntity(String element) throws Exception {
        JSONObject Entity = null;

        JSONObject jsonObject = (JSONObject) readJson();
        Entity = (JSONObject) jsonObject.get(element);
        log.info(Entity.toJSONString());
        return Entity;
    }

    public void ScreenShot(String TestCaptura) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        String screenShotName = readProperties("ScreenShotPath") + "\\" + readProperties("browser") + "\\" + TestCaptura + "_(" + dateFormat.format(GregorianCalendar.getInstance().getTime()) + ")";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        log.info("Captura de pantalla guardada como:" + screenShotName);
        FileUtils.copyFile(scrFile, new File(String.format("%s.png", screenShotName)));
    }

    public byte[] attachScreenShot(){
        log.info("Adjuntando captura de pantalla");
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return screenshot;
    }

    public boolean isElementDisplayed(String element) throws Exception {
        boolean isDisplayed = Boolean.parseBoolean(null);
        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            log.info(String.format("Esperando elemento: %s", element));
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        }catch (NoSuchElementException | TimeoutException e){
            isDisplayed = false;
            log.info(e);
        }
        log.info(String.format("%s visbilidad es: %s", element, isDisplayed));
        return isDisplayed;
    }

    public void AcceptAlert()
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            log.info("Alerta aceptada exitosamente.");
        }catch(Throwable e){
            log.error("Error al esperar que apareciera alerta. "+e.getMessage());
        }
    }

    public void dismissAlert()
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            log.info("Alerta rechazada exitosamente.");
        }catch(Throwable e){
            log.error("Error al esperar que apareciera alerta. "+e.getMessage());
        }
    }

    public static By getCompleteElement(String element) throws Exception {
        By result = null;
        JSONObject Entity = ReadEntity(element);

        GetFieldBy = (String) Entity.get("GetFieldBy");
        ValueToFind = (String) Entity.get("ValueToFind");

        if ("className".equalsIgnoreCase(GetFieldBy)) {
            result = By.className(ValueToFind);
        } else if ("cssSelector".equalsIgnoreCase(GetFieldBy)) {
            result = By.cssSelector(ValueToFind);
        } else if ("id".equalsIgnoreCase(GetFieldBy)) {
            result = By.id(ValueToFind);
        } else if ("linkText".equalsIgnoreCase(GetFieldBy)) {
            result = By.linkText(ValueToFind);
        } else if ("name".equalsIgnoreCase(GetFieldBy)) {
            result = By.name(ValueToFind);
        } else if ("link".equalsIgnoreCase(GetFieldBy)) {
            result = By.partialLinkText(ValueToFind);
        } else if ("tagName".equalsIgnoreCase(GetFieldBy)) {
            result = By.tagName(ValueToFind);
        } else if ("xpath".equalsIgnoreCase(GetFieldBy)) {
            result = By.xpath(ValueToFind);
        }
        return result;
    }

    public void selectOptionDropdownByIndex(String element, int option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Esperando elemento: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionando: " + option + "por indice");
        opt.selectByIndex(option);
    }

    public void selectOptionDropdownByText(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Esperando elemento: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionando: " + option + "por texto");
        opt.selectByVisibleText(option);
    }

    public void selectOptionDropdownByValue(String element, String option) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Esperando elemento: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Seleccionando: " + option + "por valor");
        opt.selectByValue(option);
    }

    public void checkCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if(!isChecked){
            log.info("Marcando elemento: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void UncheckCheckbox(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if(isChecked){
            log.info("Desmarcando elemento: " + element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void scrollToElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Desplazandose al elemento: " + element);
        jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(SeleniumElement));

    }

    public void ClickJSElement(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Desplazandose al elemento: " + element);
        jse.executeScript("arguments[0].click()", driver.findElement(SeleniumElement));

    }


    public void checkPartialTextElementNotPresent(String element,String text) throws Exception {
        ElementText = GetTextElement(element);

        boolean isFoundFalse = ElementText.indexOf(text) !=-1? true: false;
        Assert.assertFalse("Texto SI esta presente en elemento: " + element + " - texto: " + ElementText, isFoundFalse);

    }

    public void checkPartialTextElementPresent(String element,String text) throws Exception {

        ElementText = GetTextElement(element);

        boolean isFound = ElementText.indexOf(text) !=-1? true: false;

        Assert.assertTrue("Texto NO esta presente en elemento: " + element + " - texto: " + ElementText, isFound);

    }

    public String GetTextElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
        log.info(String.format("Esperando el elemento: %s", element));

        ElementText = driver.findElement(SeleniumElement).getText();

        return ElementText;

    }

    public void iSetElementWithText(String element, String text) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info(String.format("Al elemento %s se le pone este texto %s", element, text));
    }

    public void iSetElementWithKeyValue(String element, String key) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean exist = this.ScenaryData.containsKey(key);
        if (exist){
            String text = this.ScenaryData.get(key);
            driver.findElement(SeleniumElement).sendKeys(text);
            log.info(String.format("Al elemento %s se le pone este texto %s", element, text));
        }else{
            Assert.assertTrue(String.format("The given key %s do not exist in Context", key), this.ScenaryData.containsKey(key));
        }

    }

    public void doubleClick(String element) throws Exception
    {
        Actions action = new Actions(driver);
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        action.moveToElement(driver.findElement(SeleniumElement)).doubleClick().perform();
        log.info("Doble click al elemento: " + element);
    }

    public void iClicInElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).click();
        log.info("Click al elemento: " + element);

    }

    public void scrollPage(String to) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if(to.equals("top")){
            log.info("Desplazandose al inicio de la pagina");
            jse.executeScript("scroll(0, -250);");

        }
        else if(to.equals("end")){
            log.info("Desplazandose al final de la pagina");
            jse.executeScript("scroll(0, 250);");
        }
    }

    public void zoomTillElementDisplay(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebElement html = driver.findElement(SeleniumElement);
        html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
    }

    public void switchToFrame(String Frame) throws Exception {

        By SeleniumElement = SeleniumFunctions.getCompleteElement(Frame);
        log.info("Cambiando de frame: " + Frame);
        driver.switchTo().frame(driver.findElement(SeleniumElement));

    }

    public void switchToParentFrame() {

        log.info("Cambiando al frame padre");
        driver.switchTo().parentFrame();

    }

    public void waitForElementPresent(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Esperando que el elemento: "+element + " esté presente");
        w.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }

    public void waitForElementVisible(String element) throws Exception
    {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        log.info("Esperando que el elemento: "+element + " esté visible");
        w.until(ExpectedConditions.visibilityOfElementLocated(SeleniumElement));
    }

    public void SaveInScenario(String key, String text) {

        if (!this.ScenaryData.containsKey(key)) {
            this.ScenaryData.put(key,text);
            log.info(String.format("Guardar como contexto de escenario - llave: %s - valor: %s ", key,text));
        } else {
            this.ScenaryData.replace(key,text);
            log.info(String.format("Actualizar como contexto de escenario - llave: %s - valor: %s ", key,text));
        }

    }

}