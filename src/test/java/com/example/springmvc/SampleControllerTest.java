package com.example.springmvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello/hjkim"))
                .andDo(print())
                .andExpect(content().string("hello hjkim"));
    }

    @Test
    public void hello2() throws Exception {
        Person person = new Person();
        person.setName("hjkim");
        Person savedPerson = personRepository.save(person);

        this.mockMvc.perform(get("/hello")
                        .param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello hjkim"));
    }
}