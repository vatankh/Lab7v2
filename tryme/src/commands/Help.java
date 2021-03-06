package commands;

import java.io.Serializable;

public class Help extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        return  "help : output help for available commands\n" +
                "info : output information about the collection (type, initialization date, number of items, etc.) to the standard output stream.)\n" +
                "show : output to the standard output stream all the elements of the collection in a string representation\n" +
                "add {element} : add a new item to the collection\n" +
                "update id {element} : update the value of a collection item whose id is equal to the specified one\n" +
                "remove_by_id id : delete an item from the collection by its id\n" +
                "clear : clear the collection\n" +
                "save : clear the collection\n" +
                "execute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.\n" +
                "exit : terminate the program (without saving to a file)\n" +
                "insert_at index {element} : add a new element to the specified position\n" +
                "remove_first : delete the first item from the collection\n" +
                "remove_greater {element} : remove all items from the collection that exceed the specified\n" +
                "count_greater_than_engine_power enginePower : print the number of elements whose engine Power field value is greater than the specified one\n" +
                "filter_by_engine_power enginePower : output elements whose engine Power field value is equal to the specified\n" +
                "filter_greater_than_engine_power enginePower : output elements whose engine Power field value is greater than the specified one";

    }
}
