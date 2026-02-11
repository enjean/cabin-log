package enjean.cabinlog.cabin

import org.springframework.stereotype.Service

@Service
class CabinService(
    private val cabinRepository: CabinRepository,
) {

    fun createCabin(request: CreateCabinRequest): CabinResponse {
        val cabin = cabinRepository.save(
            CabinEntity(
                name = request.name,
            )
        )

        return CabinResponse(
            id = cabin.id,
            name = cabin.name,
        )
    }
}
