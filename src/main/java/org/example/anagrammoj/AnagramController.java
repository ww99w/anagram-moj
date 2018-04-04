package org.example.anagrammoj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnagramController {

    @Autowired
    private AnagramFinder anagramFinder;

    @GetMapping("/{input}")
    public AnagramResult findAnagrams(@PathVariable String input){
        return anagramFinder.resolve(input);
    }
}
