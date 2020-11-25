module net.pkhapps.hexagonal.domain.hibernate {
    requires static org.jetbrains.annotations;

    requires java.sql;
    requires net.pkhapps.hexagonal.domain.base;
    requires org.hibernate.orm.core;

    exports net.pkhapps.hexagonal.domain.hibernate;
}