package enjean.cabinlog.cabin

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/cabins")
class CabinController(
    private val cabinService: CabinService,
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
}
