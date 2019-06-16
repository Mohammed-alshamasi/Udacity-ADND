package com.example.jokeslibrary;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class JokesLibrary {
    List<String> Jokes= asList
            ("Did you hear about the restaurant on the moon? Great food, no atmosphere.",
                    "What do you call a fake noodle? An Impasta.",
                    "How many apples grow on a tree? All of them.",
                    "Want to hear a joke about paper? Nevermind it's tearable.",
                    "I just watched a program about beavers. It was the best dam program I've ever seen.",
                    "Why did the coffee file a police report? It got mugged.",
                    "How does a penguin build it's house? Igloos it together.",
                    "Dad, did you get a haircut? No I got them all cut.",
                    "What do you call a Mexican who has lost his car? Carlos",
                    "Dad, can you put my shoes on? No, I don't think they'll fit me."
                    );

    public String getJoke() {
        Random random=new Random();
        return Jokes.get(random.nextInt(Jokes.size()));
    }
}


