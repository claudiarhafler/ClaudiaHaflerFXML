/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Workoutcontentmodel;
import java.io.IOException;
import javafx.fxml.Initializable;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudia
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
              
        Query query = manager.createNamedQuery("Workoutcontentmodel.findAll");
        List<Workoutcontentmodel> data = query.getResultList();
        
        for (Workoutcontentmodel w : data) {            
            System.out.println(w.getId());         
        }           
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private Button createButton;

    @FXML
    private Button readButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    void createWorkout(ActionEvent event) {
           Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Number of Reps:");
        int reps = input.nextInt();
        
        System.out.println("Enter Number of Sets:");
        int sets = input.nextInt();
        
        System.out.println("Enter Exercise");
        String exercise = input.next();
        
        // create a student instance
        Workoutcontentmodel workout = new Workoutcontentmodel();
        
        // set properties
        workout.setId(id);
        workout.setNumberofreps(reps);
        workout.setNumberofsets(sets);
        workout.setExercise(exercise);
        
        // save this student to database by calling Create operation        
        create(workout);

    }

    @FXML
    void deleteWorkout(ActionEvent event) {
         Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Workoutcontentmodel s = readById(id);
        System.out.println("we are deleting this workout: "+ s.toString());
        delete(s);


    }


    @FXML
    void readWorkout(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Workoutcontentmodel s = readById(id);
        System.out.println(s.toString());

    }

    @FXML
    void updateWorkout(ActionEvent event) {
                Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Number of Reps:");
        int reps = input.nextInt();
        
        System.out.println("Enter Number of Sets:");
        int sets = input.nextInt();
        
        System.out.println("Enter Exercise");
        String exercise = input.next();
        
        // create a student instance
        Workoutcontentmodel workout = new Workoutcontentmodel();
        
        // set properties
        workout.setId(id);
        workout.setNumberofreps(reps);
        workout.setNumberofsets(sets);
        workout.setExercise(exercise);
        System.out.println("Workout was updated");

        // save this student to database by calling Create operation        
        update(workout);


    }
    
     // Database manager
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // loading data from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("ClaudiaHaflerFXPU").createEntityManager();
    }    
    
    /*
    Implementing CRUD operations
 */
    
    // Create operation
    public void create(Workoutcontentmodel content) {
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (content.getId() != null) {
                
                // create student
                manager.persist(content);
                
                // end transaction
                manager.getTransaction().commit();
                
                System.out.println(content.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Read Operations
    public List<Workoutcontentmodel> readAll(){
        Query query = manager.createNamedQuery("model.findAll");
        List<Workoutcontentmodel> model = query.getResultList();

        for (Workoutcontentmodel w : model) {
            System.out.println(w.getId());
        }
        
        return model;
    }
    
    public Workoutcontentmodel readById(int id){
        Query query = manager.createNamedQuery("Workoutcontentmodel.findById");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query
        Workoutcontentmodel model = (Workoutcontentmodel) query.getSingleResult();
        if (model != null) {
            System.out.println(model.getId() + " " + model.getNumberofreps() + " " + model.getNumberofsets()
            + " " + model.getExercise());
        }
        
        return model;
    }        
    
    public List<Workoutcontentmodel> readByName(String name){
        Query query = manager.createNamedQuery("Workoutcontentmodel.findByName");
        
        // setting query parameter
        query.setParameter("name", name);
        
        // execute query
        List<Workoutcontentmodel> model =  query.getResultList();
        for (Workoutcontentmodel w: model) {
            System.out.println(w.getId() + " " + w.getNumberofreps() + " " + w.getNumberofsets()
            + " " + w.getExercise());
        }
        
        return model;
    }        
    
    public List<Workoutcontentmodel> readByexerciseAndid(int id, String exercise){
        Query query = manager.createNamedQuery("Workoutcontentmodel.findByExerciseAndID");
        
        // setting query parameter
        query.setParameter("exercise", exercise);
        query.setParameter("id", id);
        
        
        // execute query
        List<Workoutcontentmodel> model =  query.getResultList();
        for (Workoutcontentmodel w: model) {
            System.out.println(w.getId());
        }
        
        return model;
    }        
    
    
    // Update operation
    public void update(Workoutcontentmodel model) {
       try {

            Workoutcontentmodel existingWorkout = manager.find(Workoutcontentmodel.class, model.getId());

            if (existingWorkout != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingWorkout.setNumberofreps(model.getNumberofreps());
                existingWorkout.setNumberofsets(model.getNumberofsets());
                existingWorkout.setExercise(model.getExercise());
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete operation
    public void delete(Workoutcontentmodel model) {
        try {
            Workoutcontentmodel existingmodel = manager.find(Workoutcontentmodel.class, model.getId());

            // sanity check
            if (existingmodel != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingmodel);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    void createWorkoutcontentmodel(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Exercise:");
        String exercise = input.next();
        
        System.out.println("Enter Number of Sets:");
        double numOfSets = input.nextDouble();
        
        System.out.println("Enter Number of Reps");
        double numOfReps = input.nextDouble();
        
        // create a student instance
        Workoutcontentmodel model = new Workoutcontentmodel();
        
        // set properties
        model.setId(id);
        model.setExercise(exercise);
        
        
        // save this student to database by calling Create operation        
        create(model);
    }

    @FXML
    void deleteWorkoutcontentmodel(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Workoutcontentmodel w = readById(id);
        System.out.println("We are deleting this workout content: "+ w.toString());
        delete(w);

    }
    

    @FXML
    void readByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Workoutcontentmodel w = readById(id);
        System.out.println(w.toString());

    }

    @FXML
    void readByExercise(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();
        
        List<Workoutcontentmodel> w = readByName(name);
        System.out.println(w.toString());
        

    }  
        @FXML
    void idAndWorkout(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        System.out.println("Enter Excercise:");
        String excerise = input.next();
        readByexerciseAndid(id, excerise);

    }
        @FXML
    void getRepsAndSets(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter reps:");
        int reps = input.nextInt();
        System.out.println("Enter sets:");
        int sets = input.nextInt();
        readByRepsAndSets(reps, sets);

    }
    public List<Workoutcontentmodel> readByRepsAndSets(int reps, int sets){
        Query query = manager.createNamedQuery("Workoutcontentmodel.findBySetsAndReps");
        
        // setting query parameter
        query.setParameter("numberofreps", reps);
        query.setParameter("numberofsets", sets);
        
        
        // execute query
        List<Workoutcontentmodel> model =  query.getResultList();
        for (Workoutcontentmodel w: model) {
            System.out.println(w.getId());
        }
        
        return model;
    }   
    
    @FXML 
    private TextField textboxName;
    
      @FXML
    private TableView<Workoutcontentmodel> Table;

    @FXML
    private TableColumn<Workoutcontentmodel, Integer> ID;

    @FXML
    private TableColumn<Workoutcontentmodel, String> Exercise;

    @FXML
    private TableColumn<Workoutcontentmodel, Integer> Sets;

    @FXML
    private TableColumn<Workoutcontentmodel, Integer> Reps;

    @FXML
    private Button Search;

    @FXML
    void Clicked(ActionEvent event) {

    }
    
    private ObservableList<Workoutcontentmodel> workoutData;

    public void setTableData(List<Workoutcontentmodel> workoutList){
        workoutData = FXCollextions.observableArrayList();
        
        workoutList.forEach(s -> {
            workoutData.add(s);
        });
        
        Table.setItems(workoutData);
        Table.refresh();
    }
    
     @FXML
    void searchByNameAction(ActionEvent event) {
        System.out.println("clicked");

        // getting the name from input box        
        String name = textboxName.getText();

        // calling a db read operaiton, readByName
        List<Workoutcontentmodel> workouts = readByName(name);

        if (workouts == null || workouts.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Content Found");// line 2
            alert.setHeaderText("Could Not Find");// line 3
            alert.setContentText("No Workout");// line 4
            alert.showAndWait(); // line 5
        } else {

            // setting table data
            setTableData(workouts);
        }

    }
    
     @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        System.out.println("clicked");

        // getting the name from input box        
        String name = textboxName.getText();

        // calling a db read operaiton, readByName
        List<Workoutcontentmodel> workouts = readByNameAdvanced(name);

        // setting table data
        //setTableData(students);
        // add an alert
        if (workouts == null || workouts.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Searched Content Not Found");// line 2
            alert.setHeaderText("Could Not Find");// line 3
            alert.setContentText("No Workout");// line 4
            alert.showAndWait(); // line 5
        } else {
            // setting table data
            setTableData(workouts);
        }

    }
    
    

    private List<Workoutcontentmodel> readByNameAdvanced(String name) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

     @FXML
    void actionShowDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");

        
        Workoutcontentmodel selectedWorkout = Table.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));

        Parent detailedModelView = loader.load();

        Scene tableViewScene = new Scene(detailedModelView);

        DetailModelController detailedControlled = loader.getController();


        detailedControlled.initData(selectedWorkout);

        // create a new state
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();

    }

    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("clicked");


        Workoutcontentmodel selectedWorkout = Table.getSelectionModel().getSelectedItem();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailModelView.fxml"));


        Parent detailedModelView = loader.load();


        Scene tableViewScene = new Scene(detailedModelView);


        DetailModelController detailedControlled = loader.getController();


        detailedControlled.initData(selectedWorkout);

        // pass current scene to return
        Scene currentScene = ((Node) event.getSource()).getScene();
        detailedControlled.setPreviousScene(currentScene);

        //This line gets the Stage information
        Stage stage = (Stage) currentScene.getWindow();

        stage.setScene(tableViewScene);
        stage.show();
    }

    
    @FXML
    void showDetails(ActionEvent event) {

    }
    
    @FXML
    void backButtonAction(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
 
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }

    Workoutcontentmodel selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }

    private static class backButton {

        private static void setDisable(boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public backButton() {
        }
    }

}

                
     

    
