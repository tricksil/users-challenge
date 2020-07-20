package com.challenge.users.repositories;

import com.challenge.users.models.Event;
import com.challenge.users.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findById(String id);

    Optional<List<Event>> findAllByUserOrderByDateDesc(User user);

    Event deleteByIdAndUser(String id, User user);

    List<Event> deleteAllByUser(User user);

    boolean existsByDate(LocalDateTime date);
}
