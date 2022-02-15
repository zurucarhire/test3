package com.cellulant.iprs.security;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link UsernamePasswordAuthenticationFilter}.
 *
 * @author Ben Alex
 */
@SpringBootTest
public class UsernamePasswordAuthenticationFilterTests {

    @Test
    public void testNormalOperation() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "rod");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "koala");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        // filter.init(null);
        Authentication result = filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertThat(result != null).isTrue();
        assertThat(((WebAuthenticationDetails) result.getDetails()).getRemoteAddress()).isEqualTo("127.0.0.1");
    }

    @Test
    public void testConstructorInjectionOfAuthenticationManager() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "rod");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "dokdo");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter(
                createAuthenticationManager());
        Authentication result = filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertThat(result).isNotNull();
    }

    @Test
    public void testNullPasswordHandledGracefully() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "rod");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        assertThat(filter.attemptAuthentication(request, new MockHttpServletResponse())).isNotNull();
    }

    @Test
    public void testNullUsernameHandledGracefully() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "koala");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        assertThat(filter.attemptAuthentication(request, new MockHttpServletResponse())).isNotNull();
    }

    @Test
    public void testUsingDifferentParameterNamesWorksAsExpected() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        filter.setUsernameParameter("x");
        filter.setPasswordParameter("y");
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter("x", "rod");
        request.addParameter("y", "koala");
        Authentication result = filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertThat(result).isNotNull();
        assertThat(((WebAuthenticationDetails) result.getDetails()).getRemoteAddress()).isEqualTo("127.0.0.1");
    }

    @Test
    public void testSpacesAreTrimmedCorrectlyFromUsername() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, " rod ");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, "koala");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        Authentication result = filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertThat(result.getName()).isEqualTo("rod");
    }

    @Test
    public void testFailedAuthenticationThrowsException() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, "rod");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        AuthenticationManager am = mock(AuthenticationManager.class);
        given(am.authenticate(any(Authentication.class))).willThrow(new BadCredentialsException(""));
        filter.setAuthenticationManager(am);
        assertThatExceptionOfType(AuthenticationException.class)
                .isThrownBy(() -> filter.attemptAuthentication(request, new MockHttpServletResponse()));
    }

    /**
     * SEC-571
     */
    @Test
    public void noSessionIsCreatedIfAllowSessionCreationIsFalse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAllowSessionCreation(false);
        filter.setAuthenticationManager(createAuthenticationManager());
        filter.attemptAuthentication(request, new MockHttpServletResponse());
        assertThat(request.getSession(false)).isNull();
    }

    private AuthenticationManager createAuthenticationManager() {
        AuthenticationManager am = mock(AuthenticationManager.class);
        given(am.authenticate(any(Authentication.class)))
                .willAnswer((Answer<Authentication>) (invocation) -> (Authentication) invocation.getArguments()[0]);
        return am;
    }

}
