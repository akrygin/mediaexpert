package com.mediaexpert.entity.repository;

import com.mediaexpert.entity.bean.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByNameStartsWithIgnoreCase(String lastName);
}