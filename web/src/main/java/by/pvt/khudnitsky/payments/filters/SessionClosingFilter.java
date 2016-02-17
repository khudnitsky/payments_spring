/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.filters;

import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */

public class SessionClosingFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        Session session = HibernateUtil.getInstance().getSession();
        //test(HibernateUtil.getInstance());
        chain.doFilter(request, response);
        HibernateUtil.getInstance().releaseSession(session);
    }

    public void destroy() {}

    // TODO DELETE 2nd level cache testing
    public static void test(HibernateUtil util) {
        System.out.println("Temp Dir:"+System.getProperty("java.io.tmpdir"));

        //Initialize Sessions
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Statistics stats = sessionFactory.getStatistics();
        System.out.println("Stats enabled="+stats.isStatisticsEnabled());
        stats.setStatisticsEnabled(true);
        System.out.println("Stats enabled="+stats.isStatisticsEnabled());

        Session session = util.getSession();
        Session otherSession = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Transaction otherTransaction = otherSession.beginTransaction();

        printStats(stats, 0);

        User emp = (User) session.load(User.class, 1L);
        printData(emp, stats, 1);

        emp = (User) session.load(User.class, 1L);
        printData(emp, stats, 2);

        //clear first level cache, so that second level cache is used
        session.evict(emp);
        emp = (User) session.load(User.class, 1L);
        printData(emp, stats, 3);

        emp = (User) session.load(User.class, 3L);
        printData(emp, stats, 4);

        emp = (User) otherSession.load(User.class, 1L);
        printData(emp, stats, 5);

        //Release resources
        transaction.commit();
        otherTransaction.commit();
        //sessionFactory.close();
    }

    private static void printStats(Statistics stats, int i) {
        System.out.println("***** " + i + " *****");
        System.out.println("Fetch Count="
                + stats.getEntityFetchCount());
        System.out.println("Second Level Hit Count="
                + stats.getSecondLevelCacheHitCount());
        System.out
                .println("Second Level Miss Count="
                        + stats
                        .getSecondLevelCacheMissCount());
        System.out.println("Second Level Put Count="
                + stats.getSecondLevelCachePutCount());
    }

    private static void printData(User emp, Statistics stats, int count) {
        System.out.println(count+":: Name="+emp.getFirstName()+", Surname="+emp.getLastName());
        printStats(stats, count);
    }
}