package com.ultimatix.cashrich.network;

import com.ultimatix.cashrich.datamodels.Feed;
import com.ultimatix.cashrich.datamodels.sip.SIPData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CashRichService {
    @Headers({"Accept: application/json"})
    @GET("/testCashRich")
    Call<List<SIPData>> getSIPData();
}