package com.sparta.navershop.repository;

import com.sparta.navershop.models.Folder;
import com.sparta.navershop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    List<Folder> findAllByUserAndNameIn(User user, List<String> nameList);
    Folder findByName(String folderName);
}