package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;
import com.Parfetch.ParFetch.repository.ReceiverRepository;
import com.Parfetch.ParFetch.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final ReceiverRepository receiverRepository;

    public ParcelServiceImpl(ParcelRepository parcelRepository, ReceiverRepository receiverRepository) {
        this.parcelRepository = parcelRepository;
        this.receiverRepository = receiverRepository;
    }

    @Override
    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    @Override
    public List<Parcel> getParcelsByReceiver(Receiver receiver) {
        return parcelRepository.findByReceiver(receiver);
    }

    @Override
    public void save(Parcel parcel) {
        parcelRepository.save(parcel);
    }

    @Override
    public List<Parcel> findByReceiverName(String name) {
       return parcelRepository.findByReceiverNameContaining(name);
    }

    @Override
    public List<Parcel> findByReceiverPhone(String phone) {
        return parcelRepository.findByReceiverPhone(phone);
   }

    @Override
    public Optional<Parcel> findById(Integer id) {
        return parcelRepository.findById(id);
    }

    @Override
    public List<Parcel> findByReceiverMatric(String matric) {
        Receiver receiver = receiverRepository.findByMatric(matric);
        System.out.println("Receiver for matric " + matric + ": " + (receiver != null ? receiver.getName() : "NOT FOUND"));
        return (receiver != null) ? parcelRepository.findByReceiver(receiver) : List.of();
    }

    @Override
    public void deleteParcel(Parcel parcel) {
        parcelRepository.delete(parcel);
    }
    @Override
    public List<Parcel> searchParcels(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllParcels();
        }

        String lowerKeyword = keyword.toLowerCase();

        return parcelRepository.findAll().stream()
                .filter(parcel ->
                        (parcel.getTrackingNumber() != null && parcel.getTrackingNumber().toString().contains(lowerKeyword)) ||
                                (parcel.getReceiveDate() != null && parcel.getReceiveDate().toLowerCase().contains(lowerKeyword)) ||
                                (parcel.getReceiverName() != null && parcel.getReceiverName().toLowerCase().contains(lowerKeyword)) ||
                                (parcel.getReceiverPhone() != null && parcel.getReceiverPhone().toLowerCase().contains(lowerKeyword)) ||
                                (parcel.getSenderPhone() != null && parcel.getSenderPhone().toLowerCase().contains(lowerKeyword))
                )
                .collect(Collectors.toList());
    }


    @Override
    public List<Parcel> getParcelsByReceiverPhone(String phone) {
        return parcelRepository.findAll().stream()
                .filter(p -> p.getReceiverPhone() != null && p.getReceiverPhone().equals(phone))
                .collect(Collectors.toList());
    }


}
