package enjean.cabinlog.testutil

import enjean.cabinlog.cabin.CabinResponse
import enjean.cabinlog.cabin.CreateCabinRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [DatabaseContextInitializer::class])
abstract class BaseIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    fun createTestCabin(): CabinResponse {
        val response = testRestTemplate.postForEntity(
            "/cabins",
            CreateCabinRequest(
                name = "Test Cabin",
            ),
            CabinResponse::class.java,
        )
        assert(response.statusCode.is2xxSuccessful)
        return response.body!!
    }
}
