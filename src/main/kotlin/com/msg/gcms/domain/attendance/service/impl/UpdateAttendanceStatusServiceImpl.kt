package com.msg.gcms.domain.attendance.service.impl

import com.msg.gcms.domain.attendance.exception.AttendanceNotFoundException
import com.msg.gcms.domain.attendance.exception.ScheduleNotFoundException
import com.msg.gcms.domain.attendance.presentation.data.dto.AttendanceStatusDto
import com.msg.gcms.domain.attendance.repository.AttendanceRepository
import com.msg.gcms.domain.attendance.repository.ScheduleRepository
import com.msg.gcms.domain.attendance.service.UpdateAttendanceStatusService
import com.msg.gcms.domain.attendance.util.AttendanceConverter
import com.msg.gcms.domain.user.domain.repository.UserRepository
import com.msg.gcms.domain.user.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateAttendanceStatusServiceImpl(
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val attendanceConverter: AttendanceConverter
) : UpdateAttendanceStatusService {
    override fun execute(userId: UUID, dto: AttendanceStatusDto) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        val schedule = scheduleRepository.findByIdOrNull(dto.scheduleId)
            ?: throw ScheduleNotFoundException()

        val attendance = attendanceRepository.findByUserAndSchedule(user, schedule)
            ?: throw AttendanceNotFoundException()

        attendanceConverter.toEntity(
            id = attendance.id,
            attendanceStatus = dto.attendanceStatus,
            user = attendance.user,
            schedule = attendance.schedule
        ).let { attendanceRepository.save(it) }
    }
}