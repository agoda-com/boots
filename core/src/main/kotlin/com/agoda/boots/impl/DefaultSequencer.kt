package com.agoda.boots.impl

import com.agoda.boots.Bootable
import com.agoda.boots.Key
import com.agoda.boots.Report
import com.agoda.boots.Sequencer

open class DefaultSequencer : Sequencer {
    open val isMainThreadSupported: Boolean = false

    override fun start(key: Key, boots: Set<Bootable>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(key: Key): Int {
        TODO("not implemented")
    }

    override fun next(key: Key, finished: Report?): Bootable? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop(key: Key) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
