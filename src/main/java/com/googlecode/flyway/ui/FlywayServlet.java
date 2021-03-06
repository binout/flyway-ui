/**
 * Copyright (C) 2013 Benoit Prioux <benoit.prioux@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.flyway.ui;

import com.googlecode.flyway.core.Flyway;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ServiceLoader;

@WebServlet(name="FlywayServlet", urlPatterns="/flyway")
public class FlywayServlet extends HttpServlet {

    private Flyway flyway;

    @Override
    public void init() throws ServletException {
        ServiceLoader<FlywayProvider> providers = ServiceLoader.load(FlywayProvider.class);
        if (providers.iterator().hasNext()) {
            flyway = providers.iterator().next().provides();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("flyway", flyway);
        req.getRequestDispatcher("flyway.jsp").forward(req, resp);
    }
}