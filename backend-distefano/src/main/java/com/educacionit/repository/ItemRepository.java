package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.entity.Item;

@Repository("itemRepository")
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
