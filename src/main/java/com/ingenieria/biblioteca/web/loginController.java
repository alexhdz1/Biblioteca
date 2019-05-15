package com.ingenieria.biblioteca.web;


import com.ingenieria.biblioteca.controlador.AdministradorJpaController;
import com.ingenieria.biblioteca.controlador.ProfesorJpaController;
import com.ingenieria.biblioteca.modelo.Administrador;
import com.ingenieria.biblioteca.modelo.PersistenceUtil;
import com.ingenieria.biblioteca.modelo.Profesor;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author muyalware
 */
@ManagedBean
@RequestScoped
@Named("loginController")
public class loginController {
    private int id;
    private final ProfesorJpaController jpaProfesor;
    private final AdministradorJpaController jpaAdministrador;
    private final FacesContext context = FacesContext.getCurrentInstance();
    private String correo;
    private String contra;
    private String estado;
    public FacesContext fc ; 
    public HttpSession sesion ; 

    
    

    
    /**
     * Creates a new instance of AlumnoController
     */
    public loginController() {
        jpaProfesor = new ProfesorJpaController(PersistenceUtil.getEntityManagerFactory());
        jpaAdministrador = new AdministradorJpaController(PersistenceUtil.getEntityManagerFactory());
        this.estado = "no presionado";
        fc = FacesContext.getCurrentInstance();
        sesion = (HttpSession) fc.getExternalContext().getSession(true);

 
            
    }

    public int getIDSesion(){
        return  (int) this.sesion.getAttribute("att");
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String co) {
        this.correo = co;
    }
    
    public int getId(){
        return id;
    }
    
    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /*
    Revisa si el usuario y la contraseña coinciden con un administrador, si lo 
    hacen realiza login, de otro modo llama al metodo loginProfesor
     */
    public void login() {

        Administrador miAdmin = busca();
        if (miAdmin == null) {
          
            loginProfesor();

        } else if (!contra.equals(miAdmin.getContrasena())) {

            muestraMensaje("Contraseña incorrecta");
            //System.out.println("contraseña incorrecta");
        } else if (contra.equals(miAdmin.getContrasena())) {
            
            context.getExternalContext().getSessionMap().put("nombre", miAdmin.getCorreo());
                
            id = miAdmin.getIdadministrador();
        
            System.out.println("Antes");
            
            sesion.setAttribute("att", miAdmin.getIdadministrador());
            System.out.println(sesion.getAttribute("att"));
            System.out.println("Despues");
            System.out.println("+++++++++++++++++++++++++++++"+context);
            
                     
               redirecciona("/faces/administrarProfesores.xhtml");
            
           
        }
    }

    /*
    Revisa si el usuario y la contraseña coinciden con un profesor, si lo 
    hacen realiza login, de otro modo llama al metodo loginUsuario
     */
    public void loginProfesor() {

        Profesor miProfesor = buscaProfesor();
  
        if (miProfesor == null) {
            muestraMensaje("El Usuario no existe.");

        } 
        
        else if (!contra.equals(miProfesor.getContrasena())) {

            muestraMensaje("Contraseña incorrecta");
            //System.out.println("contraseña incorrecta");
        }
        
        else if(miProfesor.getActivo()== false){
            
            muestraMensaje("Falta validacion, porfavor contacte al administrador.");
            
        }
        
        else if (contra.equals(miProfesor.getContrasena())) {

            System.out.println("inicio sesion");
            
            context.getExternalContext().getSessionMap().put("profesor", miProfesor.getCorreo());
            id = miProfesor.getIdprofesor();
            sesion.setAttribute("att", miProfesor.getIdprofesor());
            System.out.println("+++++++++++++++++++++++++++++"+context);   
            System.out.println("Antes");   
            System.out.println(sesion.getAttribute("att"));
            System.out.println("Despues");
            
            redirecciona("/faces/principalProfesor.xhtml");
            
            

        }
    }

    /**
     * Busca si el correo esta en la base de los profesores
     *
     * @return
     */
    public Profesor buscaProfesor() {
        Profesor miProfesor = null;
        List<Profesor> listaProfesores = getListaProfesores();

        for (int i = 0; i < listaProfesores.size(); i++) {
            if (listaProfesores.get(i).getCorreo().equals(correo)) {
                miProfesor = listaProfesores.get(i);
            }
        }
        return miProfesor;
    }

    public List<Profesor> getListaProfesores() {
        return jpaProfesor.findProfesorEntities();
    }

    public List<Administrador> getRegistrados() {
        return jpaAdministrador.findAdministradorEntities();
    }

    public Administrador busca() {
        Administrador miAdmin = null;
        List<Administrador> listaAdmins = getRegistrados();

        for (int i = 0; i < listaAdmins.size(); i++) {
            if (listaAdmins.get(i).getCorreo().equals(correo)) {
                miAdmin = listaAdmins.get(i);
            }
        }
        return miAdmin;
    }

    /*
      Redirecciona al usuario a una url: direccion
     */
    private void redirecciona(String direccion) {
  
        
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + direccion);
          
          
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void muestraMensaje(String mensaje) {
        FacesMessage mensajeFace = new FacesMessage(mensaje);
        RequestContext.getCurrentInstance().showMessageInDialog(mensajeFace);
    }

    /*
     * Hace logout borrando la sesion 
     */
    public void logout() {
          
        context.getExternalContext().invalidateSession();

        
        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
public void imprimeId(){
   
    System.err.println(id);
}

}
