package aoc.day06

import java.io.File
import kotlin.comparisons.compareBy
import kotlin.comparisons.compareByDescending


class Solution {

    private fun mostCommonChar(str: String): Char {
        return str.groupBy { it }
                .map { tp -> Pair(tp.key, tp.value.size) }
                .sortedWith(compareByDescending<Pair<Char, Int>> { it.second })
                .first().first
    }

    private fun leastCommonChar(str: String): Char {
        return str.groupBy { it }
                .map { tp -> Pair(tp.key, tp.value.size) }
                .sortedWith(compareBy<Pair<Char, Int>> { it.second })
                .first().first
    }

    private fun getResult(lineMapperfunc: (String) -> Char): String {
        val groupedInputs = Array(8) { "" }

        File("./inputs/day06")
                .readLines()
                .forEach { it.forEachIndexed { i, c -> groupedInputs.set(i, groupedInputs[i] + c) } }

        return groupedInputs.map { lineMapperfunc(it) }
                .fold("", { a, b -> a + b })
    }

    fun getSolution01Res(): String {
        return getResult({ str -> mostCommonChar(str) })
    }

    fun getSolution02Res(): String {
        return getResult({ str -> leastCommonChar(str) })
    }

}

fun main(args: Array<String>) {
    val sol = Solution()
    println(sol.getSolution01Res())
    println(sol.getSolution02Res())
}