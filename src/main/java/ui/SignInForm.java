package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickListener;
import test.MyUI;
import data.User;

public class SignInForm extends Form {
    FormLayout formLayout ;
    TextField username;
    PasswordField password;
    Button registerButton;

    public SignInForm(MyUI myUI ) {
        formLayout = new FormLayout();

        username = new TextField();
        username.setCaption("enter username");
        password = new PasswordField("enter password");

        registerButton = new Button("Log in");
        registerButton.addClickListener(e -> {
            //book.setId(1);
           if (myUI.logInUser(username.getValue(),password.getValue()) == 1) {
               System.out.println("logged in");
               myUI.getSession().setAttribute("currentUser", username.getValue());
               myUI.goToScreen("ListArticles");
               //loggedIn("succesfully logged in");
           }

           else {
               System.out.println("cant log in");
               notLoggedIn("username or password didnt match re-enter");
           }
        });

        formLayout.addComponents(username, password, registerButton);
        setLayout(formLayout);
    }
    private void loggedIn(String s){
        Label label = new Label(s);
        formLayout.removeAllComponents();
        formLayout.addComponent(label);
        setLayout(formLayout);
    }
    private void notLoggedIn(String s){
        Label label = new Label(s);
        username.setValue("");
        password.setValue("");
        formLayout.removeAllComponents();
        formLayout.addComponents(label,username,password,registerButton);
        setLayout(formLayout);
    }
}
