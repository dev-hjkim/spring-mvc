package com.example.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Marshaller marshaller;

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
                .andExpect(content().string("hello 1"));
    }

    @Test
    public void helloStatic() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Hello Mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
        ;
    }

    @Test
    public void StringMessage() throws Exception {
        this.mockMvc.perform(get("/message")
                .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void jsonMessage() throws Exception {
        Person person = new Person();
        person.setId(2022l);
        person.setName("hjkim");

        String jsonString = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2022))
                .andExpect(jsonPath("$.name").value("hjkim"))
                ;
    }

    @Test
    public void xmlMessage() throws Exception {
        Person person = new Person();
        person.setId(2022l);
        person.setName("hjkim");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
                        .contentType(MediaType.APPLICATION_XML)
                        .accept(MediaType.APPLICATION_XML)
                        .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/name").string("hjkim"))
                .andExpect(xpath("person/id").string("2022"))
        ;
    }
}