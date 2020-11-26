package com.axgrid.plugins.axsql.dto;

/**
 * Как преобразовывать данные
 */
public enum  AxCastType {
    /**
     * Никак
     */
    None,
    /**
     * Через метод
     * например <code>.toString()</code>
     */
    Method,

    /**
     * Через статичный вызов
     * например <code>Utils.toString(name)</code>
     */
    Execute,

    /**
     * В json строку
     */
    JSON,
    /**
     * В Google Protocol buffer bytes
     */
    PROTOBUF,
    /**
     * Для строки like
    */
    LIKE
}
