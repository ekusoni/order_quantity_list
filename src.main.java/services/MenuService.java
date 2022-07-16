package services;

import java.util.List;

import actions.views.MenuConverter;
import actions.views.MenuView;
import constants.JpaConst;
import models.Menu;

/**
 * メニューテーブルの操作に関わる処理を行うクラス
 */
public class MenuService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MenuViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<MenuView> getPerPage(int page){
        List<Menu> menus= em.createNamedQuery(JpaConst.Q_MEN_GET_ALL,Menu.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE*(page -1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return MenuConverter.toViewList(menus);
    }

    /**
     * 全てのメニューデータを取得し、MenuViewのリストで返却する
     * return 表示するデータのリスト
     */
    public List<MenuView> getPage(){
        List<Menu> menus=em.createNamedQuery(JpaConst.Q_MEN_GET_ALL,Menu.class)
                .getResultList();

        return MenuConverter.toViewList(menus);
    }

    /**
     * メニューテーブルのデータの件数を取得し、返却する
     * @return メニューテーブルのデータの件数
     */
    public long countAll() {
        long menCount=(long) em.createNamedQuery(JpaConst.Q_MEN_COUNT,Long.class)
                .getSingleResult();

        return menCount;
    }

    /**
     * idを条件に取得したデータをMenuViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MenuView findOne(int id) {
        return MenuConverter.toView(findOneInternal(id));
    }

    /**
     * メニューテーブルでidが一番大きいデータを取得しMenuViewのリストで返却する
     * @return 取得データのインスタンス
     */
    public MenuView findMaxId(){
        Menu m=(Menu)em.createNamedQuery(JpaConst.Q_MEN_GET_BY_MAXID,Menu.class)
                .getSingleResult();

        return MenuConverter.toView(m);
    }

    public long countByTopDisplay(String topDisplay) {

        long topDisplay_count=(long) em.createNamedQuery(JpaConst.Q_MEN_COUNT_RESISTERED_BY_TOPDISPLAY,Long.class)
                .setParameter(JpaConst.JPQL_PARM_DISPLAY,topDisplay)
                .getSingleResult();

        return topDisplay_count;
    }


    /**
     * メニューデータを一件登録する
     * @param mv メニューデータ
     */
    public void create(MenuView mv){
        em.getTransaction().begin();
        em.persist(MenuConverter.toModel(mv));
        em.getTransaction().commit();



    }

    /**
     * 画面から入力されたメニューの登録内容を元に、メニューデータを更新する
     * @param mv メニューの更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public String update(MenuView mv){
        String error=null;

        if(error==null) {
            updateInternal(mv);

        }
        //バリデーションで発生したエラーを返却
        return error;

    }



    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Menu findOneInternal(int id) {
        return em.find(Menu.class, id);
    }



    /**
     * メニューデータを更新する
     * @param mv メニューデータ
     */
    private void updateInternal(MenuView mv) {
        em.getTransaction().begin();
        Menu m=findOneInternal(mv.getId());
        MenuConverter.copyViewToModel(m, mv);
        em.getTransaction().commit();

    }





}
