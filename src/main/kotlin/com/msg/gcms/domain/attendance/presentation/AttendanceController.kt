package com.msg.gcms.domain.attendance.presentation

import com.msg.gcms.domain.attendance.presentation.data.dto.UserAttendanceStatusListDto
import com.msg.gcms.domain.attendance.presentation.data.request.CreateScheduleRequestDto
import com.msg.gcms.domain.attendance.presentation.data.request.UpdateAttendanceStatusRequestDto
import com.msg.gcms.domain.attendance.service.CreateScheduleService
import com.msg.gcms.domain.attendance.service.QueryCurrentAttendConditionService
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.attendance.util.ScheduleConverter
import com.msg.gcms.global.annotation.RequestController
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.validation.Valid

@RequestController("/attend")
class AttendanceController(
    private val createScheduleService: CreateScheduleService,
    private val queryCurrentAttendConditionService: QueryCurrentAttendConditionService,
    private val updateAttendanceStatusService: UpdateAttendanceStatusService,
    private val scheduleConverter: ScheduleConverter,
    private val attendanceConverter: AttendanceConverter
) {
    @PostMapping("/{club_id}/club")
    fun createSchedule(
        @PathVariable("club_id") clubId: Long,
        @RequestBody @Valid createScheduleRequestDto: CreateScheduleRequestDto
    ): ResponseEntity<Unit> =
        scheduleConverter.toDto(createScheduleRequestDto)
            .let { createScheduleService.execute(clubId, it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @GetMapping("/{club_id}")
    fun queryCurrentAttendCondition(
        @PathVariable("club_id") clubId: Long,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) period: LocalTime?
    ): ResponseEntity<UserAttendanceStatusListDto> =
        scheduleConverter.toDto(clubId, date, period)
            .let { queryCurrentAttendConditionService.execute(it) }
            .let { ResponseEntity.ok(it) }

    @PatchMapping("/{user_id}")
    fun updateAttendanceStatus(
        @PathVariable("user_id") userId: UUID,
        @RequestBody @Valid updateAttendanceStatusRequestDto: UpdateAttendanceStatusRequestDto
    ): ResponseEntity<Unit> =
        attendanceConverter.toDto(updateAttendanceStatusRequestDto)
            .let { updateAttendanceStatusService.execute(userId, it) }
            .let { ResponseEntity.noContent().build() }
}