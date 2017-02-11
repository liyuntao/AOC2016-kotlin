package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FindStarsP3Q2 {

    private final List<Integer> list = new ArrayList<>();
    private int triCount = 0;

    private void parseInput(String inputs) {
        Arrays.stream(inputs.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .forEach(list::add);
    }

    private void check() {
        for (int i = 0; i < list.size(); i = i + 9) {
            for (int j = 0; j <= 2; j++) {
                if (isTri(list.get(i + j), list.get(i + j + 3), list.get(i + j + 6)))
                    triCount++;
            }
        }
    }

    private boolean isTri(int a, int b, int c) {
        List<Integer> lst = new ArrayList<Integer>() {{
            add(a);
            add(b);
            add(c);
        }};
        Collections.sort(lst);
        return lst.get(0) + lst.get(1) > lst.get(2);
    }

    private void printRes() {
        System.out.println(triCount);
    }

    public static void main(String[] args) throws IOException {
        FindStarsP3Q2 obj = new FindStarsP3Q2();

        File file = new File("./inputs/day03");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            obj.parseInput(line);
        }
        obj.check();
        obj.printRes();
    }
}