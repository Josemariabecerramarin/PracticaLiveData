package com.example.practicalivedata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.bumptech.glide.Glide;
import com.example.practicalivedata.databinding.FragmentTiempoBinding;

public class TiempoFragment extends Fragment {
    private FragmentTiempoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentTiempoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TiempoViewModel tiempoViewModel = new ViewModelProvider(this).get(TiempoViewModel.class);

        tiempoViewModel.obtenerTiempo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer tiempos) {
                Glide.with(TiempoFragment.this).load(tiempos).into(binding.tiempos);
            }
        });

        tiempoViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeticion) {
                if(repeticion.equals("Hace sol")) {
                    binding.cambio1.setVisibility(View.VISIBLE);
                }else if(repeticion.equals("Está nublado")) {
                    binding.cambio2.setVisibility(View.VISIBLE);
                }else if(repeticion.equals("Sopla viento")) {
                    binding.cambio3.setVisibility(View.VISIBLE);
                }else if(repeticion.equals("Llueve")) {
                    binding.cambio4.setVisibility(View.VISIBLE);
                }else{
                    binding.cambio1.setVisibility(View.GONE);
                    binding.cambio2.setVisibility(View.GONE);
                    binding.cambio3.setVisibility(View.GONE);
                    binding.cambio4.setVisibility(View.GONE);
                }
                binding.repeticion.setText(repeticion);
            }
        });
    }
}
