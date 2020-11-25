module net.pkhapps.hexagonal.domain.base {
    requires static org.jetbrains.annotations;

    requires java.persistence;
    requires spring.context;
    requires spring.data.commons;
    requires spring.data.jpa;

    exports net.pkhapps.hexagonal.domain.base;
    exports net.pkhapps.hexagonal.domain.base.annotation;
    exports net.pkhapps.hexagonal.domain.base.support;
}