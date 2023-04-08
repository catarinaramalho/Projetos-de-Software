package com.ufcg.psoft.mercadofacil.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoValidarCodigoDeBarrasTest {
    @Autowired
    ProdutoValidarCodigoDeBarras driver;

    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;
    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500100")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build());
        produto = produtoRepository.find(10L);

        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500100")
                        .nome("Nome Produto Alterado")
                        .fabricante("Nome Fabricante Alterado")
                        .preco(500.00)
                        .build());
    }

    @Test
    @DisplayName("Validação de código de barras")
    void validarCodigo() {
        assertEquals(driver.validar(produto.getCodigoBarra()), true);
    }
    @Test
    @DisplayName("Validação de código de barras inválido")
    void validarCodigoInvalido() {
        assertEquals(driver.validar("7199137500105"), false);
    }
    @Test
    @DisplayName("Validação de código de barras nulo")
    void validarCodigoNulo() {
        assertEquals(driver.validar(null), false);
    }
    @Test
    @DisplayName("Validação de código de barras com menos de 13 dígitos")
    void validarCodigoMenosDe13Digitos() {
        assertEquals(driver.validar("78991375010"), false);
    }
    @Test
    @DisplayName("Validação de código de barras com mais de 13 dígitos")
    void validarCodigoMaisDe13Digitos() {
        assertEquals(driver.validar("78991375001045"), false);
    }
    @Test
    @DisplayName("Validação de código de barras com dígito verificador inválido")
    void validarCodigoDigitoVerificadorInvalido() {
        assertEquals(driver.validar("7899137500105"), false);
    }


}
