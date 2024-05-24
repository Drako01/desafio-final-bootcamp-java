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

import com.educacionit.entity.HistorialCompras;
import com.educacionit.service.HistorialComprasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/historial-compras")
@Tag(name = "Gestión de Historial de Compras", description = "Endpoints para gestionar el historial de compras")
public class HistorialComprasController {

    private static final Logger logger = LoggerFactory.getLogger(HistorialComprasController.class);

    @Autowired
    @Qualifier("historialComprasService")
    private HistorialComprasService historialComprasService;

    @Operation(summary = "Obtener una lista de todo el historial de compras")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de historial de compras obtenida correctamente", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = HistorialCompras.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<HistorialCompras>> getAllHistorialCompras() {
        List<HistorialCompras> historialCompras = historialComprasService.getAll();
        return new ResponseEntity<>(historialCompras, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un historial de compras por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de compras obtenido correctamente", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = HistorialCompras.class)) }),
        @ApiResponse(responseCode = "404", description = "Historial de compras no encontrado", content = @Content) })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HistorialCompras> getHistorialComprasById(@PathVariable("id") Integer id) {
        HistorialCompras historialCompras = historialComprasService.getById(id);
        if (historialCompras == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info(historialCompras.toString());
        return new ResponseEntity<>(historialCompras, HttpStatus.OK);
    }

    @Operation(summary = "Agregar un nuevo historial de compras")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Historial de compras creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HistorialCompras> addHistorialCompras(@RequestBody HistorialCompras historialCompras) {
        historialComprasService.save(historialCompras);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un historial de compras existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Historial de compras actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Historial de compras no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> updateHistorialCompras(@PathVariable("id") Integer id,
            @RequestBody HistorialCompras historialComprasModificado)  throws Exception {
        try {
            historialComprasService.update(id, historialComprasModificado);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar un historial de compras existente")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Historial de compras eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Historial de compras no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHistorialCompras(@PathVariable("id") Integer id) {
        HistorialCompras historialCompras = historialComprasService.getById(id);
        if (historialCompras == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        historialComprasService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
