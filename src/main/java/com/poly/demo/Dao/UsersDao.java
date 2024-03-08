package com.poly.demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.poly.demo.reqRespModels.NewCustomData;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.demo.Entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersDao extends JpaRepository<Users, Long> {

//	Users findByEmail(String email);
	Optional<Users> findByEmail(String email);
//	Users findById(long id);
//	List<Users> findByDateBetween(Date start, Date end);

	@Query("SELECT new com.poly.demo.reqRespModels.NewCustomData(o.date,  COUNT (o.username) as value ) " +
			"FROM Users o " +
			"WHERE o.date BETWEEN :startDay AND :endDay " +
			" GROUP BY o.date")


	List<NewCustomData> findByDateBetween(
			@Param("startDay") Date startDay,
			@Param("endDay") Date endDay);

}
