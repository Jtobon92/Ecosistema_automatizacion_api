package co.com.bdb.automation.definitions.crearcuentacrm;

import co.com.bdb.automation.definitions.BaseTest;
import co.com.bdb.automation.definitions.addcaseqa.ValidateIdFieldDefinitios;
import co.com.bdb.automation.utilities.CustomRequestSpecification;
import co.com.bdb.automation.utilities.EnvironmentValuesTask;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CrearCuenta {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateIdFieldDefinitios.class);
    static EnvironmentValuesTask env = new EnvironmentValuesTask();
    private static final String BASE_URL = env.getenv("CREAR_CUENTA_SIEBEL");
    private CustomRequestSpecification request;
    private final BaseTest baseTest;
    private String xmlBody;
    private final String bodiesPath = "src/test/resources/bodies/apiTest/crear_cuentas_siebel/crearCuentas.xml";

    public CrearCuenta(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Given("que tengo tipo de cedula {string} con numero {string}")
    public void queTengoTipoDeCedulaConNumero(String tipced, String numero) throws IOException {

        Path path = Paths.get(bodiesPath);
        xmlBody = Files.readString(path);

        xmlBody = xmlBody.replace("${numero}", String.valueOf(numero))
                         .replace("${tipced}",tipced);

        request = new CustomRequestSpecification(RestAssured.given().log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(xmlBody)
                .basePath(""));


    }
    @When("la respuesta de la creacion debe ser {int}")
    public void laRespuestaDeLaCreacionDebeSer(Integer statusCode) {
        baseTest.response = request
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
        baseTest.response.then().log().all();
        baseTest.response.then().statusCode(statusCode);


    }
    @Then("y debe traer la descripcion del campo como respuesta {string}")
    public void yDebeTraerLaDescripcionDelCampoComoRespuesta(String esperado) {

        String mensajeRecibido = baseTest.response.then()
                .extract()
                .xmlPath()
                .getString("RESPUESTA.StatusDesc");

        Assertions.assertEquals(esperado, mensajeRecibido,
                "El mensaje recibido no conicide con el esperado"

        );
        LOGGER.info("Mensaje esperado: "+esperado);
        LOGGER.info("Mensaje resibido: "+mensajeRecibido);



    }
}
