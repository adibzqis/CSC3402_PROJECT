package com.Parfetch.ParFetch.repository;

import com.Parfetch.ParFetch.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    @Query("SELECT s FROM Receiver s WHERE TRIM(s.phone) = TRIM(:phone)")
    Receiver findByPhone(@Param("phone") String phone);
    Receiver findByMatric(String matric);

}
