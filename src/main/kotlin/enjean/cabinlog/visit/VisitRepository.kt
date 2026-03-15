package enjean.cabinlog.visit

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VisitRepository : JpaRepository<VisitEntity, Long> {
    @Query("""
        SELECT v FROM VisitEntity v
        LEFT JOIN FETCH v._visitVisitors vv
        LEFT JOIN FETCH vv.visitor
        WHERE v.cabin.id = :cabinId 
        AND (:year IS NULL OR EXTRACT(YEAR FROM v.startDate) = :year)
        ORDER BY v.startDate DESC
    """)
    fun findByCabinIdAndYear(
        @Param("cabinId") cabinId: Long,
        @Param("year") year: Int?
    ): List<VisitEntity>
}
