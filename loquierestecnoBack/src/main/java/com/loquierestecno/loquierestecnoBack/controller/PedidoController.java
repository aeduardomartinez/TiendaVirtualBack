package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.CrearPedidoRequest;
import com.loquierestecno.loquierestecnoBack.dto.RegistroPedidoDTO;
import com.loquierestecno.loquierestecnoBack.model.Pedido;
import com.loquierestecno.loquierestecnoBack.service.CarritoService;
import com.loquierestecno.loquierestecnoBack.service.PedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {


    private final CarritoService carritoService;



}