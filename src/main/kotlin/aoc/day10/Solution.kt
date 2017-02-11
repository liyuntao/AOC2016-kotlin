package aoc.day10

import java.io.File
import java.util.*
import java.util.regex.Pattern

class Solution {
    val botMap = HashMap<String, Bot>()
    val outputMap = HashMap<String, Int>()

    data class EvilCommand(val lowTo: String, val highTo: String)

    inner class Bot(val id: String, val cmd: EvilCommand) {
        val values = ArrayList<Int>()

        fun receiveChip(value: Int) {
            values.add(value)
            if (values.size == 2) {
                val lowValue = values.min()!!
                if (cmd.lowTo.startsWith("bot"))
                    botMap[cmd.lowTo]?.receiveChip(lowValue)
                else
                    outputMap[cmd.lowTo] = lowValue

                val highValue = values.max()!!
                if (cmd.highTo.startsWith("bot"))
                    botMap[cmd.highTo]?.receiveChip(highValue)
                else
                    outputMap[cmd.highTo] = highValue

                if (highValue == 61 && lowValue == 17) println("[$id] responsible for comparing 61 and 17")
            }
        }

        override fun toString(): String {
            return "[$id] with cmd $cmd and values $values"
        }
    }


    val cmdRegex: Pattern = Pattern.compile("(bot \\d+) gives low to ((?:output|bot) \\d+) and high to ((?:output|bot) \\d+)")
    fun parseAndInitCmd(str: String) {
        val m = cmdRegex.matcher(str)
        if (m.find()) {
            val botId = m.group(1)
            val cmd = EvilCommand(m.group(2), m.group(3))
            botMap[botId] = Bot(botId, cmd)
        }
    }

    val valueRegex: Pattern = Pattern.compile("value (\\d+) goes to (bot \\d+)")
    fun parseAndInitValue(str: String) {
        val m = valueRegex.matcher(str)
        if (m.find()) {
            val botId = m.group(2)
            botMap[botId]?.receiveChip(m.group(1).toInt())
        }
    }
}

fun main(args: Array<String>) {

    val s1 = Solution()

    val map = File("./inputs/day10").readLines()
            .groupBy { it -> it.startsWith("value") }

    map[false]?.forEach { s1.parseAndInitCmd(it) }
    map[true]?.forEach { s1.parseAndInitValue(it) }

    println(s1.outputMap["output 0"]!! * s1.outputMap["output 1"]!! * s1.outputMap["output 2"]!!)
}

