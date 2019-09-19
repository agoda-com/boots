package com.agoda.boots.strict

import com.agoda.boots.Bootable
import com.agoda.boots.Key

/**
 * This class is used to determine ICC (Incorrect connected components) in the
 * [Bootable]'s dependency tree every time you invoke [add()][com.agoda.boots.Boots.add].
 *
 * As of now, ICC is considered to be:
 * - Bootable with [Bootable.isCritical] flag set to `true` which is dependent
 *   on a bootable with [Bootable.isCritical] flag set to `false`
 * - Bootable with [Bootable.isConcurrent] flag set to `false` which is dependent
 *   on a bootable with [Bootable.isCritical] flag set to `true` and system's
 *   [Executor.isMainThreadSupported][com.agoda.boots.Executor.isMainThreadSupported]
 *   is `false`
 * @param boots list of bootables to process
 * @param isMainThreadSupported flag indicating if switching to main thread's context is supported
 */
class IccFinder(private val boots: List<Bootable>, private val isMainThreadSupported: Boolean) {

    /**
     * Determines if given [Bootable] set contains ICC
     * through it's dependency tree.
     *
     * @return Set of ICCs each represented by Pair<[Key], [Key]>
     */
    fun find(): List<Pair<Key, Key>> {
        val results = mutableListOf<Pair<Key, Key>>()

        boots.filter {
            it.isCritical
        }.forEach { critical ->
            critical.dependencies.forEach { key ->
                val dependency = boots.find { it.key == key }!!

                if (!dependency.isCritical) {
                    results.add(critical.key to key)
                }
            }
        }

        if (!isMainThreadSupported) {
            boots.filter {
                it.dependencies.isNotEmpty() && !it.isConcurrent
            }.forEach { boot ->
                boot.dependencies.forEach { key ->
                    if (boots.find { it.key == key }!!.isConcurrent) {
                        results.add(boot.key to key)
                    }
                }
            }
        }

        return results
    }

}
