package enjean.cabinlog.cabin

import enjean.cabinlog.visit.CreateVisitRequest
import enjean.cabinlog.visit.VisitResponse
import enjean.cabinlog.visit.VisitService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/cabins")
class CabinController(
    private val cabinService: CabinService,
    private val visitService: VisitService,
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
}
