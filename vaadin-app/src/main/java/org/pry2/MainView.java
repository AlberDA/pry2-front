package org.pry2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout {

    //Llamada a la api
    // -----------------------HAY QUE CAMBIAR LOCALHOST POR EL NOMBRE QUE LE DEMOS AL CONTENEDOR DE DOCKER---------
    //--------------------IMPORTANTE--------------------------------------------
    //esta para local
    //private static final String api = "http://localhost:8081/%s";

    //esta para heroku
    private static final String api = "http://basedatosproyectos2.herokuapp.com/%s";

    //esta para docker , pero puede cambiar ojo
    //private static final String api = "http://api:8081/%s";
    //----------------------------------------------------------------------------------------
    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;

    // Funciones que conectan con la api

    /*private void inicializarUsuariosId(){

        String resource = String.format(api,"actualizarId");
        System.out.println(resource);
        try {
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(response.body());


        return;
    }*/


    private String recibirSaludo() {

        String resource = String.format(api, "saludo");
        System.out.println(resource);
        try {
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/java")
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(response.body());


        return response.body();
    }


    public MainView() {

        /*Actualizar ID de los usuarios*/
        //inicializarUsuariosId();

        /*Recibir gracias a la api a todos los usuarios*/
        String usuariosString = recibirSaludo();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Saludo> lista;
        Type userListType = new TypeToken<ArrayList<Saludo>>() {
        }.getType();
        lista = gson.fromJson(usuariosString, userListType);

        //Tab de los usuarios
        Tab tabusuarios = new Tab("Medicamentos");
        Div divusuarios = new Div();

        //Creacion del grid
        Grid<Saludo> gridusuarios = new Grid<>(Saludo.class);
        gridusuarios.setItems(lista);
        //gridusuarios.removeColumnByKey("id");
        gridusuarios.setColumns("nombre", "dosis");

        //Creacion de layouts principales
        VerticalLayout layoutusuariosv = new VerticalLayout();
        HorizontalLayout layoutusuariosh = new HorizontalLayout();

        //Estructura de layouts y divs
        layoutusuariosv.add(layoutusuariosh);
        divusuarios.add(gridusuarios);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tabusuarios, divusuarios);
        Tabs tabs = new Tabs(tabusuarios);
        tabs.setSizeFull();
        Div pages = new Div(divusuarios);
        pages.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        add(tabs, pages);
    }
}
