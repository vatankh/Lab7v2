package commands;

import Control.Storage;

import java.io.Serializable;
import java.util.Stack;

public class Clear extends AbsCommand implements Serializable{
    private static final long serialVersionUID = -4507489610617393544L;
    @Override
    public String work() {
        storage.clear();
        return messege;
    }
}
