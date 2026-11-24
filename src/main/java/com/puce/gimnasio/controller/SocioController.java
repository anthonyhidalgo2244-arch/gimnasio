package com.puce.gimnasio.controller;

import com.puce.gimnasio.model.Socio;
import com.puce.gimnasio.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/socio")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<Socio> verTodos() {
        return socioService.traerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socio> traerUno(@PathVariable int id) {
        return socioService.traerUno(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Socio> guardar(@RequestBody Socio socio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.guardar(socio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizar(@PathVariable int id, @RequestBody Socio socio) {
        return socioService.actualizar(id, socio).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!socioService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos")
    public List<Socio> verActivos() {
        return socioService.traerActivos();
    }

    @GetMapping("/plan/{plan}")
    public List<Socio> verPorPlan(@PathVariable String plan) {
        return socioService.traerPorPlan(plan);
    }

    @GetMapping("/ingresos")
    public double verIngresos() {
        return socioService.calcularIngresos();
    }

    @GetMapping("/ingresos-por-plan")
    public Map<String, Double> verIngresosPorPlan() {
        return socioService.calcularIngresosPorPlan();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Socio> activar(@PathVariable int id) {
        return cambiarEstado(id, true);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Socio> desactivar(@PathVariable int id) {
        return cambiarEstado(id, false);
    }

    private ResponseEntity<Socio> cambiarEstado(int id, boolean activar) {
        if (socioService.traerUno(id).isEmpty()) return ResponseEntity.notFound().build();
        return socioService.cambiarEstado(id, activar)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
