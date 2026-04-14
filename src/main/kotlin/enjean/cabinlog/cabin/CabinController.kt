package enjean.cabinlog.cabin

import enjean.cabinlog.visit.CreateVisitRequest
import enjean.cabinlog.visit.VisitResponse
import enjean.cabinlog.visit.VisitService
import enjean.cabinlog.visit.VisitSummariesResponse
import enjean.cabinlog.visit.VisitSummaryResponse
import enjean.cabinlog.visitor.CreateVisitorRequest
import enjean.cabinlog.visitor.VisitorResponse
import enjean.cabinlog.visitor.VisitorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/cabins")
class CabinController(
    private val cabinService: CabinService,
    private val visitService: VisitService,
    private val visitorService: VisitorService,
) {
    @PostMapping
    fun createCabin(@RequestBody createCabinRequest: CreateCabinRequest): ResponseEntity<CabinResponse> {
        val cabinResponse = cabinService.createCabin(createCabinRequest)

        /**
         *  Once GET /cabins/{id} is implemented, could use the following to return with the Location
         *  val location = ServletUriComponentsBuilder.fromCurrentRequest()
         *      .path("/{id}")
         *      .buildAndExpand(cabinResponse.id)
         *      .toUri()
         *  return ResponseEntity.created(location)
         *      .body(cabinResponse)
         */

        return ResponseEntity.status(HttpStatus.CREATED).body(cabinResponse)
    }

    @PostMapping("/{cabinId}/visits")
    fun createVisit(
        @PathVariable cabinId: Long,
        @RequestBody createVisitRequest: CreateVisitRequest
    ): ResponseEntity<VisitResponse> {
        val visitResponse = visitService.createVisit(cabinId = cabinId, request = createVisitRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(visitResponse)
    }

    @GetMapping("/{cabinId}/visits")
    fun getVisits(
        @PathVariable cabinId: Long,
        @RequestParam(required = false) year: Int?
    ): ResponseEntity<VisitSummariesResponse> {
        val visits = visitService.getVisits(cabinId = cabinId, year = year)
        return ResponseEntity.ok(visits)
    }

    @PostMapping("/{cabinId}/visitors")
    fun createVisitor(
        @PathVariable cabinId: Long,
        @RequestBody createVisitorRequest: CreateVisitorRequest
    ): ResponseEntity<VisitorResponse> {
        val visitorResponse = visitorService.createVisitor(
            cabinId = cabinId,
            createVisitorRequest = createVisitorRequest,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(visitorResponse)
    }
}
