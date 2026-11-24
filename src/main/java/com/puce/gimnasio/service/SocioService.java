package com.puce.gimnasio.service;

import com.puce.gimnasio.model.Socio;
import com.puce.gimnasio.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocioService {
    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> traerTodos() {
        return socioRepository.findAll();
    }

    public Optional<Socio> traerUno(int id) {
        return socioRepository.findById(id);
    }

    public Socio guardar(Socio socio) {
        return socioRepository.save(socio);
    }

    public Optional<Socio> actualizar(int id, Socio datos) {
        return socioRepository.findById(id).map(socio -> {
            socio.setNombre(datos.getNombre());
            socio.setPlan(datos.getPlan());
            socio.setMensualidad(datos.getMensualidad());
            socio.setActivo(datos.isActivo());
            return socioRepository.save(socio);
        });
    }

    public boolean eliminar(int id) {
        if (!socioRepository.existsById(id)) return false;
        socioRepository.deleteById(id);
        return true;
    }

    public List<Socio> traerActivos() {
        return socioRepository.findAll().stream().filter(Socio::isActivo).toList();
    }

    public List<Socio> traerPorPlan(String plan) {
        return socioRepository.findByPlanIgnoreCase(plan);
    }

    public double calcularIngresos() {
        return traerActivos().stream().mapToDouble(Socio::getMensualidad).sum();
    }

    public Map<String, Double> calcularIngresosPorPlan() {
        return traerActivos().stream().collect(Collectors.groupingBy(
                socio -> socio.getPlan().toLowerCase(),
                Collectors.summingDouble(Socio::getMensualidad)));
    }

    public Optional<Socio> cambiarEstado(int id, boolean activar) {
        return socioRepository.findById(id).filter(socio -> socio.isActivo() != activar).map(socio -> {
            socio.setActivo(activar);
            return socioRepository.save(socio);
        });
    }
}
