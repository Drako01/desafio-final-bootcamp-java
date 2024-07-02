package com.educacionit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.educacionit.entity.User;
import com.educacionit.service.UserService;
@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
    }

    @Test
    void testGetAllClientes() {
        when(userService.getAll()).thenReturn(userList);

        ResponseEntity<List<User>> response = userController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testGetClienteById() {
        int userId = 1;
        User user = userList.get(0);
        when(userService.getById(userId)).thenReturn(user);


        ResponseEntity<User> response = userController.getUsuarioById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetClienteByIdNotFound() {
        int userId = 3;
        when(userService.getById(userId)).thenReturn(null);

        ResponseEntity<User> response = userController.getUsuarioById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddCliente() {
        User user = new User();


        ResponseEntity<Void> response = userController.addUsuario(user);

        verify(userService, times(1)).save(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateCliente() {
        int userId = 1;
        User userModificado = new User();
        when(userService.getById(userId)).thenReturn(userList.get(0));

        ResponseEntity<Void> response = userController.updateUsuario(userId, userModificado);

        verify(userService, times(1)).save(userModificado);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateClienteNotFound() {
        int userId = 3;
        User userModificado = new User();
        when(userService.getById(userId)).thenReturn(null);


        ResponseEntity<Void> response = userController.updateUsuario(userId, userModificado);

        verify(userService, never()).save(any(User.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCliente() {
        int userId = 1;
        when(userService.getById(userId)).thenReturn(userList.get(0));

        ResponseEntity<Void> response = userController.deleteUsuario(userId);

        verify(userService, times(1)).delete(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteClienteNotFound() {
        int userId = 3;
        when(userService.getById(userId)).thenReturn(null);

        ResponseEntity<Void> response = userController.deleteUsuario(userId);

        verify(userService, never()).delete(anyInt());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}