package com.example.practicalivedata;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

public class Tiempo {
    interface TiempoListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> entrenando;

    void iniciarTiempo(TiempoListener tiempoListener) {
        if (entrenando == null || entrenando.isCancelled()) {
            entrenando = scheduler.scheduleAtFixedRate(new Runnable() {
                int tiempos=0;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones < 0) {
                        repeticiones = random.nextInt(3) + 3;
                        if (tiempos==4)tiempos = 1;
                        else tiempos ++;
                    }
                    if (tiempos == 1) {
                        tiempoListener.cuandoDeLaOrden("TIEMPO" + tiempos + ":" + (repeticiones == 0 ? "Está nublado" : repeticiones));
                    }else if (tiempos == 2) {
                        tiempoListener.cuandoDeLaOrden("TIEMPO" + tiempos + ":" + (repeticiones == 0 ? "Sopla viento" : repeticiones));
                    }else if (tiempos == 3) {
                        tiempoListener.cuandoDeLaOrden("TIEMPO" + tiempos + ":" + (repeticiones == 0 ? "Llueve" : repeticiones));
                    }else {
                        tiempoListener.cuandoDeLaOrden("TIEMPO" + tiempos + ":" + (repeticiones == 0 ? "Hace sol" : repeticiones));

                    }
                    repeticiones--;

                }
            }, 0, 1, SECONDS);
        }
    }

    void pararTiempo() {
        if (entrenando != null) {
            entrenando.cancel(true);
        }
    }
    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarTiempo(new TiempoListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararTiempo();
        }
    };
}