package drinkkitietokanta;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class NesteApplication {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:drinkkitietokanta.db");
        Raaka_Aine_Neste_Dao nesteet = new Raaka_Aine_Neste_Dao(database);
        DrinkkiDao drinkit = new DrinkkiDao(database);
        
        DrinkkiRaaka_Aine_Neste_Dao liitokset = new DrinkkiRaaka_Aine_Neste_Dao(database);

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Spark.get("/nesteet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nesteet", nesteet.findAll(liitokset));
            
            return new ModelAndView(map, "nesteet");
        }, new ThymeleafTemplateEngine());

        Spark.post("/nesteet", (req, res) -> {
            Raaka_aine_neste neste = new Raaka_aine_neste(-1, req.queryParams("nimi"), Integer.parseInt(req.queryParams("maara")), Double.parseDouble(req.queryParams("hinta")), Double.parseDouble(req.queryParams("alkoholipitoisuus")));

            nesteet.saveOrUpdate(neste);

            res.redirect("/nesteet");
            return "";

        });

        Spark.get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Drinkki drinkki = drinkit.findOne(Integer.parseInt(req.params("id")));
            drinkki.laske(liitokset);
            map.put("drinkki", drinkki);
            map.put("raakaaineet", liitokset.listaaDrinkinAineet(drinkki.drinkki_id));
            

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

        
        Spark.get("/index/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Drinkki drinkki = drinkit.findOne(Integer.parseInt(req.params("id")));
            drinkki.laske(liitokset);
            map.put("drinkki", drinkki);
            map.put("raakaaineet", liitokset.listaaDrinkinAineet(drinkki.drinkki_id));
            

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());
       

        Spark.get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nesteet", nesteet.findAll(liitokset));
            map.put("drinkit", drinkit.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        Spark.post("/uusidrinkki", (req, res) -> {
            Drinkki drinkki = new Drinkki(-1, req.queryParams("nimi"), req.queryParams("lasityyppi"), req.queryParams("resepti"));

            drinkit.saveOrUpdate(drinkki);

            res.redirect("/drinkit");
            return "";
        });

        Spark.post("/uusiraakaaine", (req, res) -> {

            liitokset.saveOrUpdate(
                    drinkit.findByName(req.queryParams("nimi")).getDrinkki_id(),
                    nesteet.findByName(req.queryParams("neste")).raaka_aine_neste_id,
                    Integer.parseInt(req.queryParams("maara")));
            //liitokset.saveOrUpdate(req.queryParams("drinkki.drinkki_id"), req.queryParams("neste.raaka_aine_neste_id"), Integer.parseInt(req.queryParams("sisalto")));
            res.redirect("/drinkit");
            return "";
        });

        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("index", drinkit.findAll());
            map.put("drinkit", drinkit.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/index", (req, res) -> {
            Drinkki drinkki = new Drinkki(-1, req.queryParams("nimi"), req.queryParams("lasityyppi"), req.queryParams("resepti"));

            drinkit.saveOrUpdate(drinkki);

            res.redirect("/index");
            return "";
        });

        
    }
}
