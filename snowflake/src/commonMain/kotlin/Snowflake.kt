/*
 * 🐻‍❄️❄ Snowflake: Easy to use Kotlin library to help you generate Twitter snowflakes asynchronously
 * Copyright (c) 2022-2023 Noelware Team <team@noelware.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.noelware.charted.snowflake

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock

public const val sequenceBits: Int = 12
public const val epochBits: Int = 41
public const val nodeBits: Int = 10

/**
 * Represents the actual Snowflake class to generate snowflakes. A [Twitter snowflake](https://developer.twitter.com/en/docs/twitter-ids)
 * is a 64-bit unsigned integer with 4 fields that have a fixed epoch value.
 */
@Suppress("MemberVisibilityCanBePrivate")
public class Snowflake {
    // max bits (sequence, node)
    private val maxSequenceBits = (1L shl sequenceBits) - 1
    private val maxNodeBits = (1L shl nodeBits) - 1

    private var lastTimestamp = -1L
    private val sequence = atomic(0L)
    private var mutex = Mutex()

    /**
     * Represents the epoch to use when creating snowflakes
     */
    public var epoch: Long = 1288834974657

    /**
     * Represents the node ID
     */
    public var nodeId: Int = 0

    init {
        if (nodeId < 0 || nodeId > maxNodeBits) {
            throw IllegalStateException("Node ID [$nodeId] cannot go under 0 and over $maxNodeBits")
        }
    }

    /**
     * Generates a new snowflake [ID].
     */
    public suspend fun generate(): ID = mutex.withLock {
        var current = now()
        if (current < lastTimestamp) {
            throw IllegalStateException("Invalid clock (went backwards)")
        }

        if (current == lastTimestamp) {
            sequence.value = (sequence.value + 1) and maxSequenceBits
            if (sequence.value == 0L) {
                current = waitUntil(current)
            }
        } else {
            sequence.value = 0
        }

        lastTimestamp = current
        ID(
            current shl (nodeBits + sequenceBits) or (nodeId shl sequenceBits).toLong() or sequence.value,
            epoch
        )
    }

    private fun waitUntil(current: Long): Long {
        var currentTs = current
        while (currentTs == lastTimestamp) {
            currentTs = now()
        }

        return currentTs
    }

    private fun now(): Long = Clock.System.now().toEpochMilliseconds() - epoch
}
