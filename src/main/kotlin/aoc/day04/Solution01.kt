package aoc.day04

import java.io.File
import kotlin.comparisons.compareByDescending
import kotlin.comparisons.thenBy


class Solution {

    var count = 0

    fun parseInput(str: String) { // e.g. vxupkizork-sgmtkzoi-pkrrehkgt-zxgototm-644[kotgr]
        val len = str.length
        val text = str.substring(0, len - 11).replace("-", "") // e.g. vxupkizorksgmtkzoipkrrehkgtzxgototm
        val checksum = str.substring(len - 6, len - 1) // e.g. "kotgr"
        val id = str.substring(len - 10, len - 7) // e.g. "644"

        if (matchCode(text, checksum)) count += id.toInt()
    }

    private fun matchCode(text: String, checksum: String): Boolean {
        val res = text.groupBy { it }
                .map { tp -> Pair(tp.key, tp.value.size) }
                .sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
                .take(5)
                .map { pair -> pair.first }
                .fold("", { a, b -> a + b })
        return res == checksum
    }

}

fun main(args: Array<String>) {
    val sol = Solution()

    File("./inputs/day04")
            .readLines()
            .forEach { sol.parseInput(it) }

    println(sol.count)
}