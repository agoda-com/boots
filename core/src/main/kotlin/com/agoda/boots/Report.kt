package com.agoda.boots

/**
 * Report provides all available information on a single [bootable][Bootable],
 * or a combination of them.
 *
 * For example, if report's key is [All][Key.All], then report represent
 * shared status of all bootables and time it took to boot all of them.
 * [dependent] property in that case will contain reports for every single
 * [bootable][Bootable] without dependencies, which in return a list of reports on [bootables][Bootable]
 * that were declaring dependency on it.
 *
 * Default rules for the calculation of common status are as follows:
 * - if there is any [bootable][Bootable] in the report failed to boot, status will be [failed][Status.Failed]
 * - if all of [bootables][Bootable] in the report succeeded to boot, status will be [booted][Status.Booted]
 * - if all of [bootables][Bootable] in the report are idle, status will be [idle][Status.Idle]
 * - if none of the above conditions are satisfied, status will be [booting][Status.Booting]
 *
 * @param key key of report. Can be any type of the [Key]
 * @param status status of report. Can be any type of the [Status]
 * @param start start timestamp of boot process. -1 if status is [idle][Status.Idle]
 * @param time time it took to finish boot process. -1 if status is not [booted][Status.Booted] or [failed][Status.Failed]
 * @param dependent reports of a single [bootables][Bootable] that are dependent on the [key] of this report, or
 *                  non dependent reports that the current report includes (e.g. reports [key] is
 *                  [multiple][Key.Multiple], [critical][Key.Critical] or [all][Key.All])
 */
data class Report(
        val key: Key,
        val status: Status,
        val start: Long = -1L,
        val time: Long = -1L,
        val dependent: List<Report> = emptyList()
)
