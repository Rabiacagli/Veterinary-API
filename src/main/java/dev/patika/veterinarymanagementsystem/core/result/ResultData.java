package dev.patika.veterinarymanagementsystem.core.result;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result {

    private T data;

    public ResultData
            (boolean status,
             String message,
             String httpCode,
             T data)
    {
        super(status, message, httpCode);
        this.data = data;
    }

    public ResultData
            (boolean status,
             String message,
             String httpCode)
    {
        super(status, message, httpCode);
    }
}
