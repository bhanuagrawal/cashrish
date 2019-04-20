package com.ultimatix.cashrich.viewmodels;

import android.app.Application;

import com.ultimatix.cashrich.data.SIPRepo;
import com.ultimatix.cashrich.datamodels.sip.SIPData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SIPViewModel extends AndroidViewModel {

    SIPRepo sipRepo;
    MutableLiveData<List<SIPData>> sipLiveData;

    public SIPViewModel(@NonNull Application application) {
        super(application);
        sipRepo = new SIPRepo(application);
        getSIPData();
    }

    public MutableLiveData<List<SIPData>> getSipLiveData() {
        if(sipLiveData == null){
            sipLiveData = new MutableLiveData<>();
        }
        return sipLiveData;
    }

    public void getSIPData(){
        sipRepo.getSIPData(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    sipLiveData.postValue((List<SIPData>) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }


}
