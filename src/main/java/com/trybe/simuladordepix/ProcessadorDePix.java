package com.trybe.simuladordepix;

import java.io.IOException;
import java.util.Optional;

public class ProcessadorDePix {

  private int valorDoPix;
  private String chaveDeUso;

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
    this.chaveDeUso = chave;
    if (valorDoPix <= 0) {
      throw  new ErroValorNaoPositivo();
    }
    if (chaveDeUso.trim() == "") {
      throw new ErroChaveEmBranco();
    }

    abrirConexao();

    return Mensagens.SUCESSO;
  }

  /**
   * Metodo para Abrir uma conexão com o servidor mockado.
   * @author Murilo Ribeiro
   * @throws ErroDeConexao Erro de conexao
   * @throws IOException erro de exceção
   * @throws ErroSaldoInsuficiente erro de saldo
   * @throws ErroChaveNaoEncontrada erro de chave
   * @throws ErroInterno erro interno
   */
  public void abrirConexao() throws
      ErroDeConexao, IOException, ErroSaldoInsuficiente, ErroChaveNaoEncontrada, ErroInterno {
    Conexao conexaoAberta = servidor.abrirConexao();
    try {
      String resultOperation = conexaoAberta.enviarPix(valorDoPix, chaveDeUso);
      if (resultOperation == "sucesso") {
        return;
      }
      if (resultOperation == "saldo_insuficiente") {
        throw new ErroSaldoInsuficiente();
      }
      if (resultOperation == "chave_pix_nao_encontrada") {
        throw new ErroChaveNaoEncontrada();
      }
      throw new ErroInterno();
    } finally {
      servidor.abrirConexao().close();
    }
  }
}
