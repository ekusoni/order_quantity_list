package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Cooking;

/**
 * 料理データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 *
 */
public class CookingConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param cv CookingViewのインスタンス
     * @return Cookingのインスタント
     */
    public static Cooking toModel(CookingView cv) {
        return new Cooking(
                cv.getId(),
                cv.getName());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンス
     * @param c Cookingのインスタンス
     * @return Cooking Viewのインスタンス
     */
    public static CookingView toView(Cooking c) {

        if(c==null) {
            return null;
        }

        return new CookingView(
                c.getId(),
                c.getName());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CookingView> toViewList(List<Cooking> list){
        List<CookingView> evs=new ArrayList<>();

        for(Cooking c : list) {
            evs.add(toView(c));
        }


        return evs;
    }


    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param c DTOモデル(コピー先)
     * @param cv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Cooking c,CookingView cv) {
        c.setId(cv.getId());
        c.setName(cv.getName());
    }

}
