package com.msg.gcms.domain.user.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.msg.gcms.domain.applicant.domain.entity.Applicant
import com.msg.gcms.domain.club.domain.entity.Club
import com.msg.gcms.domain.clubMember.domain.entity.ClubMember
import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id
    @Column
    var id: UUID,

    @Column(length = 20)
    val email: String,

    @Column(length = 20)
    val nickname: String,

    val grade: Int,

    val classNum: Int,

    val number: Int,

    @Column(columnDefinition = "TEXT")
    var profileImg: String? = "",

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val club: List<Club> = listOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val applicant: List<Applicant> = listOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val clubMember: List<ClubMember> = listOf()
) {
    fun updateProfileImg(profileImg: String?) {
        this.profileImg = profileImg
    }
}