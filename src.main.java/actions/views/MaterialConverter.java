package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Material;

/**
 * 材料データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MaterialConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv MaterialViewのインスタンス
     * @return Materialのインスタンス
     */
    public static Material toModel(MaterialView mv) {
        return new Material(
                mv.getId(),
                mv.getName(),
                mv.getUnit());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Materialのインスタンス
     * @return MaterialViewのインスタンス
     */
    public static MaterialView toView(Material m) {

        if (m == null) {
            return null;
        }

        return new MaterialView(
                m.getId(),
                m.getName(),
                m.getUnit());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param List DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MaterialView> toViewList(List<Material> list) {
        List<MaterialView> mvs = new ArrayList<>();

        for (Material m : list) {
            mvs.add(toView(m));
        }

        return mvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Material m,MaterialView mv) {
        m.setId(mv.getId());
        m.setName(mv.getName());
        m.setUnit(mv.getUnit());
    }

}
