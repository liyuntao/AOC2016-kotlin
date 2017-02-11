package aoc.day17

import aoc.MD5Utils
import java.util.*


class Solution {

    data class Path(val path: String, val x: Int, val y: Int) {

        operator fun plus(directonChar: Char): Path {
            when (directonChar) {
                'U' -> return Path(path + directonChar, x, y - 1)
                'D' -> return Path(path + directonChar, x, y + 1)
                'L' -> return Path(path + directonChar, x - 1, y)
                'R' -> return Path(path + directonChar, x + 1, y)
            }
            throw RuntimeException("Not possible")
        }

        fun isValid(): Boolean {
            return (x >= 0 && x < 4) && (y >= 0 && y < 4)
        }

        fun atEndPoint(): Boolean {
            return x == 3 && y == 3
        }
    }

    private fun genAvaliableNextStep(curPath: Path): List<Path> {
        val boolArr = MD5Utils.getMD5Hex(curPath.path).take(4).map { it > 'a' }
        return charArrayOf('U', 'D', 'L', 'R').zip(boolArr)
                .filter { it.second }
                .map { it.first }
                .map { curPath + it }.filter { it.isValid() }
    }

    fun getShortestPath(puzzleInput: String): String {
        val q: Queue<Path> = LinkedList<Path>()
        q.offer(Path(puzzleInput, 0, 0))
        while (q.isNotEmpty()) {
            val curPath = q.poll()
            if (curPath.atEndPoint())
                return curPath.path.substring(puzzleInput.length)
            else
                genAvaliableNextStep(curPath).forEach { q.offer(it) }
        }
        throw RuntimeException("No valid Path to EndPoint!")
    }

    fun getLongestSteps(puzzleInput: String): Int {
        val q: Queue<Path> = LinkedList<Path>()
        q.offer(Path(puzzleInput, 0, 0))
        var maxSteps = -1
        while (q.isNotEmpty()) {
            val curPath = q.poll()
            if (curPath.atEndPoint())
                maxSteps = curPath.path.length - puzzleInput.length
            else
                genAvaliableNextStep(curPath).forEach { q.offer(it) }
        }
        return maxSteps
    }

}

fun main(args: Array<String>) {
    val sol = Solution()
    println(sol.getShortestPath("bwnlcvfs"))
    println(sol.getLongestSteps("bwnlcvfs"))
}

