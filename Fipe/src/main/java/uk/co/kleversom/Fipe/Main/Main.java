package uk.co.kleversom.Fipe.Main;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import uk.co.kleversom.Fipe.Model.Dados;
import uk.co.kleversom.Fipe.Model.Models;
import uk.co.kleversom.Fipe.Model.Vehicles;
import uk.co.kleversom.Fipe.Service.ConsumeAPI;
import uk.co.kleversom.Fipe.Service.ConverData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    Scanner read = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConverData convert = new ConverData();
    public void showMenu() {
        var menu = """
                *** Options ***
                1 - Car
                2 - Motorcycle
                3 - Truck
                4 - Exit
                               
                Type one option: 
                 """;
        String adress = null;

        System.out.println(menu);
        int option = 0;
        option = Integer.parseInt(read.nextLine());
        switch (option){

            case 1:
                adress = URL_BASE + "carros/marcas";
                break;
            case 2:
                adress = URL_BASE + "motos/marcas";
                break;
            case 3:
                adress = URL_BASE + "caminhoes/marcas";
                break;
            case 4:
                System.out.println("System closed!");
                break;
            default:
                System.out.println("Invalid option");
            }


        var json = consumeAPI.getData(adress);
        var brands = convert.getList(json, Dados.class);
        brands.stream()
                .sorted(Comparator.comparing(Dados::cod))
                .forEach(System.out::println);


        System.out.println("Type de brands code you want to search: ");
        var codBrand = read.nextLine();

        adress = adress + "/" + codBrand + "/modelos";
        json = consumeAPI.getData(adress);
        var modelList = convert.getData(json, Models.class);

        System.out.println("Models from this brand: ");
        modelList.models().stream()
                .sorted(Comparator.comparing(Dados::cod))
                .forEach(System.out::println);

        System.out.println("Type de vehicle you want to search: ");
        var vehicle = read.nextLine();

        List<Dados> modelsSorted = modelList.models().stream()
                .filter(m -> m.name().toLowerCase().contains(vehicle.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nVehicle sorted: ");
        modelsSorted.forEach(System.out::println);

        System.out.println("Type the code of the vehicle you want to search: ");
        var codVehicle = read.nextLine();

        adress = adress + "/" + codVehicle + "/anos";
        json = consumeAPI.getData(adress);
        List<Dados> years = convert.getList(json, Dados.class);
        List<Vehicles> vehicles = new ArrayList<>();

        for (int i = 0; i<years.size(); i++){
            var adressYear = adress + "/" + years.get(i).cod();
            json = consumeAPI.getData(adressYear);
            Vehicles vehicles1 = convert.getData(json, Vehicles.class);
            vehicles.add(vehicles1);
        }
        System.out.println("\nAll vehicles sorted: ");
        vehicles.forEach(System.out::println);

    }


}
