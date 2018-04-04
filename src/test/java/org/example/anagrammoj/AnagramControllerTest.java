package org.example.anagrammoj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AnagramController.class, secure = false)
public class AnagramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnagramFinder mockAnagramFinder;

    @Test
    public void testGetEndPointForValidPathVariableWordInput() throws Exception {
        AnagramResult anagramResult = new AnagramResult();
        anagramResult.put("pictures", Arrays.asList("crepitus","cuprites","pictures","piecrust"));

        String expected = "{\"pictures\":[\"crepitus\",\"cuprites\",\"pictures\",\"piecrust\"]}";

        Mockito.when(mockAnagramFinder.resolve(Mockito.anyString())).thenReturn(anagramResult);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                            .get("/pictures")
                                            .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

}