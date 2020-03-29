package ru.pimalex1978.springstorageapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pimalex1978.springstorageapp.repo.BoxRepo;
import ru.pimalex1978.springstorageapp.repo.ItemRepo;
import ru.pimalex1978.springstorageapp.entity.Box;
import ru.pimalex1978.springstorageapp.entity.Item;

/**
 * Используя страницу браузера, обращаемся по этому адресу http://localhost:8080/
 * и видим три поля для вывода информации хранящейся в БД
 */

@Controller
public class MainController {
    @Autowired
    private BoxRepo boxRepo;

    @Autowired
    private ItemRepo itemRepo;


    //Вывел на экран список из трёх запросов к Хранилищу
    @ResponseBody
    @RequestMapping("/")
    public String homeStorage() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/showAllStorage'>Показать всё Хранилище</a></li>";
        html += " <li><a href='/showAllBoxes'>Показать все Box в Хранилище</a></li>";
        html += " <li><a href='/showAllItems'>Показать все Item в Хранилище</a></li>";
        html += "</ul>";

        return html;
    }

    //Вывел список всех сущностей хранящихся в Хранилище Storage
    @ResponseBody
    @RequestMapping("/showAllStorage")
    public String showAllStorage() {
        Iterable<Box> boxes = boxRepo.findAll();
        Iterable<Item> items = itemRepo.findAll();

        //Использовал StringBuilder вместо String
        //String html = "";
        StringBuilder html = new StringBuilder();
        for (Box box : boxes) {
            //html += box + "<br>";
            html.append(box).append("<br>");
        }
        for (Item item : items) {
            //html += item + "<br>";
            html.append(item).append("<br>");
        }
        //return html;
        return html.toString();
    }

    //Вывел список всех Box хранящихся в Хранилище Storage
    @ResponseBody
    @RequestMapping("/showAllBoxes")
    public String showAllBoxes() {
        Iterable<Box> boxes = boxRepo.findAll();

        StringBuilder html = new StringBuilder();
        for (Box box : boxes) {
            html.append(box).append("<br>");
        }

        return html.toString();
    }

    //Вывел список всех Item хранящихся в Хранилище Storage
    @ResponseBody
    @RequestMapping("/showAllItems")
    public String showAllItems() {
        Iterable<Item> items = itemRepo.findAll();

        StringBuilder html = new StringBuilder();

        for (Item item : items) {
            html.append(item).append("<br>");
        }
        return html.toString();
    }

}
