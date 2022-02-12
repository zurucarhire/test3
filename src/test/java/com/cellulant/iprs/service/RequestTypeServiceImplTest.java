package com.cellulant.iprs.service;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.repository.RequestTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RequestTypeServiceImplTest {

    @Autowired
    private IRequestTypeService requestTypeService;

    @MockBean
    private RequestTypeRepository requestTypeRepository;

    protected MockMvc mockMvc;

    private static RequestType requestType1, requestType2;

    @BeforeAll
    public static void setupModel() {
        requestType1 = RequestType.builder().requestTypeName("Alias").build();
        requestType2 = RequestType.builder().requestTypeName("Passport").build();
    }

    @Test
    @DisplayName("Should Find All Request Types")
    public void shouldFindAllRequestTypes()  {
        // when
        requestTypeRepository.findAll();

        // then
        // verify that role repository.findAll() is invoked
        verify(requestTypeRepository, times(1)).findAll();
    }
}
