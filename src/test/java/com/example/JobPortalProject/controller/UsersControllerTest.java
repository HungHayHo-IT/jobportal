package com.example.JobPortalProject.controller;

import com.example.JobPortalProject.services.UsersService;
import com.example.JobPortalProject.services.UsersTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @MockitoBean
    private UsersTypeService usersTypeService;

    @Test
    void testRegisterPostRequest() throws  Exception{
        mockMvc.perform(post("/register/new")
                .param("email","nguyenphuocnhahungl97@gmail.com")
                .param("password","123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard/"));


    }

}
