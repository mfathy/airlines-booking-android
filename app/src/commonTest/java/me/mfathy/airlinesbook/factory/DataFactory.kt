package me.mfathy.airlinesbook.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeStringList(count: Int): List<String> {
        val items = mutableListOf<String>()
        repeat(count) {
            items.add(randomString())
        }
        return items
    }
}