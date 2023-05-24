package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.List;


public interface AdsRepository extends JpaRepository<Ads,Integer> {
    Collection<Ads> findAllByAuthorId(Integer id);
    List<Ads> findByAuthor(User author);
    List<Ads> findByTitleContainingIgnoreCase(String title);


}
