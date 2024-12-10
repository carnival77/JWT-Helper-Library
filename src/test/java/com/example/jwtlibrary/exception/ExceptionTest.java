package com.example.jwtlibrary.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionTest {

    @Test
    void exceptionMessageTest(){
        InvalidTokenException invalidTokenException = new InvalidTokenException("Invalid Token");
        assertEquals(invalidTokenException.getMessage(),"Invalid Token");

        TokenExpiredException tokenExpiredException = new TokenExpiredException("Token Expired");
        assertEquals(tokenExpiredException.getMessage(),"Token Expired");

        MissingClaimsException missingClaimsException = new MissingClaimsException("Missing Claims");
        assertEquals(missingClaimsException.getMessage(),"Missing Claims");
    }
}
