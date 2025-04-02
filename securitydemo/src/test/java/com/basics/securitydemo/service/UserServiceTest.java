package com.basics.securitydemo.service;

import com.basics.securitydemo.dao.RoleDAO;
import com.basics.securitydemo.dao.UserDAO;
import com.basics.securitydemo.model.Role;
import com.basics.securitydemo.model.User;
import com.basics.securitydemo.requestdto.RegisterUserRequestDTO;
import com.basics.securitydemo.responsedto.RegisterUserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleDAO roleDAO;

        @Test
        void registerUserTest() throws Exception{
            RegisterUserRequestDTO requestDTO = new RegisterUserRequestDTO();
            requestDTO.setFirstName("John");
            requestDTO.setLastName("Doe");
            requestDTO.setEmail("john.doe@example.com");
            requestDTO.setRoleId(1L);
            requestDTO.setMobileNumber("1234567890");
            requestDTO.setAddress("New York");

            Role mockRole = new Role(1L, "USER_ROLE");

            User mockUser = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .role(mockRole)
                    .mobileNumber("1234567890")
                    .address("New York")
                    .password("encodedPassword")
                    .build();


            Mockito.when(roleDAO.getRolesById(1L)).thenReturn(mockRole);
            Mockito.doReturn("fixedPassword").when(userService).generatePasswordForUser();
            Mockito.when(userDAO.registerUser(Mockito.any(User.class))).thenReturn(mockUser);

            // Act: Call the method
            RegisterUserResponseDTO response = userService.registerUser(requestDTO);

            // Assert: Verify results
            assertNotNull(response);
            assertEquals("john.doe@example.com", response.getUserId());

            // Verify interactions
            Mockito.verify(roleDAO, Mockito.times(1)).getRolesById(1L);
            Mockito.verify(passwordEncoder, Mockito.times(1)).encode("fixedPassword");
            Mockito.verify(userDAO, Mockito.times(1)).registerUser(Mockito.any(User.class));


        }



}
