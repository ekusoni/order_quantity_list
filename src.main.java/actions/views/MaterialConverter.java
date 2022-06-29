package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Material;

/**
 *
 * 材料テーブルのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MaterialConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv MaterialViewのインスタンス
     * @return Materialのインスタンス
     */
    public static Material toModel(MaterialView mv) {
        return new Material(
                mv.getId(),
                MaterialMasterConverter.toModel(mv.getMaterialMaster()),
                CookingConverter.toModel(mv.getCooking()),
                mv.getAmount());

    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param mm Materialのインスタンス
     * @return MaterialViewのインスタンス
     */
    public static MaterialView toView(Material m) {

        if(m== null) {
            return null;
        }

        return new MaterialView(
                m.getId(),
                MaterialMasterConverter.toView(m.getMaterialMaster()),
                CookingConverter.toView(m.getCooking()),
                m.getAmount());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MaterialView> toViewList(List<Material> list){
        List<MaterialView> evs=new ArrayList<>();

        for(Material m: list) {
            evs.add(toView(m));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Material m,MaterialView mv) {
        m.setId(mv.getId());
        m.setMaterialMaster(MaterialMasterConverter.toModel(mv.getMaterialMaster()));
        m.setCooking(CookingConverter.toModel(mv.getCooking()));
        m.setAmount(mv.getAmount());
    }



}
