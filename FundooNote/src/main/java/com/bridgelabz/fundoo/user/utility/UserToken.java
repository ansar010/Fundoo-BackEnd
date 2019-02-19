package com.bridgelabz.fundoo.user.utility;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class UserToken {

	//Secret key to generated token
	private static String TOKEN_SECRET="Ansar";
	
	/**
	 * 
	 * @param id of user which mean claim/body 
	 * @return token generated based on algorithm,claim and signature
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String generateToken(long id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		//try {
		Algorithm algorithm= Algorithm.HMAC256(TOKEN_SECRET);
		String token=JWT.create()
				.withClaim("ID", id)
				.sign(algorithm);
		return token;		
		//		}
		//		catch(Exception exception)
		//		{
		//			throw new UserException(100,"Token Not Generated");
		//		}
	}

	/**
	 * 
	 * @param token it takes token
	 * @return the decoded token value
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static long tokenVerify(String token) throws IllegalArgumentException, UnsupportedEncodingException //throws Exception
	{
		long userid;
		//try {
		
		//here verify the given token's algorithm
		Verification verification=JWT.require(Algorithm.HMAC256(UserToken.TOKEN_SECRET));
		JWTVerifier jwtverifier=verification.build();
		DecodedJWT decodedjwt=jwtverifier.verify(token);
		Claim claim=decodedjwt.getClaim("ID");
		userid=claim.asLong();	
		System.out.println(userid);
		
		//}
		//catch(Exception exception)
		//{
		//throw new UserException(100,"Token Not Verified");
		//}

		return userid;
	}
}
