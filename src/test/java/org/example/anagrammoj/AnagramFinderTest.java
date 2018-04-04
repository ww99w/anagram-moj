package org.example.anagrammoj;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnagramFinderTest {

    private static AnagramFinder theSubject;

    @BeforeClass
    public static void loadData() throws IOException {
        theSubject = new AnagramFinder();
        //loadWordList wordlist data from txt file into member variable to hold in memory
        List<String> words = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("wordlist.txt").getPath()), Charset.defaultCharset() );
        theSubject.loadWordList(words);
    }

    @Test
    public void testWordListNotEmpty() {
        assertFalse(theSubject.getWordlist().isEmpty());
        //match the words in the file given for this code test
        assertTrue(theSubject.getWordlist().size() == 338782);
    }

    @Test
    public void shouldReturnEmptyArrayWhenResolveAnagramsForInvalidInput(){
        assertTrue(theSubject.resolve(null).get(null).isEmpty());
        assertTrue(theSubject.resolve("").get("").isEmpty());
        //return empty for gibberish input
        assertTrue(theSubject.resolve("sdfwehrtgegfg").get("sdfwehrtgegfg").isEmpty());
    }

    @Test
    public void shouldReturnAnagramResultsForSingleValidWordInput(){
        assertFalse(theSubject.resolve("pictures").get("pictures").isEmpty());
    }

    @Test
    public void shouldReturnAnagramResultsForMultipleCommaDelimitedInput(){
        String input = "crepitus,paste,kinship,enlist,boaster,fresher,sinks,knits,sort";
        assertTrue(theSubject.resolve(input).size() == input.split(",").length);
    }

}
