package PedroM_Guerra.controle_aso.integrationtests.swagger;

import PedroM_Guerra.controle_aso.config.TestConfigs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest{

	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		assertTrue(content.contains("Swagger UI"));
	}
}
