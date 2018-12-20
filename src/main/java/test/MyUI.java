package test;

import javax.inject.Inject;

import EJB.UserDAO;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import data.Article;
import data.User;
import ui.SignInForm;
import ui.UserForm;
import ui.ListArticles;
import ui.CreateNewArticleForm;

import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@CDIUI("")
@Theme("mytheme")
public class MyUI extends UI {

    @Inject
    UserDAO userDAO;

    //forms
    SignInForm signInForm;
    UserForm userForm;
    ListArticles listArticles;
    CreateNewArticleForm createNewArticleForm;



    final VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        signInForm = new SignInForm(this);
        userForm = new UserForm(this);
        //listArticles = new ListArticles(this);
        createNewArticleForm = new CreateNewArticleForm(this);

        Button signUpButton = new Button("Sign up");
        signUpButton.addClickListener( e -> {
            //this.addUserForm();
            goToScreen("UserForm");
        });
        layout.addComponent(signUpButton);

        Button signInButton = new Button("Sign in");
        signInButton.addClickListener( e -> {
            //this.addUserSignInForm();
            goToScreen("SignInForm");
        });
        layout.addComponent(signInButton);

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }

    private void addUserForm(){
        layout.removeAllComponents();
        layout.addComponent(new UserForm(this));
    }

    private void addUserSignInForm(){
        layout.removeAllComponents();
        layout.addComponent(new SignInForm(this));
    }

    public int addUser(User user){
        if (userDAO.findWithUsername(user.getUsername()) == null){
            userDAO.persist(user);
            return 1;
        }
        return -1;
    }
    public int logInUser(String username,String password){
        if (userDAO.findWithUsernameAndPassword(username,password) == null)
            return 0;
        return 1;
    }
    public int getUserId(String username){
        return userDAO.findWithUsername(username).getId();
    }

    public void remove (int id){userDAO.remove(id);}

    public int isOwner (String username,int articleId){
       return userDAO.find(getUserId(username),articleId);
    }

    public void addArticle(Article article){
        userDAO.persist(article);
    }

    public void removeArticle(Article article){
        userDAO.remove(article);
    }

    public List<Article> listAllArticles(){
        return userDAO.listAll();
    }

    public void goToScreen(String screenname){
        if (screenname.equals("Basic")){
            this.setContent(this.layout);
        }
        else if (screenname.equals("UserForm")){
            userForm = new UserForm(this);
            this.setContent(userForm);
        }
        else if (screenname.equals("SignInForm")){
            signInForm = new SignInForm(this);
            this.setContent(signInForm);
        }
        else if (screenname.equals("createNewArticle")){
            createNewArticleForm = new CreateNewArticleForm(this);
            this.setContent(createNewArticleForm);
        }
        else if (screenname.equals("ListArticles")){
            //listArticles.refresh();
            listArticles = new ListArticles(this);
            this.setContent(listArticles);
        }
    }
}
