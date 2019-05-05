package com.ingenieria.biblioteca.modelo;

import com.ingenieria.biblioteca.modelo.Espaciocultural;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-02T15:40:04")
@StaticMetamodel(Profesor.class)
public class Profesor_ { 

    public static volatile SingularAttribute<Profesor, String> correo;
    public static volatile CollectionAttribute<Profesor, Espaciocultural> espacioculturalCollection;
    public static volatile SingularAttribute<Profesor, String> departamento;
    public static volatile SingularAttribute<Profesor, String> contrasena;
    public static volatile SingularAttribute<Profesor, String> numTrabajador;
    public static volatile SingularAttribute<Profesor, String> tipoprof;
    public static volatile SingularAttribute<Profesor, String> nombre;
    public static volatile SingularAttribute<Profesor, Integer> idprofesor;
    public static volatile SingularAttribute<Profesor, Boolean> activo;

}