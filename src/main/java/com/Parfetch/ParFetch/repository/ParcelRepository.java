package com.Parfetch.ParFetch.repository;

import com.Parfetch.ParFetch.model.Parcel;
import com.Parfetch.ParFetch.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Integer> {

    List<Parcel> findByReceiver(Receiver receiver);
    List<Parcel> getParcelsByReceiver(Receiver receiver);
    List<Parcel> findByReceiverPhoneContaining(String phone);
    List<Parcel> findByReceiverMatric(String matric);
    List<Parcel> findByReceiverNameContainingIgnoreCase(String name);


    //@Query("SELECT p FROM Parcel p WHERE p.receiverPhone LIKE CONCAT('%', :phone, '%')")
    //List<Parcel> findByReceiverPhoneContaining(@Param("phone") String phone);

    @Query("SELECT p FROM Parcel p WHERE p.receiverPhone = :phone")
    List<Parcel> findByReceiverPhone(@Param("phone") String phone);

    @Query("SELECT p FROM Parcel p WHERE LOWER(p.receiverName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Parcel> findByReceiverNameContaining(@Param("name") String name);

    @Query("SELECT p FROM Parcel p WHERE " +
            "LOWER(p.receiverName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.receiveDate) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(p.trackingNumber AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Parcel> searchByKeyword(@Param("keyword") String keyword);

}
