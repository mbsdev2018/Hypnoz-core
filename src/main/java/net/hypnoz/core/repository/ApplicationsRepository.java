package net.hypnoz.core.repository;

import net.hypnoz.core.models.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Long>, JpaSpecificationExecutor<Applications> {
    List<Applications> findByModules_Id(Long id);
    Optional<Applications> findByCodeAndModule(String code, String module);
}