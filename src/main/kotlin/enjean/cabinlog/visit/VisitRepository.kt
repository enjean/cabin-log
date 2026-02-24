package enjean.cabinlog.visit

import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository : JpaRepository<VisitEntity, Long>
