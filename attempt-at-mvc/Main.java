public class Main {
    public static void main(String[] args) {

        // Assemble all the pieces of the MVC
        Model m = new Model("Anushka", "Hebbar");
        View v = new View("Bug Tracking Application");
        Controller c = new Controller(m, v);
        
        c.initController();
    }
}