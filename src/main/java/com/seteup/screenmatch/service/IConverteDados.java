package com.seteup.screenmatch.service;

public interface IConverteDados {
   <T> T obterDados(String json, Class<T> classe);
}
