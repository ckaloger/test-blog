package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import test.MyUI;
import data.Article;

import java.util.List;

public class ListArticles extends Form{
    FormLayout formLayout ;
    Button createNewArticle ;
    Button logOut;
    MyUI myUI;
    List<Article> list;
    Article currentArticle;

    public ListArticles(MyUI myUI){
        this.myUI = myUI;
        formLayout = new FormLayout();
        HorizontalLayout hlayout = new HorizontalLayout();
        createNewArticle = new Button("new Article");
        createNewArticle.addClickListener(e -> {
            myUI.goToScreen("createNewArticle");
            //formLayout.removeAllComponents();
            //formLayout.addComponent(label);
        });
        formLayout.addComponent(createNewArticle);

        logOut = new Button("log out");
        logOut.addClickListener(e -> {
            myUI.getSession().setAttribute("currentUser", "");
            myUI.goToScreen("Basic");
        });
        hlayout.addComponents(createNewArticle,logOut);
        formLayout.addComponent(hlayout);
        refresh();
       // listArticles();

        //formLayout.addComponent(logOut);
        setLayout(formLayout);
    }
    private void listArticles(){
         Article a;
         //final Article finalarticle;

        for (int i=0; i<list.size(); i++){

            a = list.get(i);

            HorizontalLayout hl = new HorizontalLayout();
            Label label = new Label(a.getTitle());
            TextArea context = new TextArea();
            context.setValue(a.getContext());
            context.setReadOnly(true);
            Button edit = new Button("edit");
            Button delete = new Button("delete");
            delete.setData(a.getId());
            if (myUI.isOwner((String)myUI.getSession().getAttribute("currentUser"),a.getId()) == 1) {
                edit.setEnabled(true);
                delete.setEnabled(true);
                edit.addClickListener(e -> {
                    context.setReadOnly(false);
                });
                delete.addClickListener(new Button.ClickListener(){
                    public void buttonClick(Button.ClickEvent event) {
                        Integer iid = (Integer)event.getButton().getData();
                        myUI.remove(iid);
                        myUI.goToScreen("ListArticles");
                    }
                });
                //currentArticle = a;
            }
            else {
                edit.setEnabled(false);
                delete.setEnabled(false);
            }

//            edit.addClickListener(e -> {
//                ;
//            });
            hl.addComponents(edit,delete);
            formLayout.addComponents(label,context,hl);

        }
    }

    public void refresh(){
        list = myUI.listAllArticles();
        //formLayout.removeAllComponents();;
        listArticles();
        //formLayout.addComponent(logOut);
    }

    private void deleteArticle( ){
        myUI.removeArticle(currentArticle);
    }
}


