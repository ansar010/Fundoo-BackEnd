package com.bridgelabz.fundoo.user.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.user.model.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> 
{
	public Optional<User> findByEmail(String email);
	public Optional<User> findById(long id);
	//public Optional<User> findBypassword(String password);
//	@Query(value="select password from user",nativeQuery=true)
//	public List<Object[]> findPassword();
}
