package com.tuga.konum

import com.tuga.konum.models.network.CreateApplicantDto

interface UseCase {
    interface ResourceUseCase<dto: CreateApplicantDto, T : Any> : UseCase {
        suspend fun createApplicant(dto: CreateApplicantDto): Resource<T>
    }
}
