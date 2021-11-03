package com.example.practicalivedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class TiempoViewModel extends AndroidViewModel {
    Tiempo tiempo;

    LiveData<Integer> tiemposLiveData;
    LiveData<String> repeticionLiveData;

    public TiempoViewModel(@NonNull Application application) {
        super(application);

        tiempo = new Tiempo();

        tiemposLiveData = Transformations.switchMap(tiempo.ordenLiveData, new Function<String, LiveData<Integer>>() {

            String tiemposAnterior;

            @Override
            public LiveData<Integer> apply(String orden) {

                String tiempos = orden.split(":")[0];

                if(!tiempos.equals(tiemposAnterior)){
                    tiemposAnterior = tiempos;
                    int imagen;
                    switch (tiempos) {
                        case "TIEMPO1":
                        default:
                            imagen = R.drawable.sol;
                            break;
                        case "TIEMPO2":
                            imagen = R.drawable.nube;
                            break;
                        case "TIEMPO3":
                            imagen = R.drawable.viento;
                            break;
                        case "TIEMPO4":
                           imagen = R.drawable.lluvia;
                            break;
                    }

                    return new MutableLiveData<>(imagen);

                }
                return null;
            }
        });

        repeticionLiveData = Transformations.switchMap(tiempo.ordenLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }

    LiveData<Integer> obtenerTiempo(){
        return tiemposLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }
}
