package br.com.surb.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.surb.todolist.user.entities.UserModel;
import br.com.surb.todolist.user.repositories.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  private final IUserRepository userRepository;

  public FilterTaskAuth(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    String servletPath = request.getServletPath();

    if(servletPath.startsWith("/tasks")){
      String authorization = request.getHeader("Authorization");
      String authEncoded = authorization.substring("Basic".length()).trim();

      byte[] authDecode = Base64.getDecoder().decode(authEncoded);
      String authString = new String(authDecode);
      String[] credentials = authString.split(":");

      String username = credentials[0];
      String password = credentials[1];

      UserModel user = userRepository.findByUsername(username);

      if(user == null){
          response.sendError(401);
      }else{
        BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if(passwordVerify.verified){
          request.setAttribute("idUser", user.getUserId());
          filterChain.doFilter(request, response);
        }else{
          response.sendError(401);
        }
      }
    }else{
      filterChain.doFilter(request, response);
    }
  }
}
