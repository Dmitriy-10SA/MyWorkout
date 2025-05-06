package com.andef.myworkout.data.account.mapper

import com.andef.myworkout.data.account.dto.UserInfoDto
import com.andef.myworkout.domain.account.entities.UserInfo
import javax.inject.Inject

/**
 * @property map преобразование UserInfoDto -> UserInfo
 *
 * @see UserInfoDto
 * @see UserInfo
 */
class UserInfoMapper @Inject constructor() {
    fun map(userInfoDto: UserInfoDto) = UserInfo(
        id = userInfoDto.id,
        mail = userInfoDto.mail,
        password = userInfoDto.password,
        surname = userInfoDto.surname,
        name = userInfoDto.name,
        patronymic = userInfoDto.patronymic,
        photo = userInfoDto.photo
    )
}