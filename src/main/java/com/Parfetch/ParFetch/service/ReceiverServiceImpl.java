package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;
import com.Parfetch.ParFetch.repository.ParcelRepository;
import com.Parfetch.ParFetch.repository.ReceiverRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepository receiverRepository;

    public ReceiverServiceImpl(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    @Override
    public Receiver registerReceiver(Receiver receiver) {
        return receiverRepository.save(receiver);
    }

    @Override
    public Receiver findByPhone(String phone) {
        return receiverRepository.findByPhone(phone);
    }

    @Override
    public Optional<Receiver> findById(Long id) {
        return receiverRepository.findById(id);
    }

    @Override
    public Receiver updateReceiver(Receiver receiver){
        return receiverRepository.save(receiver);
    }

    @Override
    public void deleteReceiver(Receiver receiver){
        receiverRepository.delete(receiver);
    }

    public void save(Receiver receiver) {
        receiverRepository.save(receiver);
    }

}
