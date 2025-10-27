package com.project.antiguaburguers.service;

import com.project.antiguaburguers.model.*;
import com.project.antiguaburguers.repository.*;
import com.project.antiguaburguers.utils.EstadoEntregaEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepo;
    private final PedidoRepository pedidoRepo;
    private final RepartidorRepository repartidorRepo;
    private final EstadoEntregaRepository estadoEntregaRepo;

    public EntregaService(
            EntregaRepository entregaRepo,
            PedidoRepository pedidoRepo,
            RepartidorRepository repartidorRepo,
            EstadoEntregaRepository estadoEntregaRepo
    ) {
        this.entregaRepo = entregaRepo;
        this.pedidoRepo = pedidoRepo;
        this.repartidorRepo = repartidorRepo;
        this.estadoEntregaRepo = estadoEntregaRepo;
    }

    @Transactional
    public Entrega asignarRepartidor(String numPedido, String dpiRepartidor) {
        Pedido pedido = pedidoRepo.findById(numPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado: " + numPedido));

        if (!pedido.getRequiereDelivery())
            throw new IllegalStateException("Este pedido no requiere entrega.");

        Repartidor repartidor = repartidorRepo.findById(dpiRepartidor)
                .orElseThrow(() -> new EntityNotFoundException("Repartidor no encontrado: " + dpiRepartidor));

        if (!repartidor.isEstado())
            throw new IllegalStateException("El repartidor no está disponible.");

        EstadoEntrega enRuta = estadoEntregaRepo.findById(EstadoEntregaEnum.EN_RUTA.toString())
                .orElseThrow(() -> new EntityNotFoundException("Estado EN_RUTA no encontrado."));

        // Crear o actualizar entrega
        Entrega entrega = entregaRepo.findById(numPedido).orElse(new Entrega());
        entrega.setPedido(pedido);
        entrega.setRepartidor(repartidor);
        entrega.setDireccionEntrega(pedido.getDireccionEntrega());
        entrega.setEstado(enRuta);

        // Cambiar estado del repartidor a ocupado
        repartidor.setEstado(false);

        entregaRepo.save(entrega);
        repartidorRepo.save(repartidor);

        return entrega;
    }

    @Transactional
    public Entrega marcarEntregada(String numPedido) {
        Entrega entrega = entregaRepo.findById(numPedido)
                .orElseThrow(() -> new EntityNotFoundException("Entrega no encontrada para pedido: " + numPedido));

        EstadoEntrega entregado = estadoEntregaRepo.findById(EstadoEntregaEnum.ENTREGADO.toString())
                .orElseThrow(() -> new EntityNotFoundException("Estado ENTREGADO no encontrado."));

        entrega.setEstado(entregado);

        // liberar repartidor
        if (entrega.getRepartidor() != null) {
            Repartidor r = entrega.getRepartidor();
            r.setEstado(true);
            repartidorRepo.save(r);
        }

        return entregaRepo.save(entrega);
    }
}
