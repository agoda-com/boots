package com.agoda.boots

data class Report(
        val key: Key,
        val status: Status,
        val start: Long = -1L,
        val time: Long = -1L,
        val dependent: Set<Report> = emptySet()
)
