package com.mediaexpert.controller;

import com.mediaexpert.entity.bean.Item;
import com.mediaexpert.entity.repository.ItemRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {
    private final ItemRepository repo;
    private final ItemEditor editor;
    private final Grid<Item> grid;
    final TextField filter;
    private final Button addNewBtn;

    public MainView(ItemRepository repo, ItemEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Item.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());

        grid.setColumns("id", "name", "description", "price");
//        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.setWidth("1000px");
        grid.setItems(repo.findAll());
//        grid.addColumn(Item::getName).setHeader("Name");
//        grid.addColumn(Item::getDescription).setHeader("Description");
//        grid.addColumn(Item::getPrice).setHeader("Price");

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
//        VerticalLayout verticalLayout = new VerticalLayout(grid);
        HorizontalLayout horizontalLayout = new HorizontalLayout(grid);
//        verticalLayout.add(grid);
        add(actions, editor, horizontalLayout);

//
        filter.setPlaceholder("Filter by name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listItems(e.getValue()));


        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listItems(e.getValue()));

        // Connect selected Item to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editItem(e.getValue());
        });

        // Instantiate and edit new Item the new button is clicked
        addNewBtn.addClickListener(e -> editor.editItem(new Item("", "", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItems(filter.getValue());
        });

        // Initialize listing
//        listItems(null);
    }

    private void listItems(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
        }
    }
}