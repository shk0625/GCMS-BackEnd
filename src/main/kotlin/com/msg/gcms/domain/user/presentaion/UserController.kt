package com.msg.gcms.domain.user.presentaion

import com.msg.gcms.domain.club.enums.ClubType
import com.msg.gcms.domain.user.presentaion.data.response.SearchUserResponseDto
import com.msg.gcms.domain.user.presentaion.data.response.UserResponseDto
import com.msg.gcms.domain.user.service.FindUserService
import com.msg.gcms.domain.user.service.SearchUserService
import com.msg.gcms.domain.user.utils.UserConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userConverter: UserConverter,
    private val findUserService: FindUserService,
    private val searchUserService: SearchUserService
) {
    @GetMapping
    fun findUser(): ResponseEntity<UserResponseDto> {
        val result = findUserService.execute()
        val clubListResponse = result.clubs
            .map { userConverter.toResponseDto(it) }
        val response = userConverter.toResponseDto(dto = result, responseDto =  clubListResponse)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/search")
    fun searchUser(@RequestParam type: ClubType, @RequestParam("name") name: String): ResponseEntity<List<SearchUserResponseDto>> {
        
    }
}