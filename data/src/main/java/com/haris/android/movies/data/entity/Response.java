package com.haris.android.movies.data.entity;


import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("results")
    private T data;

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("status_message")
    private int statusMessage;

    @SerializedName("status_code")
    private int statusCode;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getStatusMessage() {
        return statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
