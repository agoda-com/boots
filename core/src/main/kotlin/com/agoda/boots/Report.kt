package com.agoda.boots

/**
 * Report provides all available information on a single [Bootable],
 * or a combination of them.
 *
 * For example, if report's key is [All][Key.All], then report represent
 * shared status of all bootables and time it took to boot all of them.
 * [dependent] property in that case will contain reports for every single
 * [Bootable] without dependencies, which in return a list of reports on [Bootable]s
 * that were declaring dependency on it.
 *
 * Default rules for the calculation of common status are as follows:
 * - if there is any [Bootable] in the report failed to boot, status will be [Failed][Status.Failed]
 * - if all of [Bootable]s in the report succeeded to boot, status will be [Booted][Status.Booted]
 * - if all of [Bootable]s in the report are idle, status will be [Idle][Status.Idle]
 * - if none of the above conditions are satisfied, status will be [Booting][Status.Booting]
 *
 * @param key key of report. Can be any type of the [Key]
 * @param status status of report. Can be any type of the [Status]
 * @param start start timestamp of boot process. -1 if status is [Idle][Status.Idle]
 * @param time time it took to finish boot process. -1 if status is not [Booted][Status.Booted] or [Failed][Status.Failed]
 * @param dependent reports of a single [Bootable]s that are dependent on the [key] of this report, or
 *                  non dependent reports that the current report includes (e.g. reports [key] is
 *                  [Multiple][Key.Multiple], [Critical][Key.Critical] or [All][Key.All])
 */
data class Report(
        val key: Key,
        val status: Status,
        val start: Long = -1L,
        val time: Long = -1L,
        val dependent: List<Report> = emptyList()
)
