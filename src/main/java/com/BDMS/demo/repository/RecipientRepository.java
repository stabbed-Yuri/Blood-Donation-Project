package com.BDMS.demo.repository;

import com.BDMS.demo.persistent.RecipientEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<RecipientEntity, Integer> {

    List<RecipientEntity> findByUserId(Long userId);

    @Query("SELECT r FROM RecipientEntity r WHERE r.blood_g_needed = :bloodGNeeded AND r.closed = false")
    List<RecipientEntity> findByBloodGNeededAndClosedFalse(@Param("bloodGNeeded") String bloodGNeeded);
    @Query("SELECT r FROM RecipientEntity r JOIN r.acceptedUsers u WHERE u.id = :userId")
    List<RecipientEntity> findAcceptedRequestsByUser(@Param("userId") Long userId);

}