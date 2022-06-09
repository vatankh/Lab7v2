package commands;

import Control.Storage;
import Control.VerifyIdInStorage;

import java.io.Serializable;

public class Remove_by_id extends AbsCommand implements Serializable {
    String id;
    private static final long serialVersionUID = -4507489610617393544L;

    public Remove_by_id(String id){
        this.id=id;
    }


    @Override
    public String work() throws Exception {
        long[] index = VerifyIdInStorage.verify(storage,id);
        if (index[0] ==-1){
            return "there is no vehicle with this id";
        }else {
            storage.remove_by_id(Integer.parseInt(id),(int) index[0]);
            return messege;
        }
    }
}
