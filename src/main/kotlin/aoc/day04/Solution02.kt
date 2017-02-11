package aoc.day04

import java.io.File


class Solution02 {

    fun parseInput(str: String) {
        val len = str.length
        val text = str.substring(0, len - 11)
        val id = str.substring(len - 10, len - 7)

        matchCode(text, id.toInt() % 26, id)
    }

    fun matchCode(text: String, roll: Int, id: String) {
        val res = text.replace("-", " ")
                .map { c -> if (c == ' ') ' ' else if (c + roll > 'z') c + roll - 26 else c + roll }
                .fold("", { a, b -> a + b })

        if (res == "northpole object storage") println(id)
    }

}

fun main(args: Array<String>) {
    val sol = Solution02()

    File("./inputs/day04")
            .readLines()
            .forEach { sol.parseInput(it) }
}