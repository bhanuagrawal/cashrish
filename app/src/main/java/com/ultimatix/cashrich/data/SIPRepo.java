package com.ultimatix.cashrich.data;

import android.app.Application;

import com.ultimatix.cashrich.Myapp;
import com.ultimatix.cashrich.data.entity.Article;
import com.ultimatix.cashrich.network.CashRichService;
import com.ultimatix.cashrich.network.Clients;
import com.ultimatix.cashrich.network.NewsService;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import retrofit2.Callback;


public class SIPRepo {

    Application application;
    CashRichService cashRichService;

    @Inject
    AppDatabase appDatabase;

    public SIPRepo(Application application) {
        this.application = application;
        cashRichService = new Clients(application).getCashRichClient().create(CashRichService.class);
        ((Myapp)application).getMainAppComponent().inject(this);
    }

    public void getSIPData(Callback callback) {
        cashRichService.getSIPData().enqueue(callback);;
    }
}
