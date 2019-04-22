package org.example.anagrammoj;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode

@Component
public class AnagramFinder {

    @Getter
    @Setter
    private Collection<String> wordlist;

    @Autowired
    private ResourceLoader resourceLoader;


    protected void loadWordList(List<String> wordlist){
        this.setWordlist(wordlist);
    }


    public AnagramResult resolve(String input) {

        if (input == null || input.isEmpty()) {
            return sendNoResultForInvalidInput(input);
        }

        //process input
        String[] inputs = input.split(",");

        AnagramResult result = new AnagramResult();

        //Anagram search

        for (String word : inputs) {
            ArrayList<String> anagramMatches = new ArrayList<>();
            char[] wordInput = normalise(word);

            for (String wordCandidate : wordlist) {
                char[] candidate = normalise(wordCandidate);
                if(Arrays.equals(wordInput, candidate))
                    anagramMatches.add(wordCandidate);
            }
            result.put(word, anagramMatches);
        }
        return result;
    }

    protected AnagramResult sendNoResultForInvalidInput(String input){
        AnagramResult result = new AnagramResult();
        result.put(input,Arrays.asList());
        return result;
    }

    private char[] normalise(String value){
        char[] chars = value.toCharArray();
        Arrays.sort(chars);
        return chars;
    }

    @PostConstruct
    public void init() throws IOException {
        String fileName = "wordlist.txt";
        List<String> words = new ArrayList<>();

//        URL resource = new URL("http://static.abscond.org/wordlist.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));

        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fileName)));

        String line;
        while((line = reader.readLine()) != null)
            words.add(line);

        reader.close();

        loadWordList(words);
    }
}
