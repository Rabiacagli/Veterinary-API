package dev.patika.Veterinary.core.result;

import dev.patika.Veterinary.dto.response.vaccine.VaccineResponse;
import lombok.Getter;

import java.nio.channels.FileChannel;
import java.util.List;

@Getter
public class ResultData<T> extends Result{

   private T data;
   public ResultData(boolean status, String message, String code, T data) {
        super(status, message, code);
        this.data = data;
   }



}
