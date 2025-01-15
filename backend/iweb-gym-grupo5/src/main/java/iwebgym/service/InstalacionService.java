package iwebgym.service;

import iwebgym.dto.InstalacionDTO;
import iwebgym.model.Instalacion;
import iwebgym.repository.InstalacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstalacionService {

    @Autowired
    private InstalacionRepository instalacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<InstalacionDTO> findAllInstalaciones() {
        return instalacionRepository.findAll()
                .stream()
                .map(instalacion -> modelMapper.map(instalacion, InstalacionDTO.class))
                .collect(Collectors.toList());
    }
}
