package com.msg.gcms.domain.club.controller

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.club.presentation.ClubController
import com.msg.gcms.domain.club.presentation.data.dto.ClubDto
import com.msg.gcms.domain.club.presentation.data.request.UpdateClubRequest
import com.msg.gcms.domain.club.service.*
import com.msg.gcms.domain.club.utils.ClubConverter
import com.msg.gcms.domain.club.utils.impl.ClubConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class ExitClubControllerTest : BehaviorSpec({
    @Bean
    fun clubConverter(): ClubConverter {
        return ClubConverterImpl()
    }
    val findClubListService = mockk<FindClubListService>()
    val createClubService = mockk<CreateClubService>()
    val updateClubService = mockk<UpdateClubService>()
    val closeClubService = mockk<CloseClubService>()
    val exitClubService = mockk<ExitClubService>()
    val clubController = ClubController(createClubService, findClubListService, updateClubService, closeClubService, exitClubService, clubConverter())

    given("요청이 들어오면") {
        `when`("is received") {
            every { exitClubService.execute(1) } returns Unit
            val response = clubController.exitClub(1)

            then("서비스가 한번은 실행되어야 함") {
                verify(exactly = 1) { exitClubService.execute(1) }
            }
            then("response status should be no content") {
                response.statusCode shouldBe HttpStatus.NO_CONTENT
            }
        }
    }
})