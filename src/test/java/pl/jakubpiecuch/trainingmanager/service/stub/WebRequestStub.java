package pl.jakubpiecuch.trainingmanager.service.stub;

import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jakub on 19.09.2015.
 */
public class WebRequestStub implements WebRequest {
    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public String[] getHeaderValues(String s) {
        return new String[0];
    }

    @Override
    public Iterator<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Iterator<String> getParameterNames() {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public boolean checkNotModified(long l) {
        return false;
    }

    @Override
    public boolean checkNotModified(String s) {
        return false;
    }

    @Override
    public boolean checkNotModified(String s, long l) {
        return false;
    }

    @Override
    public String getDescription(boolean b) {
        return null;
    }

    @Override
    public Object getAttribute(String s, int i) {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o, int i) {

    }

    @Override
    public void removeAttribute(String s, int i) {

    }

    @Override
    public String[] getAttributeNames(int i) {
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable, int i) {

    }

    @Override
    public Object resolveReference(String s) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}
