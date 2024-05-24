package com.educacionit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.entity.Item;
import com.educacionit.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/items")
@Tag(name = "Gestión de Items", description = "Endpoints para gestionar items")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;

    @Operation(summary = "Obtener una lista de todos los items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de items obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un item por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item obtenido correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class)) }),
            @ApiResponse(responseCode = "404", description = "Item no encontrado", content = @Content) })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Item> getItemById(@PathVariable("id") Integer id) {
        Item item = itemService.getById(id);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info(item.toString());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Operation(summary = "Agregar un nuevo item")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Item creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        itemService.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un item existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> updateItem(@PathVariable("id") Integer id,
            @RequestBody Item itemModificado) throws Exception {
        Item currentItem = itemService.getById(id);
        if (currentItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemService.update(id, itemModificado);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un item existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Item eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Integer id) {
        Item item = itemService.getById(id);
        try {
            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            itemService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al eliminar el item", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
