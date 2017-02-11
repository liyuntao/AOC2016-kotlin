package aoc.day21

import java.io.File


class Solution {
    interface ReversibleCommand {
        fun getReversedCmd(): ReversibleCommand {
            return this
        }
    }

    data class SwapPosition(val x: Int, val y: Int) : ReversibleCommand
    data class SwapLetter(val a: Char, val b: Char) : ReversibleCommand
    data class RotateLeft(val steps: Int) : ReversibleCommand {
        override fun getReversedCmd(): ReversibleCommand {
            return RotateRight(steps)
        }
    }

    data class RotateRight(val steps: Int) : ReversibleCommand {
        override fun getReversedCmd(): ReversibleCommand {
            return RotateLeft(steps)
        }
    }


    data class ReverseRotateRightBaseOnLetter(val a: Char) : ReversibleCommand
    data class RotateRightBaseOnLetter(val a: Char) : ReversibleCommand {
        override fun getReversedCmd(): ReversibleCommand {
            return ReverseRotateRightBaseOnLetter(a)
        }
    }

    data class ReversePositions(val from: Int, val to: Int) : ReversibleCommand
    data class MovePosition(val from: Int, val to: Int) : ReversibleCommand {
        override fun getReversedCmd(): ReversibleCommand {
            return MovePosition(to, from)
        }
    }

    private fun parseLine(str: String): ReversibleCommand {
        if (str.startsWith("rotate left")) {
            return RotateLeft(str.split(" ")[2].toInt())
        } else if (str.startsWith("rotate right")) {
            return RotateRight(str.split(" ")[2].toInt())
        } else if (str.startsWith("rotate based on position of letter")) {
            return RotateRightBaseOnLetter(str[str.length - 1])
        } else if (str.startsWith("swap letter")) {
            return SwapLetter(str[12], str[str.length - 1])
        } else if (str.startsWith("reverse positions")) {
            return ReversePositions(str[18] - '0', str[str.length - 1] - '0')
        } else if (str.startsWith("swap position")) {
            return SwapPosition(str[14] - '0', str[str.length - 1] - '0')
        } else {
            return MovePosition(str[14] - '0', str[str.length - 1] - '0')
        }
    }

    private fun convert(str: String, cmd: ReversibleCommand): String {
        when (cmd) {
            is RotateLeft ->
                return str.substring(cmd.steps) + str.substring(0, cmd.steps)
            is RotateRight ->
                return str.substring(str.length - cmd.steps) + str.substring(0, str.length - cmd.steps)
            is RotateRightBaseOnLetter -> {
                val index = str.indexOf(cmd.a)
                val step = 1 + (if (index >= 4) index + 1 else index)
                val realStep = step % str.length
                return convert(str, RotateRight(realStep))
            }
            is ReverseRotateRightBaseOnLetter -> {
                var tmp = str
                while (true) {
                    if (str == convert(tmp, RotateRightBaseOnLetter(cmd.a))) {
                        return tmp
                    } else {
                        tmp = convert(tmp, RotateRight(1))
                    }
                }
            }
            is SwapLetter -> return str.replace(cmd.a, '#')
                    .replace(cmd.b, cmd.a).replace('#', cmd.b)
            is SwapPosition -> {
                val a = str[cmd.x]
                val b = str[cmd.y]
                return str.replace(a, '#')
                        .replace(b, a).replace('#', b)
            }
            is MovePosition -> {
                return StringBuffer(str).deleteCharAt(cmd.from).insert(cmd.to, str[cmd.from]).toString()
            }
            is ReversePositions -> {
                return str.substring(0, cmd.from) +
                        str.slice(cmd.from..cmd.to).reversed() +
                        str.substring(cmd.to + 1, str.length)
            }
        }
        return ""
    }

    fun getScrambledStr(input: String): String {
        return File("./inputs/day21").readLines()
                .map { parseLine(it) }
                .fold(input, { str, cmd -> convert(str, cmd) })
    }

    fun getUnScrambledStr(input: String): String {
        return File("./inputs/day21").readLines()
                .map { parseLine(it) }.reversed()
                .map { it.getReversedCmd() }
                .fold(input, { str, cmd -> convert(str, cmd) })
    }

}

fun main(args: Array<String>) {

    val sol = Solution()
    println(sol.getScrambledStr("abcdefgh"))
    println(sol.getUnScrambledStr("fbgdceah"))

}

