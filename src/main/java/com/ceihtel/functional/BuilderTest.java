package com.ceihtel.functional;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuilderTest {
    @Data
    @AllArgsConstructor
    static class Human {
        private String genre;
        private String firstName;
        private String lastName;

        static Function<String, Function<String, Function<String, Human>>> HUMAN_CREATOR = genre -> lastName -> firstName -> new Human(genre, firstName, lastName);

        static Function<String, Function<String, Human>> MAN_CREATOR = HUMAN_CREATOR.apply("MAN");
        static Function<String, Function<String, Human>> WOMAN_CREATOR = HUMAN_CREATOR.apply("WOMAN");

        interface AddGenre {
            AddLastName withGenre(String genre);
        }

        interface AddLastName {
            AddFirstName withLastName(String lastName);
        }

        interface AddFirstName {
            Human withFirstName(String firstName);
        }

        static AddGenre builder() {
            return genre -> lastName -> firstName -> new Human(genre, firstName, lastName);
        }
    }

    @Test
    void useCurriedConstructors() {
        Human man = Human.MAN_CREATOR.apply("Lethiec").apply("Jean");
        assertEquals("Lethiec", man.getLastName());
        assertEquals("Jean", man.getFirstName());
        assertEquals("MAN", man.getGenre());

        Human woman = Human.WOMAN_CREATOR.apply("Obama").apply("Michelle");
        assertEquals("Obama", woman.getLastName());
        assertEquals("Michelle", woman.getFirstName());
        assertEquals("WOMAN", woman.getGenre());
    }

    @Test
    void useBuilder() {
        Human man = Human.builder().withGenre("MAN").withLastName("Lethiec").withFirstName("Jean");

        assertEquals("Lethiec", man.getLastName());
        assertEquals("Jean", man.getFirstName());
        assertEquals("MAN", man.getGenre());
    }

}
