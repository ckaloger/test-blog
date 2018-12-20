package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickListener;
import test.MyUI;
import data.User;
import data.Article;

public class CreateNewArticleForm extends Form {

    FormLayout formLayout;
    TextField title;
    TextArea context;
    Button saveButton;

    public CreateNewArticleForm(MyUI myUI) {

        formLayout = new FormLayout();
        title = new TextField();
        title.setCaption("enter title");
        context = new TextArea("context...");
        saveButton = new Button("save");

        Article article = new Article();

        saveButton.addClickListener(e -> {
            //book.setId(1);
            article.setTitle(title.getValue());
            article.setContext(context.getValue());
            article.setOwnerId(myUI.getUserId((String) myUI.getSession().getAttribute("currentUser")));
            myUI.addArticle(article);
            myUI.goToScreen("ListArticles");
        });

        formLayout.addComponents(title, context, saveButton);
        setLayout(formLayout);
    }
}
