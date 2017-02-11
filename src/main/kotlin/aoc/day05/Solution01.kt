package aoc.day05

import aoc.MD5Utils

class Solution01 {

    tailrec fun parseInput(str: String, index: Int, count: Int) {
        if (count == 8) {
            println("total: $index")
            return
        }

        val digestStr = MD5Utils.getMD5Hex(str + index)

        if (digestStr.startsWith("00000")) {
            println(digestStr[5])
            parseInput(str, index + 1, count + 1)
        } else {
            parseInput(str, index + 1, count)
        }
    }

}

fun main(args: Array<String>) {
    val sol = Solution01()
    sol.parseInput("reyedfim", 1, 0)
}