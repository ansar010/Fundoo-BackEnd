//package com.api.user.services;
//
//import java.util.List;
//
//import com.api.user.dto.CollabUserDetails;
//import com.api.user.dto.LoginDTO;
//import com.api.user.dto.UserDTO;
//import com.api.user.dto.UserInfo;
//import com.api.user.entity.User;
//import com.api.user.exception.UserException;
//
//public interface UserServices {
//	/**
//	 * 
//	 * @param user
//	 * @return
//	 * @throws Exception
//	 */
//	 User register(UserDTO userDTO) throws UserException;
//	/**
//	 * 
//	 * @param loginuser
//	 * @return
//	 * @throws Exception
//	 */
//	 String login(LoginDTO loginuser) throws Exception;
//	/**
//	 * 
//	 * @param token
//	 * @throws Exception
//	 */
//	 void userVerify(String token) throws Exception;
//	
//	 Long collabUserId(String token,String email) throws UserException;
//	
//	 List<CollabUserDetails> userEmails(List<Long> ids);
//	 public User getUser(long id);
//	 
//	 void setProfileImage(String token,String fileName) throws UserException;
//	 
//	 String getProfileImage(long id);
//	 
//	 Long getUserId(String email);
//	 
//	 UserInfo getUserInfo(String token)throws UserException;
//	 
//}
