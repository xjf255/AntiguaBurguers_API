package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteDTO dto);
}
