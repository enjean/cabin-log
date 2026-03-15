package enjean.cabinlog.visit

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/visits")
class VisitController(
    private val visitService: VisitService,
) {
    @GetMapping("/{visitId}")
    fun getVisit(
        @PathVariable visitId: Long,
    ): ResponseEntity<VisitResponse> =
        ResponseEntity.ok(visitService.getVisit(id = visitId))

}
