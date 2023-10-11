package br.com.surb.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.surb.todolist.user.entities.UserModel;
import br.com.surb.todolist.user.repositories.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  private final IUserRepository userRepository;

  public FilterTaskAuth(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    filterChain.doFilter(request, response);

    String authorization = request.getHeader("Authorization");

    String authEncoded = authorization.substring("Basic".length()).trim();


    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
    String authString = new String(authDecode);
    String[] credentials = authString.split(":");
    System.out.println(credentials);
    String username = credentials[0];
    String password = credentials[1];

    UserModel user = userRepository.findByUsername(username);
    if(user == null){
      response.setStatus(401);
    }else{
      BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
      if(passwordVerify.verified){
        filterChain.doFilter(request, response);
      }else{
        response.setStatus(401);
      }

    }

  }
}
