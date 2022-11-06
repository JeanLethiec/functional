package com.ceihtel.functional;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurryingTest {
    @Test
    void original() {
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;

        int sum = add.apply(2, 4);

        assertEquals(6, sum);

        Function<Integer, Integer> mult5 = x -> x * 5;

        assertEquals(30, mult5.apply(sum));
    }

    @Test
    void currying() {
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        Integer sum = add.apply(2).apply(4);

        assertEquals(6, sum);

        Function<Integer, Integer> mult5 = x -> x * 5;

        assertEquals(30, mult5.apply(sum));
    }

    @Test
    void composition() {
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        Function<Integer, Integer> mult5 = x -> x * 5;

        BiFunction<Function<Integer, Integer>, Function<Integer, Integer>, Function<Integer, Integer>> compose = (f, g) -> x -> f.apply(g.apply(x));

        Function<Integer, Integer> mult5AfterAdd10 = compose.apply(mult5, add.apply(10));
        assertEquals(75, mult5AfterAdd10.apply(5));
    }
}
