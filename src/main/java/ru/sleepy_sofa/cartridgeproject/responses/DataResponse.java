package ru.sleepy_sofa.cartridgeproject.responses;

import java.util.List;

public class DataResponse<E> extends Response {
    private List<E> data;

    public DataResponse(String status, List<E> data) {
        this.setStatus(status);
        this.setData(data);
    }


    public DataResponse(List<E> data) {
        this.setStatus("success");
        this.data = data;
    }

    public DataResponse() {
    }

    public List<E> getData() {
        return this.data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
