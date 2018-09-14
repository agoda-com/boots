package com.agoda.boots

import com.agoda.boots.Key.Companion.all
import com.agoda.boots.Key.Companion.single
import com.agoda.boots.Status.*
import com.agoda.boots.Status.Companion.booting
import com.agoda.boots.Status.Companion.failed
import org.junit.Test

class MockerTest {

    @Test
    fun testMocker() {
        val one = single("ONE")
        val two = single("TWO")
        val three = single("THREE")

        Mocker {
            mock(two, booting())
            mock(three, failed(Throwable()))
        }

        Boots {
            boot(all()) {
                onBoot = { assert(it.status is Booted) }
            }

            boot(two) {
                onBoot = { assert(false) }
                onFailure = { assert(false) }
            }

            boot(three) {
                onFailure = { assert(it.status is Failed) }
            }

            assert(report(one).status is Booted)
            assert(report(two).status is Booting)
            assert(report(three).status is Failed)
        }
    }

}
