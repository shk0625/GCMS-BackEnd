package com.msg.gcms.domain.clubMember.domain.util

import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberDto
import com.msg.gcms.domain.clubMember.domain.presentation.dto.ClubMemberListDto
import com.msg.gcms.domain.clubMember.enums.MemberScope
import org.springframework.stereotype.Component

@Component
class ClubMemberConverterImpl : ClubMemberConverter {
    override fun toDto(entity: ClubMember): ClubMemberDto = ClubMemberDto(
        uuid = entity.user.id,
        email = entity.user.email,
        name = entity.user.nickname,
        grade = entity.user.grade,
        classNum = entity.user.classNum,
        number = entity.user.number,
        profileImg = entity.user.profileImg,
        scope = entity.scope
    )
