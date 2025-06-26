package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;

import java.util.List;
import java.util.Optional;

public interface ParcelService {
    List<Parcel> getAllParcels();

    List<Parcel> getParcelsByReceiver(Receiver receiver);
    void save(Parcel parcel);
    Optional<Parcel> findById(Integer id);

    List<Parcel> findByReceiverName(String name);
    List<Parcel> findByReceiverPhone(String phone);

    List<Parcel> findByReceiverMatric(String matric);

    void deleteParcel(Parcel parcel);

    List<Parcel> searchParcels(String keyword);

    List<Parcel> getParcelsByReceiverPhone(String phone);
}

