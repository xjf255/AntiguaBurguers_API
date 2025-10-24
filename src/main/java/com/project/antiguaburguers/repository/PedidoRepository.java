package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,String> {
}
