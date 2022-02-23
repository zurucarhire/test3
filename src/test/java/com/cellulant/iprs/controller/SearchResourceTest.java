package com.cellulant.iprs.controller;

import com.cellulant.iprs.dto.SearchDTO;
import com.cellulant.iprs.dto.UserRoleDTO;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchResourceTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static SearchDTO searchDTO1, searchDTO2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Execute code before running each test
     */
    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        searchDTO1 = SearchDTO.builder()
                .requestNumber(1234L)
                .requestType("Passport")
                .requestSerialNumber(1234L)
                .build();

        searchDTO2 = SearchDTO.builder()
                .requestNumber(2234L)
                .requestType("Passport")
                .requestSerialNumber(2234L)
                .build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {

    }

    @Test
    @DisplayName("shouldAllowFindIfRoleUser")
    public void shouldAllowFindIfRoleUser() throws Exception {
       // when(userService.findAllUserRoles()).thenReturn(Arrays.asList(userRoleDTO1, userRoleDTO2));
        mockMvc.perform(post("/api/iprs/search/find/{userId}",1)
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(searchDTO1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldForbidSearchRequestsIfRoleUser")
    public void shouldForbidSearchRequestsIfRoleUser() throws Exception {
        mockMvc.perform(get("/api/iprs/search/requests")
                        .param("fromDate", "20220101")
                        .param("toDate", "20220202")
                        .param("tag", "REFRESHED_LOG")
                        .param("requestNumber", "1234")
                        .param("requestSerialNumber", "1234")
                        .param("requestType", "Passport")
                        .param("requestBy", "kenotieno")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidSearchRequestsIfRoleCreator")
    public void shouldForbidSearchRequestsIfRoleCreator() throws Exception {
        mockMvc.perform(get("/api/iprs/search/requests")
                        .param("fromDate", "20220101")
                        .param("toDate", "20220202")
                        .param("tag", "REFRESHED_LOG")
                        .param("requestNumber", "1234")
                        .param("requestSerialNumber", "1234")
                        .param("requestType", "Passport")
                        .param("requestBy", "kenotieno")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowSearchRequestsIfRoleEditor")
    public void shouldAllowSearchRequestsIfRoleEditor() throws Exception {
        mockMvc.perform(get("/api/iprs/search/requests")
                        .param("fromDate", "20220101")
                        .param("toDate", "20220202")
                        .param("tag", "REFRESHED_LOG")
                        .param("requestNumber", "1234")
                        .param("requestSerialNumber", "1234")
                        .param("requestType", "Passport")
                        .param("requestBy", "kenotieno")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowSearchRequestsIfRoleAdmin")
    public void shouldAllowSearchRequestsIfRoleAdmin() throws Exception {
        mockMvc.perform(get("/api/iprs/search/requests")
                        .param("fromDate", "20220101")
                        .param("toDate", "20220202")
                        .param("tag", "REFRESHED_LOG")
                        .param("requestNumber", "1234")
                        .param("requestSerialNumber", "1234")
                        .param("requestType", "Passport")
                        .param("requestBy", "kenotieno")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
