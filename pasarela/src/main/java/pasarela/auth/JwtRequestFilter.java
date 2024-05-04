package pasarela.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/*
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /*
     * @Autowired
     * private UserDetailsService userDetailsService;
     * 
     * @Autowired
     * private JwtUtil jwtUtil;
     */

    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	    throws ServletException, IOException {

	final String authorizationHeader = request.getHeader("Authorization");

	// Comprobar que la petición lleve le token JWT y lo valida...

	// Establece el contexto de seguridad.
	ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	authorities.add(new SimpleGrantedAuthority(claims.get("rol")));
	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
		authorities); 
	// Establecemos la autenticación en el contexto de seguridad.
	// Se interpreta como que el usuario ha superado la autenticación.
	SecurityContextHolder.getContext().setAuthentication(auth);
	/*
	 * String username = null;
	 * String jwt = null;
	 * 
	 * if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
	 * {
	 * jwt = authorizationHeader.substring(7);
	 * username = jwtUtil.extractUsername(jwt);
	 * }
	 * 
	 * if (username != null &&
	 * SecurityContextHolder.getContext().getAuthentication() == null) {
	 * UserDetails userDetails =
	 * this.userDetailsService.loadUserByUsername(username);
	 * if (jwtUtil.validateToken(jwt, userDetails)) {
	 * UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
	 * UsernamePasswordAuthenticationToken(
	 * userDetails, null, userDetails.getAuthorities());
	 * usernamePasswordAuthenticationToken
	 * .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	 * SecurityContextHolder.getContext().setAuthentication(
	 * usernamePasswordAuthenticationToken);
	 * }
	 * }

	chain.doFilter(request, response);
    }
}

*/
