package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.CookingSlave;

/**
 *
 * 料理テーブルのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class CookingSlaveConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param csv CookingSlaveViewのインスタンス
     * @return CookingSlaveのインスタンス
     */
    public static CookingSlave toModel(CookingSlaveView csv) {
        return new CookingSlave(
                csv.getId(),
                MenuConverter.toModel(csv.getMenu()),
                CookingConverter.toModel(csv.getCooking()),
                csv.getAmount(),
                csv.getTimeZone());

    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param cs CookingSlaveのインスタンス
     * @return CookingSlaveViewのインスタンス
     */
    public static CookingSlaveView toView(CookingSlave cs) {

        if(cs==null) {
            return null;
        }

        return new CookingSlaveView(
                cs.getId(),
                MenuConverter.toView(cs.getMenu()),
                CookingConverter.toView(cs.getCooking()),
                cs.getAmount(),
                cs.getTimeZone());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CookingSlaveView> toViewList(List<CookingSlave> list){
        List<CookingSlaveView> csvs = new ArrayList<>();

        for(CookingSlave cs: list) {
            csvs.add(toView(cs));
        }

        return csvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param cs DTOモデル(コピー先)
     * @param csv Viewモデル(コピー元)
     */
    public static void copyViewToModel(CookingSlave cs,CookingSlaveView csv) {
        cs.setId(csv.getId());
        cs.setMenu(MenuConverter.toModel(csv.getMenu()));
        cs.setCooking(CookingConverter.toModel(csv.getCooking()));
        cs.setAmount(csv.getAmount());
        cs.setTimeZone(csv.getTimeZone());
    }

}
