package iwebgym.service;

import iwebgym.dto.SuscripcionDTO;
import iwebgym.model.Actividad;
import iwebgym.model.Suscripcion;
import iwebgym.repository.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuscripcionService {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    // Convertir Entity a DTO
    private SuscripcionDTO convertToDTO(Suscripcion suscripcion) {
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.setId(suscripcion.getId());
        dto.setTipoSuscripcion(suscripcion.getTipoSuscripcion());
        dto.setPrice(suscripcion.getPrice());
        return dto;
    }

    // Convertir DTO a Entity
    private Suscripcion convertToEntity(SuscripcionDTO dto) {
        Suscripcion suscripcion = new Suscripcion();
        if (dto.getId() != null) {
            suscripcion.setId(dto.getId());
        }
        suscripcion.setTipoSuscripcion(dto.getTipoSuscripcion());
        suscripcion.setPrice(dto.getPrice());
        return suscripcion;
    }

    @Transactional(readOnly = true)
    public List<SuscripcionDTO> getAllSuscripciones() {
        return suscripcionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SuscripcionDTO createSuscripcion(SuscripcionDTO suscripcionDTO) {
        if (suscripcionRepository.existsByTipoSuscripcion(suscripcionDTO.getTipoSuscripcion())) {
            throw new IllegalArgumentException("Ya existe una suscripción con ese nombre");
        }

        Long nextId = 1L; // Valor por defecto si no hay reservas
        List<Suscripcion> allSuscripciones = suscripcionRepository.findAll();
        if (!allSuscripciones.isEmpty()) {
            Long maxId = allSuscripciones.stream()
                    .mapToLong(Suscripcion::getId)
                    .max()
                    .orElse(0L);
            nextId = maxId + 1;
        }

        Suscripcion suscripcion = convertToEntity(suscripcionDTO);
        suscripcion.setId(nextId);
        suscripcion = suscripcionRepository.save(suscripcion);
        return convertToDTO(suscripcion);
    }

    @Transactional
    public void deleteSuscripcion(Long id) {
        if (!suscripcionRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró la suscripción con id: " + id);
        }
        suscripcionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public SuscripcionDTO getSuscripcionById(Long id) {
        return suscripcionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la suscripción con id: " + id));
    }
}

