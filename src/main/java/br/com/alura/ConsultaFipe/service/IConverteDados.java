package br.com.alura.ConsultaFipe.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
