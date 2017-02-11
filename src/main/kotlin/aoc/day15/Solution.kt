package aoc.day15

import java.io.File
import java.util.*
import java.util.regex.Pattern

class Solution {

    val discList = ArrayList<Disc>()

    inner class Disc(val id: Int, var curPos: Int, val totalPos: Int) {
        fun inc() {
            this.curPos = (this.curPos + 1) % totalPos
        }

        fun ok(): Boolean {
            return totalPos - 1 + (0 - (id - 1)) % totalPos == curPos
        }

        override fun toString(): String {
            return "[Disc #$id] has totalPos $totalPos with curPos $curPos"
        }
    }

    val regex: Pattern = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+)")
    fun parseLine(str: String): Disc {
        val m = regex.matcher(str)
        if (m.find()) {
            return Disc(m.group(1).toInt(), m.group(3).toInt(), m.group(2).toInt())
        } else throw RuntimeException()
    }
}

fun main(args: Array<String>) {
    val sol = Solution()
    File("./inputs/day15")
//    File("./inputs/day15II")
            .readLines()
            .map { sol.parseLine(it) }
            .forEach { sol.discList.add(it) }

    sol.discList.forEach { println(it) }

    var count = 0
    while (sol.discList.filter { !it.ok() }.isNotEmpty()) {
        sol.discList.forEach { it.inc() }
        count++
    }
    println(count)

    sol.discList.forEach { println(it) }
}

