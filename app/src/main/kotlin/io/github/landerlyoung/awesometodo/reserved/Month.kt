package io.github.landerlyoung.awesometodo.reserved

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-06-07
 * Time:   10:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
fun main(args: Array<String>) {
    val january = Month(1)
    val february = january + 1
    val december = Month(12)
    val november = december - 1
    var current = Month(5)

    assert(january < february)
    assert(december > february)
    assert(january != february)

    // a month passed
    current += 1
    println(current)

    // another month passed
    current++
    println(current)

    // use time machine to travel one month back
    current--
    println(current)

    println("")
    val monthRange = january..december
    for (month in monthRange) {
        println(month)
    }

    println("")
    for (month in january until december) {
        println(month)
    }
}

data class Month(val monthIndex: Int) : Comparable<Month> {
    // +
    operator fun plus(count: Int): Month {
        return Month(monthIndex + count)
    }

    operator fun minus(count: Int): Month {
        return Month(monthIndex - count)
    }

    // >, <, >=, <=
    override operator fun compareTo(other: Month): Int {
        return monthIndex - other.monthIndex
    }

    // --
    operator fun inc(): Month {
        return Month(monthIndex + 1)
    }

    operator fun dec(): Month {
        return Month(monthIndex - 1)
    }

    operator fun rangeTo(end: Month): MonthRange {
        return MonthRange(this, end)
    }

    infix fun until(end: Month): MonthRange {
        return MonthRange(this, end - 1)
    }

    override fun toString() = when (monthIndex) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> throw AssertionError()
    }


    data class MonthRange(val start: Month, val end: Month) : Iterable<Month> {
        override operator fun iterator(): MonthIterator {
            return MonthIterator(this)
        }

        class MonthIterator(private val range: MonthRange) : Iterator<Month> {
            private var current: Month = range.start

            override operator fun hasNext(): Boolean {
                return current <= range.end
            }

            override operator fun next(): Month {
                val result = current
                current += 1
                return result
            }
        }
    }

}

