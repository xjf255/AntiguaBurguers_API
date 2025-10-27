package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.CreatePedidoDTO;
import com.project.antiguaburguers.dto.PedidoDTO;
import com.project.antiguaburguers.dto.PedidoDetailDTO;
import com.project.antiguaburguers.mapper.PedidoCreateMapper;
import com.project.antiguaburguers.mapper.PedidoMapper;
import com.project.antiguaburguers.model.*;
import com.project.antiguaburguers.repository.*;
import com.project.antiguaburguers.utils.EstadoEntregaEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final DetallePedidoRepository detalleRepo;
    private final ClienteRepository clienteRepo;
    private final EstadoPedidoRepository estadoRepo;
    private final ComboRepository comboRepo;
    private final HamburguesaRepository hamburguesaRepo;
    private final BebidaRepository bebidaRepo;
    private final ComplementoRepository complementoRepo;

    private final PedidoMapper pedidoMapper;
    private final PedidoCreateMapper createMapper;
    private final RepartidorRepository repartidorRepository;
    private final EstadoEntregaRepository estadoEntregaRepository;

    public PedidoService(
            PedidoRepository pedidoRepo,
            DetallePedidoRepository detalleRepo,
            ClienteRepository clienteRepo,
            EstadoPedidoRepository estadoRepo,
            ComboRepository comboRepo,
            HamburguesaRepository hamburguesaRepo,
            BebidaRepository bebidaRepo,
            ComplementoRepository complementoRepo,
            PedidoMapper pedidoMapper,
            PedidoCreateMapper createMapper,
            RepartidorRepository repartidorRepository, EstadoEntregaRepository estadoEntregaRepository) {
        this.pedidoRepo = pedidoRepo;
        this.detalleRepo = detalleRepo;
        this.clienteRepo = clienteRepo;
        this.estadoRepo = estadoRepo;
        this.comboRepo = comboRepo;
        this.hamburguesaRepo = hamburguesaRepo;
        this.bebidaRepo = bebidaRepo;
        this.complementoRepo = complementoRepo;
        this.pedidoMapper = pedidoMapper;
        this.createMapper = createMapper;
        this.repartidorRepository = repartidorRepository;
        this.estadoEntregaRepository = estadoEntregaRepository;
    }

    @Transactional
    public List<PedidoDTO> listarPedidos() {
        return pedidoRepo.findAll()
                .stream()
                .map(pedidoMapper::toSummary)
                .toList();
    }

    @Transactional
    public PedidoDetailDTO obtenerDetalle(String numPedido) {
        Pedido pedido = pedidoRepo.findById(numPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado: " + numPedido));

        pedido.getDetalles().size();

        return pedidoMapper.toDetail(pedido);
    }

    @Transactional
    public PedidoDetailDTO crearPedido(CreatePedidoDTO dto) {

        Pedido pedido = createMapper.toEntity(dto);

        EstadoPedido estadoInicial = estadoRepo.findById("PENDIENTE")
                .orElseThrow(() -> new EntityNotFoundException("Estado inicial no encontrado"));
        pedido.setEstado(estadoInicial);

        Cliente cliente = clienteRepo.findById(dto.dpiCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        pedido.setCliente(cliente);

        pedido = pedidoRepo.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        for (var itemDto : dto.items()) {
            DetallePedido detalle = createMapper.toEntity(itemDto);
            detalle.setPedido(pedido);

            if (itemDto.numCombo() != null)
                detalle.setCombo(comboRepo.getReferenceById(itemDto.numCombo()));

            if (itemDto.hamburguesa() != null)
                detalle.setHamburguesa(hamburguesaRepo.getReferenceById(itemDto.hamburguesa()));

            if (itemDto.bebidaNombre() != null && itemDto.bebidaCantidad() != null)
                detalle.setBebida(bebidaRepo.getReferenceById(
                        new BebidaId(itemDto.bebidaNombre(), itemDto.bebidaCantidad()))
                );

            if (itemDto.complemento() != null)
                detalle.setComplemento(complementoRepo.getReferenceById(itemDto.complemento()));

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0)
                throw new IllegalArgumentException("Cantidad inválida en ítem");
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0)
                throw new IllegalArgumentException("Precio unitario inválido");

            BigDecimal subtotal = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotal);

            total = total.add(subtotal);

            detalleRepo.save(detalle);
        }

        pedido.setTotal(total);
        pedidoRepo.save(pedido);

        return pedidoMapper.toDetail(pedido);
    }

    @Transactional
    public PedidoDTO actualizarEstado(String numPedido, String nuevoEstado) {
        Pedido pedido = pedidoRepo.findById(numPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        EstadoPedido estado = estadoRepo.findById(nuevoEstado.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Estado inválido: " + nuevoEstado));

        pedido.setEstado(estado);
        pedidoRepo.save(pedido);

        return pedidoMapper.toSummary(pedido);
    }

    @Transactional
    public void eliminarPedido(String numPedido) {
        if (!pedidoRepo.existsById(numPedido))
            throw new EntityNotFoundException("Pedido no encontrado: " + numPedido);
        pedidoRepo.deleteById(numPedido);
    }
}
