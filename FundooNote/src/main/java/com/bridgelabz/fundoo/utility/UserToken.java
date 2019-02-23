package com.bridgelabz.fundoo.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.fundoo.exception.TokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:message.properties")
public class UserToken {
	
	@Autowired
	private static  Environment environment;

	//Secret key to generated token
	private static String TOKEN_SECRET=environment.getProperty("0");

	/**
	 * 
	 * @param id of user which mean claim/body 
	 * @return token generated based on algorithm,claim and signature
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String generateToken(long id) 
	{
		Algorithm algorithm;

		try {
			algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token=JWT.create()
					.withClaim("ID", id)
					.sign(algorithm);
			return token;		
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			log.error("Token Error"+e.getMessage());
			throw new TokenException(200,environment.getProperty("6"));
		}
	}
	
	
	/**
	 * 
	 * @param token it takes token
	 * @return the decoded token value
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static long tokenVerify(String token)
	{
		long userid;
		//here verify the given token's algorithm
		Verification verification;
		try {
			verification = JWT.require(Algorithm.HMAC256(UserToken.TOKEN_SECRET));
			JWTVerifier jwtverifier=verification.build();
			DecodedJWT decodedjwt=jwtverifier.verify(token);
			Claim claim=decodedjwt.getClaim("ID");
			userid=claim.asLong();	
			System.out.println(userid);

			return userid;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new TokenException(200, environment.getProperty("6"));
		}
	}
}
