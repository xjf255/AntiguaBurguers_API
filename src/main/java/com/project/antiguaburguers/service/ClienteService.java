package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.ClienteDTO;
import com.project.antiguaburguers.mapper.ClienteMapper;
import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    public ClienteDTO buscarPorDpi(String dpi) {
        return clienteRepository.findById(dpi)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    public ClienteDTO crearCliente(ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        clienteRepository.save(cliente);
        return mapper.toDTO(cliente);
    }
}
