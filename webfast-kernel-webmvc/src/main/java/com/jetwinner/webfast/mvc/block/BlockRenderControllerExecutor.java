package com.jetwinner.webfast.mvc.block;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

/**
 * @author xulixin
 */
@Component
public class BlockRenderControllerExecutor implements InitializingBean {

    private BlockRequestMappingHandlerMapping mapping;
    private RequestMappingHandlerAdapter adapter;
    private ApplicationContext applicationContext;

    public BlockRenderControllerExecutor(BlockRequestMappingHandlerMapping mapping,
                                         ApplicationContext appContext) {
        this.applicationContext = appContext;
        this.adapter = new RequestMappingHandlerAdapter();
        this.adapter.setApplicationContext(appContext);
        this.mapping = mapping;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.mapping.setAlwaysUseFullPath(true);
        this.adapter.afterPropertiesSet();
    }

    public ModelAndView handleRequest(HttpServletRequest request) throws Exception {
        // You can use other implementations of HttpServletRequest and HttpServletResponse as per your requirement
        // e.g. FormContentRequestWrapper , ResourceUrlEncodingResponseWrapper many more

        HandlerExecutionChain mappedHandler = mapping.getHandler(request);
        if (mappedHandler == null || mappedHandler.getHandler() == null) {
            return null;
        }
        // Determine handler adapter for the current request.
        if (adapter.supports(mappedHandler.getHandler())) {
            return adapter.handle(request, new DummyResponse(), mappedHandler.getHandler());
        }
        return null;
    }

    private static final class DummyResponse implements HttpServletResponse {

        @Override
        public void addCookie(Cookie cookie) {

        }

        @Override
        public boolean containsHeader(String s) {
            return false;
        }

        @Override
        public String encodeURL(String s) {
            return null;
        }

        @Override
        public String encodeRedirectURL(String s) {
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeUrl(String s) {
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeRedirectUrl(String s) {
            return null;
        }

        @Override
        public void sendError(int i, String s) throws IOException {

        }

        @Override
        public void sendError(int i) throws IOException {

        }

        @Override
        public void sendRedirect(String s) throws IOException {

        }

        @Override
        public void setDateHeader(String s, long l) {

        }

        @Override
        public void addDateHeader(String s, long l) {

        }

        @Override
        public void setHeader(String s, String s1) {

        }

        @Override
        public void addHeader(String s, String s1) {

        }

        @Override
        public void setIntHeader(String s, int i) {

        }

        @Override
        public void addIntHeader(String s, int i) {

        }

        @Override
        public void setStatus(int i) {

        }

        @SuppressWarnings("deprecation")
        @Override
        public void setStatus(int i, String s) {

        }

        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public String getHeader(String s) {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String s) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return null;
        }

        @Override
        public void setCharacterEncoding(String s) {

        }

        @Override
        public void setContentLength(int i) {

        }

        @Override
        public void setContentLengthLong(long l) {

        }

        @Override
        public void setContentType(String s) {

        }

        @Override
        public void setBufferSize(int i) {

        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException {

        }

        @Override
        public void resetBuffer() {

        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public void reset() {

        }

        @Override
        public void setLocale(Locale locale) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }
    }
}
