package com.trybe.simuladordepix;

import java.io.IOException;
import java.util.Optional;

public class ProcessadorDePix {

  private int valorDoPix;

  private final Servidor servidor;

  public ProcessadorDePix(Servidor servidor) {
    this.servidor = servidor;
  }

  /**
   * Executa a operação do pix. Aqui é implementada a lógica de negócio
   * sem envolver as interações do aplicativo com a pessoa usuária.
   *
   * @param valor Valor em centavos a ser transferido.
   * @param chave Chave Pix do beneficiário da transação.
   *
   * @throws ErroDePix Erro de aplicação, caso ocorra qualquer inconformidade.
   * @throws IOException Caso aconteça algum problema relacionado à comunicação
   *                     entre o aplicativo e o servidor na nuvem.
   */
  public String executarPix(int valor, String chave) throws ErroDePix, IOException {
    this.valorDoPix = valor;
    Optional<Integer> optional = Optional.ofNullable(valorDoPix);
    try {
      if (optional.isPresent()) {
        if (valorDoPix <= 0) {
          throw  new ErroValorNaoPositivo();
        }
        if (chave == "") {
          throw new ErroChaveEmBranco();
        }
      }
    } catch (Exception e) {
      return e.getMessage();
    }
    return Mensagens.SUCESSO;
  }
}
