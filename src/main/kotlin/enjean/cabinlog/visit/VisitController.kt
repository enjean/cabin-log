package enjean.cabinlog.visit

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/visits")
class VisitController(
    private val visitService: VisitService,
) {
    @PostMapping
    fun createVisit(@RequestBody createVisitRequest: CreateVisitRequest): ResponseEntity<VisitResponse> {
        val visitResponse = visitService.createVisit(createVisitRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(visitResponse)
    }
}
