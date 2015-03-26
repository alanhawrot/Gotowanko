package pl.edu.uj.gotowanko.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.stream.Collectors;

@WebFilter("/*")
public class LogRequestFilter implements Filter {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(LogRequestFilter.class.getSimpleName());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse && logger.isInfoEnabled()) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            //Copies input and output as they are used while request handling.
            //It is only possible way to log them as they can be read only once from streams.
            //after chain has finished, request is processed, copy of request and response body is
            //available with .getCopy() methods.

            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
            CopyOutputStream copyOutputStream = new CopyOutputStream(response.getOutputStream());

            chain.doFilter(requestWrapper, new HttpServletResponseWrapper(httpResponse) {

                private PrintWriter printWriter = new PrintWriter(copyOutputStream);

                @Override
                public ServletOutputStream getOutputStream() throws IOException {
                    return copyOutputStream;
                }

                @Override
                public PrintWriter getWriter() {
                    return printWriter;
                }

            });

            String requestContentType = httpRequest.getHeader("content-type");
            String responseContentType = httpResponse.getContentType();

            logger.info("Request URL: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
            logger.info("Request content-type: " + requestContentType);
            logger.info("Request headers:" + Collections.list(httpRequest.getHeaderNames())
                    .stream()
                    .map(x -> httpRequest.getHeader(x).toString())
                    .collect(Collectors.joining(", ")));

            String requestBody = new String(requestWrapper.getContentAsByteArray());

            if (requestContentType != null && requestContentType.startsWith("application/json") && !requestBody.isEmpty()) {
                logger.info("Request body:" + LINE_SEPARATOR + objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(objectMapper.readValue(requestBody, Object.class)));
            } else {
                logger.info("Request body:" + LINE_SEPARATOR + requestBody);
            }

            logger.info("Response Status: " + httpResponse.getStatus());
            logger.info("Response Content-Type: " + responseContentType);


            String responseBody = copyOutputStream.getCopy();
            if (responseContentType != null && responseContentType.startsWith("application/json") && !responseBody.isEmpty()) {
                logger.info("Response body:" + LINE_SEPARATOR + objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(objectMapper.readValue(responseBody, Object.class)));
            } else {
                logger.info("Response body:" + LINE_SEPARATOR + responseBody);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public class CopyOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream copy = new ByteArrayOutputStream(1024);
        private ServletOutputStream outputStream;

        public CopyOutputStream(ServletOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public boolean isReady() {
            return outputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            outputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            copy.write(b);
            outputStream.write(b);
        }

        String getCopy() {
            return copy.toString();
        }
    }

    @Override
    public void destroy() {

    }
}