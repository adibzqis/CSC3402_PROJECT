package com.Parfetch.ParFetch.service;

import com.Parfetch.ParFetch.model.Receiver;
import java.util.Optional;

public interface ReceiverService {
    Receiver registerReceiver(Receiver receiver);
    Receiver findByPhone(String phone);
    Optional<Receiver> findById(Long id);
    Receiver updateReceiver(Receiver receiver);
    void deleteReceiver(Receiver receiver);
    void save(Receiver receiver);
}
