package actions.views;


import java.util.ArrayList;
import java.util.List;

import models.MaterialMaster;

public class MaterialMasterConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param  mmv MaterialMasterViewのインスタンス
     * @return MaterialMasterのインスタンス
     */
    public static MaterialMaster toModel(MaterialMasterView mmv) {
        return new MaterialMaster(
                mmv.getId(),
                mmv.getName(),
                mmv.getUnit());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param mm MaterialMasterのインスタンス
     * @return MaterialMasterViewのインスタンス
     */
    public static MaterialMasterView toView(MaterialMaster mm) {

        if(mm==null) {
            return null;
        }

        return new MaterialMasterView(
                mm.getId(),
                mm.getName(),
                mm.getUnit());

    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param List DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MaterialMasterView> toViewList(List<MaterialMaster> list){
        List<MaterialMasterView> mmvs=new ArrayList<>();

        for(MaterialMaster mm : list) {
            mmvs.add(toView(mm));
        }

        return mmvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(MaterialMaster mm,MaterialMasterView mmv) {
        mm.setId(mmv.getId());
        mm.setName(mmv.getName());
        mm.setUnit(mmv.getUnit());
    }

}
