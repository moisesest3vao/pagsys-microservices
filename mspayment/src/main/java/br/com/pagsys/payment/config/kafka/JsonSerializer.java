package br.com.pagsys.payment.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {

    private final Gson gson = new GsonBuilder().create();


    @Override
    public byte[] serialize(String s, T t) {
        return gson.toJson(t).getBytes();
    }

    public void close() {

    }
}
