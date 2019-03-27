//package com.api.user.services;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.api.user.dto.CollabUserDetails;
//import com.api.user.dto.LoginDTO;
//import com.api.user.dto.UserDTO;
//import com.api.user.dto.UserInfo;
//import com.api.user.entity.User;
//import com.api.user.exception.UserException;
//import com.api.user.repository.UserRepository;
//import com.api.user.utils.UserToken;
//import org.modelmapper.ModelMapper;
//
//@Service
//public class UserServicesImpl implements UserServices {
//
//	@Autowired
//	private UserRepository userRepositoty;
//	@Autowired
//	private PasswordEncoder passwordencoder;
//	@Autowired
//	private ModelMapper modelMapper;
//	/**
//	 * 
//	 * @param user
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public User register(UserDTO userDTO) throws UserException {
//		
//		Optional<User> useravailable=userRepositoty.findByEmail(userDTO.getEmail());
//		if(useravailable.isPresent())
//		{
//			throw new UserException(100,"Duplicate user found");
//		}
//		User user=modelMapper.map(userDTO, User.class);
//		user.setPassword(passwordencoder.encode(user.getPassword()));
//		return userRepositoty.save(user);
//	}
//	
//	/**
//	 * 
//	 * @param loginuser
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public String login(LoginDTO loginuser) throws UserException{
//	
//		return userRepositoty.findByEmail(loginuser.getEmail())
//							 .map(fromDBUser-> {
//								try {
//									return this.validUser(fromDBUser, loginuser.getPassword());
//								} catch (UserException e) {
//									new UserException(100,"Please Verify Your mail"); 
//									e.printStackTrace();
//								}
//								return null;
//							})
//							 .orElseThrow(()-> new UserException(100,"Not valid User"));
//	}
//	private String validUser(User fromDBUser, String password) throws UserException{
//		boolean isValid =passwordencoder.matches(password, fromDBUser.getPassword());
//		if(isValid)
//		{ 
//			return UserToken.generateToken(fromDBUser.getId());
//		}
//		throw new UserException(100,"Not valid User");
//	}
//	/**
//	 * 
//	 * @param token
//	 * @throws UnsupportedEncodingException 
//	 * @throws IllegalArgumentException 
//	 * @throws Exception
//	 */
//	@Override
//	public void userVerify(String token) throws Exception{
//		long userId = UserToken.tokenVerify(token);
//		userRepositoty.findById(userId).map(this::verify).orElseThrow(()-> new UserException(100,"Not valid User"));
//	}
//	private User verify(User user) {
//		
//		user.setIsverification(true);
//		return userRepositoty.save(user);
//	}
//	
//	
//	public Long collabUserId(String token,String email) throws UserException
//	{
//		System.out.println(email);
//		UserToken.tokenVerify(token);
//	return userRepositoty.findByEmail(email).map(x -> 
//	{
//		return (Long)x.getId();
//	}).orElse(-1L); //.orElseThrow(UserException::new);
//	}
//	
//	@Override
//	public List<CollabUserDetails> userEmails(List<Long> ids)
//	{	
//		List<CollabUserDetails> list=new ArrayList<>();
//		userRepositoty.findEmailofUsers(ids).get().forEach( x -> list.add(new CollabUserDetails(x)));
//		return list;
//	}
//	
//	public User getUser(long id)
//	{
//		
//	return userRepositoty.findById(id).get();
//	}
//	
//	public  void setProfileImage(String token,String fileName) throws UserException
//	 {
//		 long id=UserToken.tokenVerify(token);
//		 User user=userRepositoty.findById(id).get();
//		 user.setProfileImage(fileName);
//		 userRepositoty.save(user);
//	 }
//	
//	 public String getProfileImage(long id)
//	 {
//		 User user=userRepositoty.findById(id).get();
//		 return user.getProfileImage(); 
//	 }
//
//	@Override
//	public Long getUserId(String email) {
//		
//		return userRepositoty.findByEmail(email).get().getId();
//	}
//
//	@Override
//	public UserInfo getUserInfo(String token)throws UserException {
//		Long id=UserToken.tokenVerify(token);
//		User user= userRepositoty.findById(id).get();
//		UserInfo userinfo=(UserInfo) modelMapper.map(user, UserInfo.class);
//		
//		return userinfo;
//		
//	}
//
//
//	
//}
