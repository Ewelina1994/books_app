package pl.klobut.books_app.GUI.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomRequestCache extends HttpSessionRequestCache {
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        super.saveRequest(request, response);
    }
}
