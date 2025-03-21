package com.loquierestecno.loquierestecnoBack.service;

import com.loquierestecno.loquierestecnoBack.dto.RegistroPedidoDTO;
import com.loquierestecno.loquierestecnoBack.dto.RegistroUsuarioDTO;
import com.loquierestecno.loquierestecnoBack.exceptions.EmailAlreadyExistsException;
import com.loquierestecno.loquierestecnoBack.model.*;
import com.loquierestecno.loquierestecnoBack.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
}