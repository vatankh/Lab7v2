package Client;
import LoginAndRegiester.AbsAuth;
import collection.Vehicle;
import commands.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CommandHandler {
    ClientNetworkUdp clientNetworkUdp;
    final List<String> commands= Arrays.asList("add","clear","show","remove_first","help","info","remove_greater","exit"
            ,"update","remove_by_id","insert_at","count_greater_than_engine_power","filter_greater_than_engine_power","filter_by_engine_power"
            ,"execute_script","show_all");
    InputHandler inputHandler;
    AbsAuth auth;

    public CommandHandler(ClientNetworkUdp clientNetworkUdp,Scanner scanner,AbsAuth auth){
        this.clientNetworkUdp = clientNetworkUdp;
        this.inputHandler=new InputHandler(scanner);
        this.auth=auth;

    }

    public  HandelObject handle(String command) throws Exception {

        if (isCommand(command)) {
            if (command.split(" ").length==1 ){
                String com =command.split(" ")[0];
                if (com.equals("update") ||com.equals("remove_by_id")||com.equals("insert_at")||com.equals("count_greater_than_engine_power")
                ||com.equals("filter_greater_than_engine_power")||com.equals("filter_by_engine_power")||com.equals("execute_script")){
                    return new HandelObject(false,true);
                }
            }
            HandelObject handelObject=new HandelObject(true,true);
            int len = command.split(" ").length;
            if (len == 1) {
                switch (command.trim()) {
                    case "clear":
                        Clear clear = new Clear();
                        clientNetworkUdp.sendobj(clear);
                        return handelObject;
                    case "add":
                        Vehicle vehicle = inputHandler.handleVechile();
                        Add add = new Add(vehicle);
                        clientNetworkUdp.sendobj(add);
                        return handelObject;
                    case "show":
                        Show show = new Show();
                        clientNetworkUdp.sendobj(show);
                        return handelObject;
                    case "show_all":
                        show_all showAll=new show_all();
                        clientNetworkUdp.sendobj(showAll);
                        return handelObject;
                    case "remove_first":
                        remove_first removeFirst = new remove_first();
                        clientNetworkUdp.sendobj(removeFirst);
                        return handelObject;
                    case "help":
                        Help help = new Help();
                        clientNetworkUdp.sendobj(help);
                        return handelObject;
                    case "info":
                        Info info = new Info();
                        clientNetworkUdp.sendobj(info);
                        return handelObject;
                    case "remove_greater":
                        vehicle = inputHandler.handleVechile();
                        Remove_greater remove_greater = new Remove_greater(vehicle);
                        clientNetworkUdp.sendobj(remove_greater);
                        return handelObject;
                    case "exit":
                        handelObject=new HandelObject(false,false);
                        Exit exit =new Exit(auth);
                        clientNetworkUdp.sendobj(exit);
                        return handelObject;
                }
            } else if (len == 2 || command.split(" ")[0].equals("execute_script")) {
                String[] splited = command.split(" ");
                String theCommand = splited[0];
                String theValue = splited[1];
                switch (theCommand) {
                    case "update":
                        System.out.println("here");
                        Vehicle vehicle = inputHandler.handleVechile();
                        Update update = new Update(vehicle, theValue);
                        clientNetworkUdp.sendobj(update);
                        return handelObject;
                    case "remove_by_id":
                        Remove_by_id remove_by_id = new Remove_by_id(theValue);
                        clientNetworkUdp.sendobj(remove_by_id);
                        return handelObject;
                    case "insert_at":
                        vehicle = inputHandler.handleVechile();
                        Insert_at insert_at = new Insert_at(theValue,vehicle);
                        clientNetworkUdp.sendobj(insert_at);
                        return handelObject;
                    case "count_greater_than_engine_power":
                        Count_greater_than_engine_power cgtep = new Count_greater_than_engine_power(theValue);
                        clientNetworkUdp.sendobj(cgtep);
                        return handelObject;
                    case "filter_greater_than_engine_power":
                        Filter_greater_than_engine_power filterGreater = new Filter_greater_than_engine_power(theValue);
                        clientNetworkUdp.sendobj(filterGreater);
                        return handelObject;
                    case "filter_by_engine_power":
                        Filter_by_engine_power filter = new Filter_by_engine_power(theValue);
                        clientNetworkUdp.sendobj(filter);
                        return handelObject;
                    case "execute_script":
                        try {
                            File file = new File(fix(command));
                            if (!file.exists()) {
                                System.out.println("file does not exist");
                                return new HandelObject(false,true);
                            } else {
                                if (!file.canRead()) {
                                    System.out.println("no permission for reading");
                                    return new HandelObject(false,true);
                                } else {
                                    InputStream in = new FileInputStream(file);
                                   handelObject =new HandelObject(true,true,in);
                                    return handelObject;
                                }
                            }
                        }catch (Exception e){
                            System.out.println("ex");
                            return  new HandelObject(false,true);
                        }
                }
            }
            return handelObject;
        }else {
            return new HandelObject(false,true);
        }
    }

    public boolean isCommand(String command){
        int len =command.split(" ").length;
        if (len ==1){
            return commands.contains(command.trim());
        }else if (len ==2){
            String com = command.split(" ")[0];
            return commands.contains(com);
        }
        return command.split(" ")[0].equals("execute_script");
    }

    public  String fix(String string){
        String[] strings = string.split(" ");
        StringBuilder value =new StringBuilder();
        for (int i=1;i<strings.length;i++){
            value.append(strings[i]);
            if (i <strings.length -1){
                value.append(" ");
            }
        }
        return value.toString();
    }
}
