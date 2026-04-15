package enjean.cabinlog.visitor

import org.springframework.data.jpa.repository.JpaRepository

interface VisitorRepository : JpaRepository<VisitorEntity, Long> {
    fun findAllByCabinId(cabinId: Long): List<VisitorEntity>
}
