package com.andef.myworkout.data.account.mapper

import com.andef.myworkout.data.account.dto.ChangeUserInfoRequestDto
import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import javax.inject.Inject

/**
 * @property map преобразование ChangeUserInfoRequest -> ChangeUserInfoRequestDto
 *
 * @see ChangeUserInfoRequestDto
 * @see ChangeUserInfoRequest
 */
class ChangeUserInfoRequestMapper @Inject constructor() {
    fun map(changeUserInfoRequest: ChangeUserInfoRequest) = ChangeUserInfoRequestDto(
        surname = changeUserInfoRequest.surname,
        name = changeUserInfoRequest.name,
        patronymic = changeUserInfoRequest.patronymic,
        photo = changeUserInfoRequest.photo
    )
}