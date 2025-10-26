package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.ClienteDTO;
import com.project.antiguaburguers.mapper.ClienteMapper;
import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ClienteDTO buscarPorDpi(String dpi) {
        return clienteRepository.findById(dpi)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    @Transactional
    public ClienteDTO crearCliente(ClienteDTO dto) {
        if(clienteRepository.existsById(dto.dpi())){
            throw new EntityExistsException("Cliente ya existe");
        }
        Cliente cliente = mapper.toEntity(dto);
        clienteRepository.save(cliente);
        return mapper.toDTO(cliente);
    }

    @Transactional
    public ClienteDTO actualizarCliente(String dpi, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(dpi)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        mapper.updateEntityFromDto(dto, cliente); // si tu mapper lo soporta
        clienteRepository.save(cliente);
        return mapper.toDTO(cliente);
    }
}
