package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FindStarsP3Q1 {

    private int triCount = 0;

    private void parseInput(String inputs) {
        String[] input = inputs.split("\\s+");
        List<Integer> lst = new ArrayList<>(3);
        for (String s : input) {
            if (s.isEmpty()) continue;
            lst.add(Integer.parseInt(s));
        }
        Collections.sort(lst);
        if (lst.get(0) + lst.get(1) > lst.get(2)) triCount++;
    }

    void printRes() {
        System.out.println(triCount);
    }

    public static void main(String[] args) throws IOException {
        FindStarsP3Q1 obj = new FindStarsP3Q1();

        File file = new File("./inputs/day03");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            obj.parseInput(line);
        }

        obj.printRes();
    }
}