package enjean.cabinlog.visit

import enjean.cabinlog.visitor.VisitorEntity
import jakarta.persistence.*

@Entity
@Table(name = "visit_visitors")
class VisitVisitorEntity(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_id")
    var visit: VisitEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visitor_id")
    var visitor: VisitorEntity,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
