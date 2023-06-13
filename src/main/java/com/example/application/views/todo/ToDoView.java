package com.example.application.views.todo;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("To Do")
@Route(value = "to-do")
@RouteAlias(value = "")
public class ToDoView extends VerticalLayout {

    public ToDoView() {
        Tab toDo = new Tab(new Span("To-Do"));
        Tab completed = new Tab(new Span("Completed"));

        Tabs tabs = new Tabs(toDo, completed);
    }

}
