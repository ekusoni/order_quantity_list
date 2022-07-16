package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Menu;

/**
 * メニューデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MenuConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv MenuViewのインスタンス
     * @return Menuのインスタンス
     */
    public static Menu toModel(MenuView mv) {
        return new Menu(
                mv.getId(),
                mv.getStartDate(),
                mv.getEndDate(),
                mv.getTopDisplay());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Menuのインスタンス
     * @return MenuViewのインスタンス
     */
    public static MenuView toView(Menu m) {

        if(m==null) {
            return null;
        }

        return new MenuView(
                m.getId(),
                m.getStartDate(),
                m.getEndDate(),
                m.getTopDisplay());
    }

    /**
     * DTOモデルのリストViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return VIewモデルのリスト
     */
    public static List<MenuView> toViewList(List<Menu> list){
        List<MenuView> mvs=new ArrayList<>();

        for(Menu m:list) {
            mvs.add(toView(m));
        }

        return mvs;
    }


    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドに変換する
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Menu m,MenuView mv) {
        m.setId(mv.getId());
        m.setStartDate(mv.getStartDate());
        m.setEndDate(mv.getEndDate());
        m.setTopDisplay(mv.getTopDisplay());
    }

}
