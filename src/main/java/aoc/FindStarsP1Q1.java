package aoc;

import java.util.Arrays;

import static aoc.Direction.*;

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

public class FindStarsP1Q1 {
    private Direction[] list = {UP, RIGHT, DOWN, LEFT};
    private int currentDirectionIndex = 0;

    private int x = 0;
    private int y = 0;

    public static void main(String[] args) {
        FindStarsP1Q1 fs = new FindStarsP1Q1();
        String input = "R1, R3, L2, L5, L2, L1, R3, L4, R2, L2, L4, R2, L1, R1, L2, R3, L1, L4, R2, L5, R3, R4, L1, R2, L1, R3, L4, R5, L4, L5, R5, L3, R2, L3, L3, R1, R3, L4, R2, R5, L4, R1, L1, L1, R5, L2, R1, L2, R188, L5, L3, R5, R1, L2, L4, R3, R5, L3, R3, R45, L4, R4, R72, R2, R3, L1, R1, L1, L1, R192, L1, L1, L1, L4, R1, L2, L5, L3, R5, L3, R3, L4, L3, R1, R4, L2, R2, R3, L5, R3, L1, R1, R4, L2, L3, R1, R3, L4, L3, L4, L2, L2, R1, R3, L5, L1, R4, R2, L4, L1, R3, R3, R1, L5, L2, R4, R4, R2, R1, R5, R5, L4, L1, R5, R3, R4, R5, R3, L1, L2, L4, R1, R4, R5, L2, L3, R4, L4, R2, L2, L4, L2, R5, R1, R4, R3, R5, L4, L4, L5, L5, R3, R4, L1, L3, R2, L2, R1, L3, L5, R5, R5, R3, L4, L2, R4, R5, R1, R4, L3";
        fs.parseInput(input);
    }

    protected void parseInput(String input) {
        Arrays.stream(input.split(", ")).forEach(this::step);
        System.out.println("Current coordinate: (x, y)=" + x + ", " + y);
        System.out.println("otal steps: " + (x + y));
    }

    private void step(String singleStep) {
        if ('R' == singleStep.charAt(0)) {
            currentDirectionIndex++;
        } else {
            currentDirectionIndex--;
        }
        currentDirectionIndex = (currentDirectionIndex + list.length) % list.length; // roll

        int steps = Integer.parseInt(singleStep.substring(1));

        switch (list[currentDirectionIndex]) {
            case UP:
                y = y + steps;
                break;
            case DOWN:
                y = y - steps;
                break;
            case LEFT:
                x = x - steps;
                break;
            case RIGHT:
                x = x + steps;
                break;
        }
    }

}