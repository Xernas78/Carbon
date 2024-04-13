package dev.xernas.carbon.json;

public interface JsonSerializable<T> {

    String toJson();

    T fromJson(String json);

}
