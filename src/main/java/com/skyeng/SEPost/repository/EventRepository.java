package com.skyeng.SEPost.repository;

import com.skyeng.SEPost.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByPostItem_Id(Long id);
    Event findFirstByPostItem_IdOrderByHappenAtDesc(Long id);
}
