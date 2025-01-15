package iwebgym.service;

import iwebgym.dto.InstalacionDTO;
import iwebgym.dto.MonitorData;
import iwebgym.model.Monitor;
import iwebgym.repository.InstalacionRepository;
import iwebgym.repository.MonitorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<MonitorData> findAllMonitores() {
        return monitorRepository.findAll()
                .stream()
                .map(monitor -> modelMapper.map(monitor, MonitorData.class))
                .collect(Collectors.toList());
    }

}
