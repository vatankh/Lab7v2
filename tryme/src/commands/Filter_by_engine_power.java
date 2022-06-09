package commands;

import Control.Storage;
import utils.Parser;

import java.io.Serializable;

public class Filter_by_engine_power extends AbsCommand implements Serializable {
    String power;
    private static final long serialVersionUID = -4507489610617393544L;

    public Filter_by_engine_power(String power){
        this.power=power;
    }

    @Override
    public String work()  {
        messege = Parser.ParseDouble(power);
        if (messege.isEmpty()){
            return storage.filter_by_engine_power(Double.parseDouble(power));
        }else {
            return messege;
        }

    }
}
