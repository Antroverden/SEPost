package com.skyeng.SEPost.repository;

import com.skyeng.SEPost.entity.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}
