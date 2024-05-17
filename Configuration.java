import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javafx.*;

public class Configuration implements Serializable {

    private Map<String, Configuration> configurations;
    private String name;
    private String compilerPath;
    private String compilerParameters;
    private String executionMethod;

    public Configuration() {
        configurations = new HashMap<>();
    }
    public void createConfigurationWithoutParameter(String ConfigName, String CompilerPath, String ExecutionMethod)throws IllegalArgumentException{

        if (configurations.containsKey(ConfigName.toLowerCase())) {
            throw new IllegalArgumentException("Configuration with name '" + ConfigName.toLowerCase() + "' already exists.");
        }
        Configuration config = new Configuration();
        config.setName(ConfigName);
        config.setCompilerPath(CompilerPath);
        config.setExecutionMethod(ExecutionMethod);
        configurations.put(ConfigName.toLowerCase(), config);

    }
    public void createConfigurationWithParameter(String ConfigName, String CompilerPath, String CompilerParameters, String ExecutionMethod)throws IllegalArgumentException {
        if (configurations.containsKey(ConfigName.toLowerCase())) {
            throw new IllegalArgumentException("Configuration with name '" + ConfigName.toLowerCase() + "' already exists.");
        }
        Configuration config = new Configuration();
        config.setName(ConfigName);
        config.setCompilerPath(CompilerPath);
        config.setCompilerParameters(CompilerParameters);
        config.setExecutionMethod(ExecutionMethod);
        configurations.put(ConfigName.toLowerCase(), config);
    }

    public Map<String, Configuration> getConfigurations() {
        return configurations;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getCompilerPath() {
        return compilerPath;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }

    public String getCompilerParameters() {
        return compilerParameters;
    }

    public void setCompilerParameters(String compilerParameters) {
        this.compilerParameters = compilerParameters.toLowerCase();
    }

    public String getExecutionMethod() {
        return executionMethod;
    }

    public void setExecutionMethod(String executionMethod) {
        this.executionMethod = executionMethod;
    }
    public void editConfiguration(String ConfigName, int choice)throws IllegalArgumentException {
        Configuration config = configurations.get(ConfigName.toLowerCase());
        if (config == null) {
            throw new IllegalArgumentException("Configuration with name '" + ConfigName.toLowerCase() + "' not found.");
        }
        Scanner scan = new Scanner(System.in);
        switch (choice) {
            case 1 -> {
                System.out.println("Change the name of the configuration");
                config.name = scan.nextLine().toLowerCase();
            }
            case 2 -> {
                System.out.println("Change the path of the configuration");
                config.compilerPath = scan.nextLine();
            }
            case 3 -> {
                System.out.println("Change the parameters of the configuration");
                config.compilerParameters = scan.nextLine();
            }
            case 4 -> {
                System.out.println("Change the execution method of the configuration");
                config.executionMethod = scan.nextLine();
            }
            default -> throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }
    public void deleteConfiguration(String ConfigName) throws IllegalArgumentException {
        Configuration removedConfig = configurations.get(ConfigName.toLowerCase());
        if (removedConfig == null) {
            throw new IllegalArgumentException("Configuration with name '" + ConfigName.toLowerCase() + "' not found.");
        }
        configurations.remove(ConfigName.toLowerCase());
        System.out.println("Configuration removed: "+ ConfigName);
    }

    public void printConfiguration(String ConfigName) throws IllegalArgumentException{
        Configuration config = configurations.get(ConfigName.toLowerCase());
        if(config != null){
            System.out.println("Configuration name: " +config.getName());
            System.out.println("Compiler Path: "+ config.getCompilerPath());
            if(config.getCompilerParameters()!= null){
                System.out.println("Compiler parameters: "+ config.getCompilerParameters());
            }
            System.out.println("Execution method: " + config.getExecutionMethod());
        }
        else{
            throw new IllegalArgumentException("Configuration with name '" + ConfigName + "' not found.");
        }

    }

}