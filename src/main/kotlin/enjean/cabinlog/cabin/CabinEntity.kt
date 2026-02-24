package enjean.cabinlog.cabin

import jakarta.persistence.*

@Entity
@Table(name = "cabins")
class CabinEntity(
    var name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
