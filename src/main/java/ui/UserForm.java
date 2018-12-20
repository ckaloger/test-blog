package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickListener;
import test.MyUI;
import data.User;

public class UserForm extends Form {

    FormLayout formLayout ;
    TextField username ;
    PasswordField password ;
    Button saveButton ;

    public UserForm(MyUI myUI ) {

        formLayout = new FormLayout();
        username = new TextField();
        username.setCaption("enter username");
        password = new PasswordField("enter password");
        saveButton = new Button("save");

        User user = new User();

        saveButton.addClickListener(e -> {
            //book.setId(1);
            user.setUsername(username.getValue());
            user.setPassword(password.getValue());
            if (myUI.addUser(user) == -1) {
                System.out.println("refresh");
                refresh();
            }
            else {
//                Label label = new Label("successfully registration");
//                formLayout.removeAllComponents();
//                formLayout.addComponent(label);
//                setLayout(formLayout);
                myUI.goToScreen("Basic");
            }
        });

        formLayout.addComponents(username, password, saveButton);
        setLayout(formLayout);
    }

    private void refresh(){
            Label label = new Label("user already exists re-enter...");
            username.setValue("");
            password.setValue("");
            formLayout.removeAllComponents();
            if (saveButton == null)
            System.out.println("saveButton == null " );
            formLayout.addComponents(label,username, password, saveButton);
            setLayout(formLayout);

    }
}
