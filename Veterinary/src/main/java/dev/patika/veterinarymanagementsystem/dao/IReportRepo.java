package dev.patika.veterinarymanagementsystem.dao;



import dev.patika.veterinarymanagementsystem.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReportRepo extends JpaRepository<Report,Long> {
}
