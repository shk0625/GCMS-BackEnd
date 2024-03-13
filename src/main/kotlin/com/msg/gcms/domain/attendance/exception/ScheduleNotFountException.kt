package com.msg.gcms.domain.attendance.exception

import com.msg.gcms.global.exception.ErrorCode
import com.msg.gcms.global.exception.exceptions.BasicException

class ScheduleNotFountException : BasicException(ErrorCode.SCHEDULE_NOT_FOUND) {
}