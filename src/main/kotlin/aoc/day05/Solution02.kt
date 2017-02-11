package aoc.day05

import aoc.MD5Utils

class Solution02 {

    tailrec fun parseInput(str: String, index: Int, password: Array<Char?>) {
        if (password.filter { it == null }.count() == 0) {
            println("total: $index")
            println(password.fold("", { a, b -> a + b }))
            return
        }

        val digestStr = MD5Utils.getMD5Hex(str + index)

        if (digestStr.startsWith("00000")) {
            val pwdIndex = digestStr[5]
            if (pwdIndex <= '7' && pwdIndex >= '0') {
                if (password[pwdIndex - '0'] == null) {
                    val pwdValue = digestStr[6]
                    password[pwdIndex - '0'] = pwdValue
                    println("$pwdIndex $pwdValue")
                }
            }
        }

        parseInput(str, index + 1, password)
    }

}

fun main(args: Array<String>) {
    val sol = Solution02()
    sol.parseInput("reyedfim", 1, kotlin.arrayOfNulls<Char>(8))
}