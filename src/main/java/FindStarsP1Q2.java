import java.awt.geom.Line2D;
import java.util.ArrayList;

import static com.gf.cheetah.test.Direction.*;

class Coordinate {
    final int x;
    final int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Vector {
    final Coordinate start; // not included
    final Coordinate end; // included

    Vector(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }
}

public class FindStarsP1Q2 {
    private Direction[] list = {UP, RIGHT, DOWN, LEFT};
    private int currentDirectionIndex = 0;

    private int x = 0;
    private int y = 0;

    private ArrayList<Vector> vectors = new ArrayList<>();

    public static void main(String[] args) {
        FindStarsP1Q2 fs = new FindStarsP1Q2();
        String input = "R1, R3, L2, L5, L2, L1, R3, L4, R2, L2, L4, R2, L1, R1, L2, R3, L1, L4, R2, L5, R3, R4, L1, R2, L1, R3, L4, R5, L4, L5, R5, L3, R2, L3, L3, R1, R3, L4, R2, R5, L4, R1, L1, L1, R5, L2, R1, L2, R188, L5, L3, R5, R1, L2, L4, R3, R5, L3, R3, R45, L4, R4, R72, R2, R3, L1, R1, L1, L1, R192, L1, L1, L1, L4, R1, L2, L5, L3, R5, L3, R3, L4, L3, R1, R4, L2, R2, R3, L5, R3, L1, R1, R4, L2, L3, R1, R3, L4, L3, L4, L2, L2, R1, R3, L5, L1, R4, R2, L4, L1, R3, R3, R1, L5, L2, R4, R4, R2, R1, R5, R5, L4, L1, R5, R3, R4, R5, R3, L1, L2, L4, R1, R4, R5, L2, L3, R4, L4, R2, L2, L4, L2, R5, R1, R4, R3, R5, L4, L4, L5, L5, R3, R4, L1, L3, R2, L2, R1, L3, L5, R5, R5, R3, L4, L2, R4, R5, R1, R4, L3";
        fs.parseInput(input);
    }

    protected void parseInput(String inputs) {
        for (String input : inputs.split(", ")) {
            step(input);
            Coordinate crossPoint = checkIntersecting();
            if (crossPoint != null) {
                System.out.println("intersecting point: " + crossPoint.x + ", " + crossPoint.y);
                return;
            }
        }
        System.out.println("no intersecting point");
    }

    private void step(String singleStep) {
        Coordinate start = new Coordinate(x, y);

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

        Coordinate end = new Coordinate(x, y);

        vectors.add(new Vector(start, end));
    }

    private Coordinate checkIntersecting() {
        Vector lastV = vectors.get(vectors.size() - 1);

        for (int i = vectors.size() - 2; i >= 0; i--) {
            Coordinate res = checkSectionIntersecting(vectors.get(i), lastV);
            if (res != null) return res;
        }
        return null;
    }

    private Coordinate checkSectionIntersecting(Vector v1, Vector v2) {
        if (v1.end.x == v2.start.x && v1.end.y == v2.start.y) return null;

        if (Line2D.linesIntersect(
                v1.start.x, v1.start.y,
                v1.end.x, v1.end.y,
                v2.start.x, v2.start.y,
                v2.end.x, v2.end.y)) {
            // find intersection point
            if ((v1.start.x == v1.end.x && v2.start.x == v2.end.x)
                    || (v1.start.y == v1.end.y && v2.start.y == v2.end.y)) { // 平行
                if (v1.start.x == v1.end.x) {
                    if (v2.start.y > v1.end.y) {
                        return new Coordinate(v1.end.x, v1.end.y);
                    } else {
                        return new Coordinate(v1.start.x, v1.start.y);
                    }
                } else {
                    if (v2.start.x > v1.end.x) {
                        return new Coordinate(v1.end.x, v1.end.y);
                    } else {
                        return new Coordinate(v1.start.x, v1.start.y);
                    }
                }
            } else { // 垂直
                if (v1.start.x == v1.end.x) {
                    return new Coordinate(v1.start.x, v2.start.y);
                } else {
                    return new Coordinate(v2.start.x, v1.start.y);
                }
            }
        }
        return null;
    }

}