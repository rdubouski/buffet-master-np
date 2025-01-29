package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Box;
import by.tms.buffetmasternp.enums.Status;
import by.tms.buffetmasternp.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findAllByStatusAndType(Status status, Type type);
}
