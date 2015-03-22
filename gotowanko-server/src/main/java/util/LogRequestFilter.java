package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
            final CopyOutputStream copyOutputStream = new CopyOutputStream(response.getOutputStream());
            final CopyInputStream copyInputStream = new CopyInputStream(httpRequest.getInputStream());

            chain.doFilter(
                    new HttpServletRequestWrapper(httpRequest) {

                        @Override
                        public ServletInputStream getInputStream() throws IOException {
                            return copyInputStream;
                        }

                        @Override
                        public BufferedReader getReader() throws IOException {
                            return new BufferedReader(new InputStreamReader(copyInputStream));
                        }

                    }, new HttpServletResponseWrapper(httpResponse) {
                        @Override
                        public ServletOutputStream getOutputStream() throws IOException {
                            return copyOutputStream;
                        }
                    });

            logger.info("Request URL: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
            logger.info("Request content-type: " + httpRequest.getHeader("content-type"));
            logger.info("Request headers:" + Collections.list(httpRequest.getHeaderNames())
                    .stream()
                    .map(x -> httpRequest.getHeader(x).toString())
                    .collect(Collectors.joining(", ")));

            if (httpRequest.getHeader("content-type").startsWith("application/json")) {
                logger.info("Request body:" + LINE_SEPARATOR + objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(objectMapper.readValue(copyInputStream.getCopy(), Object.class)));
            } else {
                logger.info("Request body:" + LINE_SEPARATOR + copyInputStream.getCopy());
            }

            logger.info("Response Status: " + httpResponse.getStatus());
            logger.info("Response Content-Type: " + httpResponse.getContentType());
            if (httpResponse.getContentType().startsWith("application/json")) {
                logger.info("Response body:" + LINE_SEPARATOR + objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(objectMapper.readValue(copyOutputStream.getCopy(), Object.class)));
            } else {
                logger.info("Response body:" + LINE_SEPARATOR + copyOutputStream.getCopy());
            }
        }
    }


    @Override
    public void destroy() {

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

    class CopyInputStream extends ServletInputStream {
        private ByteArrayOutputStream copy = new ByteArrayOutputStream(1024);
        private ServletInputStream inputStream;

        public CopyInputStream(ServletInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            int value = inputStream.read();
            copy.write(value);
            return value;
        }

        @Override
        public boolean isFinished() {
            return inputStream.isFinished();
        }

        @Override
        public boolean isReady() {
            return inputStream.isReady();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            inputStream.setReadListener(readListener);
        }

        String getCopy() {
            return copy.toString();
        }

    }
}