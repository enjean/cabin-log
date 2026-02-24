package enjean.cabinlog.cabin

import org.springframework.data.jpa.repository.JpaRepository

interface CabinRepository : JpaRepository<CabinEntity, Long>
