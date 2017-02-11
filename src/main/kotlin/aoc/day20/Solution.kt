package aoc.day20

import java.io.File
import java.util.*


class Solution {

    data class Rule(val low: Long, val high: Long) {
        fun canMerge(rule: Rule): Boolean {
            return rule.low - 1 <= this.high
        }

        fun mergeRule(rule: Rule): Rule {
            return Rule(this.low, if (rule.high > this.high) rule.high else this.high)
        }
    }

    private fun foldWithMergeAction(lst: ArrayList<Rule>, rule: Rule): ArrayList<Rule> {
        if (lst.last().canMerge(rule)) {
            lst[lst.size - 1] = lst.last().mergeRule(rule)
        } else {
            lst.add(rule)
        }
        return lst
    }

    private fun parseLine(str: String): Rule {
        val sp = str.split("-")
        return Rule(sp[0].toLong(), sp[1].toLong())
    }

    private fun getMergedRules(): ArrayList<Rule> {
        return File("./inputs/day20").readLines()
                .map { parseLine(it) }
                .sortedBy { it.low }
                .fold(arrayListOf(Rule(-1, -1)), { lst, r -> foldWithMergeAction(lst, r) })
    }

    fun getLowestAvailableIp(): Long {
        return getMergedRules().get(0).high + 1
    }

    fun getAvailableCount(): Long {
        val lst = getMergedRules()
        lst.add(Rule(4294967296, 4294967296))

        var result = 0L
        for (i in lst.size - 1 downTo 1) {
            result += lst[i].low - lst[i - 1].high - 1
        }
        return result
    }
}

fun main(args: Array<String>) {
    val sol = Solution()
    println(sol.getLowestAvailableIp())
    println(sol.getAvailableCount())
}

