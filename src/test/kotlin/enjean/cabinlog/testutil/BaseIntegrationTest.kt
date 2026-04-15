package enjean.cabinlog.testutil

import enjean.cabinlog.cabin.CabinResponse
import enjean.cabinlog.cabin.CreateCabinRequest
import enjean.cabinlog.visit.CreateVisitRequest
import enjean.cabinlog.visit.VisitResponse
import enjean.cabinlog.visit.VisitorsInfo
import enjean.cabinlog.visitor.CreateVisitorRequest
import enjean.cabinlog.visitor.VisitorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDate

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [DatabaseContextInitializer::class])
abstract class BaseIntegrationTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    fun createTestCabin(name: String = "Test Cabin"): CabinResponse {
        val response = testRestTemplate.postForEntity<CabinResponse>(
            "/cabins",
            CreateCabinRequest(
                name = name,
            ),
        )
        assert(response.statusCode.is2xxSuccessful)
        return response.body!!
    }

    fun createTestVisitor(cabinId: Long, name: String): VisitorResponse {
        val response = testRestTemplate.postForEntity<VisitorResponse>(
            "/cabins/$cabinId/visitors",
            CreateVisitorRequest(
                name = name,
            ),
        )
        assert(response.statusCode.is2xxSuccessful)
        return response.body!!
    }

    fun createTestVisit(
        cabinId: Long,
        name: String,
        startDate: LocalDate,
        endDate: LocalDate,
        fullTimeVisitorIds: List<Long>,
    ): VisitResponse {
        val request = CreateVisitRequest(
            name = name,
            startDate = startDate,
            endDate = endDate,
            visitors = VisitorsInfo(
                fullTimeVisitorIds = fullTimeVisitorIds,
            )
        )
        val response = testRestTemplate.postForEntity(
            "/cabins/$cabinId/visits",
            request,
            VisitResponse::class.java,
        )
        assert(response.statusCode.is2xxSuccessful)
        return response.body!!
    }
}
