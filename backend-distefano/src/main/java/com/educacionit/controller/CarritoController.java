package com.educacionit.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.entity.Carrito;
import com.educacionit.entity.Producto;
import com.educacionit.entity.User;
import com.educacionit.service.CarritoServiceInterface;
import com.educacionit.service.UserService;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SELLER')")
@RequestMapping(value = "/carrito", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarritoController {

    @Autowired
    @Qualifier("carritoServiceImpl")
    private CarritoServiceInterface carritoService;

    @Autowired
    private UserService userService;

    
    @PostMapping("/{userId}")
    public ResponseEntity<Carrito> addCarrito(@PathVariable Integer userId, @RequestBody Carrito carrito) {
        User user = userService.getById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.addCarrito(carrito);
        Carrito savedCarrito = carritoService.save(carrito);
        return new ResponseEntity<>(savedCarrito, HttpStatus.CREATED);
    }

    
    @GetMapping("/{userId}")
    public ResponseEntity<Set<Carrito>> getCarritosByUserId(@PathVariable Integer userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Carrito> carritos = user.getCarritos();
        return new ResponseEntity<>(carritos, HttpStatus.OK);
    }

   
    @PostMapping("/{carritoId}/productos")
    public ResponseEntity<Carrito> addProductoToCarrito(@PathVariable Integer carritoId, @RequestBody Producto producto) {
        Carrito carrito = carritoService.findById(carritoId);
        if (carrito == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carrito.addProducto(producto);
        Carrito updatedCarrito = carritoService.save(carrito);
        return new ResponseEntity<>(updatedCarrito, HttpStatus.OK);
    }
}
