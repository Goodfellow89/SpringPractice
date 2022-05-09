package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T,G> {
    List<T> retrieveAll();

    void store(T book);

    boolean removeItem(String paramName, G param);
}
