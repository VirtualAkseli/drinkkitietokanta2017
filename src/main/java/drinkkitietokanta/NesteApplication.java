package drinkkitietokanta;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class NesteApplication {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:drinkkitietokanta.db");
        Raaka_Aine_Neste_Dao nesteet = new Raaka_Aine_Neste_Dao(database);
        DrinkkiDao drinkit = new DrinkkiDao(database);
        Raaka_Aine_Kiintea_Dao kiinteat = new Raaka_Aine_Kiintea_Dao(database);
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        
        Spark.get("/nesteet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nesteet", nesteet.findAll());
            map.put("kiinteat", kiinteat.findAll());
            return new ModelAndView(map, "nesteet");
        }, new ThymeleafTemplateEngine());

        Spark.post("/nesteet", (req, res) -> {
            Raaka_aine_neste neste = new Raaka_aine_neste(-1, req.queryParams("nimi"), Integer.parseInt(req.queryParams("maara")), Double.parseDouble(req.queryParams("hinta")), Double.parseDouble(req.queryParams("alkoholipitoisuus")));

            nesteet.saveOrUpdate(neste);
            
            res.redirect("/nesteet");
            return "";

        });

        Spark.get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("drinkit", drinkit.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        Spark.post("/drinkit", (req, res) -> {
            Drinkki drinkki = new Drinkki(-1, req.queryParams("nimi"), req.queryParams("lasityyppi"), req.queryParams("resepti"));

            drinkit.saveOrUpdate(drinkki);

            res.redirect("/drinkit");
            return "";
        });
        
        Spark.post("/index", (req, res) -> {
            Drinkki drinkki = new Drinkki(-1, req.queryParams("nimi"), req.queryParams("lasityyppi"), req.queryParams("resepti"));

            drinkit.saveOrUpdate(drinkki);

            res.redirect("/index");
            return "";
        });
        
        

        Spark.post("/kiinteat", (req, res) -> {
            Raaka_aine_kiintea kiintea = new Raaka_aine_kiintea(-1, req.queryParams("nimi"), Integer.parseInt(req.queryParams("paino")), Double.parseDouble(req.queryParams("hinta")));

            kiinteat.saveOrUpdate(kiintea);

            res.redirect("/nesteet");
            return "";

        });
    }
}
